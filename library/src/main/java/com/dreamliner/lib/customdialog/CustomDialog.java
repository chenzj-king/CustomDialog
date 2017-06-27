package com.dreamliner.lib.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.util.DialogUtils;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.LEFT;


/**
 * @文件名: CustomDialogBackup
 * @功能描述: 提示对话框
 * @Create by chenzj on 2015-12-17 下午6:54:17
 * @email: chenzj@sq580.com
 * @修改记录:
 */
public class CustomDialog extends Dialog implements View.OnClickListener {

    public static final int ALL_BUTTON = 0x01;
    public static final int ONLY_CONFIRM_BUTTON = 0x02;

    protected Builder mBuilder = null;

    private View mView;
    private TextView mTitleTextView;
    private TextView mContentTextView;

    private TextView mCancelTextView;
    private TextView mConfirmTextView;
    private TextView mOnlyConfirmTextView;

    private LinearLayout mAllLinearLayout;
    private LinearLayout mOnlyConfirmLinearLayout;

    private CustomDialog(Builder builder) {
        super(builder.context, R.style.alert_dialog);
        mBuilder = builder;
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_dialog, null);
        setContentView(mView);
        init(this);
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public TextView getContentTextView() {
        return mContentTextView;
    }

    public TextView getCancelTextView() {
        return mCancelTextView;
    }

    public TextView getConfirmTextView() {
        return mConfirmTextView;
    }

    public TextView getOnlyConfirmTextView() {
        return mOnlyConfirmTextView;
    }

    @Override
    public final void onClick(View v) {
        CustomDialogAction tag = (CustomDialogAction) v.getTag();
        switch (tag) {
            case POSITIVE: {
                if (mBuilder.onPositiveCallback != null)
                    mBuilder.onPositiveCallback.onClick(this, tag);
                if (mBuilder.onlyPositiveCallback != null)
                    mBuilder.onlyPositiveCallback.onClick(this, tag);
                break;
            }
            case NEGATIVE: {
                if (mBuilder.onNegativeCallback != null)
                    mBuilder.onNegativeCallback.onClick(this, tag);
                if (mBuilder.autoDismiss) dismiss();
                break;
            }
        }
    }

    private void init(final CustomDialog dialog) {

        CustomDialog.Builder builder = dialog.mBuilder;

        mTitleTextView = (TextView) mView.findViewById(R.id.view_custom_dialog_tilte);
        mContentTextView = (TextView) mView.findViewById(R.id.view_custom_dialog_content);

        mCancelTextView = (TextView) mView.findViewById(R.id.view_tips_dialog_cancel);
        mConfirmTextView = (TextView) mView.findViewById(R.id.view_tip_dialog_confirm);
        mOnlyConfirmTextView = (TextView) mView.findViewById(R.id.view_tip_dialog_only_confirm);

        mAllLinearLayout = (LinearLayout) mView.findViewById(R.id.view_tip_dialog_all_ll);
        mOnlyConfirmLinearLayout = (LinearLayout) mView.findViewById(R.id.view_tip_only_confirm_ll);

        mCancelTextView.setVisibility(builder.negativeText != null ? View.VISIBLE : View.GONE);
        mConfirmTextView.setVisibility(builder.positiveText != null ? View.VISIBLE : View.GONE);
        mOnlyConfirmTextView.setVisibility(builder.onlyPositiveText != null ? View.VISIBLE : View.GONE);

        switch (mBuilder.style) {
            case ALL_BUTTON:
                mAllLinearLayout.setVisibility(View.VISIBLE);
                mOnlyConfirmLinearLayout.setVisibility(View.GONE);
                break;
            case ONLY_CONFIRM_BUTTON:
                mAllLinearLayout.setVisibility(View.GONE);
                mOnlyConfirmLinearLayout.setVisibility(View.VISIBLE);
                break;
        }
        if (!TextUtils.isEmpty(mBuilder.title)) {
            mTitleTextView.setText(mBuilder.title);
        } else {
            mTitleTextView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mBuilder.content)) {
            mContentTextView.setText(mBuilder.content);
            mContentTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
            if (mBuilder.content.length() < 22) {
                if (TextUtils.isEmpty(mBuilder.title)) {
                    mContentTextView.setTextSize(16);
                    mContentTextView.setMinHeight((int) (getContext().getResources().getDisplayMetrics().density * 50 + 0.5));
                }
            } else if (mBuilder.content.length() < 66) {
                mContentTextView.setGravity(CENTER);
            } else {
                mContentTextView.setGravity(LEFT);
            }
        }

        if (!TextUtils.isEmpty(mBuilder.negativeText)) {
            if (null != mBuilder.negativeColor) {
                mCancelTextView.setTextColor(mBuilder.negativeColor);
            }
            mCancelTextView.setText(mBuilder.negativeText);
            mCancelTextView.setTag(CustomDialogAction.NEGATIVE);
            mCancelTextView.setOnClickListener(dialog);
        }

        if (!TextUtils.isEmpty(mBuilder.positiveText)) {
            if (null != mBuilder.positiveColor) {
                mConfirmTextView.setTextColor(mBuilder.positiveColor);
            }
            mConfirmTextView.setText(mBuilder.positiveText);
            mConfirmTextView.setTag(CustomDialogAction.POSITIVE);
            mConfirmTextView.setOnClickListener(dialog);
        }

        if (!TextUtils.isEmpty(mBuilder.onlyPositiveText)) {
            if (null != mBuilder.onlyPositiveColor) {
                mOnlyConfirmTextView.setTextColor(mBuilder.onlyPositiveColor);
            }
            mOnlyConfirmTextView.setText(mBuilder.onlyPositiveText);
            mOnlyConfirmTextView.setTag(CustomDialogAction.POSITIVE);
            mOnlyConfirmTextView.setOnClickListener(dialog);
        }

        setCancelable(builder.cancelable);
        setCanceledOnTouchOutside(builder.cancelable);
    }

    public static class Builder {

        Context context;

        boolean cancelable = false;
        boolean autoDismiss = true;

        protected int style = ALL_BUTTON;

        CharSequence title;
        CharSequence content;
        int titleColor = -1;
        int contentColor = -1;

        CharSequence positiveText;
        CharSequence negativeText;
        CharSequence onlyPositiveText;

        CustomButtonCallback onPositiveCallback;
        CustomButtonCallback onNegativeCallback;
        CustomButtonCallback onlyPositiveCallback;

        ColorStateList positiveColor;
        ColorStateList negativeColor;
        ColorStateList onlyPositiveColor;

        boolean titleColorSet = false;
        boolean contentColorSet = false;
        boolean positiveColorSet = false;
        boolean onlyPositiveColorSet = false;
        boolean negativeColorSet = false;

        public final Context getContext() {
            return context;
        }

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder title(@StringRes int titleRes) {
            title(this.context.getText(titleRes));
            return this;
        }

        public Builder title(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder titleColor(@ColorInt int color) {
            this.titleColor = color;
            this.titleColorSet = true;
            return this;
        }

        public Builder titleColorRes(@ColorRes int colorRes) {
            return titleColor(DialogUtils.getColor(this.context, colorRes));
        }

        public Builder titleColorAttr(@AttrRes int colorAttr) {
            return titleColor(DialogUtils.resolveColor(this.context, colorAttr));
        }

        public Builder content(@StringRes int contentRes) {
            content(this.context.getText(contentRes));
            return this;
        }

        public Builder content(@NonNull CharSequence content) {
            this.content = content;
            return this;
        }

        public Builder contentColor(@ColorInt int color) {
            this.contentColor = color;
            this.contentColorSet = true;
            return this;
        }

        public Builder contentColorRes(@ColorRes int colorRes) {
            contentColor(DialogUtils.getColor(this.context, colorRes));
            return this;
        }

        public Builder contentColorAttr(@AttrRes int colorAttr) {
            contentColor(DialogUtils.resolveColor(this.context, colorAttr));
            return this;
        }

        //确认按钮
        public Builder positiveText(@StringRes int postiveRes) {
            if (postiveRes == 0) return this;
            positiveText(this.context.getText(postiveRes));
            return this;
        }

        public Builder positiveText(@NonNull CharSequence message) {
            this.positiveText = message;
            return this;
        }

        public Builder positiveColor(@ColorInt int color) {
            return positiveColor(DialogUtils.getActionTextStateList(context, color));
        }

        public Builder positiveColorRes(@ColorRes int colorRes) {
            return positiveColor(DialogUtils.getActionTextColorStateList(this.context, colorRes));
        }

        public Builder positiveColorAttr(@AttrRes int colorAttr) {
            return positiveColor(DialogUtils.resolveActionTextColorStateList(this.context, colorAttr, null));
        }

        public Builder positiveColor(@NonNull ColorStateList colorStateList) {
            this.positiveColor = colorStateList;
            this.positiveColorSet = true;
            return this;
        }

        //仅仅一个确认按钮
        public Builder onlyPositiveText(@StringRes int neutralRes) {
            if (neutralRes == 0) return this;
            return onlyPositiveText(this.context.getText(neutralRes));
        }

        public Builder onlyPositiveText(@NonNull CharSequence message) {
            this.onlyPositiveText = message;
            return this;
        }

        public Builder onlyPositiveColor(@ColorInt int color) {
            return onlyPositiveColor(DialogUtils.getActionTextStateList(context, color));
        }

        public Builder onlyPositiveColorRes(@ColorRes int colorRes) {
            return onlyPositiveColor(DialogUtils.getActionTextColorStateList(this.context, colorRes));
        }

        public Builder onlyPositiveColorAttr(@AttrRes int colorAttr) {
            return onlyPositiveColor(DialogUtils.resolveActionTextColorStateList(this.context, colorAttr, null));
        }

        public Builder onlyPositiveColor(@NonNull ColorStateList colorStateList) {
            this.onlyPositiveColor = colorStateList;
            this.onlyPositiveColorSet = true;
            return this;
        }

        //取消按钮
        public Builder negativeText(@StringRes int negativeRes) {
            if (negativeRes == 0) return this;
            return negativeText(this.context.getText(negativeRes));
        }

        public Builder negativeText(@NonNull CharSequence message) {
            this.negativeText = message;
            return this;
        }

        public Builder negativeColor(@ColorInt int color) {
            return negativeColor(DialogUtils.getActionTextStateList(context, color));
        }

        public Builder negativeColorRes(@ColorRes int colorRes) {
            return negativeColor(DialogUtils.getActionTextColorStateList(this.context, colorRes));
        }

        public Builder negativeColorAttr(@AttrRes int colorAttr) {
            return negativeColor(DialogUtils.resolveActionTextColorStateList(this.context, colorAttr, null));
        }

        public Builder negativeColor(@NonNull ColorStateList colorStateList) {
            this.negativeColor = colorStateList;
            this.negativeColorSet = true;
            return this;
        }

        public Builder onPositive(@NonNull CustomButtonCallback callback) {
            this.onPositiveCallback = callback;
            return this;
        }

        public Builder onNegative(@NonNull CustomButtonCallback callback) {
            this.onNegativeCallback = callback;
            return this;
        }

        public Builder onNeutral(@NonNull CustomButtonCallback callback) {
            this.onlyPositiveCallback = callback;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder style(int style) {
            this.style = style;
            return this;
        }

        @UiThread
        public CustomDialog build() {
            return new CustomDialog(this);
        }

        @UiThread
        public CustomDialog show() {
            CustomDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }
}
