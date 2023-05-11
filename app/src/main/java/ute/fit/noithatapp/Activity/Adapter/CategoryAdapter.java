package ute.fit.noithatapp.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ute.fit.noithatapp.Model.CategoryModel;
import ute.fit.noithatapp.R;
import com.bumptech.glide.Glide;

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
        CategoryModel categoryModel=categoryModelList.get(position);
        if(categoryModel==null)
            return;
        //text
        holder.textViewCategory.setText(categoryModel.getName());
        holder.textViewCategory.setOnClickListener(view -> {
            iClick.onClickCategoryItem(categoryModel);
        });

        //image
        Glide.with(context).load(categoryModel.getImage()).into(holder.imgViewCategory);
        holder.imgViewCategory.setOnClickListener(view -> {
            iClick.onClickCategoryItem(categoryModel);
        });


    }

    @Override
    public int getItemCount() {
        if(categoryModelList!=null){
            return categoryModelList.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewCategory;
        private ImageView imgViewCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategory=itemView.findViewById(R.id.textViewCategory);
            imgViewCategory=itemView.findViewById(R.id.imgbtnCategory);

        }
    }
}