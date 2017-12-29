package org.room76.newyearwallpaper.newyear;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;

import org.room76.newyearwallpaper.R;
import org.room76.newyearwallpaper.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * Created by Alexey on 12/29/17.
 */

public class SnowRenderer implements GLSurfaceView.Renderer {
    public static String TAG = SnowRenderer.class.getSimpleName();
    private Context context;
    private int programId;
    private FloatBuffer vertexData;
    private int uColorLocation;
    private int aPositionLocation;

    public SnowRenderer(Context context) {
        this.context = context;
        prepareData();
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to gray.
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        int vertexShaderId = Utils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader);
        int fragmentShaderId = Utils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader);
        programId = Utils.createProgram(vertexShaderId, fragmentShaderId);
        glUseProgram(programId);
        bindData();
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

        // Do a complete rotation every 10 seconds.
        long time = SystemClock.uptimeMillis() % 10000L;

        glDrawArrays(GL_POINTS, 0, vertexData.remaining());
    }

    private void prepareData() {
//        float[] vertices = {-0.5f, -0.2f, 0.0f, 0.2f, 0.5f, -0.2f,};
        int n = (int) (Math.random()*100);
        float[] vertices = new float[n%2==0?n:n+1];
        for (int i = 0; i < vertices.length; i++){
            vertices[i] = (float) Math.random() - 0.5f;
        }
        Log.v(TAG, Arrays.toString(vertices));
        vertexData = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(vertices);
    }

    private void bindData() {
        uColorLocation = glGetUniformLocation(programId, "u_Color");
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        aPositionLocation = glGetAttribLocation(programId, "a_Position");
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }
}