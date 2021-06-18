package com.moataz.mox.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.moataz.mox.R;
import com.moataz.mox.data.model.Article;
import com.moataz.mox.data.model.response.ArticleResponse;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArticleResponse items = null;

    public void setNewsList(ArticleResponse items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == R.id.image_news) {
            return new NewsViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.list_news,
                            parent,
                            false
                    )
            );
        } else throw new IllegalArgumentException("unknown view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == R.id.image_news) {
            Article firstImageModel = items.getArticles().get(position);
            ((NewsViewHolder) holder).setNewsData(firstImageModel);
        }
    }

    @Override
    public int getItemCount() {
            if (items ==null) return 0;
            return items.getArticles().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && position < items.getArticles().size()) {
            return R.id.image_news;
        }
        return R.id.image_news;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageNews;
        private final TextView titleNews;
        private final TextView descriptionNews;
        private final TextView nameNews;
        private final TextView authorNews;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageNews = itemView.findViewById(R.id.image_news);
            titleNews = itemView.findViewById(R.id.title);
            descriptionNews = itemView.findViewById(R.id.description);
            nameNews = itemView.findViewById(R.id.sorce_name);
            authorNews = itemView.findViewById(R.id.author_name);
        }

        void setNewsData(Article articleModel) {
                Glide.with(itemView.getContext())
                        .load(articleModel.getUrlToImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageNews);

                titleNews.setText(articleModel.getTitle());
                descriptionNews.setText(articleModel.getDescription());
                nameNews.setText(articleModel.getSource().getId());
                authorNews.setText(articleModel.getAuthor());
        }
    }
}
