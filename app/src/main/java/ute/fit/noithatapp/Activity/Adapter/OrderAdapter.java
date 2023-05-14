package ute.fit.noithatapp.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import ute.fit.noithatapp.Model.OrderModel;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private ArrayList<Long> countList;
    private ArrayList<ProductModel> productModelList;
    private Context context;

    public void setCountList(ArrayList<Long> countList) {
        this.countList = countList;
    }

    public OrderAdapter(ArrayList<Long> countList, ArrayList<ProductModel> productModelList, Context context) {
        this.productModelList = productModelList;
        this.context = context;
        this.countList = countList;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        ProductModel productModel=productModelList.get(position);
        Long count=countList.get(position);
        if (productModel==null){
            return ;
        }
        holder.tvProductName.setText(productModel.getName());
        holder.tvProductPrice.setText(productModel.getPrice().toString());
        Glide.with(context).load(productModel.getImage()).into(holder.imgProduct);
        holder.edtAmount.setText(count.toString());
    }

    @Override
    public int getItemCount() {
        if(productModelList!=null){
            return productModelList.size();
        }
        return 0;
    }
    public class OrderViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView tvProductName,tvProductPrice;
        private Button btnDelete,btnIncrease,btnDecrease;
        private EditText edtAmount;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imageView);
            tvProductName=itemView.findViewById(R.id.minimal_sta);
            tvProductPrice=itemView.findViewById(R.id.some_id);
            btnDelete=itemView.findViewById(R.id.btnDeleteOrder);
            btnIncrease=itemView.findViewById(R.id.buttonIncreaseOrder);
            btnDecrease=itemView.findViewById(R.id.buttonDecreaseOrder);
            edtAmount=itemView.findViewById(R.id.increaseAmountOrder);
        }
    }
}
