package com.example.rxlabs;

import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MapAandFlatMapActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rightButton.setText("Map");
        leftButton.setText("Flat Map");
        rightButton.setOnClickListener((view)->{
            Observable.just(1,2,3,4,5).map(new Function<Integer, Integer>() {
               @Override
               public Integer apply(Integer integer) throws Exception {
                   return integer*10;
                }
            }
            ).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( "map - onSubscribe");
                }

                @Override
                public void onNext(Integer integer) {
                    log( "map - onNext " +integer);
                }

                @Override
                public void onError(Throwable e) {
                    log( "map - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "map - onComplete " );
                }
            });
        });

        leftButton.setOnClickListener((view)->{
            Observable.just(1,2,3,4,5,6).flatMap(new Function<Integer, ObservableSource<String>>() {
                @Override
                public ObservableSource<String> apply(Integer integer) throws Exception {
                    return  Observable.just("flat map"+ integer);
                }
            }).subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( "flat map - onSubscribe");
                }

                @Override
                public void onNext(String string) {
                    log( "flat map - onNext " +string);
                }

                @Override
                public void onError(Throwable e) {
                    log( "flat map - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "flat map - onComplete " );
                }
            });
        });
    }
}
