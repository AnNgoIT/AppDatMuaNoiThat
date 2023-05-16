package ute.fit.noithatapp.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ute.fit.noithatapp.Model.NotificationModel;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    ArrayList<Long> countList;
    ArrayList<ProductModel> productModelArrayList;
    ArrayList<NotificationModel> notificationModelArrayList;
    Context context;


    public void setCountList(ArrayList<Long> countList) {
        this.countList = countList;
    }

    public void setProductModelArrayList(ArrayList<ProductModel> productModelArrayList) {
        this.productModelArrayList = productModelArrayList;
    }

    public void setNotificationModelArrayList(ArrayList<NotificationModel> notificationModelArrayList) {
        this.notificationModelArrayList = notificationModelArrayList;
    }

    public ArrayList<Long> getCountList() {
        return countList;
    }

    public ArrayList<ProductModel> getProductModelArrayList() {
        return productModelArrayList;
    }


    public NotificationAdapter(ArrayList<Long> countList, ArrayList<ProductModel> productModelArrayList, ArrayList<NotificationModel> notificationModelArrayList, Context context) {
        this.countList = countList;
        this.productModelArrayList = productModelArrayList;
        this.notificationModelArrayList = notificationModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notificationModel=notificationModelArrayList.get(position);
        ProductModel productModel=productModelArrayList.get(position);
        Long count=countList.get(position);
        holder.tvDescriptionNotification.setText(notificationModel.getDescription());
        holder.tvCountNotification.setText("Count:"+count.toString());
        Glide.with(context).load(productModel.getImage()).into(holder.imgViewNotification);

    }

    @Override
    public int getItemCount() {
        if(notificationModelArrayList.size()!=0){
            return notificationModelArrayList.size();
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
