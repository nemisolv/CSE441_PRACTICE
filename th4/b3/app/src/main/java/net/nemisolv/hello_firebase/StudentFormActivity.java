package net.nemisolv.hello_firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class StudentFormActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etName, etMSSV, etClass, etGrade;
    private FirebaseFirestore db;
    private String studentId;
    private ImageView imgStudent;
    private Button btnChooseImage;
    private Uri imageUri; // To store the URI of the selected image

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

        btnChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData(); // Store the URI of the selected image
            imgStudent.setImageURI(imageUri); // Display the selected image
        }
    }

    // Initialize UI elements
    private void initUI() {
        etName = findViewById(R.id.etStudentName);
        etMSSV = findViewById(R.id.etMSSV);
        etClass = findViewById(R.id.etClass);
        etGrade = findViewById(R.id.etGrade);
        imgStudent = findViewById(R.id.imageViewProfile);
        btnChooseImage = findViewById(R.id.btnChooseImage);
    }

    private void initFirebase() {
        db = FirebaseFirestore.getInstance();
    }

    // Load student data if editing an existing student
    private void loadStudentData(String studentId) {
        DocumentReference studentRef = db.collection("sinhvien").document(studentId);
        studentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Student student = task.getResult().toObject(Student.class);
                if (student != null) {
                    etName.setText(student.getName());
                    etMSSV.setText(student.getId());
                    etClass.setText(student.getClassName());
                    etGrade.setText(String.valueOf(student.getScore()));
                    // Load the profile image if available
                    Glide.with(this)
                            .load(student.getImgUrl()) // URL of the profile image
                            .placeholder(android.R.drawable.ic_menu_camera) // Placeholder image
                            .into(imgStudent);
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
            showAlertDialog("Lỗi", "Dữ liệu không hợp lệ");
            return;
        }

        float score = Float.parseFloat(scoreStr);
        Student student = new Student(name, mssv, className, score);

        if (imageUri != null) {
            // Upload image to Cloud Storage
            uploadImageToFirebase(mssv, student);
        } else {
            // If there's no image, save the student information without an avatar
            saveStudentToFirestoreCloud(mssv, student, null);
        }
    }

    private void uploadImageToFirebase(String mssv, Student student) {
        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("avatars/" + mssv + ".jpg");

        // Upload the image to Cloud Storage
        UploadTask uploadTask = storageRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Get the uploaded image URL
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String avatarUrl = uri.toString();
                // Save student information to Firestore
                saveStudentToFirestoreCloud(mssv, student, avatarUrl);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Lỗi khi tải ảnh lên", Toast.LENGTH_SHORT).show();
            Log.d("StudentFormActivity", e.getMessage());
        });
    }

    private void saveStudentToFirestoreCloud(String mssv, Student student, String avatarUrl) {
        student.setImgUrl(avatarUrl); // Update the image URL in the Student object
        if (studentId == null) {
            // Check for duplicate MSSV
            Query query = db.collection("sinhvien").whereEqualTo("id", mssv);
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    // Add a new student
                    db.collection("sinhvien").document(mssv).set(student)
                            .addOnSuccessListener(aVoid -> Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Lỗi khi thêm sinh viên", Toast.LENGTH_SHORT).show();
                                Log.d("AddStudentActivity", e.getMessage());
                            });
                    finish();  // Close the activity
                }
            });
        } else {
            // Update the student information
            db.collection("sinhvien").document(studentId).set(student)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi cập nhật sinh viên", Toast.LENGTH_SHORT).show());
            finish();  // Close the activity
        }
    }

    // Show an alert dialog
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Validate input fields
    private boolean validateInput(String name, String mssv, String className, String scoreStr) {
        if (name.isEmpty() || mssv.isEmpty() || className.isEmpty() || scoreStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            float score = Float.parseFloat(scoreStr);  // Validate score is a number
            if (score < 0 || score > 10) {
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
