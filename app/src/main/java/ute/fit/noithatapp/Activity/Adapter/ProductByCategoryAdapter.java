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

import java.util.List;

import ute.fit.noithatapp.Interface.ItemClickListener;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class ProductByCategoryAdapter extends RecyclerView.Adapter<ProductByCategoryAdapter.ViewHolder>{


    List<ProductModel> productList;
    Context context;

    public ProductByCategoryAdapter(List<ProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductByCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_productbycategory, parent, false);

        return new ProductByCategoryAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductByCategoryAdapter.ViewHolder holder, int position) {
        holder.title.setText(productList.get(position).getName());
        holder.itemView.getContext().getResources().getIdentifier(productList.get(position).getName(), "drawable", holder.itemView.getContext().getPackageName());
//        holder.setItemClickListener((view, position1, isLongClick) -> {
//            Intent i = new Intent(context, ProductByCategoryActivity.class);
//            view.getContext().startActivity(i);
//        });
        Glide.with(context)
                .load(productList.get(position).getName())
                .into(holder.pic);
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        private ItemClickListener itemClickListener;
        TextView title;
        ImageView pic;

        public void setItemClickListener(ItemClickListener itemClickListener) {

            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            title = itemView.findViewById(R.id.productTitle);
            pic = itemView.findViewById(R.id.productPic);
        }
    }
}
