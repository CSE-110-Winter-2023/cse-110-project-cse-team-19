package com.example.cse110project.activity;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.cse110project.R;
import com.example.cse110project.Utilities;
import com.example.cse110project.model.EnterFriendViewModel;
import com.example.cse110project.model.User;
import com.example.cse110project.model.UserAPI;
import com.example.cse110project.model.UserAdapter;
import com.example.cse110project.model.UserDao;
import com.example.cse110project.model.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class EnterFriendActivity extends AppCompatActivity {

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_friend);

        SharedPreferences prefs = getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);
        this.recreateUser(prefs);


        var viewModel = setUpViewModel();
        var adapter = setupAdapter(viewModel);

        setupViews(viewModel, adapter);
    }

    private void setupViews(EnterFriendViewModel viewModel, UserAdapter adapter) {
        setupRecycler(adapter);
        setupInput(viewModel);
    }

    private void setupInput(EnterFriendViewModel viewModel) {
        var input = (EditText) findViewById(R.id.enterUIDEditText);
        input.setOnEditorActionListener((view, actionId, event) -> {
            // If the event isn't "done" or "enter", do nothing.
            if (actionId != EditorInfo.IME_ACTION_DONE) {
                return false;
            }
            Context context = getApplicationContext();

            var public_uid = input.getText().toString();
            try {
                var user = viewModel.getOrCreateUser(public_uid, context);
                if (user == null) {
                    Utilities.showAlert(this, "This public UID doesn't exist!");
                    return false;
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }

            return true;
        });
    }

    @SuppressLint("RestrictedApi")
    private void setupRecycler(UserAdapter adapter) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private UserAdapter setupAdapter(EnterFriendViewModel viewModel) {
        UserAdapter adapter = new UserAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnUserDeleteClickedListener(user -> onUserDeleteClicked(user, viewModel));
        LiveData<List<User>> listOfUsers= viewModel.getUsers();
        //Wanted to remove user's ID but having trouble removing user from the list
//        listOfUsers.observe(this, activity -> {
//            //activity.remove(0);
//        });
        listOfUsers.observe(this, adapter::setUsers);
        return adapter;
    }

    private void onUserDeleteClicked(User user, EnterFriendViewModel viewModel) {
        viewModel.delete(user);
    }

    private EnterFriendViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(EnterFriendViewModel.class);
    }

//    public void onAddPressed(View view) throws ExecutionException, InterruptedException, TimeoutException {
//        EditText textBoxUid = findViewById(R.id.enterUIDEditText);
//        String uid = textBoxUid.getText().toString();
//        var user =
//
//
//        var context = getApplicationContext();
//        var db = UserDatabase.provide(context);
//        var dao = db.getDao();
//
//
//        dao.upsert(user);
//
//        textBoxUid.setText("");
//    }

    public void onNextPressed(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(View view) {
        Intent intent = new Intent(this, EnterNameActivity.class);
        startActivity(intent);
        finish();
    }

    public void recreateUser(SharedPreferences prefs) {
        String user_public_code = prefs.getString(Utilities.USER_PUBLIC_UID, "");
        String user_personal_code = prefs.getString(Utilities.USER_PRIVATE_UID, "");
        String user_label = prefs.getString(Utilities.LABEL_NAME, "");
        float user_latitude = prefs.getFloat(Utilities.USER_LATITUDE, 0);
        float user_longitude = prefs.getFloat(Utilities.USER_LONGITUDE, 0);
        String user_created_at = prefs.getString(Utilities.CREATED_AT, "");
        String user_updated_at = prefs.getString(Utilities.UPDATED_AT, "");

        Utilities.personalUser = new User(user_public_code, user_personal_code,
                user_label, user_latitude, user_longitude);
        Utilities.personalUser.created_at = user_created_at;
        Utilities.personalUser.updated_at = user_updated_at;
    }

    public void onMockUrlBtnPressed(View view) {
        Intent intent = new Intent(this, mockUrlActivity.class);
        startActivity(intent);
    }
}