package com.moataz.mox.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moataz.mox.R;
import com.moataz.mox.data.model.ArticlesModel;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<ArticlesModel> items;
    private Context context;

    public NewsAdapter(Context context, List<ArticlesModel> movieList) {
        this.context = context;
        this.items = movieList;
    }

    public void setNewsList(List<ArticlesModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {

        Glide.with(context)
                .load(this.items.get(position).getUrlToImage())
                .into(holder.imageNews);
        holder.titleNews.setText(this.items.get(position).getTitle());
        holder.descriptionNews.setText(this.items.get(position).getDetails());
        holder.nameNews.setText(this.items.get(position).getPages());
        holder.authorNews.setText(this.items.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        if(this.items != null) {
            return this.items.size();
        }
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
         ImageView imageNews;
         TextView titleNews;
         TextView descriptionNews;
         TextView nameNews;
         TextView authorNews;
         Context context;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageNews = itemView.findViewById(R.id.image_news);
            titleNews = itemView.findViewById(R.id.title);
            descriptionNews = itemView.findViewById(R.id.description);
            nameNews = itemView.findViewById(R.id.sorce_name);
            authorNews = itemView.findViewById(R.id.author_name);
        }
    }
}
