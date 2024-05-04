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

    List<User> users;

    Dialog dialog;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.tvUserName.setText(users.get(position).getName());
        holder.cvUser.setOnClickListener(view -> {
            showUser(users.get(position));
        });
        holder.ivEdit.setOnClickListener(view -> {
            editInfo(users.get(position));
        });
        holder.ivDelete.setOnClickListener(view -> {
            deleteInfo(users.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    protected abstract void deleteInfo(User deleteUser);

    protected abstract void editInfo(User editUser);

    public abstract void showUser(User user);

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


