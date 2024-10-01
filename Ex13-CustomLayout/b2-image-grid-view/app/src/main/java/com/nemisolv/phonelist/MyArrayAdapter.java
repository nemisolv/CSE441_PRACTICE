package com.nemisolv.phonelist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
public class MyArrayAdapter extends ArrayAdapter<Image> {
    Activity context = null;
    List<Image> myArray = null;
    int LayoutId;

    public MyArrayAdapter( Activity context, int LayoutId, List<Image> arr) {
        super(context, LayoutId, arr);
        this.context = context;
        this.LayoutId = LayoutId;
        this.myArray = arr;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutId, null);
        final Image myimage = myArray.get(position);
        final ImageView imgItem =
                convertView.findViewById(R.id.img_thumb);
        imgItem.setImageResource(myimage.getImgId());
        final TextView myname =convertView.findViewById(R.id.txt_name);
        myname.setText(myimage.getName());
        return convertView;
    }
}
