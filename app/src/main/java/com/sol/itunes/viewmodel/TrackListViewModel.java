package com.sol.itunes.viewmodel;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class TrackListViewModel extends ViewModel {
    private ObservableField<Boolean> isRefreshing = new ObservableField<>();
    private ObservableField<Boolean> isResultEmpty = new ObservableField<>();
    private ObservableField<String> searchTerm = new ObservableField<>();

    public TrackListViewModel(){ }

    @BindingAdapter({"refreshVisibility"})
    public static void setRefreshVisibility(SwipeRefreshLayout view, boolean isRefreshing) {
        view.setRefreshing(isRefreshing);
    }

    @BindingAdapter({"listVisibility"})
    public static void setListVisibility(View view, boolean isEmpty) {
        view.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter({"emptyViewVisibility"})
    public static void setEmptyViewVisibility(View view, boolean isEmpty) {
        view.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    public ObservableField<Boolean> isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean progressVisible) {
        this.isRefreshing.set(progressVisible);
    }

    public ObservableField<String> getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm.set(searchTerm);
    }

    public ObservableField<Boolean> getIsResultEmpty() {
        return isResultEmpty;
    }

    public void setIsResultEmpty(boolean isResultEmpty) {
        this.isResultEmpty.set(isResultEmpty);
    }
}
