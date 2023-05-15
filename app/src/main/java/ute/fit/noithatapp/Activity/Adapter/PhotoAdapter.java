package ute.fit.noithatapp.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ute.fit.noithatapp.Model.PhotoModel;
import ute.fit.noithatapp.R;

public class PhotoAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<PhotoModel> photoModelList;

    public PhotoAdapter(Context context, ArrayList<PhotoModel> photoModelList) {
        this.context = context;
        this.photoModelList = photoModelList;
    }

    @Override
    public int getCount() {
        if (photoModelList!=null)
            return photoModelList.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo,container,false);
        ImageView imgPhoto=view.findViewById(R.id.imgPhoto);
        PhotoModel photoModel=photoModelList.get(position);
        if(photoModel!=null){
            Glide.with(context).load(photoModel.getImg()).into(imgPhoto);
        }
        //add view group
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
