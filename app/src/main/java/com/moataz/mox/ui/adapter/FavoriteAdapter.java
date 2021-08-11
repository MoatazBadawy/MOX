package com.moataz.mox.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Item> items = new ArrayList<>();;
    private Context context;

    @SuppressLint("NotifyDataSetChanged")
    public void setFavoriteList(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_save,
                        parent,
                        false
                )
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FavoriteViewHolder) holder).setData();
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView source;
        private final TextView author;
        private final Activity activity = new Activity();

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_article_save);
            title = itemView.findViewById(R.id.title_article_save);
            source = itemView.findViewById(R.id.source_article_save);
            author = itemView.findViewById(R.id.author_name_article_save);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        void setData() {
            FavouriteFragment favouriteFragment = new FavouriteFragment();
            Bundle bundle = favouriteFragment.getArguments();
            String getTitle = bundle.getString("title");
            String getImage = bundle.getString("image");
            String getSource = bundle.getString("source");
            String getAuthor = bundle.getString("author");
            String getLink = bundle.getString("link");

            Glide.get(itemView.getContext()).clearMemory();
            // load images in MainThread
            activity.runOnUiThread (() -> {
                Glide.with(itemView.getContext())
                        .load((getImage))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .skipMemoryCache(true)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(image);

                Glide.get(itemView.getContext()).clearMemory();
            });

            title.setText(getTitle);
            source.setText(getSource);
            author.setText(getAuthor);

            itemView.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setStartAnimations(itemView.getContext(),R.anim.slide_in_right, R.anim.slide_out_left);
                customTabIntent.setExitAnimations(itemView.getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                customTabIntent.setShowTitle(true);
                openCustomTabs(itemView.getContext(),customTabIntent.build(),Uri.parse(getLink));
            });
        }

        static void openCustomTabs(Context activity, CustomTabsIntent customTabsIntent, Uri uri) {
            String packageName = "com.android.chrome";
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity,uri);
        }
    }
}
