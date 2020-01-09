package com.twentyone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {


    private EditText etEmail, etPassword;
    private DataBaseHelper dataBaseHelper;
    private Context context = LoginActivity.this;

    private SharedPreferences sharedpreferences;
    public static final String LOGIN_PREFERENCES = "loginPreferences";
    public static final String USER_OBJECT_KEY = "userObjectKey";
    public static final String USERNAME_KEY = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findId();
    }


    private void findId() {
        dataBaseHelper = DataBaseHelper.getInstance(context); //  create instance of db

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    public void onLoginbtnClick(View view) {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.equalsIgnoreCase("")) {

            Toast.makeText(context, getResources().getString(R.string.err_enter_your_email), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equalsIgnoreCase("")) {
            Toast.makeText(context, getResources().getString(R.string.err_enter_your_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.equalsIgnoreCase(CommonFunctions.ADMIN_EMAIL) && password.equalsIgnoreCase(CommonFunctions.ADMIN_PASSWORD)) {
            startActivity(new Intent(context, AdminAddQuotesActivity.class));
            finish();
            return;
        } else {

            // checks only for users not for admin
            UserFields user = dataBaseHelper.checkCredentials(email, password);

            if (user != null) {
                if (user.equals(null)) {
                    Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    sharedpreferences = getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    // stores only for users not for admin
                    if (!user.getEmail().equalsIgnoreCase("") || !user.getEmail().equalsIgnoreCase(CommonFunctions.ADMIN_EMAIL)) {
                        editor.putString(USERNAME_KEY, user.getEmail());
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editor.commit();


                } catch (Exception e) {
                    Log.e("login btn click error", e.getMessage());
                    Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();
                    return;
                } finally {
                    startActivity(new Intent(LoginActivity.this, GoalListActivity.class));
                    finish();
                }
            } else {
                Toast.makeText(context, getResources().getString(R.string.err_credentialisnotmatching), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onCancelbtnClick(View view) {


        etEmail.setText("");
        etPassword.setText("");

    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }

}
