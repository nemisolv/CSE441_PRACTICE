package net.nemisolv.hello_firebase;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName, tvMSSV, tvClass, tvGrade;
    public ImageView btnEdit, btnDelete;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvName);
        tvMSSV = itemView.findViewById(R.id.tvMSSV);
        tvClass = itemView.findViewById(R.id.tvClass);
        tvGrade = itemView.findViewById(R.id.tvGrade);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }

    public void bind(Student student) {
        tvName.setText(student.getName());
        tvMSSV.setText(student.getId());
        tvClass.setText(student.getClassName());
        tvGrade.setText(String.valueOf(student.getScore()));
    }
}
