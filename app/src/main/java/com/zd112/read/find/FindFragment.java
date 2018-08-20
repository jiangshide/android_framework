package com.zd112.read.find;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.zd112.framework.BaseFragment;
import com.zd112.framework.utils.ViewUtils;
import com.zd112.framework.view.CusProgressbar;
import com.zd112.read.R;

public class FindFragment extends BaseFragment{


    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.tab_find, this, true).setEnableLoadMore(false);
        showStatusBar();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

}
