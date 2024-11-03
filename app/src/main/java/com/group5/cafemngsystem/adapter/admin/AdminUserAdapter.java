package com.group5.cafemngsystem.adapter.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.dbo.UserDto;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {

    private List<UserDto> userList;

    public AdminUserAdapter(List<UserDto> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public com.group5.cafemngsystem.adapter.admin.AdminUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new AdminUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserAdapter.ViewHolder holder, int position) {
        UserDto user = userList.get(position);
        holder.setData(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtUserId;
        private TextView txtUserName;
        private TextView txtFullname;
        private TextView txtRole;

        private void bindingView() {
            txtUserId = itemView.findViewById(R.id.txtListUserId);
            txtUserName = itemView.findViewById(R.id.txtUserUsername);
            txtFullname = itemView.findViewById(R.id.txtUserFname);
            txtRole = itemView.findViewById(R.id.txtUserRole);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
        }

        public void setData(UserDto user) {
            txtUserId.setText(String.valueOf(user.getId()));
            txtUserName.setText(user.getUsername());
            txtFullname.setText(user.getFullname());
            txtRole.setText(user.getRole().toString());
        }
    }
}


