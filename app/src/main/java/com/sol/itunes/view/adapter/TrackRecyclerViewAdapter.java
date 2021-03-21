package com.sol.itunes.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sol.itunes.R;
import com.sol.itunes.databinding.TrackListBinding;
import com.sol.itunes.model.Track;

import java.util.ArrayList;

public class TrackRecyclerViewAdapter extends RecyclerView.Adapter<TrackRecyclerViewAdapter.TrackViewHolder> {

    private final ArrayList<Track> trackArrayList;
    private TrackRecyclerViewOnClickListener onClickListener;

    public TrackRecyclerViewAdapter(ArrayList<Track> trackArrayList, TrackRecyclerViewOnClickListener onClickListener) {
        this.trackArrayList = trackArrayList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TrackListBinding trackListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.track_list, parent, false);
        return new TrackViewHolder(trackListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrackViewHolder holder, final int position) {
        Track track = trackArrayList.get(position);

        holder.trackListBinding.setTrack(track);

        holder.trackListBinding.layoutItem.setOnClickListener(view -> onClickListener.onClickItem(track));
    }

    @Override
    public int getItemCount() {
        return trackArrayList.size();
    }


    static class TrackViewHolder extends RecyclerView.ViewHolder {
        private TrackListBinding trackListBinding;

        public TrackViewHolder(@NonNull TrackListBinding mTrackListBinding) {
            super(mTrackListBinding.getRoot());

            this.trackListBinding = mTrackListBinding;
        }
    }
}
