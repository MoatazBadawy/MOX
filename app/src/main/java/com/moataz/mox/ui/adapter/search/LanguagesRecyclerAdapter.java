package com.moataz.mox.ui.adapter.search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.moataz.mox.R;
import com.moataz.mox.data.db.Data;

import java.util.List;

public class LanguagesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Data> items;
    private final Context context;

    @SuppressLint("NotifyDataSetChanged")
    public void setFirstRecyList(List<Data> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public LanguagesRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_search,
                        parent,
                        false
                )
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data favorite = items.get(position);
        ((TopViewHolder) holder).setData(favorite);
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    static class TopViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final Activity activity = new Activity();

        TopViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_topic_search);
            title = itemView.findViewById(R.id.title_topic_search);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        void setData(Data favorite) {

            Glide.get(itemView.getContext()).clearMemory();
            // load images in MainThread
            activity.runOnUiThread(() -> {
                Glide.with(itemView.getContext())
                        .load((favorite.getImageUrl()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .skipMemoryCache(true)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(image);

                Glide.get(itemView.getContext()).clearMemory();
            });

            title.setText(favorite.getTitle());
            itemView.setOnClickListener(v -> { });
        }
    }
}