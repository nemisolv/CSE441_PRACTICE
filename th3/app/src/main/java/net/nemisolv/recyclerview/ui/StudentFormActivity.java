    package net.nemisolv.recyclerview.ui;
    
    import android.annotation.SuppressLint;
    import android.app.AlertDialog;
    import android.app.DatePickerDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Patterns;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Spinner;
    import android.widget.Toast;
    
    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import net.nemisolv.recyclerview.R;
    import net.nemisolv.recyclerview.data.model.Student;

    public class StudentFormActivity extends AppCompatActivity {
    
        private EditText editTextStudentId, editTextFullName, editTextBirthDate, editTextEmail, editTextGpa;
        private Spinner spinnerAddress, spinnerMajor, spinnerStudentYear;
        private RadioGroup radioGroupGender;
        private Button btnSubmit;
        private Intent intent;
        private ImageButton buttonBack;
    
        private String[] addresses = {
                "Chọn địa chỉ", "Hà Nội", "Đà Nẵng", "Hồ Chí Minh", "Cần Thơ",
                "Nha Trang", "Hải Phòng", "Nam Định", "Thành Phố Hồ Chí Minh",
                "Biên Hòa", "Vinh", "Hà Tĩnh", "Hà Giang", "Hà Nam",
                "Ninh Bình", "Quảng Ninh", "Thái Nguyên", "Bắc Ninh",
                "Quảng Nam", "Bến Tre"
        };

        private String[] majors = {
                "Chọn chuyên ngành", "CNTT", "Kinh Tế", "Xây Dựng", "Hóa Học",
                "Cơ Khí", "Điện Tử", "Ngôn Ngữ", "Quản Trị Kinh Doanh", "Y Dược",
                "Sư Phạm", "Tin Học", "Kỹ Thuật Môi Trường", "Marketing",
                "Luật", "Tài Chính", "Viễn Thông", "Khoa Học Máy Tính",
                "Nông Nghiệp", "Công Nghệ Thông Tin", "Thiết Kế Đồ Họa"
        };

        private String[] studentYears = {"Chọn năm học", "1", "2", "3", "4"};

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_student_form);
    
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addStudent), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
    
            setupViews();
           if(intent ==null || !intent.hasExtra("isEdit")) {
                setupSpinners();
            }
    
            editTextBirthDate.setOnClickListener(v -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this);
                datePickerDialog.show();
    
                datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    editTextBirthDate.setText(date);
                });
            });
    
            btnSubmit.setOnClickListener(this::handleSubmitForm);
            buttonBack.setOnClickListener(v -> finish());
        }
    
        private void setupViews() {
            editTextStudentId = findViewById(R.id.editTextStudentId);
            editTextFullName = findViewById(R.id.editTextFullName);
            editTextBirthDate = findViewById(R.id.editTextBirthDate);
            editTextEmail = findViewById(R.id.editTextEmail);
            editTextGpa = findViewById(R.id.editTextGpa);
            spinnerAddress = findViewById(R.id.spinnerAddress);
            spinnerMajor = findViewById(R.id.spinnerMajor);
            spinnerStudentYear = findViewById(R.id.spinnerStudentYear);
            radioGroupGender = findViewById(R.id.radioGroupGender);
            btnSubmit = findViewById(R.id.buttonSubmit);
            buttonBack = findViewById(R.id.buttonBack);
    
            // set up in case of editing
             intent = getIntent();
    
    
    
    
            if (intent.hasExtra("student") && intent.hasExtra("isEdit")) {
                Student student = (Student) intent.getSerializableExtra("student");
                editTextStudentId.setText(student.getId());
                editTextStudentId.setEnabled(false);
                editTextStudentId.setBackgroundColor(getResources().getColor(R.color.gray));
    
    
                editTextFullName.setText(student.getFullName().toString());
                editTextBirthDate.setText(student.getBirthDate());
                editTextEmail.setText(student.getEmail());

                setupSpinners();
                spinnerAddress.setSelection(getIndex(spinnerAddress, student.getAddress()));

                spinnerMajor.setSelection(getIndex(spinnerMajor, student.getMajor()));
                spinnerStudentYear.setSelection(getIndex(spinnerStudentYear, String.valueOf(student.getYear())));
                editTextGpa.setText(String.valueOf(student.getGpa()));
                RadioButton genderButton = radioGroupGender.findViewWithTag(student.getGender());
                genderButton.setChecked(true);
            }
        }

        private int getIndex(Spinner spinner, String defaultValue) {
            if (defaultValue == null || defaultValue.isEmpty()) return 0;  // Handle null or empty case

            for (int i = 0; i < spinner.getCount(); i++) {
                String spinnerItem = spinner.getItemAtPosition(i).toString().trim();
                if (spinnerItem.equalsIgnoreCase(defaultValue.trim())) {
                    return i;
                }
            }
            return 0;  // Default to first option if no match found
        }


        private void setupSpinners() {
            // Setting up the address spinner
            ArrayAdapter<String> addressAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, addresses);
            addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAddress.setAdapter(addressAdapter);
    
            // Setting up the major spinner
            ArrayAdapter<String> majorAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, majors);
            majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMajor.setAdapter(majorAdapter);
    
            // Setting up the student year spinner
            ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, studentYears);
            yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerStudentYear.setAdapter(yearAdapter);
        }
    
        private void handleSubmitForm(View view) {
            String studentId = editTextStudentId.getText().toString().trim();
            String fullName = editTextFullName.getText().toString().trim();
            String birthDate = editTextBirthDate.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String address = spinnerAddress.getSelectedItem() != null ? spinnerAddress.getSelectedItem().toString() : "";
            String major = spinnerMajor.getSelectedItem() != null ? spinnerMajor.getSelectedItem().toString() : "";
            String year = spinnerStudentYear.getSelectedItem() != null ? spinnerStudentYear.getSelectedItem().toString() : "";
            String gpa = editTextGpa.getText().toString().trim();
    
            int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
            RadioButton selectedGender = findViewById(selectedGenderId);
            String gender = selectedGender != null ? selectedGender.getText().toString() : "Không xác định";
    
            if (!validateInput(studentId, fullName, birthDate, email, address, major, year, gpa)) {
                Toast.makeText(this, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
    
            Student.FullName buildFullName = buildFullName(fullName);
    
            Student student = new Student(studentId, buildFullName, gender, birthDate, email, address, major, Double.parseDouble(gpa), Integer.parseInt(year));
           if(intent!=null && intent.hasExtra("isEdit")) {
    
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
                  builder.setMessage("Bạn có chắc chắn muốn cập nhật sinh viên này?")
                         .setPositiveButton("Cập nhật", (dialog, which) -> {
                             MainActivity.updateStudent(student);
                             Intent intent = new Intent(this, StudentDetailActivity.class);
                             intent.putExtra("updatedStudent", student);
                             setResult(RESULT_OK, intent);
                             String message = "Cập nhật sinh viên thành công";
                             Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    
                             finish();
                         })
                         .setNegativeButton("Hủy", null)
                         .show();
            } else {
                MainActivity.addStudent(student);
               String message = "Thêm sinh viên thành công";
    
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            finish();
            }
    
        }
    
        private Student.FullName buildFullName(String fullName) {
            String[] parts = fullName.split(" ");
            String lastName = parts[0];
            String firstName = parts[parts.length - 1];
            String middleName = "";
            for (int i = 1; i < parts.length - 1; i++) {
                middleName += parts[i] + " ";
            }
            // note that the lastName (first)
            return new Student.FullName(firstName, middleName.trim(), lastName);
        }
    
        private boolean validateInput(String studentId, String fullName, String birthDate, String email, String address, String major, String year, String gpa) {
            boolean isValid = true;
    
            if (studentId.isEmpty()) {
                isValid = false;
                editTextStudentId.setHint("Vui lòng nhập mã sinh viên");
                editTextStudentId.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (fullName.isEmpty()) {
                isValid = false;
                editTextFullName.setHint("Vui lòng nhập họ tên");
                editTextFullName.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (birthDate.isEmpty()) {
                isValid = false;
                editTextBirthDate.setHint("Vui lòng nhập ngày sinh");
                editTextBirthDate.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValid = false;
                editTextEmail.setHint("Vui lòng nhập email hợp lệ");
                editTextEmail.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (address.equals("Chọn địa chỉ")) {
                Toast.makeText(this, "Vui lòng chọn địa chỉ", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (major.equals("Chọn chuyên ngành")) {
                Toast.makeText(this, "Vui lòng chọn chuyên ngành", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (year.equals("Chọn năm học")) {
                Toast.makeText(this, "Vui lòng chọn năm học", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (studentId.length() < 5) {
                isValid = false;
                editTextStudentId.setHint("Mã sinh viên phải có ít nhất 5 ký tự");
                editTextStudentId.setHintTextColor(getResources().getColor(R.color.red));
            }
            if (gpa.isEmpty()) {
                isValid = false;
                editTextGpa.setHint("Vui lòng nhập điểm");
                editTextGpa.setHintTextColor(getResources().getColor(R.color.red));
            }
            try {
                float gpaValue = Float.parseFloat(gpa);
                if (gpaValue < 0 || gpaValue > 4) {
                    isValid = false;
                    editTextGpa.setHint("Điểm phải nằm trong khoảng 0 - 4");
                    editTextGpa.setHintTextColor(getResources().getColor(R.color.red));
                }
            } catch (NumberFormatException e) {
                isValid = false;
                editTextGpa.setBackgroundColor(getResources().getColor(R.color.red));
            }
    
            return isValid;
        }
    }
