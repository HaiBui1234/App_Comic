package com.example.comicuniverse.allAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.comicuniverse.DetailCategory;
import com.example.comicuniverse.allModel.CategoryModel;
import com.example.comicuniverse.allModel.ComicModel;
import com.example.comicuniverse.allModel.UserModel;
import com.exmple.comicuniverse.R;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HoriViewHodel> {
    private Context mContext;
    private ArrayList<CategoryModel> modelArrayList;

    private UserModel userModel;
    public HorizontalAdapter(Context mContext, ArrayList<CategoryModel> modelArrayList,UserModel userModel) {
        this.mContext = mContext;
        this.modelArrayList = modelArrayList;
        this.userModel=userModel;
    }

    @NonNull
    @Override
    public HoriViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_item, parent, false);
        return new HoriViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoriViewHodel holder, int position) {
        CategoryModel data = modelArrayList.get(position);
        if (data == null) {
            return;
        }
        holder.tv_name.setText(data.getName_Catagory());
        Glide.with(mContext)
                .load(data.getImage_Category()).error(R.drawable.ic_action_erron)
                .into(holder.img_horizontal);
        holder.LineHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DetailCategory.class);
                intent.putExtra("user",userModel);
                intent.putExtra("Category",data);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (modelArrayList == null) {
            return 0;
        }
        return modelArrayList.size();
    }

    public static class HoriViewHodel extends RecyclerView.ViewHolder {
        LinearLayout LineHorizontal;
        ImageView img_horizontal;
        TextView tv_name;

        public HoriViewHodel(@NonNull View itemView) {
            super(itemView);
            img_horizontal = itemView.findViewById(R.id.img_horizontal_item);
            tv_name = itemView.findViewById(R.id.tv_name_horizontal_item);
            LineHorizontal=itemView.findViewById(R.id.id_LineHorizontal);
        }
    }
}
