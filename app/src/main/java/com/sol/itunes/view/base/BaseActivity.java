package com.sol.itunes.view.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import butterknife.ButterKnife;

public abstract class BaseActivity extends DaggerBaseActivity {
    private ViewDataBinding activityMainBinding;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, layoutRes());
        ButterKnife.bind(this);

    }

    public ViewDataBinding getActivityBinding() {
        return activityMainBinding;
    }
}
