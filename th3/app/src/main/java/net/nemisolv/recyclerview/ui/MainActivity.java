package net.nemisolv.recyclerview.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.nemisolv.recyclerview.R;
import net.nemisolv.recyclerview.data.model.Student;
import net.nemisolv.recyclerview.ui.adapter.StudentAdapter;
import net.nemisolv.recyclerview.utils.JsonUtils;
import net.nemisolv.recyclerview.utils.SearchUtils;
import net.nemisolv.recyclerview.utils.SortedUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerStudentList;
    private ImageButton btnSearch, btnTranslator, btnMoreOptions, btnCancelSearch;
    private static StudentAdapter studentAdapter;
    public static List<Student> studentList;
    private FloatingActionButton btnAddStudent;
    private EditText editTextSearch;
    private TextView textViewStudentList;

    // Separate state variables for sorting
    private static boolean isSortingByNameAsc = false;
    private static boolean isSortingByIdAsc = false;
    private static boolean isSortingByGpaAsc = false;

    @SuppressLint("MissingInflatedId")
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
        setupViews();
        studentList = JsonUtils.readStudentsFromJson(this);
        Log.d("MainActivity", "Student List Size: " + studentList.size());
        if (studentList.size() > 0) {
            Log.d("MainActivity", "First Student: " + studentList.get(0).getFullName());
        }
        studentAdapter = new StudentAdapter(studentList);
        recyclerStudentList.setAdapter(studentAdapter);
        recyclerStudentList.setLayoutManager(new LinearLayoutManager(this));
        // Custom divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        recyclerStudentList.addItemDecoration(dividerItemDecoration);

        setupListeners();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupListeners() {
        btnAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentFormActivity.class);
            startActivity(intent);
        });

        btnTranslator.setOnClickListener(v -> {
            if (isSortingByNameAsc) {
                SortedUtils.sortByLastNameAndFirstNameDesc(studentList);
            } else {
                SortedUtils.sortByLastNameAndFirstNameAsc(studentList);
            }
            isSortingByNameAsc = !isSortingByNameAsc; // Toggle state
            studentAdapter.notifyDataSetChanged();
        });

        btnMoreOptions.setOnClickListener(this::showPopupMenu);
        btnSearch.setOnClickListener(v -> {
            textViewStudentList.setVisibility(View.GONE);
            editTextSearch.setVisibility(View.VISIBLE);
            editTextSearch.requestFocus();
            btnCancelSearch.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.GONE);

            searchStudents();
        });
        btnCancelSearch.setOnClickListener(v -> {
            editTextSearch.setVisibility(View.GONE);
            textViewStudentList.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
            btnCancelSearch.setVisibility(View.GONE);
            editTextSearch.setText("");
            studentList = JsonUtils.readStudentsFromJson(this);
            studentAdapter.updateData(studentList);
        });
    }

    private void searchStudents() {
        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean isEntered = actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            if (isEntered) {
                String query = editTextSearch.getText().toString().trim();
                if (query.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                    return false;
                }

                // Perform the search
                List<Student> searchResults = SearchUtils.searchStudents(studentList, query);
                if (searchResults.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                } else {
                    studentAdapter.updateData(searchResults);
                }
//                editTextSearch.setText("");
//                editTextSearch.setVisibility(View.GONE);
                return true;
            }
            return false;
        });
    }

    @SuppressLint("WrongViewCast")
    private void setupViews() {
        recyclerStudentList = findViewById(R.id.recyclerViewStudentList);
        btnSearch = findViewById(R.id.buttonSearch);
        btnTranslator = findViewById(R.id.buttonTranslator);
        btnMoreOptions = findViewById(R.id.buttonMoreOptions);
        btnAddStudent = findViewById(R.id.buttonAdd);
        editTextSearch = findViewById(R.id.editTextSearch);
        textViewStudentList = findViewById(R.id.textViewStudentList);
        btnCancelSearch = findViewById(R.id.buttonCancelSearch);
    }

    public static void deleteStudent(String studentId) {
        for (Student student : studentList) {
            if (student.getId().equals(studentId)) {
                studentList.remove(student);
                studentAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    public static void addStudent(Student student) {
        studentList.add(student);
        studentAdapter.notifyDataSetChanged();
    }

    public static void updateStudent(Student student) {
        for (Student s : studentList) {
            if (s.getId().equals(student.getId())) {
                s.setFullName(student.getFullName());
                s.setGender(student.getGender());
                s.setGpa(student.getGpa());
                s.setAddress(student.getAddress());
                s.setYear(student.getYear());
                s.setBirthDate(student.getBirthDate());
                s.setEmail(student.getEmail());
                s.setMajor(student.getMajor());
                studentAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private void showPopupMenu(View view) {
        // Create a PopupMenu
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_more_options, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();

            if (id == R.id.action_sort_by_id) {
                if (isSortingByIdAsc) {
                    SortedUtils.sortByStudentIdDesc(studentList);
                    Toast.makeText(MainActivity.this, "Sắp xếp theo ID giảm dần", Toast.LENGTH_SHORT).show();
                } else {
                    SortedUtils.sortByStudentIdAsc(studentList);
                    Toast.makeText(MainActivity.this, "Sắp xếp theo ID tăng dần", Toast.LENGTH_SHORT).show();
                }
                isSortingByIdAsc = !isSortingByIdAsc; // Toggle state
                studentAdapter.notifyDataSetChanged();
                return true;
            } else if (id == R.id.action_sort_by_gpa) {
                if (isSortingByGpaAsc) {
                    SortedUtils.sortByGpaDesc(studentList);
                    Toast.makeText(MainActivity.this, "Sắp xếp theo GPA giảm dần", Toast.LENGTH_SHORT).show();
                } else {
                    SortedUtils.sortByGpaAsc(studentList);
                    Toast.makeText(MainActivity.this, "Sắp xếp theo GPA tăng dần", Toast.LENGTH_SHORT).show();
                }
                isSortingByGpaAsc = !isSortingByGpaAsc; // Toggle state
                studentAdapter.notifyDataSetChanged();
                return true;
            }
            return false;
        });

        popupMenu.show();
    }
}
