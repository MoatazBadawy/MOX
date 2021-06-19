package com.moataz.mox.ui.adapter;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.moataz.mox.R;
import com.moataz.mox.data.model.Article;

import java.util.List;
import java.util.Objects;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Article> items = null;

    public void setNewsList(List<Article> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_news,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Article article = items.get(position);
        ((NewsViewHolder) holder).setNewsData(article);
        ((NewsViewHolder) holder).setOnClick(article);
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
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

        void setNewsData(Article article) {
            Glide.with(itemView.getContext())
                    .load(article.getUrlToImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageNews);

            titleNews.setText(article.getTitle());
            descriptionNews.setText(article.getDescription());
            nameNews.setText(Objects.requireNonNull(article.getSource()).getId());
            authorNews.setText(article.getAuthor());
        }

        /**
         * When the user click on article it will open the article
         * in new Tab using ChromeCustomTab
         */
        void setOnClick(Article article) {
            itemView.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setExitAnimations(itemView.getContext(), android.R.anim.fade_in, android.R.anim.slide_out_right);
                customTabIntent.setShowTitle(true);
                openCustomTabs(itemView.getContext(),customTabIntent.build(),Uri.parse(article.getUrl()));
            });
        }

        static void openCustomTabs(Context activity, CustomTabsIntent customTabsIntent, Uri uri) {
            String packageName = "com.android.chrome";
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity,uri);
        }
    }
}
