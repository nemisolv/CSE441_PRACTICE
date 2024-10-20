package net.nemisolv.hello_firebase;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class StudentViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName, tvMSSV, tvClass, tvGrade;
    public ImageView btnEdit, btnDelete;
    public ImageView imgProfile;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvName);
        tvMSSV = itemView.findViewById(R.id.tvMSSV);
        tvClass = itemView.findViewById(R.id.tvClass);
        tvGrade = itemView.findViewById(R.id.tvGrade);
        imgProfile = itemView.findViewById(R.id.imageViewProfile);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }

    public void bind(Student student) {
        tvName.setText(student.getName());
        tvMSSV.setText(student.getId());
        tvClass.setText(student.getClassName());
        tvGrade.setText(String.valueOf(student.getScore()));
         Glide.with(itemView)
                .load(student.getImgUrl()) // URL of the profile image
                .placeholder(android.R.drawable.ic_menu_camera) // Placeholder image
                .into(imgProfile);

    }
}
