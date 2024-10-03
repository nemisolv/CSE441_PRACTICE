package com.nemisolv.b3;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import android.graphics.Color;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    private StaffViewModel staffViewModel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        TextView textViewStaffDetails = findViewById(R.id.textViewStaffDetails);
//        TextView textViewStatus = findViewById(R.id.textViewStatus);  // TextView để hiển thị trạng thái màu đỏ
//        EditText editTextStaffId = findViewById(R.id.editTextStaffId);
//        EditText editTextStaffName = findViewById(R.id.editTextStaffName);
//        EditText editTextBirthDate = findViewById(R.id.editTextBirthDate);
//        EditText editTextSalary = findViewById(R.id.editTextSalary);
//        Button buttonAddStaff = findViewById(R.id.buttonAddStaff);
//        staffViewModel = new ViewModelProvider(this).get(StaffViewModel.class);
//
//        staffViewModel.getStaffDetails().observe(this, new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> detailsList) {
//                if (detailsList != null && !detailsList.isEmpty()) {
//                    StringBuilder staffList = new StringBuilder();
//                    for (String details : detailsList) {
//                        staffList.append(details).append("\n");
//                    }
//                    textViewStaffDetails.setText(staffList.toString());
//                } else {
//                    textViewStaffDetails.setText("No Result!!");
//                }
//            }
//        });
//
//        // Xử lý sự kiện khi nhấn nút thêm nhân viên
//        buttonAddStaff.setOnClickListener(v -> {
//            String staffId = editTextStaffId.getText().toString();
//            String name = editTextStaffName.getText().toString();
//            String birthDate = editTextBirthDate.getText().toString();
//            String salary = editTextSalary.getText().toString();
//
//            if (staffId.isEmpty() || name.isEmpty() || birthDate.isEmpty() || salary.isEmpty()) {
//                textViewStatus.setText("Chưa nhập dữ liệu");
//                textViewStatus.setTextColor(Color.RED);
//            } else {
//                // Gọi phương thức trong ViewModel để thêm thông tin nhân viên
//                staffViewModel.addStaffDetails(staffId, name, birthDate, salary);
//                textViewStatus.setText("Đã nhấn nút thêm mới");
//                textViewStatus.setTextColor(Color.RED);
//            }
//        });
//
//        // Kiểm tra nếu các trường đã nhập nhưng chưa nhấn nút
//        editTextStaffId.setOnFocusChangeListener((v, hasFocus) -> {
//            if (!hasFocus && !editTextStaffId.getText().toString().isEmpty() &&
//                    !editTextStaffName.getText().toString().isEmpty() &&
//                    !editTextBirthDate.getText().toString().isEmpty() &&
//                    !editTextSalary.getText().toString().isEmpty()) {
//                textViewStatus.setText("Đã nhập nhưng chưa nhấn nút");
//                textViewStatus.setTextColor(Color.RED);
//            }
//        });
//    }
//}

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StaffViewModel staffViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kết nối các thành phần giao diện
        TextView textViewStaffDetails = findViewById(R.id.textViewStaffDetails);
        TextView textViewStatus = findViewById(R.id.textViewStatus);  // TextView để hiển thị trạng thái màu đỏ
        EditText editTextStaffId = findViewById(R.id.editTextStaffId);
        EditText editTextStaffName = findViewById(R.id.editTextStaffName);
        EditText editTextBirthDate = findViewById(R.id.editTextBirthDate);
        EditText editTextSalary = findViewById(R.id.editTextSalary);
        Button buttonAddStaff = findViewById(R.id.buttonAddStaff);

        // Khởi tạo ViewModel
        staffViewModel = new ViewModelProvider(this).get(StaffViewModel.class);

        // Quan sát LiveData từ ViewModel
        staffViewModel.getStaffDetails().observe(this, detailsList -> {
            if (detailsList != null && !detailsList.isEmpty()) {
                StringBuilder staffList = new StringBuilder();
                for (String details : detailsList) {
                    staffList.append(details).append("\n");
                }
                if(staffList.length() >1) {
                    textViewStatus.setText("Sau khi thêm vài nhân viên");
                    textViewStatus.setTextColor(Color.RED);
                }
                textViewStaffDetails.setText(staffList.toString());
            } else {
                textViewStaffDetails.setText("No Result!!");
            }
        });

        // TextWatcher để lắng nghe thay đổi trên các trường EditText
        TextWatcher inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Kiểm tra nếu tất cả các trường đều không rỗng
                if (!editTextStaffId.getText().toString().isEmpty() &&
                        !editTextStaffName.getText().toString().isEmpty() &&
                        !editTextBirthDate.getText().toString().isEmpty() &&
                        !editTextSalary.getText().toString().isEmpty()) {

                    textViewStatus.setText("Đã nhập nhưng chưa nhấn nút");
                    textViewStatus.setTextColor(Color.RED);
                } else {
                    textViewStatus.setText("");  // Xóa trạng thái nếu chưa nhập đủ
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        // Gán TextWatcher cho các trường EditText
        editTextStaffId.addTextChangedListener(inputWatcher);
        editTextStaffName.addTextChangedListener(inputWatcher);
        editTextBirthDate.addTextChangedListener(inputWatcher);
        editTextSalary.addTextChangedListener(inputWatcher);

        // Xử lý sự kiện khi nhấn nút thêm nhân viên
        buttonAddStaff.setOnClickListener(v -> {
            String staffId = editTextStaffId.getText().toString();
            String name = editTextStaffName.getText().toString();
            String birthDate = editTextBirthDate.getText().toString();
            String salary = editTextSalary.getText().toString();

            if (staffId.isEmpty() || name.isEmpty() || birthDate.isEmpty() || salary.isEmpty()) {
                textViewStatus.setText("Chưa nhập dữ liệu");
                textViewStatus.setTextColor(Color.RED);
            } else {
                // Gọi phương thức trong ViewModel để thêm thông tin nhân viên
                staffViewModel.addStaffDetails(staffId, name, birthDate, salary);
                textViewStatus.setText("Đã nhấn nút thêm mới");
                textViewStatus.setTextColor(Color.RED);
            }
        });
    }
}

