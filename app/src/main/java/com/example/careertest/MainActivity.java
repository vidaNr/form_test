package com.example.careertest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careertest.databinding.ActivityMainBinding;
import com.example.careertest.databinding.DateDialogBinding;
import com.example.careertest.databinding.DetailUserDialogBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Dialog dialog;
    DateDialogBinding dateDialogBinding;

    DetailUserDialogBinding userDialogBinding;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    View dateView, detailView;

    String[] months = new String[]{
            "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
            "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
    String name, birthDate, month, marital, gender;

    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initDateDialog();

        sharedPref = getSharedPreferences("user-data", Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        // select date
        binding.btnSelectDate.setOnClickListener(view -> {
            setDate();

        });

        // confirm date
        dateDialogBinding.btnConfirm.setOnClickListener(view -> {

            for (int i = 0; i < months.length - 1; i++) {
                if (i == dateDialogBinding.numMonth.getValue()) {
                    month = months[i];
                }
            }
            birthDate = String.valueOf(dateDialogBinding.numDay.getValue()) + "  "
                    + month + "  " + String.valueOf(dateDialogBinding.numYear.getValue());

            dialog.dismiss();
        });

        // confirm Info
        binding.fabDone.setOnClickListener(view -> {
            saveData();
            showInfoDialog();
        });

        userDialogBinding.btnConfirm.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ListUserActivity.class));
            finish();
        });

    }


    private void initDateDialog() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dateView = LayoutInflater.from(this).inflate(R.layout.date_dialog, null);
        dateDialogBinding = DateDialogBinding.bind(dateView);

        detailView = LayoutInflater.from(this).inflate(R.layout.detail_user_dialog, null);
        userDialogBinding = DetailUserDialogBinding.bind(detailView);

    }

    private void setDate() {

        dateDialogBinding.numMonth.setMinValue(0);
        dateDialogBinding.numMonth.setMaxValue(months.length - 1);
        dateDialogBinding.numMonth.setDisplayedValues(months);

        for (int i = 0; i < months.length - 1; i++) {
            if (i > 6) {
                dateDialogBinding.numDay.setMinValue(1);
                dateDialogBinding.numDay.setMaxValue(31);

            } else {
                dateDialogBinding.numDay.setMinValue(1);
                dateDialogBinding.numDay.setMaxValue(30);
            }
        }
        dateDialogBinding.numYear.setMinValue(1320);
        dateDialogBinding.numYear.setMaxValue(1403);
        dateDialogBinding.numYear.setValue(1370);

        dialog.setContentView(dateView);
        dialog.show();

    }

    private void saveData() {

        //name
        editor.putString("user-name", binding.ietName.getText().toString());

        // status marital
        if (binding.cbMarried.isChecked()) {
            editor.putString("user-marital-status", "متاهل");
        } else {
            editor.putString("user-marital-status", "مجرد");
        }

        // gender
        if (!binding.radioWoman.isChecked()) {
            editor.putString("user-gender", "مرد");
        } else {
            editor.putString("user-gender", "زن");
        }

        // birthday
        editor.putString("user-birthday", birthDate);

        editor.apply();

    }

    private void showInfoDialog() {

        userDialogBinding.tvUsername.setText(sharedPref.getString("user-name", null));
        userDialogBinding.tvGenderUser.setText(sharedPref.getString("user-gender", null));
        userDialogBinding.tvStatusUser.setText(sharedPref.getString("user-marital-status", null));
        userDialogBinding.tvBirthdayUser.setText(sharedPref.getString("user-birthday", null));
        dialog.setContentView(detailView);
        dialog.show();

    }

}
