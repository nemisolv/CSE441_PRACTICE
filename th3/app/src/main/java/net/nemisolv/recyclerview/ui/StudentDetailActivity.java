package net.nemisolv.recyclerview.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.nemisolv.recyclerview.R;
import net.nemisolv.recyclerview.data.model.Student;

public class StudentDetailActivity extends AppCompatActivity {

    // UI Elements
    private ImageView imageGender;
    private TextView textStudentId, textFullName, textBirthDate, textAddress, textGender, textEmail;
    private TextView textMajor, textGPA, textStudentYear, textTitleStudentId;
    private ImageButton buttonEdit, buttonDelete,buttonBack;
    private FloatingActionButton btnAddStudent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_detail);

        handleEdgeToEdgeInsets();

        // Initialize UI components
        initializeViews();

        // Retrieve and display student data
        Student student = (Student) getIntent().getSerializableExtra("student");
        if (student != null) {
            displayStudentDetails(student);
            setupListeners(student);
        }
    }

    // Handle edge-to-edge insets
    private void handleEdgeToEdgeInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.studentDetail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Initialize all views
    private void initializeViews() {
        textTitleStudentId = findViewById(R.id.textTitleStudentId);
        imageGender = findViewById(R.id.imageAvatar);
        textStudentId = findViewById(R.id.textId);
        textFullName = findViewById(R.id.textFullName);
        textBirthDate = findViewById(R.id.textBirthDate);
        textAddress = findViewById(R.id.textAddress);
        textGender = findViewById(R.id.textGender);
        textEmail = findViewById(R.id.textEmail);
        textMajor = findViewById(R.id.textMajor);
        textGPA = findViewById(R.id.textGPA);
        textStudentYear = findViewById(R.id.textStudentYear);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);
        btnAddStudent = findViewById(R.id.buttonAdd);
        buttonBack = findViewById(R.id.buttonBack);
    }

    // Display student details in TextViews
    private void displayStudentDetails(Student student) {
        textStudentId.setText(student.getId());
        textTitleStudentId.setText(student.getId());
        textFullName.setText("Họ và Tên: " + student.getFullName());
        textBirthDate.setText("Ngày sinh: " + student.getBirthDate());
        textAddress.setText("Địa chỉ: " + student.getAddress());
        textGender.setText("Giới tính: " + student.getGender());
        textEmail.setText("Email: " + student.getEmail());
        textMajor.setText("Chuyên ngành: " + student.getMajor());
        textGPA.setText("Điểm TB tích lũy: " + student.getGpa());
        textStudentYear.setText("SV năm: " + student.getYear());

        setGenderIcon(student.getGender());
    }

    // Set the appropriate gender icon
    private void setGenderIcon(String gender) {
        if ("Nữ".equals(gender)) {
            imageGender.setImageResource(R.drawable.ic_woman);
        } else {
            imageGender.setImageResource(R.drawable.ic_man);
        }
    }

    // Setup button listeners for edit, delete, and add actions
    private void setupListeners(Student student) {
        buttonDelete.setOnClickListener(v -> confirmDelete(student));

        btnAddStudent.setOnClickListener(v -> {
            Intent addStudentIntent = new Intent(this, StudentFormActivity.class);
            startActivity(addStudentIntent);
        });

        buttonEdit.setOnClickListener(v -> {
            Intent editStudentIntent = new Intent(this, StudentFormActivity.class);
            editStudentIntent.putExtra("student", student);
            editStudentIntent.putExtra("isEdit", true);
            startActivityForResult(editStudentIntent, 1);
        });

        buttonBack.setOnClickListener(v -> finish());
    }

    // Show confirmation dialog before deleting a student
    private void confirmDelete(Student student) {
        new AlertDialog.Builder(this)
                .setMessage("Bạn có chắc chắn muốn xóa sinh viên này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    MainActivity.deleteStudent(student.getId());
                    Toast.makeText(this, "Xóa sinh viên thành công", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Student updatedStudent = (Student) data.getSerializableExtra("updatedStudent");
            if (updatedStudent != null) {
                displayStudentDetails(updatedStudent);
            }
        }
    }
}
