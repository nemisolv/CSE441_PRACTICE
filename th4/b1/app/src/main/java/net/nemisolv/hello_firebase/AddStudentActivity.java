package net.nemisolv.hello_firebase;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStudentActivity extends AppCompatActivity {

    private EditText etName, etMSSV, etClass, etGrade;
    private DatabaseReference studentRef;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        initUI();
        initFirebase();

        studentId = getIntent().getStringExtra("studentId");
        if (studentId != null) {
            loadStudentData(studentId);
        }

        findViewById(R.id.btnSaveStudent).setOnClickListener(v -> saveStudentData());
    }

    // Initialize UI elements
    private void initUI() {
        etName = findViewById(R.id.etStudentName);
        etMSSV = findViewById(R.id.etMSSV);
        etClass = findViewById(R.id.etClass);
        etGrade = findViewById(R.id.etGrade);
    }

    // Initialize Firebase database reference
    private void initFirebase() {
        studentRef = FirebaseDatabase.getInstance().getReference("sinhvien");
    }

    // Load student data if editing an existing student
    private void loadStudentData(String studentId) {
        studentRef.child(studentId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Student student = task.getResult().getValue(Student.class);
                if (student != null) {
                    etName.setText(student.getName());
                    etMSSV.setText(student.getId());
                    etClass.setText(student.getClassName());
                    etGrade.setText(String.valueOf(student.getScore()));
                } else {
                    Toast.makeText(this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Save or update student data
    private void saveStudentData() {
        String name = etName.getText().toString().trim();
        String mssv = etMSSV.getText().toString().trim();
        String className = etClass.getText().toString().trim();
        String scoreStr = etGrade.getText().toString().trim();

        if (!validateInput(name, mssv, className, scoreStr)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lỗi");
            builder.setMessage("Dữ liệu không hợp lệ");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
            return;
        }

        float score = Float.parseFloat(scoreStr);
        Student student = new Student(name, mssv, className, score);

        if (studentId == null) {
            // Kiểm tra MSSV không bị trùng
            studentRef.child(mssv).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm sinh viên mới
                    studentRef.child(mssv).setValue(student)
                            .addOnSuccessListener(aVoid -> Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi thêm sinh viên", Toast.LENGTH_SHORT).show());
                    finish();  // Đóng activity
                }
            });
        } else {
            studentRef.child(studentId).setValue(student)  // Update existing student
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi cập nhật sinh viên", Toast.LENGTH_SHORT).show());
            finish();  // Close activity

        }

    }

    // Validate input fields
    private boolean validateInput(String name, String mssv, String className, String scoreStr) {
        if (name.isEmpty() || mssv.isEmpty() || className.isEmpty() || scoreStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
           float score =  Float.parseFloat(scoreStr);  // Validate score is a number
            if(score < 0 || score > 10) {
                Toast.makeText(this, "Điểm phải nằm trong khoảng 0-10", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Điểm không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
