package com.example.rxlabs;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.ResourceSubscriber;

public class DeviceSensorTest extends MainActivity{
    private  Flowable<SensorEvent> sensorFlowable;
    private  CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leftButton.setVisibility(View.GONE);
        rightButton.setVisibility(View.GONE);
        compositeDisposable = new CompositeDisposable();
        sensorFlowable = Flowable.create(emitter -> {
            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                        emitter.onNext(sensorEvent);
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };

            SensorManager sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
            emitter.setCancellable(()-> sensorManager.unregisterListener(sensorEventListener));
        }, BackpressureStrategy.BUFFER);

          Disposable disposable = sensorFlowable
                .subscribeOn(Schedulers.io())
                //.sample(10 , TimeUnit.SECONDS)
                // .observeOn(AndroidSchedulers.mainThread(), false, 20)
                //.throttleFirst(1, TimeUnit.SECONDS)
                .throttleLast(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeWith(new ResourceSubscriber<SensorEvent>() {
                    @Override
                    public void onNext(SensorEvent sensorEvent) {
                        log("X "+sensorEvent.values[0]);
                        log("Y "+sensorEvent.values[1]);
                        log("Z "+sensorEvent.values[2]);
                    }
                    @Override
                    public void onError(Throwable t) {
                        log(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        log("completed");
                    }
                });
          compositeDisposable.add(disposable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable.size() > 0) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
