package com.henry.OpenGl;

import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glViewport;
import static javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GlsurfaceView extends GLSurfaceView {

    public RendererTest renderer;

    public GlsurfaceView(Context context) {
        super(context);
        renderer = new RendererTest();
        setEGLContextClientVersion(2);
        setRenderer(renderer);
        //RENDERMODE_WHEN_DIRTY      RENDERMODE_CONTINUOUSLY
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public GlsurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        renderer = new RendererTest();
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
        BasicShape shape;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            glClearColor(0.1f, 0.5f, 0.5f, 0.0f);
            // shape = new Triangle();
            glEnable(GL_DEPTH_TEST|GL_COLOR_BUFFER_BIT);
            shape = new cube();
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
            shape.draw(MatrixCreator());
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
            Matrix.setRotateM(mRotationMatrix, 0, angle, 0.2f, 0.4f, 1.0f);
            // 将旋转矩阵与投影和相机视图组合在一起
            // Note that the mMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
            return scratch;
        }
        /**
         * Matrix.frustumM(float[] m, int offset, float left, float right, float bottom, float top, float near, float far):
         * 该方法用于设置透视投影矩阵。
         * 参数：
         * m：目标数组，用于存储变换后的矩阵。
         * offset：目标数组中开始存储矩阵的偏移量。
         * left、right、bottom、top：定义了观察空间的近裁剪面和远裁剪面的位置。
         * near：观察空间的近裁剪面距离观察者的距离。
         * far：观察空间的远裁剪面距离观察者的距离。
         *
         * Matrix.setLookAtM(float[] rm, int rmOffset, float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ):
         * 该方法用于设置摄像机的位置和方向，生成视图矩阵。
         * 参数：
         * rm：目标数组，用于存储变换后的矩阵。
         * rmOffset：目标数组中开始存储矩阵的偏移量。
         * eyeX、eyeY、eyeZ：摄像机位置的坐标。
         * centerX、centerY、centerZ：摄像机观察的位置的坐标。
         * upX、upY、upZ：摄像机的上方向向量。
         *
         * Matrix.multiplyMM(float[] result, int resultOffset, float[] lhs, int lhsOffset, float[] rhs, int rhsOffset):
         * 该方法用于将两个 4x4 矩阵相乘。
         * 参数：
         * result：存储相乘结果的数组。
         * resultOffset：存储相乘结果的数组的偏移量。
         * lhs：左侧矩阵。
         * lhsOffset：左侧矩阵的偏移量。
         * rhs：右侧矩阵。
         * rhsOffset：右侧矩阵的偏移量。
         *
         * Matrix.setRotateM(float[] rm, int rmOffset, float a, float x, float y, float z):
         * 该方法用于设置旋转矩阵。
         * 参数：
         * rm：目标数组，用于存储变换后的矩阵。
         * rmOffset：目标数组中开始存储矩阵的偏移量。
         * a：旋转角度。
         * x、y、z：旋转轴的坐标。
         */
    }
}
