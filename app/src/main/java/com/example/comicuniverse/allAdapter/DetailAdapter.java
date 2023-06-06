package com.example.comicuniverse.allAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicuniverse.AllInterface.InterfaceChapter;
import com.example.comicuniverse.DetailChapter;
import com.example.comicuniverse.allModel.ChapterModel;
import com.exmple.comicuniverse.R;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHodelChapter> {
    private Context mContext;
    private ArrayList<ChapterModel> chapterModels;
    private InterfaceChapter interfaceChapter;
    public DetailAdapter(Context mContext, ArrayList<ChapterModel> chapterModels,InterfaceChapter interfaceChapter) {
        this.mContext = mContext;
        this.chapterModels = chapterModels;
        this.interfaceChapter=interfaceChapter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodelChapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.detail_comic,parent,false);
        return new ViewHodelChapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelChapter holder, int position) {
    ChapterModel model=chapterModels.get(position);
    if (model==null){
        return;
    }
    holder.tvname.setText(model.getName_Chapter());
    holder.tvdate.setText(model.getDayPost_Chapter());
    holder.tvView.setText(String.valueOf(model.getViewChapter()));
    holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(mContext, DetailChapter.class);
            intent.putExtra("Chapter",model.getLinkChapter());
            mContext.startActivity(intent);
            interfaceChapter.UpdateChapter(model);
            interfaceChapter.addHistory();
        }
    });
    }

    @Override
    public int getItemCount() {
        if (chapterModels==null){
            return 0;
        }
        return chapterModels.size();
    }

    public static class ViewHodelChapter extends RecyclerView.ViewHolder {
        TextView tvname,tvdate,tvView;
        LinearLayout mLinearLayout;
        public ViewHodelChapter(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.nameChapter);
            tvdate=itemView.findViewById(R.id.dateChapter);
            mLinearLayout=itemView.findViewById(R.id.lineChapter);
            tvView=itemView.findViewById(R.id.ChapterView);
        }
    }
}
