package com.example.infocity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infocity.R;
import com.example.infocity.models.OfferModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private Context context;
    private List<OfferModel> oList;

    public OfferAdapter(Context context, List<OfferModel> oList) {
        this.context = context;
        this.oList = oList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_offer_item, parent, false);
        return new OfferAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.offer_name.setText(oList.get(position).getOffer_name());
        holder.offer_description.setText(oList.get(position).getOffer_desc());
        Picasso.get().load(oList.get(position).getImg_uri()).into(holder.iv_offer);

    }

    @Override
    public int getItemCount() {
        return oList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView offer_name;
        private TextView offer_description;
        private ImageView iv_offer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            offer_name = itemView.findViewById(R.id.tv_offer_name_single_offer);
            offer_description = itemView.findViewById(R.id.tv_description_single_offer);
            iv_offer = itemView.findViewById(R.id.iv_offer_image_single_offer);

        }
    }
}
