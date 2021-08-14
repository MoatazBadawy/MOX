package com.moataz.mox.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
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
import androidx.annotation.RequiresApi;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.moataz.mox.R;

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
        ((NewsViewHolder) holder).setOnClickItems(News);
        ((NewsViewHolder) holder).setOnClickButtons(News);
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView description;
        private final TextView source;
        private final TextView author;
        private final Button more;
        private final Button share;
        private final Button save;
        private final ImageButton saveButton;
        private final Activity activity = new Activity();

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_news);
            title = itemView.findViewById(R.id.title_news);
            description = itemView.findViewById(R.id.description_news);
            source = itemView.findViewById(R.id.source_news);
            author = itemView.findViewById(R.id.author_name_news);
            more = itemView.findViewById(R.id.more_button_news_onClick);
            share = itemView.findViewById(R.id.share_button_news_onClick);
            save = itemView.findViewById(R.id.save_button_news_onClick);
            saveButton = itemView.findViewById(R.id.save_button_news_list);
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
        void setOnClickItems(Item news) {
            itemView.setOnClickListener(v -> {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                customTabIntent.setToolbarColor(Color.parseColor("#ffffff"));
                customTabIntent.setStartAnimations(itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                customTabIntent.setExitAnimations(itemView.getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                customTabIntent.setShowTitle(true);
                openCustomTabs(itemView.getContext(), customTabIntent.build(), Uri.parse(news.getLink()));
            });

            itemView.setOnLongClickListener(v -> {
                openBottomSheet(v, news);
                return false;
            });
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
                SharedPreferences preferences = v.getContext().getSharedPreferences("MY_PREFS_NAME", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("savedImageButton", R.drawable.ic_black_favorite_24);
                editor.apply();
                saveButton.setBackgroundResource(preferences.getInt("savedImageButton", R.drawable.ic_black_favorite_24));
                saveButton.setSelected(true);
            });
        }

        void openCustomTabs(Context activity, CustomTabsIntent customTabsIntent, Uri uri) {
            String packageName = "com.android.chrome";
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        }

        @SuppressLint("InflateParams")
        void openBottomSheet(View view, Item news) {
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
                String shareBodyText = shareSubText + news.getLink();
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
                openCustomTabs(itemView.getContext(), customTabIntent.build(), Uri.parse(news.getLink()));
                mBottomSheetDialog.dismiss();
            });
        }
    }


}
