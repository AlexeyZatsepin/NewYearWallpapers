package org.room76.newyearwallpaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Alexey on 12/29/17.
 */

public class PickerActivity extends Activity {

    private static final String LIVE_WALLPAPER_CHOOSER = "android.service.wallpaper.LIVE_WALLPAPER_CHOOSER";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(LIVE_WALLPAPER_CHOOSER));
        finish();
    }

}
