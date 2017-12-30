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
    private Snowflake[] particles;
    private int uColorLocation;
    private int aPositionLocation;
    private float slowdown = 2.0f;
    private static int PARTICLES_COUNT = 1000;
    private Background background;

    public SnowRenderer(Context context) {
        this.context = context;
        prepareData();
        background = new Background();
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to gray.
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        int vertexShaderId = Utils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader);
        int fragmentShaderId = Utils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader);
        programId = Utils.createProgram(vertexShaderId, fragmentShaderId);
        glUseProgram(programId);

        background.generateProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

        moveParticles();
        bindData();

        glDrawArrays(GL_POINTS, 0, PARTICLES_COUNT);

//        background.draw();
    }

    private void prepareData() {
        particles = new Snowflake[PARTICLES_COUNT];
        for (int i = 0; i < particles.length; i++){
            float x = (float) Math.random()*2 - 1f;
            float gravity = (float) Math.random()*.1f;
            Snowflake snowflake = new Snowflake(x,1f, 0f, gravity);
            particles[i] = snowflake;
        }
        Log.v(TAG, Arrays.toString(particles));
        putParticlesInBuffer();
    }

    private void bindData() {
        putParticlesInBuffer();
        uColorLocation = glGetUniformLocation(programId, "u_Color");
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        aPositionLocation = glGetAttribLocation(programId, "a_Position");
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }

    private void moveParticles(){
        for (int i = 0; i < PARTICLES_COUNT;i++){
            if (particles[i].ypos<-1){
                particles[i].vel = 0;
                particles[i].ypos = 1;
                particles[i].xpos = (float) Math.random()*2 - 1f;
                particles[i].gravity = (float) Math.random()*.1f;
            }
            particles[i].ypos += particles[i].vel / (slowdown*1000);
            particles[i].vel -= particles[i].gravity;
        }
    }

    private void putParticlesInBuffer(){
        vertexData = ByteBuffer.allocateDirect(particles.length * 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (Snowflake item: particles) {
            vertexData.put(item.xpos);
            vertexData.put(item.ypos);
        }
    }
}