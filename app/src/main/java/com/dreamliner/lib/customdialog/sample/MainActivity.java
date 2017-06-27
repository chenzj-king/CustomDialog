package com.dreamliner.lib.customdialog.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dreamliner.lib.customdialog.CustomButtonCallback;
import com.dreamliner.lib.customdialog.CustomDialog;
import com.dreamliner.lib.customdialog.CustomDialogAction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("", "是否保存测试记录？");
            }
        });

        findViewById(R.id.text1_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("标题", "是否保存测试记录？");
            }
        });

        findViewById(R.id.text2_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("标题", "是否保存测试记录是否保存测试记录是否保存测试记录是否保存测试记录？");
            }
        });

        findViewById(R.id.text3_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("标题", "是否保存测试记录是否保存测试记录是否保存测试记录是否保存测试记录是否保存测试记录是否保存测试记录" +
                        "是否保存测试记录是否保存测试记录是否保存测试记录是否保存测试记录是否保存测试记录是否保存测试记录是否保存测试" +
                        "记录是否保存测试记录是否保存测试记录是否保存测试记录记录是否保存测试记录是否保存测试记录是否保存测试记录记录" +
                        "记录是否保存测试记录是否保存测试记录是否保存测试记录记录是否保存测试记录是否保存测试记录是否保存测试记录是否" +
                        "记录是否保存测试记录是否保存测试记录是否保存测试记录记录是否保存测试记录是否保存测试记录是否保存测试记录保存" +
                        "测试记录是否保存测试记录是否保存测试记录测试记录是否保存测试记录是否保存测试记录测试记录是否保存测试记录是否" +
                        "保存测试记录测试记录是否保存测试记录是否保存测试记录测试记录是否保存测试记录是否保存测试记录测试记录是否保存" +
                        "测试记录是否保存测试记录测试记录是否保存测试记录是否保存测试记录？");
            }
        });

    }

    private void showDialog(String title, String content) {
        new CustomDialog.Builder(this)
                .title(title)
                .content(content)
                .positiveText("确定").negativeText("取消")
                .onPositive(new CustomButtonCallback() {
                    @Override
                    public void onClick(@NonNull CustomDialog dialog, @NonNull CustomDialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new CustomButtonCallback() {
                    @Override
                    public void onClick(@NonNull CustomDialog dialog, @NonNull CustomDialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build().show();
    }
}
