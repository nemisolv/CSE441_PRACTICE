package com.nemisolv.phonelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {
    private final List<Phone> phones;

    public PhoneAdapter(List<Phone> phones) {
        this.phones = phones;
    }


    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_item, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        Phone phone = phones.get(position);
        holder.name.setText(phone.getName());
        holder.price.setText(String.valueOf(phone.getPrice()));
        holder.image.setImageResource(phone.getImage());
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), phone.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    static class PhoneViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView price;
        private final ImageView image;
        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name);
            price = itemView.findViewById(R.id.txt_price);
            image = itemView.findViewById(R.id.imageView);


        }



    }
}
