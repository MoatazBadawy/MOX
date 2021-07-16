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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.moataz.mox.R;
import com.moataz.mox.data.model.article.Item;
import java.util.List;

public class MediumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> items = null;

    public void setMediumList(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MediumViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_articles,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item mediumArticle = items.get(position);
        ((MediumViewHolder) holder).setData(mediumArticle);
        ((MediumViewHolder) holder).setOnClick(mediumArticle);
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    static class MediumViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView source;
        private final TextView author;

        MediumViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_article);
            title = itemView.findViewById(R.id.title_article);
            source = itemView.findViewById(R.id.source_article);
            author = itemView.findViewById(R.id.author_name_article);
        }

        void setData(Item mediumArticle) {
            Glide.with(itemView.getContext())
                    .load(mediumArticle.getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(image);

            title.setText(mediumArticle.getTitle());
            source.setText(R.string.medium);
            author.setText(mediumArticle.getAuthor());
        }

        /**
         * When the user click on article it will open the article
         * in new Tab using ChromeCustomTab
         */
        void setOnClick(Item mediumArticle) {
            itemView.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setStartAnimations(itemView.getContext(),R.anim.slide_in_right, R.anim.slide_out_left);
                customTabIntent.setExitAnimations(itemView.getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
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
