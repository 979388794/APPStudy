package com.example.OpenGl;

import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glViewport;
import static javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GlsurfaceView extends GLSurfaceView {

    public RendererTest renderer;

    public GlsurfaceView(Context context, int timecount) {
        super(context);
        renderer = new RendererTest();
        renderer.gpurate = timecount;
        setEGLContextClientVersion(2);
        setRenderer(renderer);
        //RENDERMODE_WHEN_DIRTY      RENDERMODE_CONTINUOUSLY
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public GlsurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        renderer = new RendererTest();
        renderer.gpurate = 200;
        setEGLContextClientVersion(2);
        setRenderer(renderer);
        //RENDERMODE_WHEN_DIRTY      RENDERMODE_CONTINUOUSLY
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
    }


    public static class RendererTest implements Renderer {
        // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
        private static final float[] mMVPMatrix = new float[16];
        private static final float[] mProjectionMatrix = new float[16];
        private static final float[] mViewMatrix = new float[16];
        private static float[] mRotationMatrix = new float[16];
        Triangle triangle;
        static int gpurate;
        static int i = 1;
        boolean flag2 = true;
        Threadcount tt = new Threadcount("xuejie");

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // Set the background frame color
            glClearColor(0.5f, 0.5f, 0.5f, 0.0f);
            triangle = new Triangle();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            glViewport(0, 0, width, height);
            float ratio = (float) width / height;
            // 这个投影矩阵被应用于对象坐标在onDrawFrame（）方法中
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            glClear(GL_COLOR_BUFFER_BIT);
            if (flag2) {
                tt.start();
                flag2 = false;
            }
            for (int j = 0; j < i; j++) {
                triangle.draw(MatrixCreator());
            }
        }

        public static class Threadcount extends Thread {
            public Threadcount(String name) {
                super(name);
            }

            @Override
            public void run() {
                while (true) {
                    if (SurfaceViewActivity.getGpuCurFreq() < (gpurate - 1)) {
                        i++;
                    } else if (SurfaceViewActivity.getGpuCurFreq() > (gpurate + 1)) {
                        i--;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    if (SurfaceViewActivity.getGpuCurFreq() == 0) {
//                        break;
//                    }
                }
            }
        }

        public static int loadShader(int type, String shaderCode) {
            // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
            // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
            int shader = glCreateShader(type);
            // add the source code to the shader and compile it
            glShaderSource(shader, shaderCode);
            glCompileShader(shader);
            return shader;
        }

        public static float[] MatrixCreator() {
            // Set the camera position (View matrix)
            Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            //圆锥相机位置
            // Matrix.setLookAtM(mViewMatrix, 0, 6, 0, -1f, 0f, 0f, 0f, 0.0f, 0.0f, 1.0f);

            // Calculate the projection and view transformation
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
            float[] scratch = new float[16];
            // 创建一个旋转矩阵
            long time = SystemClock.uptimeMillis() % 4000L;
            float angle = 0.090f * ((int) time);
            Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);
            // 将旋转矩阵与投影和相机视图组合在一起
            // Note that the mMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
            return scratch;
        }
    }
}
