package com.moataz.mox.ui.adapter.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import com.moataz.mox.data.db.Favorite;
import com.moataz.mox.data.db.SQLiteDatabaseManager;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Favorite> items;
    SQLiteDatabaseManager sqliteManager;

    public FavoriteAdapter(List<Favorite> items, Context context) {
        this.items = items;
        sqliteManager = new SQLiteDatabaseManager(context);
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
        Favorite favorite = items.get(position);
        ((FavoriteViewHolder) holder).setData(favorite);
    }

    public void deleteItem(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Favorite favorite = items.get(direction);
        sqliteManager.deleteFavorite(favorite.getImage());
        items.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearAll () {
        items.clear();
        notifyDataSetChanged();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final Activity activity = new Activity();

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_article_save);
            title = itemView.findViewById(R.id.title_article_save);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        void setData(Favorite favorite) {

            Glide.get(itemView.getContext()).clearMemory();
            // load images in MainThread
            activity.runOnUiThread(() -> {
                Glide.with(itemView.getContext())
                        .load((favorite.getImage()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .skipMemoryCache(true)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(image);

                Glide.get(itemView.getContext()).clearMemory();
            });
            title.setText(favorite.getTitle());

            itemView.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setStartAnimations(itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                customTabIntent.setExitAnimations(itemView.getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                customTabIntent.setShowTitle(true);
                openCustomTabs(itemView.getContext(), customTabIntent.build(), Uri.parse(favorite.getLink()));
            });
        }

        void openCustomTabs(Context activity, @NonNull CustomTabsIntent customTabsIntent, Uri uri) {
            String packageName = "com.android.chrome";
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        }
    }
}
