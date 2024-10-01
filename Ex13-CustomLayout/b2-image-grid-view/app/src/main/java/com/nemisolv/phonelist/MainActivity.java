package com.nemisolv.phonelist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyArrayAdapter myArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();

    }

    @SuppressLint("WrongViewCast")
    private void initial() {
        GridView gridView = findViewById(R.id.gridView);
        List<Image> imageList = List.of(
                new Image("Image 1", R.drawable.image1),
                new Image("Image 2", R.drawable.image2),
                new Image("Image 3", R.drawable.image3),
                new Image("Image 4", R.drawable.image4),
                new Image("Image 5", R.drawable.image5)
        );
        myArrayAdapter = new MyArrayAdapter(this,R.layout.list_view_item, imageList);
        gridView.setAdapter(myArrayAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,ResultActivity.class);
            Image current = myArrayAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putString("name", current.getName());
            bundle.putInt("imgId", current.getImgId());
            intent.putExtras(bundle);
            startActivity(intent);
        });



    }
}