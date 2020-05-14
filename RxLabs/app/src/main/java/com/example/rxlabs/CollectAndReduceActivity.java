package com.example.rxlabs;

import android.os.Bundle;

import java.util.Arrays;
import java.util.concurrent.Callable;

import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;

public class CollectAndReduceActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leftButton.setText("Reduce");
        rightButton.setText("Collect");
        leftButton.setOnClickListener((view)->{
            Observable.fromIterable(Arrays.asList(2,2,2,2,2,2,2,2,2,2)).reduce(new BiFunction<Integer, Integer, Integer>() {
                @Override
                public Integer apply(Integer integer, Integer integer2) throws Exception {
                    return integer*integer2;
                }
            }).toObservable().subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( "reduce - onSubscribe");
                }

                @Override
                public void onNext(Integer integer) {
                    log( "reduce - onNext " +integer);
                }

                @Override
                public void onError(Throwable e) {
                    log( "reduce - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "reduce - onComplete " );
                }
            });
        });

        rightButton.setOnClickListener(view -> {
            Observable.just("A", "B", "C").collect(new Callable<StringBuilder>() {
                @Override
                public StringBuilder call() throws Exception {
                    return new StringBuilder();
                }
            }, new BiConsumer<StringBuilder, String>() {
                @Override
                public void accept(StringBuilder stringBuilder, String s) throws Exception {
                    stringBuilder.append("    ").append(s);
                }
            }).toObservable().subscribe(new Observer<StringBuilder>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( "collect - onSubscribe");
                }

                @Override
                public void onNext(StringBuilder stringBuilder) {
                    log( "collect - onNext"+ stringBuilder);
                }


                @Override
                public void onError(Throwable e) {
                    log( "collect - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "collect - onComplete " );
                }
            });
        });
    }
}
