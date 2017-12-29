package org.room76.newyearwallpaper.newyear;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Alexey on 12/29/17.
 */

public class SnowRenderer implements GLSurfaceView.Renderer {

        public SnowRenderer() {}

        @Override
        public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
            // Set the background clear color to gray.
            GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        }

        @Override
        public void onSurfaceChanged(GL10 glUnused, int width, int height) {
            // Set the OpenGL viewport to the same size as the surface.
            GLES20.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 glUnused) {
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

            // Do a complete rotation every 10 seconds.
            long time = SystemClock.uptimeMillis() % 10000L;
        }
    }