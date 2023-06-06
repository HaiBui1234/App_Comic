package com.example.comicuniverse.allAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comicuniverse.DetailComic;
import com.example.comicuniverse.allModel.ComicModel;
import com.exmple.comicuniverse.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHodel> implements Filterable {
    private Context mContext;
    private ArrayList<ComicModel> modelArrayList;
    private ArrayList<ComicModel> modelArrayListOld;

    public SearchAdapter(Context mContext, ArrayList<ComicModel> modelArrayList) {
        this.mContext = mContext;
        this.modelArrayList = modelArrayList;
        this.modelArrayListOld = modelArrayList;
    }

    @NonNull
    @Override
    public SearchHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.topcomic_item, parent, false);
        return new SearchHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHodel holder, int position) {
        ComicModel comicModel = modelArrayList.get(position);
        holder.tvname.setText(comicModel.getName_Comic());
        holder.tvAuthor.setText("Post by: " + comicModel.getName_Author());
        holder.tvSearch.setText(String.valueOf(comicModel.getViewComic()));
        Glide.with(mContext).load(comicModel.getImg_comic()).error(R.drawable.ic_action_erron)
                .into(holder.img);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DetailComic.class);
                intent.putExtra("Comic",comicModel);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (modelArrayList != null) {
            return modelArrayList.size();
        }
        return 0;
    }


    public class SearchHodel extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvname, tvAuthor,tvSearch;
        LinearLayout linearLayout;
        public SearchHodel(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView3);
            tvname = itemView.findViewById(R.id.nameTop);
            tvAuthor = itemView.findViewById(R.id.AuthorTop);
            linearLayout=itemView.findViewById(R.id.TopComic);
            tvSearch=itemView.findViewById(R.id.ViewSeach);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    modelArrayList = modelArrayListOld;
                } else {
                    ArrayList<ComicModel> listComic = new ArrayList<>();
                    for (ComicModel comicModel : modelArrayListOld) {
                        if (comicModel.getName_Comic().toLowerCase().contains(strSearch.toLowerCase())) {
                            listComic.add(comicModel);
                        }
                    }
                    modelArrayList = listComic;
                }
                FilterResults results = new FilterResults();
                results.values = modelArrayList;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                modelArrayList = (ArrayList<ComicModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
