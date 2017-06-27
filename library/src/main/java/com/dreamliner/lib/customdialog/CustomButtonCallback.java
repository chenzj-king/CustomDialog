package com.dreamliner.lib.customdialog;

import android.support.annotation.NonNull;

/**
 * Created by chenzj on 2016/12/28.
 */

public interface CustomButtonCallback {
    void onClick(@NonNull CustomDialog dialog, @NonNull CustomDialogAction which);
}
