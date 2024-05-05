package com.example.careertest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careertest.databinding.ActivitySplashBinding;
import com.example.careertest.databinding.PermissionDialogBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private Dialog dialog;
    private PermissionDialogBinding permissionBinding;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private int repeat = 0;
    private Integer[] isDeny = new Integer[3];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initPermissionDialog();

        sharedPref = getSharedPreferences("permission", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        boolean isCheckedPermission = sharedPref.getBoolean("access-resources", false);

        if (!isCheckedPermission) {
            checkSmsDialog();
            editor.putBoolean("access-resources", true);
            editor.apply();
        } else {
            goMain();
        }
    }

    private void initPermissionDialog() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View permissionView = LayoutInflater.from(this).inflate(R.layout.permission_dialog, null);
        permissionBinding = PermissionDialogBinding.bind(permissionView);

        dialog.setContentView(permissionView);

    }

    private void checkSmsDialog() {

        permissionBinding.tvTitleDialog.setText("دسترسی پیامک");
        permissionBinding.tvContentDialog.setText("آیا اجازه دسترسی به پیامک ها را می دهید؟");
        permissionBinding.btnPositive.setText("تایید");
        permissionBinding.btnNegative.setText("لغو");
        dialog.show();

        permissionBinding.btnPositive.setOnClickListener(view -> {
            Dexter.withContext(this)
                    .withPermission(Manifest.permission.READ_SMS)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            //camera permission
                            isDeny[0] = 0;
                            checkCameraDialog();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            isDeny[0] = 1;
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
            dialog.dismiss();
            //camera
            isDeny[0] = 1;
            checkCameraDialog();
        });
        permissionBinding.btnNegative.setOnClickListener(view -> {
            dialog.dismiss();
            //camera
            isDeny[0] = 0;
            checkCameraDialog();
        });

        permissionBinding.cbAsk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    permissionBinding.btnPositive.setClickable(false);
                    permissionBinding.btnPositive.setBackgroundResource(R.drawable.rounded_btn2);
                    isDeny[0] = 2;
                } else {
                    permissionBinding.btnPositive.setClickable(true);
                    permissionBinding.btnPositive.setBackgroundResource(R.drawable.rounded_btn);
                }
            }
        });


    }

    private void checkCameraDialog() {

        permissionBinding.tvTitleDialog.setText("دسترسی دوربین");
        permissionBinding.tvContentDialog.setText("آیا اجازه دسترسی به دوربین می دهید؟");
        permissionBinding.btnPositive.setText("تایید");
        permissionBinding.btnNegative.setText("لغو");
        dialog.show();


        permissionBinding.btnPositive.setOnClickListener(view -> {
            Dexter.withContext(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            //Media permission
                            isDeny[1] = 0;
                            checkMediaDialog();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            isDeny[1] = 1;
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
            dialog.dismiss();
            //media
            isDeny[1] = 1;
            checkMediaDialog();
        });
        permissionBinding.btnNegative.setOnClickListener(view -> {
            dialog.dismiss();
            //media
            isDeny[1] = 0;
            checkMediaDialog();
        });

        //TODO Don't ask again
//        permissionBinding.cbAsk.isChecked() ? permissionBinding.btnPositive.setFocusable(false) : permissionBinding.btnPositive.setFocusable(true)

        permissionBinding.cbAsk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    permissionBinding.btnPositive.setClickable(false);
                    permissionBinding.btnPositive.setBackgroundResource(R.drawable.rounded_btn2);
                    isDeny[1] = 2;
                } else {
                    permissionBinding.btnPositive.setClickable(true);
                    permissionBinding.btnPositive.setBackgroundResource(R.drawable.rounded_btn);
                }
            }
        });


    }

    private void checkMediaDialog() {

        permissionBinding.tvTitleDialog.setText("دسترسی حافظه");
        permissionBinding.tvContentDialog.setText("آیا اجازه دسترسی به حافظه را می دهید؟");
        permissionBinding.btnPositive.setText("تایید");
        permissionBinding.btnNegative.setText("لغو");
        dialog.show();


        permissionBinding.btnPositive.setOnClickListener(view -> {
            /*Dexter.withContext(this)
                    .withPermissions(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                isDeny[2] = false;
                                goMain();
                            } else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                isDeny[2] = true;
                                // check permissions
                                checkPermissions();
                            } else checkPermissions();

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();

                        }

                    }).check();*/
            dialog.dismiss();
            // check permissions
            isDeny[2] = 1;
            checkPermissions();
//            goMain();
        });
        permissionBinding.btnNegative.setOnClickListener(view -> {
            dialog.dismiss();
            isDeny[2] = 0;
            // check permissions
            checkPermissions();
        });

        //TODO Don't ask again
//        permissionBinding.cbAsk.isChecked() ? permissionBinding.btnPositive.setFocusable(false) : permissionBinding.btnPositive.setFocusable(true)

        permissionBinding.cbAsk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    permissionBinding.btnPositive.setClickable(false);
                    permissionBinding.btnPositive.setBackgroundResource(R.drawable.rounded_btn2);
                    isDeny[2] = 2;
                } else {
                    permissionBinding.btnPositive.setClickable(true);
                    permissionBinding.btnPositive.setBackgroundResource(R.drawable.rounded_btn);
                }
            }
        });


    }

    private void checkPermissions() {

        if (repeat == 0) {
            if (isDeny[0] == 0) {
                isDeny[0] = 2;
                checkSmsDialog();
            } else if (isDeny[1] == 0) {
                isDeny[1] = 2;
                checkCameraDialog();
            } else {
                isDeny[2] = 2;
                checkMediaDialog();
                repeat++;
            }
        } else if (repeat == 1) {
            if (isDeny[0] == 2 || isDeny[1] == 2 || isDeny[2] == 2) {
                settingDialog();
                repeat++;
            }
        } else goMain();


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

    }


}
