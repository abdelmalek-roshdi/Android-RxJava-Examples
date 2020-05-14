package com.example.rxlabs;

import android.os.Bundle;
import java.util.Arrays;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CreateObserverActivity extends MainActivity {
    Integer[] numbers = {1,2,3,4,5};
    List<Integer> numberList = Arrays.asList(1,2,3,4,5,6);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rightButton.setText("From Iterable");
        leftButton.setText("From Array");
        leftButton.setOnClickListener((view)->{
            Observable.fromArray(numbers).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( "from array - onSubscribe");
                }

                @Override
                public void onNext(Integer integer) {
                    log( "from array - onNext " +integer);
                }

                 @Override
                public void onError(Throwable e) {
                     log( "from array - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "from array - onComplete " );
                }
            });
        });

        rightButton.setOnClickListener((view)->{
            Observable.fromIterable(numberList).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    log( "from iterable - onSubscribe");
                }

                @Override
                public void onNext(Integer integer) {
                    log( "from iterable - onNext " +integer);
                }

                @Override
                public void onError(Throwable e) {
                    log( "from iterable - onError " +e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    log( "from iterable - onComplete " );
                }
            });

        });
    }


}
