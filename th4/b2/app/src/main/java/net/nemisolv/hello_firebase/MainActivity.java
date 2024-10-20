package net.nemisolv.hello_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import net.nemisolv.hello_firebase.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<Student, StudentViewHolder> adapter;

    private FirebaseFirestore db;
    private CollectionReference studentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();
        initFirebase();
        loadStudents();

        binding.btnAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentFormActivity.class);
            startActivity(intent);
        });
    }

    // Initialize RecyclerView setup
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Initialize Firestore reference
    private void initFirebase() {
        db = FirebaseFirestore.getInstance();
        studentRef = db.collection("sinhvien");
    }

    // Load students using FirestoreRecyclerAdapter
    private void loadStudents() {
        FirestoreRecyclerOptions<Student> options = new FirestoreRecyclerOptions.Builder<Student>()
                .setQuery(studentRef, Student.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Student, StudentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StudentViewHolder holder, int position, @NonNull Student model) {
                holder.bind(model);

                // Get the document ID for the student
                String studentId = getSnapshots().getSnapshot(position).getId();

                holder.btnEdit.setOnClickListener(v -> {
                    // Handle edit
                    Intent intent = new Intent(MainActivity.this, StudentFormActivity.class);
                    intent.putExtra("studentId", studentId);
                    startActivity(intent);
                });

                holder.btnDelete.setOnClickListener(v -> {
                    // Handle delete
                    studentRef.document(studentId).delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(MainActivity.this, "Sinh viên đã được xóa", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Xóa sinh viên thất bại", Toast.LENGTH_SHORT).show());
                });
            }

            @NonNull
            @Override
            public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
                return new StudentViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();  // Start listening to Firestore
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();  // Notify adapter to refresh the list
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();  // Stop listening to Firestore
    }
}
