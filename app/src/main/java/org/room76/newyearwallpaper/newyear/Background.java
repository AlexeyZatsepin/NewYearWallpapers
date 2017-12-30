package org.room76.newyearwallpaper.newyear;

import android.content.Context;

import org.room76.newyearwallpaper.R;
import org.room76.newyearwallpaper.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

/**
 * Created by Alexey on 12/30/17.
 */

public class Background {
    private int programId;
    private int texture;

    private static final float squareVertices[] = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f,  1.0f,
            1.0f,  1.0f,
    };

    private static final float textureVertices[] = {
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f,  1.0f,
            0.0f,  0.0f,
    };

    private FloatBuffer squareData;
    private FloatBuffer textureData;


    void generateProgram(Context context){
        int vertexShaderId = Utils.createShader(context, GL_VERTEX_SHADER, R.raw.texture_vertex_shader);
        int fragmentShaderId = Utils.createShader(context, GL_FRAGMENT_SHADER, R.raw.texture_fragment_shader);
        programId = Utils.createProgram(vertexShaderId, fragmentShaderId);
        glLinkProgram(programId);

        texture = Utils.loadTexture(context,R.drawable.debug_image);
        squareData = ByteBuffer.allocateDirect(squareVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        squareData.put(squareVertices);
        textureData = ByteBuffer.allocateDirect(textureVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureData.put(textureVertices);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);

        int aPositionLocation = glGetAttribLocation(programId, "a_Position");
        int aCoordinateLocation = glGetAttribLocation(programId, "inputTextureCoordinate");

        squareData.position(0);
        textureData.position(0);

        glUniform1i(aCoordinateLocation, 0);

        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, squareData);
        glEnableVertexAttribArray(aPositionLocation);
        glVertexAttribPointer(aCoordinateLocation, 2, GL_FLOAT, false, 0, textureData);
        glEnableVertexAttribArray(aCoordinateLocation);
    }

    void draw(){
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }

}
