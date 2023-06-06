package com.example.comicuniverse.allAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.comicuniverse.AllInterface.InterFaceFavorite;
import com.example.comicuniverse.DetailComic;
import com.example.comicuniverse.allModel.ComicModel;
import com.example.comicuniverse.allModel.FavoriteComic;
import com.exmple.comicuniverse.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.VerticalHodel> {

    Context mContext;
    ArrayList<ComicModel> modelArrayList;
    InterFaceFavorite interFaceFavorite;
    @SuppressLint("NotifyDataSetChanged")
    public GridViewAdapter(Context mContext, ArrayList<ComicModel> modelArrayList, InterFaceFavorite interFaceFavorite) {
        this.mContext = mContext;
        this.modelArrayList = modelArrayList;
        this.interFaceFavorite=interFaceFavorite;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public VerticalHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.grid_item,parent,false);
        return new VerticalHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalHodel holder, int position) {
    ComicModel data=modelArrayList.get(position);
    if (data==null){
        return;
    }
    holder.tv_title.setText(data.getTitle());
    holder.tv_name.setText((data.getName_Comic()));
    Glide.with(mContext).load(data.getImg_comic()).error(R.drawable.ic_action_erron)
                .into(holder.img_vertical);
    holder.Grid_layout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(mContext,DetailComic.class);
            intent.putExtra("Comic",data);
            mContext.startActivity(intent);
        }
    });
   holder.imgbtnBottomSheet.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(mContext,R.style.BottomSheetDialogTheme);
           bottomSheetDialog.setContentView(R.layout.bottomsheet_item);
           LinearLayout id_addFavorite=bottomSheetDialog.findViewById(R.id.id_addFavorite);
           ImageView imageView=bottomSheetDialog.findViewById(R.id.img_bottom);
           TextView textView=bottomSheetDialog.findViewById(R.id.nameComic_bottom);
           TextView textViewadd=bottomSheetDialog.findViewById(R.id.id_tv);
           assert imageView != null;
           Glide.with(mContext).load(data.getImg_comic()).error(R.drawable.ic_action_erron)
                   .into(imageView);
           assert textView != null;
           textView.setText(data.getName_Comic());
           if (data.isFavouriteComic()){
               assert textViewadd != null;
               textViewadd.setText("Xóa khỏi danh sách yêu thích");
           }else {
               assert textViewadd != null;
               textViewadd.setText("Thêm vào danh sách yêu thích");
           }
           assert id_addFavorite != null;
           id_addFavorite.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (!data.isFavouriteComic()) {
                       FavoriteComic favoriteComic = new FavoriteComic(data.getId_Comic(), data.getId_Category(), data.getTitle(), data.getDate(),
                               data.getImg_comic(), data.getName_Author(), data.getName_Comic(),data.getViewComic(),data.getChapterModels(),data.getDesScription(), false, true);
                       interFaceFavorite.addFavorite(favoriteComic, data.getId_Comic());
                       bottomSheetDialog.dismiss();
                   }else {
                       interFaceFavorite.adDeleteFavorite(data.getId_Comic());
                       bottomSheetDialog.dismiss();
                   }
               }
           });
           bottomSheetDialog.show();
       }
   });
    }

    @Override
    public int getItemCount() {
        if (modelArrayList==null){
            return 0;
        }
        return modelArrayList.size();
    }



    public static class VerticalHodel extends RecyclerView.ViewHolder {
        ImageView img_vertical;
        ImageButton imgbtnBottomSheet;
        LinearLayout Grid_layout;
        TextView tv_name,tv_title;
        public VerticalHodel(@NonNull View itemView) {
            super(itemView);
            img_vertical=itemView.findViewById(R.id.img_vertical_item);
            tv_name=itemView.findViewById(R.id.tv_name_vertical_item);
            tv_title=itemView.findViewById(R.id.tv_title_vertical);
            Grid_layout=itemView.findViewById(R.id.Grid_layout);
            imgbtnBottomSheet=itemView.findViewById(R.id.id_bottomSheet);
        }
    }
}
