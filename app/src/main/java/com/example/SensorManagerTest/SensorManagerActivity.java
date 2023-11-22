package com.example.SensorManagerTest;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.HomeScreen.R;

/**
 * 目前未找到支持传感器的设备
 */


public class SensorManagerActivity extends AppCompatActivity {
    private final String TAG = "sensor-sample";
    private TextView mAccelerometerSensorTextView;
    private TextView mMagneticSensorTextView;
    private TextView mGyroscopeSensorTextView;
    private TextView mOrientationSensorTextView;
    private SensorManager mSensorManager;
    private MySensorEventListener mMySensorEventListener;
    private float[] mAccelerometerReading = new float[3];
    private float[] mMagneticFieldReading = new float[3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_manager);

        mAccelerometerSensorTextView = findViewById(R.id.g_sensor);
        mMagneticSensorTextView = findViewById(R.id.dis);
        mGyroscopeSensorTextView = findViewById(R.id.o_sensor);
        mOrientationSensorTextView = findViewById(R.id.ois);

        this.mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.mMySensorEventListener = new MySensorEventListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorManager == null) {
            return;
        }

        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor != null) {
            //register accelerometer sensor listener
            mSensorManager.registerListener(mMySensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Log.d(TAG, "Accelerometer sensors are not supported on current devices.");
        }

        Sensor magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticSensor != null) {
            //register magnetic sensor listener
            mSensorManager.registerListener(mMySensorEventListener, magneticSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Log.d(TAG, "Magnetic sensors are not supported on current devices.");
        }

        Sensor gyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscopeSensor != null) {
            //register gyroscope sensor listener
            mSensorManager.registerListener(mMySensorEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Log.d(TAG, "Gyroscope sensors are not supported on current devices.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager == null) {
            return;
        }
        //unregister all listener
        mSensorManager.unregisterListener(mMySensorEventListener);
    }

    /*
    This orientation sensor was deprecated in Android 2.2 (API level 8), and this sensor type was deprecated in Android 4.4W (API level 20).
    The sensor framework provides alternate methods for acquiring device orientation.
     */
    private void calculateOrientation() {
        final float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null, mAccelerometerReading, mMagneticFieldReading);

        final float[] orientationAngles = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        Log.d(TAG, "orientation data[x:" + orientationAngles[0] + ", y:" + orientationAngles[1] + ", z:" + orientationAngles[2] + "]");
        mOrientationSensorTextView.setText("[x:" + orientationAngles[0] + ", y:" + orientationAngles[1] + ", z:" + orientationAngles[2] + "]");
    }

    private class MySensorEventListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mAccelerometerReading = event.values;
                Log.d(TAG, "accelerometer data[x:" + event.values[0] + ", y:" + event.values[1] + ", z:" + event.values[2] + "]");
                mAccelerometerSensorTextView.setText("[x:" + event.values[0] + ", y:" + event.values[1] + ", z:" + event.values[2] + "]");
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mMagneticFieldReading = event.values;
                Log.d(TAG, "magnetic data[x:" + event.values[0] + ", y:" + event.values[1] + ", z:" + event.values[2] + "]");
                mMagneticSensorTextView.setText("[x:" + event.values[0] + ", y:" + event.values[1] + ", z:" + event.values[2] + "]");
            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                Log.d(TAG, "gyroscope data[x:" + event.values[0] + ", y:" + event.values[1] + ", z:" + event.values[2] + "]");
                mGyroscopeSensorTextView.setText("[x:" + event.values[0] + ", y:" + event.values[1] + ", z:" + event.values[2] + "]");
            }
            calculateOrientation();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d(TAG, "onAccuracyChanged:" + sensor.getType() + "->" + accuracy);
        }

    }


}