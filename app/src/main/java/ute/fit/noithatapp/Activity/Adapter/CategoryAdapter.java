package ute.fit.noithatapp.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ute.fit.noithatapp.Activity.ProductByCategoryActivity;
import ute.fit.noithatapp.Interface.ItemClickListener;
import ute.fit.noithatapp.Model.CategoryModel;
import ute.fit.noithatapp.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private ArrayList<CategoryModel> categoryModelList;
    private IClick iClick;
    private Context context;

    //interface for the event click
    public interface IClick{
        void onClickCategoryItem(CategoryModel categoryModel);
    }

    public CategoryAdapter(Context context,ArrayList<CategoryModel> categoryModelList, IClick iClick) {
        this.context=context;
        this.categoryModelList = categoryModelList;
        this.iClick=iClick;
    }
    public void updateCategories(ArrayList<CategoryModel> categories){
        this.categoryModelList=categories;
    }

    public ArrayList<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(ArrayList<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.textViewCategory.setText(categoryModelList.get(position).getName());

//        if(categoryModel==null)
//            return;
//        //text
//        holder.textViewCategory.setText(categoryModel.getName());
//        holder.textViewCategory.setOnClickListener(view -> {
//            iClick.onClickCategoryItem(categoryModel);
//        });
//
//        //image
//        Glide.with(context).load(categoryModel.getImage()).into(holder.imgViewCategory);
//        holder.imgViewCategory.setOnClickListener(view -> {
//            iClick.onClickCategoryItem(categoryModel);
//        });
        holder.setItemClickListener((view, position1, isLongClick) -> {
            Intent i = new Intent(context, ProductByCategoryActivity.class);
            i.putExtra("id", 1);
            view.getContext().startActivity(i);
        });
        Glide.with(context).load(categoryModelList.get(position).getImage()).into(holder.imgViewCategory);


    }

    @Override
    public int getItemCount() {
        if(categoryModelList!=null){
            return categoryModelList.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView textViewCategory;
        private ImageView imgViewCategory;
        private ItemClickListener itemClickListener;

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
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            textViewCategory=itemView.findViewById(R.id.textViewCategory);
            imgViewCategory=itemView.findViewById(R.id.imgbtnCategory);

        }
    }
}
