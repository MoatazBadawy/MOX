package com.moataz.mox.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.moataz.mox.R;
import com.moataz.mox.data.model.news.Item;

import java.util.List;
import java.util.Objects;

public class CNNAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> items = null;

    public void setCNNList(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_article,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item News = items.get(position);
        ((NewsViewHolder) holder).setData(News);
        ((NewsViewHolder) holder).setOnClick(News);
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView source;
        private final TextView author;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_medium);
            title = itemView.findViewById(R.id.title_medium);
            source = itemView.findViewById(R.id.source_medium);
            author = itemView.findViewById(R.id.author_name_medium);
        }

        void setData(Item news) {
            Glide.with(itemView.getContext())
                    .load(Objects.requireNonNull(news.getEnclosure()).getLink())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(image);

            title.setText(news.getTitle());
            source.setText(R.string.cnn);
            author.setText(R.string.author);
        }

        /**
         * When the user click on article it will open the article
         * in new Tab using ChromeCustomTab
         */
        void setOnClick(Item mediumArticle) {
            itemView.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setExitAnimations(itemView.getContext(), android.R.anim.fade_in, android.R.anim.slide_out_right);
                customTabIntent.setShowTitle(true);
                openCustomTabs(itemView.getContext(),customTabIntent.build(),Uri.parse(mediumArticle.getLink()));
            });
        }

        static void openCustomTabs(Context activity, CustomTabsIntent customTabsIntent, Uri uri) {
            String packageName = "com.android.chrome";
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity,uri);
        }
    }
}
