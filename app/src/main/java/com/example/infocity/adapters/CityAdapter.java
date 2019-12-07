package com.example.infocity.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infocity.Categories;
import com.example.infocity.R;
import com.example.infocity.models.CityModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>{
    private Context context;
    private List<CityModel> cList;

    public CityAdapter(Context context, List<CityModel> cList) {
        this.context = context;
        this.cList = cList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_city_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get().load(cList.get(position).getImg_uri()).into(holder.iv_city);
        holder.tv_city.setText(cList.get(position).getName());
        holder.iv_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Categories.class);
                SharedPreferences sharedPreferences = context.getSharedPreferences("selected_city",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("city",cList.get(position).getName());
                editor.commit();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_city;
        TextView tv_city;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_city = itemView.findViewById(R.id.image_single_city);
            tv_city = itemView.findViewById(R.id.tv_city_name_single_city);

        }
    }
}
