package com.example.careertest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    boolean checkConfirmSMS = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // access permission == 1
                } else {
                    // not access
                }
                break;
            case 1:
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // access permission == 1
//                    SMSPermission();

                } else {
                    // not access
                    break;
                }
            case 2:
                if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                    // access permission == 1
//                    SMSPermission();

                } else {
                    // not access
                    break;
                }
            default:

        }


    }

    private void checkPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            builder.setTitle("دسترسی پیامک")
                    .setMessage("آیا اجازه دسترسی به پیامک ها را می دهید؟")
                    .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                        checkConfirmSMS = true;
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_SMS}, 0);

                            // next dialog(Camera permission)
                        }
                    }).setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            checkConfirmSMS = false;
                            // next dialog(Camera permission)

                        }
                    })
                    .show();

        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            builder.setTitle("دسترسی دوربین")
                    .setMessage("آیا اجازه دسترسی به دوربین می دهید؟")
                    .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                        checkConfirmSMS = true;
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA}, 1);

                            // next dialog(Camera permission)
                        }
                    }).setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            checkConfirmSMS = false;
                            // next dialog(Camera permission)

                        }
                    })
                    .show();

        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            builder.setTitle("دسترسی حافظه")
                    .setMessage("آیا اجازه دسترسی به حافظه را می دهید؟")
                    .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                        checkConfirmSMS = true;
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

                            // next dialog(Camera permission)
                        }
                    }).setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            checkConfirmSMS = false;
                            // next dialog(Camera permission)

                        }
                    })
                    .show();


        } else {
            // go to form page
        }


    }
}
