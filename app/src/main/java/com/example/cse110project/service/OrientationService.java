package com.example.cse110project;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class OrientationService implements SensorEventListener {
    private static OrientationService instance;

    private final SensorManager sensorManager;
    private float[] accelerometerReading;
    private float[] magnetometerReading;
    private MutableLiveData<Float> azimuth;
    private MediatorLiveData<Float> returnedAzimuth;

    private LiveData<Float> mockOrientation = null;


    protected OrientationService(Activity activity){
        this.azimuth = new MutableLiveData<>();
        this.returnedAzimuth = new MediatorLiveData<>();
        returnedAzimuth.addSource(azimuth,returnedAzimuth::postValue);

        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.registerSensorListeners();
    }

    private void registerSensorListeners(){
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public static OrientationService singleton(Activity activity) {
        if (instance == null) {
            instance = new OrientationService(activity);
        }
        return instance;
    }

    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerReading = event.values;
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnetometerReading = event.values;
        }
        if(accelerometerReading != null && magnetometerReading != null){
            onBothSensorDataAvailable();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //do nothing
    }

    private void onBothSensorDataAvailable() {
        if(accelerometerReading == null || magnetometerReading == null) {
            throw new IllegalStateException("Both sensors must be available to compute orientation.");
        }

        var r = new float[9];
        var i = new float[9];
        boolean success = SensorManager.getRotationMatrix(r,i,accelerometerReading,magnetometerReading);
        if(success) {
            float[] orientation = new float[3];
            SensorManager.getOrientation(r, orientation);
            this.returnedAzimuth.postValue(orientation[0]);
        }
    }

    public void unregisterSensorListeners(){
        sensorManager.unregisterListener(this);
    }

    public LiveData<Float> getOrientation() {
        return this.returnedAzimuth;
    }

    public void setMockOrientationSource(MutableLiveData<Float> mockDataSource) {
        this.unregisterSensorListeners();
        this.mockOrientation = mockDataSource;
        returnedAzimuth.removeSource(azimuth);
        returnedAzimuth.addSource(mockOrientation,returnedAzimuth::postValue);
    }

    public void clearMockOrientationSource(){
        this.registerSensorListeners();
        returnedAzimuth.removeSource(mockOrientation);
        returnedAzimuth.addSource(azimuth,returnedAzimuth::postValue);
    }
}

