package com.example.comicuniverse.allAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.comicuniverse.DetailComic;
import com.example.comicuniverse.allModel.ComicModel;
import com.exmple.comicuniverse.R;

import java.util.ArrayList;

public class ViewpagerSlideAdapter extends PagerAdapter{
    private final Context mContext;
    private ArrayList<ComicModel> modelArrayList;

    public ViewpagerSlideAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setdataSLide(ArrayList<ComicModel> modelArrayList){
        this.modelArrayList=modelArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.slide_item,container,false);
        ComicModel model=modelArrayList.get(position);
        ImageView img_slide=view.findViewById(R.id.img_slide);
        TextView tvViewSlide=view.findViewById(R.id.ViewSlide);
        if (model!=null){
            Glide.with(mContext)
                    .load(model.getImg_comic()).error(R.drawable.ic_action_erron)
                    .into(img_slide);
            tvViewSlide.setText(String.valueOf(model.getViewComic()));
        }
        img_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DetailComic.class);
                intent.putExtra("Comic",model);
                mContext.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (modelArrayList==null){
            return 0;
        }
        return modelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
