package com.example.infocity.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infocity.R;
import com.example.infocity.models.SingleEntityModel;
import com.example.infocity.shop_owner.ManageShopActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SingleEntityAdapter extends RecyclerView.Adapter<SingleEntityAdapter.ViewHolder> {

    private Context context;
    private List<SingleEntityModel> eList;
    private List<String> cList;

    public SingleEntityAdapter(Context context, List<SingleEntityModel> eList, List<String> cList) {
        this.context = context;
        this.eList = eList;
        this.cList = cList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_entity, parent, false);
        return new SingleEntityAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.setDescription(eList.get(position).getOther());
        holder.setIv_image(eList.get(position).getImg_uri());
        holder.setShop_name(eList.get(position).getName());
        holder.gst.setText(eList.get(position).getGst_no());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageShopActivity.class);
                intent.putExtra("category",cList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_image;
        ImageButton ibt_call,ibt_message;
        TextView shop_name,description,gst;
        CardView card;
        ImageView iv_approve,iv_reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gst = itemView.findViewById(R.id.tv_gst_single_entity);
            iv_approve = itemView.findViewById(R.id.iv_approve_single_entity);
            iv_reject = itemView.findViewById(R.id.iv_reject_single_entity);
            iv_image = itemView.findViewById(R.id.iv_image_single_entity);
            ibt_call = itemView.findViewById(R.id.ibt_call_single_entity);
            ibt_message = itemView.findViewById(R.id.ibt_text_single_entity);
            shop_name = itemView.findViewById(R.id.et_title_single_entity);
            description = itemView.findViewById(R.id.tv_info_single_entity);
            card = itemView.findViewById(R.id.card_single_entity);
            ibt_call.setVisibility(View.GONE);
            ibt_message.setVisibility(View.GONE);
            iv_approve.setVisibility(View.GONE);
            iv_reject.setVisibility(View.GONE);
        }



        public void setIv_image(String uri) {
            Picasso.get().load(uri).into(iv_image);
        }

        public void setShop_name(String local_shop_name) {
            shop_name.setText(local_shop_name);
        }

        public void setDescription(String local_description) {
            description.setText(local_description);
        }
    }
}
