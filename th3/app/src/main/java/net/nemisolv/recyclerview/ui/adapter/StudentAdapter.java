package net.nemisolv.recyclerview.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.nemisolv.recyclerview.R;
import net.nemisolv.recyclerview.ui.StudentDetailActivity;
import net.nemisolv.recyclerview.data.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }
    private List<Student> studentList;

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.txtStudentName.setText(student.getFullName().toString());
        holder.txtStudentId.setText(student.getId());
        holder.txtGPA.setText(String.valueOf(student.getGpa()));
        holder.imgStudentPhoto.setImageResource(student.getGender().equals("Nam") ? R.drawable.ic_man : R.drawable.ic_woman);
        holder.itemView.setOnClickListener( v-> {
            // Handle click event
            Intent intent = new Intent(v.getContext(), StudentDetailActivity.class);
            intent.putExtra("student", student);
            v.getContext().startActivity(intent);
        });
    }

    public void updateData(List<Student> newStudents) {
        this.studentList.clear(); // Clear current data
        this.studentList.addAll(newStudents); // Add new data
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }


    @Override
    public int getItemCount() {
        return studentList.size();
    }


    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtStudentName,txtStudentId,txtGPA;
        private final ImageView imgStudentPhoto;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtStudentName = itemView.findViewById(R.id.textStudentName);
            txtStudentId = itemView.findViewById(R.id.textTitleStudentId);
            txtGPA = itemView.findViewById(R.id.textGPA);
            imgStudentPhoto = itemView.findViewById(R.id.imageGender);
        }


    }
}
