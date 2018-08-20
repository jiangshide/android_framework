package com.zd112.framework.view.refresh.util;

public enum SpinnerStyle {
    Translate,//平行移动        特点: HeaderView高度不会改变，
    Scale,//拉伸形变            特点：在下拉和上弹（HeaderView高度改变）时候，会自动触发OnDraw事件
    FixedBehind,//固定在背后    特点：HeaderView高度不会改变，
    FixedFront,//固定在前面     特点：HeaderView高度不会改变，
    MatchLayout//填满布局       特点：HeaderView高度不会改变，尺寸充满 RefreshLayout
}
