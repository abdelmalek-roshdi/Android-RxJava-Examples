package com.iti.myapplication.search;
import com.iti.myapplication.model.RemoteDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.PresenterInterface {
    private final String TAG = "SearchPresenter";

    SearchContract.ViewInterface viewInterface;
    RemoteDataSource dataSource;
    CompositeDisposable compositeDisposable;

    public SearchPresenter(SearchContract.ViewInterface viewInterface, RemoteDataSource dataSource) {
        this.viewInterface = viewInterface;
        this.dataSource = dataSource;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getSearchResults(String query) {
         compositeDisposable.add(dataSource
                 .search(query)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe( tmdbResponse -> {viewInterface.displayResult(tmdbResponse);}
                         , throwable -> {viewInterface.displayError("Error fetching Movie Data");
                         }));
    }

    @Override
    public void stop() {
        //release resources
        if (compositeDisposable.size() > 0 && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
