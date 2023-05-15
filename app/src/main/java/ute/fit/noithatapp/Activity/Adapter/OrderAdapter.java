package ute.fit.noithatapp.Activity.Adapter;

import android.app.NotificationChannel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private ArrayList<Long> countList;
    private ArrayList<ProductModel> productModelList;
    private Context context;
    private IClickIncrease iClickIncrease;
    private IClickDecrease iClickDecrease;
    private IClick iClick;

    public interface IClick{
        void onClickOrderItem(Integer productId,int position);
    }
    public interface  IClickIncrease{
        void onClickIncrease(Long count,Integer productId);
    }
    public interface IClickDecrease{
        void onCLickDecrease(Long count,Integer productId);
    }
    public void setCountList(ArrayList<Long> countList) {
        this.countList = countList;
    }

    public OrderAdapter(ArrayList<Long> countList, ArrayList<ProductModel> productModelList, Context context,IClick iClick,
                        IClickIncrease iClickIncrease,IClickDecrease iClickDecrease) {
        this.productModelList = productModelList;
        this.context = context;
        this.countList = countList;
        this.iClick=iClick;
        this.iClickIncrease=iClickIncrease;
        this.iClickDecrease=iClickDecrease;
    }

    public ArrayList<Long> getCountList() {
        return countList;
    }

    public ArrayList<ProductModel> getProductModelList() {
        return productModelList;
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
        holder.btnDelete.setOnClickListener(view -> {
            iClick.onClickOrderItem(productModel.getProductId(),position);
            countList.remove(position);
            productModelList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,countList.size());

        });
        holder.btnIncrease.setOnClickListener(view -> {
            Long newCount=Long.valueOf(holder.edtAmount.getText().toString())+Long.valueOf(1);
            holder.edtAmount.setText(String.valueOf(newCount));
            iClickIncrease.onClickIncrease(newCount, productModel.getProductId());
        });
        holder.btnDecrease.setOnClickListener(view ->{
            Long newCount=Long.valueOf(holder.edtAmount.getText().toString())-Long.valueOf(1);
            if (newCount >= 0) {
                holder.edtAmount.setText(String.valueOf(newCount));
                iClickDecrease.onCLickDecrease(newCount, productModel.getProductId());
            }
        });
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
    public void clearAll(){
        countList.clear();
        productModelList.clear();
        notifyDataSetChanged();
    }
}
