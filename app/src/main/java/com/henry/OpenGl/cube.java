package com.henry.OpenGl;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClear;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class cube extends BasicShape {
    private final int mProgram;
    private final FloatBuffer colorBuffer;
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
  //  private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;    //9/3=3
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex   3x4=12

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    short[] indices = {
            0, 1, 2, 3, 4, 5,
            6, 7, 8, 9, 10, 11,
            12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23,
            24, 25, 26, 27, 28, 29,
            30, 31, 32, 33, 34, 35,
    };

    float triangleCoords[] = {
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,

            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,

            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
    };


    float[] color = {
            // 红色
            1.0f, 0.0f, 0.0f, 1.0f, // RGBA
            1.0f, 0.0f, 0.0f, 1.0f, // RGBA
            1.0f, 0.0f, 0.0f, 1.0f, // RGBA
            1.0f, 0.0f, 0.0f, 1.0f, // RGBA
            1.0f, 0.0f, 0.0f, 1.0f, // RGBA
            1.0f, 0.0f, 0.0f, 1.0f, // RGBA

            // 橙色
            1.0f, 0.5f, 0.0f, 1.0f, // RGBA
            1.0f, 0.5f, 0.0f, 1.0f, // RGBA
            1.0f, 0.5f, 0.0f, 1.0f, // RGBA
            1.0f, 0.5f, 0.0f, 1.0f, // RGBA
            1.0f, 0.5f, 0.0f, 1.0f, // RGBA
            1.0f, 0.5f, 0.0f, 1.0f, // RGBA


            // 黄色
            1.0f, 1.0f, 0.0f, 1.0f, // RGBA
            1.0f, 1.0f, 0.0f, 1.0f, // RGBA
            1.0f, 1.0f, 0.0f, 1.0f, // RGBA
            1.0f, 1.0f, 0.0f, 1.0f, // RGBA
            1.0f, 1.0f, 0.0f, 1.0f, // RGBA
            1.0f, 1.0f, 0.0f, 1.0f, // RGBA

            // 绿色
            0.0f, 1.0f, 0.0f, 1.0f, // RGBA
            0.0f, 1.0f, 0.0f, 1.0f, // RGBA
            0.0f, 1.0f, 0.0f, 1.0f, // RGBA
            0.0f, 1.0f, 0.0f, 1.0f, // RGBA
            0.0f, 1.0f, 0.0f, 1.0f, // RGBA
            0.0f, 1.0f, 0.0f, 1.0f, // RGBA

            // 蓝色
            0.0f, 0.0f, 1.0f, 1.0f, // RGBA
            0.0f, 0.0f, 1.0f, 1.0f, // RGBA
            0.0f, 0.0f, 1.0f, 1.0f, // RGBA
            0.0f, 0.0f, 1.0f, 1.0f, // RGBA
            0.0f, 0.0f, 1.0f, 1.0f, // RGBA
            0.0f, 0.0f, 1.0f, 1.0f, // RGBA

            // 紫色
            1.0f, 0.0f, 1.0f, 1.0f, // RGBA
            1.0f, 0.0f, 1.0f, 1.0f, // RGBA
            1.0f, 0.0f, 1.0f, 1.0f, // RGBA
            1.0f, 0.0f, 1.0f, 1.0f, // RGBA
            1.0f, 0.0f, 1.0f, 1.0f, // RGBA
            1.0f, 0.0f, 1.0f, 1.0f, // RGBA
    };

    private IntBuffer intBufferUtil(int[] arr) {
        IntBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    private FloatBuffer floatBufferUtil(float[] arr) {
        FloatBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    private ShortBuffer shortBufferUtil(short[] arr) {
        ShortBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*2，因为一个short占2个字节
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                arr.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        mBuffer = dlb.asShortBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }


    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 uMVPMatrix;" +
                    "varying  vec4 vColor;" +
                    "attribute vec4 aColor;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix*vPosition;" +
                    "  vColor=aColor;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    public cube() {
        // 顶点数据
        vertexBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(triangleCoords).position(0);

        // 颜色数据
        colorBuffer = ByteBuffer.allocateDirect(color.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(color).position(0);

        // 索引数据
        indexBuffer = ByteBuffer.allocateDirect(indices.length * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(indices).position(0);


        //数据转换
        int vertexShader = GlsurfaceView.RendererTest.loadShader(GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = GlsurfaceView.RendererTest.loadShader(GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // 创建空的OpenGL ES程序
        mProgram = GLES20.glCreateProgram();
        // 添加顶点着色器到程序中
        GLES20.glAttachShader(mProgram, vertexShader);
        // 添加片段着色器到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);
        // 创建OpenGL ES程序可执行文件
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix) {
        //清除颜色、深度缓冲
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        // 将程序添加到OpenGL ES环境
        GLES20.glUseProgram(mProgram);

        // 顶点着色器的位置
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //准备三角形坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        //顶点着色器颜色
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false,
                16, colorBuffer);

        //矩阵
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_SHORT, indexBuffer);

        //  绘制三角形
        //  GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

