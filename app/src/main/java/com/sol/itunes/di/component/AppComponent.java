package com.sol.itunes.di.component;

import com.sol.itunes.data.TrackRepository;
import com.sol.itunes.di.module.TrackRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TrackRepositoryModule.class})
public interface AppComponent {
    TrackRepository getTrackRepository();
}