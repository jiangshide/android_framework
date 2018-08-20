package com.zd112.read.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zd112.framework.BaseFragment;
import com.zd112.framework.apdater.CommAdapter;
import com.zd112.framework.net.annotation.RequestStatus;
import com.zd112.framework.net.helper.NetInfo;
import com.zd112.framework.utils.SystemUtils;
import com.zd112.framework.utils.ViewUtils.ViewInject;
import com.zd112.framework.view.BannerView;
import com.zd112.framework.view.CusListView;
import com.zd112.framework.view.refresh.RefreshView;
import com.zd112.read.MainActivity;
import com.zd112.read.R;
import com.zd112.read.home.data.HomeData;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements BannerView.OnBannerItemClickListener, RefreshView.OnTouchEventListener {

    @ViewInject(R.id.homeBanner)
    private BannerView homeBanner;
    @ViewInject(R.id.listView)
    private CusListView listView;
    private CommAdapter<HomeData.Res> commAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.tab_home, this, true);
        SystemUtils.setNoStatusBarFullMode(getActivity(), true);
    }

    @Override
    protected void setListener() {
        homeBanner.setOnBannerItemClickListener(this);
        mRefreshView.setOnTouchListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        List list = new ArrayList();
        list.add("https://test2.etongdai.com/u/cms/www/201807/15101936v22b.png");
        list.add("https://test2.etongdai.com/u/cms/www/201712/261339026r9n.jpg");
        list.add("https://test2.etongdai.com/u/cms/www/201802/01143159ks3w.jpg");
        list.add("https://test2.etongdai.com/u/cms/www/201804/09105814rqlz.jpg");
        homeBanner.setViewUrls(list);
        request("app", HomeData.class, true);
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onMove() {
        ((MainActivity) getActivity()).navigationBar.scrollScan(40, 160);
    }

    @Override
    public void onUp() {
        ((MainActivity) getActivity()).navigationBar.scrollScan(160, 160);
    }

    @Override
    public void onSuccess(NetInfo info) {
        super.onSuccess(info);
        HomeData homeData = info.getResponseObj();
        if (info.getStatus() == RequestStatus.NORMAL) {
            listView.setAdapter(commAdapter = new CommAdapter<HomeData.Res>(getActivity(), homeData.res, R.layout.list_item) {
                @Override
                protected void convertView(int position, View item, HomeData.Res res) {
                    ((TextView) get(item, R.id.itemTitle)).setText(res.Name);
                    ((TextView) get(item, R.id.itemContent)).setText(res.Channel);
                }
            });
        } else {
            if (homeData.res != null || homeData.res.size() < default_page_size) {
                mRefreshView.setEnableLoadMore(false);
            } else {
                commAdapter.addList(homeData.res, info.getStatus());
            }
        }
    }

}
