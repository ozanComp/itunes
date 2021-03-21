package com.sol.itunes.view.ui;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.sol.itunes.R;
import com.sol.itunes.databinding.ActivityTrackDetailBinding;
import com.sol.itunes.model.Track;
import com.sol.itunes.model.TrackEvent;
import com.sol.itunes.view.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class TrackDetailActivity extends BaseActivity {

    ActivityTrackDetailBinding activityTrackDetailBinding;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    Track track = null;

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_track_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTrackDetailBinding = (ActivityTrackDetailBinding) getActivityBinding();

        setToolBar();

        if(track != null){
            activityTrackDetailBinding.setTrack(track);
            setToolBarTitle(track);
        }
    }

    private void setToolBar(){
        setSupportActionBar(mToolBar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(TrackEvent trackEvent) {
        track = trackEvent.getTrack();

        if(track != null){
            activityTrackDetailBinding.setTrack(track);
            setToolBarTitle(track);
        }
    }

    public void setToolBarTitle(Track track){
        mToolBar.setTitle(track.getTrackName()==null ? track.getCollectionName() : track.getTrackName());
    }
}
