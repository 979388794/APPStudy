package com.example.OpenGl;


import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Cone {


    //一个Float占用4Byte
    private static final int BYTES_PER_FLOAT = 4;
    //顶点位置缓存(圆锥侧面)
    private final FloatBuffer vertexBuffer;
    //顶点位置缓存(圆锥底面)
    private FloatBuffer vertexBuffer1;
    //顶点颜色缓存
    private final FloatBuffer colorBuffer;
    //渲染程序
    private int mProgram;

    private int mPositionHandle;
    private int mColorHandle;
    // Use to access and set the view transformation
    private int mMVPMatrixHandle;


    //圆锥顶点位置（侧面）
    private float coneCoords[];

    //圆锥顶点位置（圆底面）
    private float coneCoords1[];

    //顶点的颜色
    private float color[];

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 uMVPMatrix;" +
                    "varying  vec4 vColor;" +
                    "attribute vec4 aColor;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix*vPosition;" +
                    "  gl_PointSize = 10.0;"+
                    "  vColor=aColor;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";


    public Cone() {
        //圆锥的侧面数据
        createPositions(0.5f,60);

        //圆锥的圆形底面数据
        createCircularPositions();

        //顶点位置相关（圆锥侧面）
        //分配本地内存空间,每个浮点型占4字节空间；将坐标数据转换为FloatBuffer，用以传入给OpenGL ES程序
        vertexBuffer = ByteBuffer.allocateDirect(coneCoords.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexBuffer.put(coneCoords);
        vertexBuffer.position(0);

        //顶点颜色相关
        colorBuffer = ByteBuffer.allocateDirect(color.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);

        //顶点位置相关（圆锥底面）
        vertexBuffer1 = ByteBuffer.allocateDirect(coneCoords1.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexBuffer1.put(coneCoords1);
        vertexBuffer1.position(0);

        //数据转换
        Log.d("xuejie", "RendererTest.loadShader");
        int vertexShader = GlsurfaceView.RendererTest.loadShader(GL_VERTEX_SHADER,
                vertexShaderCode);
        Log.d("xuejie", "fragmentShaderCode");
        int fragmentShader = GlsurfaceView.RendererTest.loadShader(GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        // 创建空的OpenGL ES程序
        Log.d("xuejie", "GLES20.glCreateProgram");
        mProgram = GLES20.glCreateProgram();
        Log.d("xuejie", "GLES20.glAttachShader");
        // 添加顶点着色器到程序中
        GLES20.glAttachShader(mProgram, vertexShader);
        // 添加片段着色器到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);
        // 创建OpenGL ES程序可执行文件
        GLES20.glLinkProgram(mProgram);
    }

    private void createCircularPositions() {
        coneCoords1 = new float[coneCoords.length];

        for (int i=0;i<coneCoords.length;i++){
            if (i==2){
                coneCoords1[i]=0.0f;
            }else {
                coneCoords1[i]=coneCoords[i];
            }
        }
    }

    private void createPositions(float radius, int n){
        ArrayList<Float> data=new ArrayList<>();
        data.add(0.0f);//设置锥心坐标
        data.add(0.0f);
        data.add(-0.5f);

        float angDegSpan=360f/n;
        for(float i=0;i<360+angDegSpan;i+=angDegSpan){
            data.add((float) (radius*Math.sin(i*Math.PI/180f)));
            data.add((float)(radius*Math.cos(i*Math.PI/180f)));
            data.add(0.0f);
        }
        float[] f=new float[data.size()];

        for (int i=0;i<f.length;i++){
            f[i]=data.get(i);
        }

        coneCoords = f;

        //处理各个顶点的颜色
        color = new float[f.length*4/3];
        ArrayList<Float> tempC = new ArrayList<>();
        ArrayList<Float> totalC = new ArrayList<>();
        ArrayList<Float> totalCForColor = new ArrayList<>();

        tempC.add(0.8f);
        tempC.add(0.8f);
        tempC.add(0.8f);
        tempC.add(1.0f);

        ArrayList<Float> zeroIndexC = new ArrayList<>();
        zeroIndexC.add(1.0f);
        zeroIndexC.add(0.0f);
        zeroIndexC.add(0.0f);
        zeroIndexC.add(1.0f);
        for (int i=0;i<f.length/3;i++){
            if (i==0){
                totalC.addAll(zeroIndexC);
            }else {
                totalC.addAll(tempC);
            }

            totalCForColor.addAll(tempC);
        }

        for (int i=0; i<totalC.size();i++){
            color[i]=totalC.get(i);
        }
    }



    public void draw(float[] mvpMatrix) {

        // 将程序添加到OpenGL ES环境
        GLES20.glUseProgram(mProgram);

        // 得到形状的变换矩阵的句柄
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // 将投影和视图转换传递给着色器
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // 获取顶点着色器的位置的句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        //准备三角形坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, 3,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);

        // 启用三角形顶点位置的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");

        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle, 4,
                GLES20.GL_FLOAT, false,
                0, colorBuffer);



        //绘制圆锥侧面
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, coneCoords.length/3);
        //准备坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer1);
        //启用顶点位置句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //绘制圆锥圆形底面
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, coneCoords1.length/3);


        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);

    }


}
