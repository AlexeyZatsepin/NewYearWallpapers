package org.room76.newyearwallpaper.service;

import android.opengl.GLSurfaceView;

import org.room76.newyearwallpaper.newyear.SnowRenderer;

/**
 * Created by Alexey on 12/29/17.
 */

public class NewYearWallpaperService extends OpenGLES2WallpaperService{

    @Override
    public GLSurfaceView.Renderer getNewRenderer() {
        return new SnowRenderer(getApplicationContext());
    }
}
