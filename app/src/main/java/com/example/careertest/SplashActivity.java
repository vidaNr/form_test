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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.careertest.databinding.ActivitySplashBinding;
import com.example.careertest.databinding.PermissionDialogBinding;


public class SplashActivity extends AppCompatActivity {

    Dialog dialog;
    PermissionDialogBinding permissionBinding;
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //create dialog
        initPermissionDialog();

        checkSMSDialog();

        //TODO check access to resources
        /*
         requestCode == 0 SMS
         .. == 1 Camera
         .. == 2 Memory
         */


    }

    // check what its work?????
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != RESULT_CANCELED) {

//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
//                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
//                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, perform your operations here

                //TODO if permission granted

                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                };
                handler.postDelayed(runnable, 500);
//            } else settingDialog();
        }

        // Permission denied, show a message or dialog to the user
//                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
//
//                if (requestCode == 0)
//                    checkSMSDialog();
//                else if (requestCode == 1)
//                    checkCameraDialog();
//                else if (requestCode == 2)
//                    checkMemoryDialog();


    }

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
        dialog.show();

        permissionBinding.btnPositive.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.READ_SMS}, 0);
            dialog.dismiss();
            checkCameraDialog();
        });
        permissionBinding.btnNegative.setOnClickListener(view -> {
            dialog.dismiss();
            checkCameraDialog();
        });


        //TODO Don't ask again
//        permissionBinding.cbAsk.isChecked() ? permissionBinding.btnPositive.setFocusable(false) : permissionBinding.btnPositive.setFocusable(true)


    }

    private void checkCameraDialog() {

        permissionBinding.tvTitleDialog.setText("دسترسی دوربین");
        permissionBinding.tvContentDialog.setText("آیا اجازه دسترسی به دوربین می دهید؟");
        permissionBinding.btnPositive.setText("تایید");
        permissionBinding.btnNegative.setText("لغو");
        dialog.show();

        permissionBinding.btnPositive.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.CAMERA}, 1);
            dialog.dismiss();
//            checkMemoryDialog();
        });
        permissionBinding.btnNegative.setOnClickListener(view -> {
            dialog.dismiss();
//            checkMemoryDialog();
        });


        //TODO Don't ask again
//        permissionBinding.cbAsk.isChecked() ? permissionBinding.btnPositive.setFocusable(false) : permissionBinding.btnPositive.setFocusable(true)

    }

    private void checkMemoryDialog() {

        permissionBinding.tvTitleDialog.setText("دسترسی حافظه");
        permissionBinding.tvContentDialog.setText("آیا اجازه دسترسی به حافظه می دهید؟");
        permissionBinding.btnPositive.setText("تایید");
        permissionBinding.btnNegative.setText("لغو");
        dialog.show();

        permissionBinding.btnPositive.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 2);
            dialog.dismiss();
            settingDialog();
        });
        permissionBinding.btnNegative.setOnClickListener(view -> {
            dialog.dismiss();
            settingDialog();
        });

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
        });



        /*
        builder = new AlertDialog.Builder(this);
        builder.setTitle("تنطیمات")
                .setMessage("در صورتی که دسترسی های خواسته شده را به برنامه بدهید، میتوانید از آن استفاده کنید.")
                .setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .show();
         */

    }


}
