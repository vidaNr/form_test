package com.example.careertest;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    List<String> userNames;

    Dialog dialog;

    public UserAdapter(List<String> userNames) {
        this.userNames = userNames;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.tvUserName.setText(userNames.get(position));
        holder.cvUser.setOnClickListener(view -> {
            showUser(userNames.get(position));
        });
        holder.ivEdit.setOnClickListener(view -> {
            editInfo(userNames.get(position));
        });
        holder.ivDelete.setOnClickListener(view -> {
            deleteInfo(userNames.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return userNames.size();
    }

    protected abstract void deleteInfo(String deleteUser);

    protected abstract void editInfo(String editUser);

    public abstract void showUser(String username);

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUserName;
        public ImageView ivEdit, ivDelete;
        public CardView cvUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_username);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            cvUser = itemView.findViewById(R.id.cv_user);
        }
    }

}


