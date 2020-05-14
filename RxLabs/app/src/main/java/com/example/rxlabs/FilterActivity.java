package com.example.rxlabs;

import android.os.Bundle;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

public class FilterActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rightButton.setVisibility(View.GONE);
        leftButton.setText("Filter");
        leftButton.setOnClickListener((ivew)->{
            Observable.just(1,2,3,4,5).filter(new Predicate<Integer>() {
                @Override
                public boolean test(Integer integer) throws Exception {
                    return integer > 2;
                }
            }).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( " filter - onSubscribe");
                }

                @Override
                public void onNext(Integer integer) {
                    log( "filter - onNext " +integer);
                }

                @Override
                public void onError(Throwable e) {
                    log( "filter - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "filter - onComplete " );
                }
            });
        });
    }
}
