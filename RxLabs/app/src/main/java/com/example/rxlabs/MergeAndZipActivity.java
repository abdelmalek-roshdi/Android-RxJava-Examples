package com.example.rxlabs;

import android.os.Bundle;
import android.util.Pair;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;


public class MergeAndZipActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leftButton.setText("Merge");
        rightButton.setText("Zip");
        leftButton.setOnClickListener((view)->{
            Observable<Integer> observable1 = Observable.just(1,2,3,4);
            Observable<Integer> observable2 = Observable.just(100,200,300,400,500,600,700,800);
            Observable<Integer> observable3 = Observable.just(1000,2000,3000,4000,5000,6000,7000,8000);
            Observable.merge(observable1, observable2, observable3).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( "merge - onSubscribe");
                }

                @Override
                public void onNext(Integer integer) {
                    log( "merge - onNext " +integer);
                }

                @Override
                public void onError(Throwable e) {
                    log( "merge - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "merge - onComplete " );
                }
            });
        });

        rightButton.setOnClickListener((view)->{
            Observable<Integer> observable1 = Observable.just(11,22,33,44);
            Observable<Integer> observable2 = Observable.just(1100,2200,3300,4400,5500,6600,7700,8800);
            Observable.zip(observable1, observable2, new BiFunction<Integer, Integer, Pair<Integer, Integer>>() {
                @Override
                public Pair<Integer, Integer> apply(Integer integer, Integer integer2) throws Exception {
                    return new Pair<>(integer, integer2);
                }
            }).subscribe(new Observer<Pair<Integer, Integer>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( "zip - onSubscribe");
                }

                @Override
                public void onNext(Pair<Integer, Integer> pair) {
                    log( "zip - onNext " +pair.first+" "+pair.second);
                }

                @Override
                public void onError(Throwable e) {
                    log( "zip - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "zip - onComplete " );
                }
            });
        });
    }
}
