package com.zd112.read.book;

import android.os.Bundle;

import com.zd112.framework.BaseFragment;
import com.zd112.read.R;

public class BookFragment extends BaseFragment{
    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.tab_book, this, true).setEnableLoadMore(false);
        showStatusBar();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
