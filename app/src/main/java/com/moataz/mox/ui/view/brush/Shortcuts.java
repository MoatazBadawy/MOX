package com.moataz.mox.ui.view.brush;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.moataz.mox.R;

public class Shortcuts {

    public static void setupShortcuts(Context context) {
        @SuppressLint("RestrictedApi") ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(context, "id1")
                .setShortLabel("Instagram")
                .setLongLabel("Instagram")
                .setIcon(IconCompat.createFromIcon(Icon.createWithResource(context, R.mipmap.ic_instagram_round)))
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/thecoderui/")))
                .build();
        ShortcutManagerCompat.pushDynamicShortcut(context, shortcut);

        @SuppressLint("RestrictedApi") ShortcutInfoCompat shortcut2 = new ShortcutInfoCompat.Builder(context, "id2")
                .setShortLabel("Linkedin")
                .setLongLabel("Linkedin")
                .setIcon(IconCompat.createFromIcon(Icon.createWithResource(context, R.mipmap.ic_linkedin_round)))
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.linkedin.com/company/moxarticles/")))
                .build();
        ShortcutManagerCompat.pushDynamicShortcut(context, shortcut2);
    }
}
