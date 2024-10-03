package com.nemisolv.b3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private List<Country> countryList;

    public CountryAdapter(List<Country> countryList) {
        this.countryList = countryList;
    }


    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item,parent,false);
        return new CountryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.flag.setImageResource(country.getFlagId());
        holder.name.setText(country.getName());
        holder.capital.setText(country.getCapital());

        holder.itemView.setOnClickListener(v -> {
            Country country1 = countryList.get(position);
            Intent intent = new Intent(v.getContext(), CountryDetail.class);
            intent.putExtra("countryName", country1.getName());
            intent.putExtra("capital", country1.getCapital());
            intent.putExtra("flag", country1.getFlagId());
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {
        ImageView flag;
        TextView name;
        TextView capital;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flagImageView);
            name = itemView.findViewById(R.id.countryNameTextView);
            capital = itemView.findViewById(R.id.capitalTextView);
        }
    }
}
