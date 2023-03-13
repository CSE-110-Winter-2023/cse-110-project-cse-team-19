package com.example.cse110project.model;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110project.R;
import com.example.cse110project.Utilities;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users = Collections.emptyList();
    private Consumer<User> onUserDeleteClicked;

    public void setOnUserDeleteClickedListener(Consumer<User> onUserDeleteClicked) {
        this.onUserDeleteClicked = onUserDeleteClicked;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View itemView;
        public final TextView public_uid_View;
        public final View deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            // Populate text views
            this.public_uid_View = itemView.findViewById(R.id.user_public_uid_title);
            this.deleteButton = itemView.findViewById(R.id.user_item_delete);
        }

        public void bind(User user) {
            public_uid_View.setText(user.public_code);
            deleteButton.setOnClickListener(v -> onUserDeleteClicked.accept(user));
        }
    }

    public void setUsers(List<User> users) {
        //users.remove(Utilities.personalUser);
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        var view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        var user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public long getItemId(int position) {
        // We don't actually have a unique int/long ID on the Note object, so instead
        // we generate a unique ID based on the title. It is possible that two notes
        // could have different titles but the same hash code, but it is beyond unlikely.

        // Same logic here except with a public UID code instead of a title
        return users.get(position).public_code.hashCode();
    }
}