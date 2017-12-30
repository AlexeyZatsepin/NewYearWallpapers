package org.room76.newyearwallpaper.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.texImage2D;

/**
 * Created by Alexey on 12/30/17.
 */

public class Utils {
    private Utils(){}

    public static String readTextFromRaw(Context context, int resourceId) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = null;
            try {
                InputStream inputStream = context.getResources().openRawResource(resourceId);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\r\n");
                }
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace(); //TODO crashlytics log
        } catch (Resources.NotFoundException nfex) {
            nfex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static int createProgram(int vertexShaderId, int fragmentShaderId) {
        final int programId = glCreateProgram();
        if (programId == 0) {
            return 0;
        }
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        glLinkProgram(programId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            glDeleteProgram(programId);
            return 0;
        }
        return programId;
    }

    public static int createShader(Context context, int type, int shaderRawId) {
        String shaderText = readTextFromRaw(context, shaderRawId);
        return createShader(type, shaderText);
    }

    public static int createShader(int type, String shaderText) {
        final int shaderId = glCreateShader(type);
        if (shaderId == 0) {
            return 0;
        }
        glShaderSource(shaderId, shaderText);
        glCompileShader(shaderId);
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderId);
            return 0;
        }
        return shaderId;
    }

    public static int loadTexture(Context context, int resourceId) {
        final int[] textureObjectIds = new int[1];
        glGenTextures(1, textureObjectIds, 0);
        if (textureObjectIds[0] == 0) {
            return 0;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        if (bitmap == null) {
            glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }
        glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);
        return textureObjectIds[0];
    }
}
