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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    String birthday, month, userString;
    User user ;

    Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        initDateDialog();

        //check edit
        if (getIntent().getBooleanExtra("bool-edit", false)) {
            user = (User) getIntent().getExtras().getSerializable("edit-user");
            binding.ietName.setText(user.getName());
            if (user.getGender() == "زن") {
                binding.radioMan.setChecked(true);
            } else
                binding.radioWoman.setChecked(true);
            if (user.getMarital_Status() == "متاهل")
                binding.cbMarried.setChecked(true);
            else
                binding.cbSingle.setChecked(true);

            //birthday
            binding.btnSelectDate.setOnClickListener(view -> {
//                setDateEdit(user);
                setDate();
            });
        }

        clickedButtons();

    }


//    private void setDateEdit(User user) {
//
//        Integer mon=user.getBirthMonth();
//        mon--;
//        dateDialogBinding.numDay.setValue(user.getBirthDay());
//        dateDialogBinding.numMonth.setValue(mon);
//        dateDialogBinding.numYear.setValue(user.getBirthYear());
//        dialog.setContentView(dateView);
//        dialog.show();
//
//    }

    private void init() {
        sharedPref = getSharedPreferences("permission", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean("access-resources", true);
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

    }

    private void clickedButtons() {

        // select date
        binding.btnSelectDate.setOnClickListener(view -> {
            setDate();
            dialog.setContentView(dateView);
            dialog.show();

        });

        // confirm date dialog
        dateDialogBinding.btnConfirm.setOnClickListener(view -> {
            for (int i = 0; i < months.length - 1; i++) {
                if (i == dateDialogBinding.numMonth.getValue()) {
                    month = months[i];
                    break;
                }
            }
            birthday = dateDialogBinding.numDay.getValue() + "  "
                    + month + "  " + dateDialogBinding.numYear.getValue();
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

    private void saveData() {
        user = new User();
        //name
        String name = binding.ietName.getText().toString();
        user.setName(name);

        // status marital
        if (binding.radioWoman.isChecked()) {
            user.setGender("زن");
        } else {
            user.setGender("مرد");
        }
        // gender
        if (binding.cbMarried.isChecked()) {
            user.setMarital_Status("متاهل");
        } else {
            user.setMarital_Status("مجرد");
        }
        //birthday
        user.setBirthDay(birthday);
//            user= new User(name,m)


        userString = gson.toJson(user);
        SharedPreferences sharedPref = getSharedPreferences("user-data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(name, userString);
        editor.apply();

    }

    private void showInfoDialog() {

        userDialogBinding.tvUsername.setText(user.getName());
        userDialogBinding.tvGenderUser.setText(user.getGender());
        userDialogBinding.tvStatusUser.setText(user.getMarital_Status());
        userDialogBinding.tvBirthdayUser.setText(user.getBirthDay());
        dialog.setContentView(detailView);
        dialog.show();

    }
}
