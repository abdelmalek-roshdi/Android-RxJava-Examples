package com.example.rxlabs;

import android.os.Bundle;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ConcatActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leftButton.setText("Concat");
        rightButton.setVisibility(View.GONE);
        leftButton.setOnClickListener((view)->{
            Observable<Integer> observable1 = Observable.just(1,2,3,4);
            Observable<Integer> observable2 = Observable.just(100,200,300,400,500,600,700,800);
            Observable<Integer> observable3 = Observable.just(1000,2000,3000,4000,5000,6000,7000,8000);
            Observable.concat(observable1, observable2, observable3).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( "concat - onSubscribe");
                }

                @Override
                public void onNext(Integer integer) {
                    log( "concat - onNext " +integer);
                }

                @Override
                public void onError(Throwable e) {
                    log( "concat - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "concat - onComplete " );
                }
            });
        });
    }
}
