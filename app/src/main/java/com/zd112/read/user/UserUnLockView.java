package com.zd112.read.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


import com.zd112.framework.apdater.CommAdapter;
import com.zd112.framework.utils.DateUtils;
import com.zd112.framework.utils.DialogUtils;
import com.zd112.framework.utils.ShareParamUtils;
import com.zd112.framework.view.CusButton;
import com.zd112.framework.view.DialogView;
import com.zd112.framework.view.UnLockView;
import com.zd112.read.MyApplication;
import com.zd112.read.R;
import com.zd112.read.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class UserUnLockView implements DialogView.DialogViewListener, UnLockView.OnGestureDoneListener, View.OnClickListener, DialogView.DialogOnClickListener {

    private Context context;
    private GridView userUnLockViewTips;
    private UnLockView unLockView;
    private TextView userUnlockTips;
    private DialogView dialogView;
    private DialogUtils dialogUtils;
    private String gesturePsw;
    private OnUserUnlockListener onUserUnlockListener;
    private boolean isModify;
    private boolean isCheck;
    private CommAdapter<Boolean> booleanCommAdapter;

    public DialogView init(Context context, boolean isModify, boolean isCheck, OnUserUnlockListener listener) {
        this.context = context;
        this.isModify = isModify;
        this.isCheck = isCheck;
        this.onUserUnlockListener = listener;
        cancel();
        dialogView = new DialogView(context, R.style.DialogTheme, R.layout.main_user_unlock, this).setOutsideClose(false).setFull(true).setListener(this);
        dialogView.setAnim(R.style.bottomAnim).show();
        dialogUtils = new DialogUtils(context);
        return dialogView;
    }

    public void cancel() {
        if (dialogView != null) {
            dialogView.cancel();
            dialogView = null;
        }
    }

    @Override
    public void onView(View view) {
//        Button userUnlockCancel = view.findViewById(R.id.userUnlockCancel);
        Button userUnlockJump = view.findViewById(R.id.userUnlockJump);
        TextView userUnlockName = view.findViewById(R.id.userUnlockName);
        userUnLockViewTips = view.findViewById(R.id.userUnLockViewTips);
        unLockView = view.findViewById(R.id.userUnLockView);
        unLockView.setOnGestureDoneListener(this);
        userUnlockTips = view.findViewById(R.id.userUnlockTips);
        Button userUnlockLast = view.findViewById(R.id.userUnlockLast);
        Button userUnlockOther = view.findViewById(R.id.userUnlockOther);
        ((TextView) view.findViewById(R.id.userUnlockDay)).setText(DateUtils.getDay());
        ((TextView) view.findViewById(R.id.userUnlockWeek)).setText(DateUtils.getWeek());
        ((TextView) view.findViewById(R.id.userUnlockMonth)).setText(DateUtils.getMonth());
        userUnlockName.setText(context.getString(R.string.welcome) + AppSessionEngine.getUserLoginName());
//        userUnlockCancel.setOnClickListener(this);
        userUnlockOther.setOnClickListener(this);
        userUnlockLast.setOnClickListener(this);
        userUnlockJump.setOnClickListener(this);
        gesturePsw = ShareParamUtils.getString(Constant.GESTURE_PSW);
        if (TextUtils.isEmpty(gesturePsw)) {
            userUnlockJump.setVisibility(View.VISIBLE);
        }
        if (!isModify) {
            userUnlockLast.setVisibility(View.VISIBLE);
            userUnlockOther.setVisibility(View.VISIBLE);
            userUnLockViewTips.setVisibility(View.GONE);
            userUnlockTips.setText("");
        } else if (isModify) {
//            userUnlockCancel.setVisibility(View.VISIBLE);
            userUnlockLast.setVisibility(View.VISIBLE);
            userUnlockOther.setVisibility(View.VISIBLE);
            userUnLockViewTips.setVisibility(View.VISIBLE);
            userUnlockTips.setText("");
        }
        showTips(null);
    }


    private void showTips(String gesturePsw) {
        List<Boolean> list = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            boolean isShow = false;
            if (!TextUtils.isEmpty(gesturePsw)) {
                for (int j = 0; j < gesturePsw.length(); j++) {
                    int temp = gesturePsw.charAt(j) - '0';
                    if (temp == i) {
                        isShow = true;
                    }
                }
            }
            if (isShow) {
                list.add(true);
            } else {
                list.add(false);
            }
        }
        userUnLockViewTips.setAdapter(booleanCommAdapter = new CommAdapter<Boolean>(context, list, R.layout.main_user_unlock_tips) {
            @Override
            protected void convertView(int position, View item, Boolean isSelected) {
                CusButton cusButton = get(item, R.id.userUnLockViewTipsItem);
                cusButton.setNormalColor(isSelected ? R.color.colorAccent : R.color.white);
            }
        });
    }

    @Override
    public boolean isValidGesture(int pointCount) {
        if (pointCount < 4) {
            userUnlockTips.setText(context.getString(R.string.gesture_psw_low));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void inputOK(UnLockView view, String psw) {
        showTips(psw);
        if (isModify && !TextUtils.isEmpty(gesturePsw) && gesturePsw.equals(psw)) {
            isModify = false;
            gesturePsw = null;
            userUnlockTips.setText(context.getString(R.string.gesture_psw_input));
            return;
        }
        if (!TextUtils.isEmpty(gesturePsw)) {
            if (!gesturePsw.equals(psw)) {
                userUnlockTips.setText(context.getString(R.string.gesture_psw_inconsistent));
                gesturePsw = null;
            } else {
                ShareParamUtils.INSTANCE.putString(Constant.GESTURE_PSW, psw);
                if (onUserUnlockListener != null) {
                    ShareParamUtils.INSTANCE.putBoolean(Constant.GESTURE_PSW_STATE, true);
                    onUserUnlockListener.onUserUnlock(true, isCheck);
                }
                cancel();
            }
        } else {
            gesturePsw = psw;
            userUnlockTips.setText(context.getString(R.string.gesture_psw_confirm));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userUnlockCancel:
                if (onUserUnlockListener != null) {
                    onUserUnlockListener.onUserUnlock(false, isCheck);
                }
                cancel();
                break;
            case R.id.userUnlockJump:
                dialogUtils.loading(context.getString(R.string.gesture_psw_tips)).setListener(new DialogView.DialogOnClickListener() {
                    @Override
                    public void onDialogClick(boolean isCancel) {
                        if (isCancel) return;
                        if (onUserUnlockListener != null) {
                            onUserUnlockListener.onUserUnlock(false, isCheck);
                        }
                        cancel();
                    }
                });
                break;
            case R.id.userUnlockLast:
                dialogUtils.loading(context.getString(R.string.gesture_psw_reset)).setListener(new DialogView.DialogOnClickListener() {
                    @Override
                    public void onDialogClick(boolean isCancel) {
                        if (isCancel) return;
                        MyApplication.application.cleanJump(LoginActivity.class, Constant.TAB_HOME);
                    }
                });
                break;
            case R.id.userUnlockOther:
                dialogUtils.loading(context.getString(R.string.gesture_psw_other)).setListener(new DialogView.DialogOnClickListener() {
                    @Override
                    public void onDialogClick(boolean isCancel) {
                        if (isCancel) return;
                        ShareParamUtils.clear();
                        MyApplication.application.cleanJump(LoginActivity.class, Constant.TAB_HOME);
                    }
                });
                break;
        }
    }

    @Override
    public void onDialogClick(boolean isCancel) {
        if (isCancel) return;
        if (onUserUnlockListener != null) {
            onUserUnlockListener.onUserUnlock(false, isCheck);
        }
    }

    public interface OnUserUnlockListener {
        void onUserUnlock(boolean isPsw, boolean isCheck);
    }
}
