package net.nemisolv.contactlist;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Contact> contactList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recycler_view);
        contactList = List.of(
                new Contact("John Doe", "1234567890"),
                new Contact("Jane Doe", "0987654321"),
                new Contact("Alice", "1234567890"),
                new Contact("Bob", "0987654321"),
                new Contact("Charlie", "1234567890"),
                new Contact("David", "0987654321"),
                new Contact("Eve", "1234567890"),
                new Contact("Frank", "0987654321"),
                new Contact("Grace", "1234567890"),
                new Contact("Hank", "0987654321"),
                new Contact("Ivy", "1234567890"),
                new Contact("Jack", "0987654321"),
                new Contact("Karl", "1234567890"),
                new Contact("Lily", "0987654321"),
                new Contact("Mary", "1234567890"),
                new Contact("Nancy", "0987654321"),
                new Contact("Oscar", "1234567890"),
                new Contact("Peter", "0987654321"),
                new Contact("Quinn", "1234567890"),
                new Contact("Rose", "0987654321"),
                new Contact("Steve", "1234567890"),
                new Contact("Tom", "0987654321"),
                new Contact("Ursula", "1234567890"),
                new Contact("Victor", "0987654321"),
                new Contact("Wendy", "1234567890"),
                new Contact("Xavier", "0987654321"),
                new Contact("Yvonne", "1234567890"),
                new Contact("Zack", "0987654321")
        );
        ContactAdapter adapter = new ContactAdapter(contactList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }
}