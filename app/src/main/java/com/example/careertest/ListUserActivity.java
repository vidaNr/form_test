package com.example.careertest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.careertest.databinding.ActivityUserListBinding;
import com.example.careertest.databinding.DetailUserDialogBinding;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {

    private ActivityUserListBinding binding;
    private Dialog dialog;
    private DetailUserDialogBinding userDialogBinding;
    private View detailView;
    private SharedPreferences sharedPref;
    private List<String> userNames = new ArrayList<>();
    private UserAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPref = getSharedPreferences("user-data", MODE_PRIVATE);
        userNames.add(sharedPref.getString("user-name", null));
//        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUsers.setHasFixedSize(true);
        adapter = new UserAdapter(userNames) {
            @Override
            protected void deleteInfo(String deleteUser) {
                userNames.remove(deleteUser);
                notifyDataSetChanged();
            }

            @Override
            protected void editInfo(String editUser) {
//                Bundle bundle=new Bundle();
//                bundle.putBoolean("from-list-activity",true);
//                bundle.putString("user-name",sharedPref.getString("user-name", null));
//                bundle.putString("user-gender",sharedPref.getString("user-gender", null));
//                bundle.putString("user-marital-status",sharedPref.getString("user-marital-status", null));
//                bundle.putString("user-birthday",sharedPref.getString("user-birthday", null));
//                startActivity(new Intent(ListUserActivity.this, MainActivity.class),bundle);
//                finish();
                onBackPressed();
            }

            @Override
            public void showUser(String username) {
                InfoDialog();
            }

        };

        binding.rvUsers.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void InfoDialog() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        detailView = LayoutInflater.from(this).inflate(R.layout.detail_user_dialog, null);
        userDialogBinding = DetailUserDialogBinding.bind(detailView);

        userDialogBinding.tvUsername.setText(sharedPref.getString("user-name", null));
        userDialogBinding.tvGenderUser.setText(sharedPref.getString("user-gender", null));
        userDialogBinding.tvStatusUser.setText(sharedPref.getString("user-marital-status", null));
        userDialogBinding.tvBirthdayUser.setText(sharedPref.getString("user-birthday", null));
        dialog.setContentView(detailView);

        dialog.show();

        userDialogBinding.btnConfirm.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }
}
