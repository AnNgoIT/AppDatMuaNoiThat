package ute.fit.noithatapp.Activity.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ute.fit.noithatapp.Activity.MainActivity;
import ute.fit.noithatapp.Model.CategoryModel;
import ute.fit.noithatapp.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    public ArrayList<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(ArrayList<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    private ArrayList<CategoryModel> categoryModelList;
    private IClick iClick;
    public interface IClick{
        void onClickCategoryItem(CategoryModel categoryModel);
    }

    public CategoryAdapter(ArrayList<CategoryModel> categoryModelList, IClick iClick) {
        this.categoryModelList = categoryModelList;
        this.iClick=iClick;
    }
    public void updateCategories(ArrayList<CategoryModel> categories){
        this.categoryModelList=categories;
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
        holder.textViewCategory.setText(categoryModel.getName());
        holder.textViewCategory.setOnClickListener(view -> {
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
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategory=itemView.findViewById(R.id.textViewCategory);
        }
    }
}
