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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.nemisolv.hello_firebase.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Student, StudentViewHolder> adapter;
    private DatabaseReference studentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();
        initFirebase();
        loadStudents();

        binding.btnAddStudent.setOnClickListener(v -> {
            // Navigate to AddStudentActivity
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });
    }

    // Initialize RecyclerView setup
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Initialize Firebase reference
    private void initFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("sinhvien");
    }

    // Load students using FirebaseRecyclerAdapter
    private void loadStudents() {
        FirebaseRecyclerOptions<Student> options = new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(studentRef, Student.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Student, StudentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StudentViewHolder holder, int position, @NonNull Student model) {
                holder.bind(model);

                holder.btnEdit.setOnClickListener(v -> {
                    // Handle edit
                    Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                    intent.putExtra("studentId", getRef(position).getKey());
                    startActivity(intent);
                });

                holder.btnDelete.setOnClickListener(v -> {
                    studentRef.child(getRef(position).getKey()).removeValue()
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
        adapter.startListening();  // Start listening to Firebase
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();  // Notify adapter to refresh the list
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();  // Stop listening to Firebase
    }
}
