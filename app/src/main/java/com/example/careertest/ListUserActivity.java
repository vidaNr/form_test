package com.example.careertest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.careertest.databinding.ActivityUserListBinding;
import com.example.careertest.databinding.DetailUserDialogBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListUserActivity extends AppCompatActivity {

    private ActivityUserListBinding binding;
    private Dialog dialog;
    private DetailUserDialogBinding userDialogBinding;
    private View detailView;
    private SharedPreferences sharedPref;
    //    private List<User> userList = new ArrayList<>();
    private UserAdapter adapter;
    private String userListString;
    private Gson gson = new Gson();

    User user = new User();
    // change placeU
    List<User> userList=new ArrayList<User>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.fabAdd.setOnClickListener(view -> {
            startActivity(new Intent(ListUserActivity.this, MainActivity.class));
//            finish();
        });



    }

    private void init() {

        sharedPref = getSharedPreferences("user-data", MODE_PRIVATE);
        Type userType = new TypeToken<User>() {
        }.getType();

        Map<String, ?> all = sharedPref.getAll();

        for (Map.Entry<String, ?> entry : all.entrySet()) {

            Log.v("mySharedPreferences", entry.getKey() + ": " + entry.getValue().toString());


            userListString = sharedPref.getString(entry.getKey(), null);
            user = gson.fromJson(userListString, userType);

            userList.add(user);
        }


        Log.v("testShard",user.getName());

        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUsers.setHasFixedSize(true);

        setAdapter(userList);
    }

    private void setAdapter(List<User> userList) {
        adapter = new UserAdapter(userList) {
            @Override
            protected void deleteInfo(User deleteUser) {
                userList.remove(deleteUser);
                notifyDataSetChanged();
            }

            @Override
            protected void editInfo(User editUser) {
                Intent intent = new Intent(ListUserActivity.this, MainActivity.class);
                intent.putExtra("bool-edit", true);
                intent.putExtra("edit-user", editUser);
                startActivity(intent);
//                onBackPressed();
//                finish();
            }

            @Override
            public void showUser(User user) {
                InfoDialog(user);
            }

        };
        binding.rvUsers.setAdapter(adapter);
    }

    private void InfoDialog(User user) {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        detailView = LayoutInflater.from(this).inflate(R.layout.detail_user_dialog, null);
        userDialogBinding = DetailUserDialogBinding.bind(detailView);

        userDialogBinding.tvUsername.setText(user.getName());
        userDialogBinding.tvGenderUser.setText(user.getGender());
        userDialogBinding.tvStatusUser.setText(user.getMarital_Status());
        userDialogBinding.tvBirthdayUser.setText(user.getBirthDay());
        dialog.setContentView(detailView);
        dialog.show();

        userDialogBinding.btnConfirm.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }
}
