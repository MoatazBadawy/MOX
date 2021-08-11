package com.moataz.mox.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.moataz.mox.R;
import com.moataz.mox.data.model.news.Item;
import com.moataz.mox.ui.view.fragment.FavouriteFragment;

import java.util.List;
import java.util.Objects;

public class CNNAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> items = null;

    @SuppressLint("NotifyDataSetChanged")
    public void setCNNList(List<Item> items) {
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item News = items.get(position);
        ((NewsViewHolder) holder).setData(News);
        ((NewsViewHolder) holder).setOnClick(News);
        ((NewsViewHolder) holder).sendData(News);
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView description;
        private final TextView source;
        private final TextView author;
        private final Button sendDataButton;
        private final Activity activity = new Activity();

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_news);
            title = itemView.findViewById(R.id.title_news);
            description = itemView.findViewById(R.id.description_news);
            source = itemView.findViewById(R.id.source_news);
            author = itemView.findViewById(R.id.author_name_news);
            sendDataButton = itemView.findViewById(R.id.button_save);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        void setData(Item news) {
            Glide.get(itemView.getContext()).clearMemory();
            // load images in MainThread
            activity.runOnUiThread(() -> {
                Glide.with(itemView.getContext())
                        .load((Objects.requireNonNull(news.getEnclosure())).getLink())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .skipMemoryCache(true)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(image);

                Glide.get(itemView.getContext()).clearMemory();
            });

            title.setText(news.getTitle());
            description.setText(Html.fromHtml(Objects.requireNonNull(news.getDescription()).replaceAll("(<(/)img>)|(<img.+?>)", ""), Html.FROM_HTML_MODE_COMPACT));
            source.setText(R.string.cnn);
            author.setText(R.string.author);
        }

        /**
         * When the user click on article it will open the article
         * in new Tab using ChromeCustomTab
         */
        void setOnClick(Item news) {
            itemView.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setStartAnimations(itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                customTabIntent.setExitAnimations(itemView.getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                customTabIntent.setShowTitle(true);
                openCustomTabs(itemView.getContext(), customTabIntent.build(), Uri.parse(news.getLink()));
            });
        }

        void sendData(Item news) {
            sendDataButton.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("title", news.getTitle());
                bundle.putString("image", Objects.requireNonNull(news.getEnclosure()).getLink());
                bundle.putString("source", "CNN");
                bundle.putString("author", "CNN editor's");
                bundle.putString("link", news.getLink());
                FavouriteFragment favouriteFragment = new FavouriteFragment();
                favouriteFragment.setArguments(bundle);
            });
        }

        static void openCustomTabs(Context activity, CustomTabsIntent customTabsIntent, Uri uri) {
            String packageName = "com.android.chrome";
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        }
    }
}
