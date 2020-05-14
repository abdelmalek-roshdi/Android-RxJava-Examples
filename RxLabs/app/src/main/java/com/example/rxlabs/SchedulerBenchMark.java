package com.example.rxlabs;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;


import com.jakewharton.rxbinding2.view.RxView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SchedulerBenchMark extends MainActivity {
    static List<Long> numbers ;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leftButton.setText("Start");
        rightButton.setText("Double Click");
        Observable<Object> becnchButton = RxView.clicks(leftButton);
        Observable<Object> doubleButton = RxView.clicks(rightButton);
        numbers = new ArrayList<>();

        for (long i =1; i <= 1_0_000; i++){
            numbers.add(i);
        }

        Observable<List<Object>> nClicks = becnchButton
                .buffer(500, TimeUnit.MILLISECONDS)
                .filter(o-> o .size()==1);
//        nClicks.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o-> {
//                        benchmarck();
//                } );

        nClicks
                .flatMap( objects ->  Observable.fromIterable(numbers).subscribeOn(Schedulers.computation())
                        .filter( i -> i % 2 == 0)
                        .map(Math::sqrt)
                        .map(Math::cos)
                        .reduce((n1, n2)-> n2-n1)
                        .toObservable().observeOn(AndroidSchedulers.mainThread())

                       ) .subscribe( res ->{
            log("result = "+res );
            long endTime = System.nanoTime();
            //log("total time = " +(endTime-startTime)/1000000 +"ms");
        });

//     not working !!
//        nClicks
//                .flatMap( objects ->  Observable.fromIterable(numbers))
//                .filter( i -> i % 2 == 0)
//                .map(Math::sqrt)
//                .map(Math::cos)
//                .doOnError(e -> log(e.getMessage()))
//                .reduce((n1, n2)->n2-n1)
//                .toObservable().subscribe(res -> log(res));
//
//
        doubleButton
                .buffer(500, TimeUnit.MILLISECONDS)
                .filter(o-> o .size()>=2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o->{
            log("you clicked "+ o.size()+" in less than 500 ms");
        });

    }
    public void benchmarck (){
        long startTime = System.nanoTime();
        Observable.fromIterable(numbers)
                .subscribeOn(Schedulers.computation())
                .filter( i -> i % 2 == 0)
                .map(Math::sqrt)
                .map(Math::cos)
                .reduce((n1, n2)->n2-n1)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( res ->{
                    log("result = "+res );
                    long endTime = System.nanoTime();
                    log("total time = " +(endTime-startTime)/1000000 +"ms");
                });
    }
}
