package com.jzg.jcpt.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jzg.jcpt.R;
import com.jzg.jcpt.fragment.TakePhotoFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * @description
 * @Author qiwx
 * @Date 2020/8/6 2:19 PM
 **/
public class TestCameraXActivity extends AppCompatActivity {
    TakePhotoFragment takePhotoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_camerax);

        File filePictures = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.e("TAG", filePictures.getAbsolutePath());
        File filePath = new File(filePictures.getAbsolutePath() + "/Jzg");
        if (!filePath.exists()) {
            filePath.mkdir();
        }

        takePhotoFragment = new TakePhotoFragment();
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_container, takePhotoFragment)   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
        findViewById(R.id.btn_takepic).setOnClickListener(v -> {
            if (!filePath.exists()) {
                try {
                    filePath.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            takePhotoFragment.takePicture(filePath, "124", new TakePhotoFragment.ITakePictureListener() {
                @Override
                public void onSuccess(@NotNull Uri uri) {
                    Log.e("TAG", uri.getPath());
                }
            });
        });

    }
}
