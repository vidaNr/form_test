package com.example.careertest;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.careertest.databinding.ActivitySplashBinding;
import com.example.careertest.databinding.PermissionDialogBinding;

import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends AppCompatActivity {

    Dialog dialog;
    PermissionDialogBinding permissionBinding;
    ActivitySplashBinding binding;
    Boolean accessSMS = false;
    Boolean accessCamera = false;
    Boolean accessMemory = false;


    private static final int REQUEST_CODE_PERMISSIONS = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //create dialog
        initPermissionDialog();

        checkPermissionDialog();


    }

    private void checkPermissionDialog() {


        String[] permissionsNeeded = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_SMS

        };

        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissionsNeeded) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
                settingDialog();
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]),
                    REQUEST_CODE_PERMISSIONS);
        } else {
            // All permissions are already granted
            goMain();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false;
                        break;
                    }
                }
            }
            if (allPermissionsGranted) {
                goMain();
            } else {
                // Permission denied, disable functionality that depends on this permission.
                settingDialog();
            }
        }
    }


    private void goMain() {

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 50);
    }

    // check what its work?????

    private void initPermissionDialog() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View permissionView = LayoutInflater.from(this).inflate(R.layout.permission_dialog, null);
        permissionBinding = PermissionDialogBinding.bind(permissionView);

        dialog.setContentView(permissionView);

    }

    private void checkSMSDialog() {

        permissionBinding.tvTitleDialog.setText("دسترسی پیامک");
        permissionBinding.tvContentDialog.setText("آیا اجازه دسترسی به پیامک ها را می دهید؟");
        permissionBinding.btnPositive.setText("تایید");
        permissionBinding.btnNegative.setText("لغو");


        permissionBinding.btnPositive.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.READ_SMS}, REQUEST_CODE_PERMISSIONS);
            dialog.dismiss();
            checkCameraDialog();
        });
        permissionBinding.btnNegative.setOnClickListener(view -> {
            dialog.dismiss();

            checkCameraDialog();

        });

        if (permissionBinding.cbAsk.isChecked()) {
            permissionBinding.btnPositive.setEnabled(false);
            permissionBinding.btnPositive.setFocusable(false);
        }

        //TODO Don't ask again
//        permissionBinding.cbAsk.isChecked() ? permissionBinding.btnPositive.setFocusable(false) : permissionBinding.btnPositive.setFocusable(true)

        dialog.show();

    }

    private void checkCameraDialog() {

        permissionBinding.tvTitleDialog.setText("دسترسی دوربین");
        permissionBinding.tvContentDialog.setText("آیا اجازه دسترسی به دوربین می دهید؟");
        permissionBinding.btnPositive.setText("تایید");
        permissionBinding.btnNegative.setText("لغو");


        permissionBinding.btnPositive.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSIONS);
            dialog.dismiss();

            goMain();
//            checkMemoryDialog();
        });
        permissionBinding.btnNegative.setOnClickListener(view -> {
            dialog.dismiss();
            settingDialog();
//            checkMemoryDialog();
        });


        //TODO Don't ask again
//        permissionBinding.cbAsk.isChecked() ? permissionBinding.btnPositive.setFocusable(false) : permissionBinding.btnPositive.setFocusable(true)
        dialog.show();

    }

    private void checkMemoryDialog() {

        permissionBinding.tvTitleDialog.setText("دسترسی حافظه");
        permissionBinding.tvContentDialog.setText("آیا اجازه دسترسی به حافظه می دهید؟");
        permissionBinding.btnPositive.setText("تایید");
        permissionBinding.btnNegative.setText("لغو");


        permissionBinding.btnPositive.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);
            dialog.dismiss();
//            goMain();
//            settingDialog();
        });
        permissionBinding.btnNegative.setOnClickListener(view -> {
            dialog.dismiss();
//goMain();
            settingDialog();
        });


//        if (!permissionBinding.cbAsk.isChecked()) {
//            permissionBinding.btnPositive.setFocusable(false);
//            accessMemory = false;
//        }
        dialog.show();

    }

    private void settingDialog() {


        permissionBinding.tvTitleDialog.setText("تنطیمات");
        permissionBinding.tvContentDialog.setText("در صورتی که دسترسی های خواسته شده را به برنامه بدهید، میتوانید از آن استفاده کنید.");
        permissionBinding.cbAsk.setVisibility(View.GONE);
        permissionBinding.tv1.setVisibility(View.GONE);
        permissionBinding.btnPositive.setText("تنطیمات");
        permissionBinding.btnNegative.setVisibility(View.GONE);
        dialog.show();

        permissionBinding.btnPositive.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            checkPermissionDialog();
        });



    }


}
