package com.example.careertest;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careertest.databinding.ActivityMainBinding;
import com.example.careertest.databinding.DateDialogBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Dialog dialog;
    DateDialogBinding dateDialogBinding;

    SharedPreferences sharedPref = getSharedPreferences("user-data", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();

    String[] months = new String[]{
            "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
            "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
    String birthDate, month, marital, gender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDateDialog();


        // select date
        binding.btnSelectDate.setOnClickListener(view -> {
            setDateOfDialog();
            dialog.show();
        });

//        String dateOfBirth =


        dateDialogBinding.btnConfirm.setOnClickListener(view -> {

            for (int i = 0; i < months.length - 1; i++) {
                if (i == dateDialogBinding.numMonth.getValue()) {
                    month = months[i];
                }
            }

            birthDate = String.valueOf(dateDialogBinding.numDay.getValue()) + " "
                    + month + " "
                    + String.valueOf(dateDialogBinding.numYear.getValue());

            dialog.dismiss();
        });
        binding.fabDone.setOnClickListener(view -> {
            saveData();
            editor.apply();
            Toast.makeText(MainActivity.this, "کاربر جدید ذخیره شد!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setDateOfDialog() {

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
        dateDialogBinding.numYear.setValue(1360);

    }

    private void initDateDialog() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View dateView = LayoutInflater.from(this).inflate(R.layout.date_dialog, null);
        dateDialogBinding = DateDialogBinding.bind(dateView);
        dialog.setContentView(dateView);

    }

    private void saveData() {
        editor.putString("user-name", String.valueOf(binding.ietName.getText()));

        if (binding.radioWoman.isSelected()) {
            editor.putBoolean("user-gender", true); //woman==true
        } else editor.putBoolean("user-gender", false);


        binding.cbMarried.setOnClickListener(view -> {
            checkMaritalAndGender("married");

        });
        binding.cbSingle.setOnClickListener(view -> {
            checkMaritalAndGender("single");
        })
        ;
        binding.radioWoman.setOnClickListener(view -> {
            checkMaritalAndGender("زن");
        });
        binding.radioMan.setOnClickListener(view -> {
            checkMaritalAndGender("مرد");
        });
//
//        if (binding.cbMarried.isChecked()) {
//        } else editor.putBoolean("user-marital-status", false);

        editor.putString("user-birthday", birthDate);

    }

    private void checkMaritalAndGender(String status) {
        switch (status) {
            case "married":
                binding.cbMarried.setChecked(true);
                binding.cbSingle.setChecked(false);
                marital = "married";
                break;
            case "single":
                binding.cbSingle.setChecked(true);
                binding.cbMarried.setChecked(false);
                marital = "single";
                break;
            case "مرد":
                gender = "مرد";
                break;
            case "زن":
                gender = "زن";
                break;
            default:
                binding.cbSingle.setChecked(false);
                binding.cbMarried.setChecked(false);
                break;
        }
        editor.putString("user-marital-status", marital); //married==true
        editor.putString("user-gender", gender);

    }


}
