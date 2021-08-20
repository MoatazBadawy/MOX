package com.moataz.mox.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.moataz.mox.R;
import com.moataz.mox.data.db.Favorite;
import com.moataz.mox.data.db.SQLiteDatabaseManager;
import com.moataz.mox.data.model.article.Item;

import java.util.List;

public class MediumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> items = null;
    SQLiteDatabaseManager sqliteManager;

    @SuppressLint("NotifyDataSetChanged")
    public void setMediumList(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public MediumAdapter(Context context) {
        sqliteManager = new SQLiteDatabaseManager(context);
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
        ((MediumViewHolder) holder).setOnClickItem(mediumArticle);
        ((MediumViewHolder) holder).setOnClickButtons(mediumArticle);
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    class MediumViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView source;
        private final TextView author;
        private final Button more;
        private final Button share;
        private final Button save;
        private final ImageButton saveButton;
        private final Activity activity = new Activity();

        MediumViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_article);
            title = itemView.findViewById(R.id.title_article);
            source = itemView.findViewById(R.id.source_article);
            author = itemView.findViewById(R.id.author_name_article);

            more = itemView.findViewById(R.id.more_button_article_onClick);
            share = itemView.findViewById(R.id.share_button_article_onClick);
            save = itemView.findViewById(R.id.save_button_article_onClick);
            saveButton = itemView.findViewById(R.id.save_button_article_list);
        }

        void setData(Item mediumArticle) {
            Glide.get(itemView.getContext()).clearMemory();
            // load images in MainThread
            activity.runOnUiThread(() -> {
                Glide.with(itemView.getContext())
                        .load(mediumArticle.getThumbnail())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .skipMemoryCache(true)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(image);

                Glide.get(itemView.getContext()).clearMemory();
            });

            title.setText(mediumArticle.getTitle());
            source.setText(R.string.medium);
            author.setText(mediumArticle.getAuthor());
        }

        void setOnClickButtons(Item news) {
            more.setOnClickListener(v -> openBottomSheet(v, news));

            share.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareSubText = "Check out this great article from *MOX APP*" + '\n';
                String shareBodyText = shareSubText + news.getLink();
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                v.getContext().startActivity(Intent.createChooser(shareIntent, "Share With"));
            });

            save.setOnClickListener(v -> {
                // here we will insert the data into our dataBase and we will send it to the fragment
                sqliteManager.insertFavorite(new Favorite(news.getTitle(), news.getThumbnail(), news.getLink(), news.getAuthor()));
                saveButton.setBackgroundResource(R.drawable.ic_black_favorite_24);
                saveButton.setSelected(true);
            });
        }

        /**
         * When the user click on article it will open the article
         * in new Tab using ChromeCustomTab
         */
        void setOnClickItem(Item article) {
            itemView.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setStartAnimations(itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                customTabIntent.setExitAnimations(itemView.getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                customTabIntent.setShowTitle(true);
                openCustomTabs(itemView.getContext(), customTabIntent.build(), Uri.parse(article.getLink()));
            });

            itemView.setOnLongClickListener(v -> {
                openSecretBottomSheet(v);
                return false;
            });
        }

        void openCustomTabs(Context activity, CustomTabsIntent customTabsIntent, Uri uri) {
            String packageName = "com.android.chrome";
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        }

        @SuppressLint("InflateParams")
        void openBottomSheet(View view, Item article) {
            Context context = view.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bottom_sheet, null);
            LinearLayout save = view.findViewById(R.id.save);
            LinearLayout share = view.findViewById(R.id.share);
            LinearLayout open = view.findViewById(R.id.open_in_tab);

            final Dialog mBottomSheetDialog = new Dialog(context, R.style.BottomSheetDialogTheme);
            mBottomSheetDialog.setContentView(view);
            mBottomSheetDialog.setCancelable(true);
            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
            mBottomSheetDialog.show();


            save.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Clicked Backup", Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            });

            share.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareSubText = "Check out this great article from *MOX APP*" + '\n';
                String shareBodyText = shareSubText + article.getLink();
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                v.getContext().startActivity(Intent.createChooser(shareIntent, "Share With"));
                mBottomSheetDialog.dismiss();
            });

            open.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setStartAnimations(v.getContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                customTabIntent.setExitAnimations(v.getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                customTabIntent.setShowTitle(true);
                openCustomTabs(itemView.getContext(), customTabIntent.build(), Uri.parse(article.getLink()));
                mBottomSheetDialog.dismiss();
            });
        }

        @SuppressLint("InflateParams")
        void openSecretBottomSheet(View view) {
            Context context = view.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bottom_sheet_secret, null);
            Button buttonSecret = view.findViewById(R.id.button_Secret);

            final Dialog mBottomSheetDialog = new Dialog(context, R.style.BottomSheetDialogTheme);
            mBottomSheetDialog.setContentView(view);
            mBottomSheetDialog.setCancelable(true);
            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
            mBottomSheetDialog.show();


            buttonSecret.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setStartAnimations(v.getContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                customTabIntent.setExitAnimations(v.getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                customTabIntent.setShowTitle(true);
                openCustomTabs(itemView.getContext(), customTabIntent.build(), Uri.parse("https://github.com/MoatazBadawy/MOX"));
                mBottomSheetDialog.dismiss();
            });
        }
    }
}
