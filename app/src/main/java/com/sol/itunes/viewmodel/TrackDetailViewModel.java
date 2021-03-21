package com.sol.itunes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sol.itunes.data.TrackRepository;
import com.sol.itunes.model.ResultResponse;

public class TrackDetailViewModel extends ViewModel {
    private MutableLiveData<ResultResponse> mutableLiveData;

    public void searchTrack(TrackRepository trackRepository, String term, String mediaType){
        mutableLiveData = trackRepository.searchTrack(term, mediaType);
    }

    public LiveData<ResultResponse> getTrackRepository() {
        return mutableLiveData;
    }
}
