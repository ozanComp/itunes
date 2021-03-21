package com.sol.itunes.data;

import androidx.lifecycle.MutableLiveData;

import com.sol.itunes.model.ResultResponse;
import com.sol.itunes.model.TrackResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class TrackRepository {
    private CompositeDisposable disposable;
    private TrackApi trackApi;

    @Inject
    public TrackRepository(TrackApi trackApi, CompositeDisposable disposable){
        this.trackApi = trackApi;
        this.disposable = disposable;
    }

    public MutableLiveData<ResultResponse> searchTrack(String term, String media){
        MutableLiveData<ResultResponse> trackData = new MutableLiveData<>();

        disposable.add(trackApi.searchTracks(term, media)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<TrackResponse>>(){

                    @Override
                    public void onSuccess(Response<TrackResponse> trackResponseResponse) {
                        trackData.setValue(new ResultResponse<>(trackResponseResponse.body(), trackResponseResponse.raw()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        trackData.setValue(null);
                    }
                })

        );

        return trackData;
    }
}
