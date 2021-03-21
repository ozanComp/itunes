package com.sol.itunes.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sol.itunes.R;
import com.sol.itunes.Utility;
import com.sol.itunes.databinding.ActivityTrackListBinding;
import com.sol.itunes.model.Track;
import com.sol.itunes.model.TrackEvent;
import com.sol.itunes.model.TrackResponse;
import com.sol.itunes.view.adapter.TrackRecyclerViewAdapter;
import com.sol.itunes.view.base.BaseActivity;
import com.sol.itunes.viewmodel.TrackDetailViewModel;
import com.sol.itunes.viewmodel.TrackListViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnEditorAction;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;

public class TrackListActivity extends BaseActivity {

    ActivityTrackListBinding activityTrackListBinding;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout srlLoading;

    @BindView(R.id.segmentedCategories)
    SegmentedControl smCategories;

    @BindView(R.id.editTextTerm)
    EditText etTerm;

    @BindView(R.id.recyclerViewTrack)
    RecyclerView rvTrackList;

    int mediaTypePosition = 0;

    String term = "";

    ArrayList<Track> trackArrayList = new ArrayList<>();

    TrackDetailViewModel trackDetailViewModel;
    TrackListViewModel trackListViewModel;

    PublishSubject<CharSequence> searchObservable;

    TrackRecyclerViewAdapter trackRecyclerViewAdapter;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_track_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTrackListBinding = (ActivityTrackListBinding) getActivityBinding();

        setViewModel();

        setSearchObservable();

        setUI();

        callSearch();
    }

    @OnEditorAction(R.id.editTextTerm)
    public boolean onEditorAction(int actionId){
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            attemptSearch();
            return true;
        }
        return false;
    }

    private void setViewModel(){

        trackListViewModel = ViewModelProviders.of(this).get(TrackListViewModel.class);
        trackDetailViewModel = ViewModelProviders.of(this).get(TrackDetailViewModel.class);

        activityTrackListBinding.setViewModel(trackListViewModel);
    }

    private void setUI(){
        smCategories.setSelectedSegment(mediaTypePosition);

        srlLoading.setOnRefreshListener(this::attemptSearch);

        setupCategoryFilterListener();

        setupRecyclerView();

        setupSearchEditText();
    }

    private void setupRecyclerView() {
        if (trackRecyclerViewAdapter == null) {
            trackRecyclerViewAdapter = new TrackRecyclerViewAdapter(trackArrayList, track -> {
                EventBus.getDefault().postSticky(new TrackEvent(track));
                Intent in = new Intent(TrackListActivity.this, TrackDetailActivity.class);
                TrackListActivity.this.startActivity(in);
            });


            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rvTrackList.setLayoutManager(staggeredGridLayoutManager);
            rvTrackList.setAdapter(trackRecyclerViewAdapter);
            rvTrackList.setItemAnimator(new DefaultItemAnimator());
            rvTrackList.setNestedScrollingEnabled(true);
            rvTrackList.hasFixedSize();
        } else {
            trackRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void setupCategoryFilterListener(){
        smCategories.addOnSegmentClickListener(segmentViewHolder -> {
            mediaTypePosition = segmentViewHolder.getAbsolutePosition();
            attemptSearch();
        });
    }

    private void setupSearchEditText(){
        etTerm.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchObservable.onNext(s);
            }
        });
    }

    private void attemptSearch(){
        Utility.hideKeyboard(TrackListActivity.this, etTerm);
        term = etTerm.getText().toString();

        callSearch();
    }

    private void callSearch(){
        trackArrayList.clear();
        trackDetailViewModel.searchTrack(getTrackRepository(), term, Utility.getMediaType(TrackListActivity.this, mediaTypePosition));
        observeTrackDataResponse();
    }

    private void observeTrackDataResponse(){
        trackListViewModel.setRefreshing(true);
        trackDetailViewModel.getTrackRepository().observe(this, trackResponseResult -> {
            if(trackResponseResult.getResponseBody().isSuccessful()){
                List<Track> tracks = ((TrackResponse) trackResponseResult.getResponse()).getResults();
                trackListViewModel.setIsResultEmpty(tracks.size() <= 0);
                trackArrayList.addAll(tracks);
            } else{
                if(!Utility.isNetworkAvailable(TrackListActivity.this)) {
                    Toast.makeText(TrackListActivity.this, getString(R.string.no_network_available), Toast.LENGTH_SHORT).show();
                    trackArrayList.clear();
                }
            }

            trackRecyclerViewAdapter.notifyDataSetChanged();
            trackListViewModel.setRefreshing(false);
        });
    }

    private void setSearchObservable(){
        searchObservable = PublishSubject.create();
        searchObservable.debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override public void onSubscribe(Disposable d) { }
                    @Override public void onComplete() { }

                    @Override
                    public void onNext(CharSequence s) {
                        if (s.length() > 2) {
                            term = etTerm.getText().toString();
                            callSearch();
                        }
                    }

                    @Override
                    public void onError(Throwable e) { }
                });
    }
}
