package com.sol.itunes.view.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sol.itunes.data.TrackRepository;
import com.sol.itunes.di.component.AppComponent;
import com.sol.itunes.di.component.DaggerAppComponent;
import com.sol.itunes.di.module.ContextModule;

import javax.inject.Inject;

public class DaggerBaseActivity extends AppCompatActivity {
    @Inject
    TrackRepository trackRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        trackRepository = component.getTrackRepository();
    }

    public TrackRepository getTrackRepository() {
        return trackRepository;
    }
}
