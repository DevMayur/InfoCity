package com.example.infocity.adapters;

import android.content.Context;
import android.content.Intent;
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

import com.example.infocity.EntityDetails;
import com.example.infocity.R;
import com.example.infocity.models.ShopReferenceModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ShopRejectedAdapter extends RecyclerView.Adapter<ShopRejectedAdapter.ViewHolder> {
    private Context context;
    private List<ShopReferenceModel> rList;

    public ShopRejectedAdapter(Context context, List<ShopReferenceModel> rList) {
        this.context = context;
        this.rList = rList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_entity, parent, false);
        return new ShopRejectedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final String seller_id = rList.get(position).getUser_id();
        final String shop_id = rList.get(position).getShop_id();
        final String category = rList.get(position).getCategory();

        String collection_address = "users/"+seller_id + "/" +category;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(collection_address).document(shop_id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(final DocumentSnapshot documentSnapshot) {

                        holder.setIv_image(documentSnapshot.getString("img_uri"));
                        holder.setDescription(documentSnapshot.getString("other"));
                        holder.setShop_name(documentSnapshot.getString("name"));

                        holder.card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, EntityDetails.class);
                                intent.putExtra("address", documentSnapshot.getString("address"));
                                intent.putExtra("latitude", documentSnapshot.getDouble("latitude"));
                                intent.putExtra("longitude", documentSnapshot.getDouble("longitude"));
                                intent.putExtra("name", documentSnapshot.getString("name"));
                                intent.putExtra("other", documentSnapshot.getString("other"));
                                intent.putExtra("phone", documentSnapshot.getString("phone"));
                                intent.putExtra("img_uri", documentSnapshot.getString("img_uri"));
                                intent.putExtra("shop_id", rList.get(position).getShop_id());
                                intent.putExtra("seller_id", rList.get(position).getUser_id());
                                context.startActivity(intent);
                            }
                        });
                    }
                });

        holder.iv_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveEntity(seller_id,shop_id,category,rList.get(position).BlogPostId);
            }
        });

        holder.iv_reject.setVisibility(View.GONE);

    }



    private void approveEntity(String seller_id, String shop_id, String category, final String verification_id) {

        String collection_address = "users/"+seller_id + "/" +category;

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(collection_address).document(shop_id).update("isVerified","true")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            firebaseFirestore.collection("rejected_queue").document(verification_id).delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(context, "Congratulations ! Shop Verified", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(context, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else{
                            Toast.makeText(context, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return rList.size();
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
