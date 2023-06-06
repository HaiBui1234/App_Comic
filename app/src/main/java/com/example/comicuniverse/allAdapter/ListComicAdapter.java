package com.example.comicuniverse.allAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicuniverse.AllInterface.InterFaceFavorite;
import com.example.comicuniverse.allModel.ListComic;
import com.example.comicuniverse.allModel.UserModel;
import com.exmple.comicuniverse.R;

import java.util.ArrayList;

public class ListComicAdapter extends RecyclerView.Adapter<ListComicAdapter.ListViewHodel> {
    public static final int HORZONTAL=1;
    public static final int VERTICAL=2;
    Context mContext;
    private ArrayList<ListComic> dataComic;
    InterFaceFavorite interFaceFavorite;

    private UserModel userModel;
    public ListComicAdapter(Context mContext,UserModel userModel, InterFaceFavorite interFaceFavorite) {
        this.mContext = mContext;
        this.interFaceFavorite=interFaceFavorite;
        this.userModel=userModel;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setdataList(ArrayList<ListComic> dataComic){
        this.dataComic=dataComic;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.listcomic_item,parent,false);
        return new ListViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHodel holder, int position) {
    ListComic data=dataComic.get(position);
    if (data==null){
        return;
    }
    if (HORZONTAL==holder.getItemViewType()){
        HorizontalAdapter adapter=new HorizontalAdapter(mContext,data.getDataCate(),userModel);
        LinearLayoutManager manager=new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
        holder.tv_Catagory.setText(data.getTitle());
        holder.Rc_ListComic.setLayoutManager(manager);
        holder.Rc_ListComic.setAdapter(adapter);

    }else if (VERTICAL==holder.getItemViewType()){
        GridViewAdapter adapter=new GridViewAdapter(mContext,data.getData(),interFaceFavorite);
        GridLayoutManager manager=new GridLayoutManager(mContext,3,RecyclerView.VERTICAL,false);
        holder.tv_Catagory.setText(data.getTitle());
        holder.Rc_ListComic.setLayoutManager(manager);
        holder.Rc_ListComic.setAdapter(adapter);


    }
    }
    @Override
    public int getItemViewType(int position) {
        return dataComic.get(position).getType();
    }
    @Override
    public int getItemCount() {
        if (dataComic==null){
            return 0;
        }
        return dataComic.size();
    }

    public static class ListViewHodel extends RecyclerView.ViewHolder {
         TextView tv_Catagory;
         RecyclerView Rc_ListComic;
        public ListViewHodel(@NonNull View itemView) {
            super(itemView);
            tv_Catagory=itemView.findViewById(R.id.tv_Catagory);
            Rc_ListComic=itemView.findViewById(R.id.Rc_ListComic);
        }
    }
}
