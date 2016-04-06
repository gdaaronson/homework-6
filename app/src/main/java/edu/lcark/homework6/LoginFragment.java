package edu.lcark.homework6;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Greg on 3/31/2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText name;

    private Button login;

    private Button newUser;

    private SQLHelper sqlHelper;

    public static final String USER = "LoginFragment.User";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmennt_login, container, false);

        name = (EditText) rootView.findViewById(R.id.fragment_login_edittext);

        login = (Button) rootView.findViewById(R.id.fragment_login_button_login);
        login.setOnClickListener(this);

        newUser = (Button) rootView.findViewById(R.id.fragment_login_button_new_user);
        newUser.setOnClickListener(this);

        sqlHelper = SQLHelper.getInstance(getContext());

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_login_button_login){
            Log.d(LoginFragment.class.getSimpleName(), "The login button was pressed");
            User user = new User(name.getText().toString());
            Cursor cursor = sqlHelper.getReadableDatabase().rawQuery("SELECT " + User.COLUMN_NAME_NAME + " FROM " + User.TABLE_NAME, null);
            boolean taken = false;
            if (cursor.moveToFirst()){
                do {
                    if (user.getUsername().equals(cursor.getString(0))) {
                        taken = true;
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
            if (!taken) {
                sqlHelper.insertUser(user);
            }
            if (!user.getUsername().equals("")) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_framelayout, MapFragment.newInstance(user.getUsername()));
                transaction.commit();
            } else {
                Snackbar.make(v, R.string.blank_username, Snackbar.LENGTH_SHORT).show();
            }
        }

        if (v.getId() == R.id.fragment_login_button_new_user){
            Log.d(LoginFragment.class.getSimpleName(), "The new user button was pressed");
            User user = new User(name.getText().toString());
            Cursor cursor = sqlHelper.getReadableDatabase().rawQuery("SELECT " + User.COLUMN_NAME_NAME + " FROM " + User.TABLE_NAME, null);
            boolean taken = false;
            if (cursor.moveToFirst()){
                do {
                    if (user.getUsername().equals(cursor.getString(0))) {
                        taken = true;
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
            if (taken) {
                Snackbar.make(v, getString(R.string.already_exists), Snackbar.LENGTH_SHORT).show();
            } else {
                sqlHelper.insertUser(user);
            }
        }
    }


}
