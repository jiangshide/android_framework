package com.zd112.read.top;

import android.os.Bundle;

import com.zd112.framework.BaseFragment;
import com.zd112.read.R;

public class TopFragment extends BaseFragment {
    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.tab_top, this, true).setEnableLoadMore(false);
        showStatusBar();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
