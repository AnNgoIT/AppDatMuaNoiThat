package ute.fit.noithatapp.Activity.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    ArrayList<Long> countList;
    ArrayList<ProductModel> productModelArrayList;
    Context context;

    public NotificationAdapter(ArrayList<Long> countList, ArrayList<ProductModel> productModelArrayList, Context context) {
        this.countList = countList;
        this.productModelArrayList = productModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(productModelArrayList.size()!=0){
            return productModelArrayList.size();
        }
        return 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
        ImageView imgViewNotification;
        TextView tvDescriptionNotification,tvCountNotification,tvClearNotification;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewNotification=itemView.findViewById(R.id.imgNotification);
            tvDescriptionNotification=itemView.findViewById(R.id.descriptionNotification);
            tvCountNotification=itemView.findViewById(R.id.countNotification);
            tvClearNotification=itemView.findViewById(R.id.clearNotification);
        }
    }
}
