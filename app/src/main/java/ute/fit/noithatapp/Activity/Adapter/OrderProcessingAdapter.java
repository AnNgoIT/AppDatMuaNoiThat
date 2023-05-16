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

import java.text.DecimalFormat;
import java.util.ArrayList;

import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class OrderProcessingAdapter extends RecyclerView.Adapter<OrderProcessingAdapter.OrderProcessingViewHolder> {
    ArrayList<Long> countOrderList;
    ArrayList<ProductModel> productModelArrayList;
    Context context;

    public void setCountOrderList(ArrayList<Long> countOrderList) {
        this.countOrderList = countOrderList;
    }

    public OrderProcessingAdapter(ArrayList<ProductModel> productModelArrayList,ArrayList<Long> countOrderList, Context context) {
        this.countOrderList = countOrderList;
        this.productModelArrayList = productModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderProcessingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new OrderProcessingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProcessingViewHolder holder, int position) {
        ProductModel productModel=productModelArrayList.get(position);
        Long count=countOrderList.get(position);
        if (productModel==null){
            return ;
        }
        holder.productName.setText(productModel.getName());
        //
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String price = formatter.format(productModel.getPrice());
        holder.productPrice.setText(price+" VNĐ");
        //
        holder.state.setText("processing");
        holder.countProcessing.setText(count.toString());
        Glide.with(context).load(productModel.getImage()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        if(productModelArrayList!=null){
            return productModelArrayList.size();
        }
        return 0;
    }

    public class OrderProcessingViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView productName,productPrice,state,countProcessing;
        public OrderProcessingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageViewOrder);
            productName=itemView.findViewById(R.id.productNameOrder);
            productPrice=itemView.findViewById(R.id.productPriceOrder);
            state=itemView.findViewById(R.id.state);
            countProcessing=itemView.findViewById(R.id.countProcessing);

        }
    }
}
