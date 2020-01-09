package com.twentyone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {


    private EditText etName, etPassword, etEmail, etConfirmPassword, etPhone;
    private DataBaseHelper dataBaseHelper;
    private Context context = RegistrationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findId();
    }

    private void findId() {


        dataBaseHelper = DataBaseHelper.getInstance(context); //  create instance of db


        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);


    }

    @Override
    public void onBackPressed() {
        redirectToLoginScreen();
        super.onBackPressed();
    }

    // click functions
    public void onRegistrationBtnClick(View view) {


        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String ConfirmPassword = etConfirmPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phoneStr = etPhone.getText().toString().trim();


        if (name.equalsIgnoreCase("")) {
            Toast.makeText(context, getResources().getString(R.string.err_enter_your_name), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equalsIgnoreCase("")) {
            Toast.makeText(context, getResources().getString(R.string.err_enter_your_password), Toast.LENGTH_SHORT).show();
            return;
        }
        if (ConfirmPassword.equalsIgnoreCase("")) {
            Toast.makeText(context, getResources().getString(R.string.err_enter_your_confirm_password), Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.equalsIgnoreCase("")) {
            // TODO emaill varification
            Toast.makeText(context, getResources().getString(R.string.err_enter_your_email), Toast.LENGTH_SHORT).show();
            return;
        }
        if (phoneStr.equalsIgnoreCase("")) {
            Toast.makeText(context, getResources().getString(R.string.err_enter_your_phone), Toast.LENGTH_SHORT).show();
            return;
        }


        if (!isSamePasswords(password, ConfirmPassword)) { // if password is not same
            Toast.makeText(context, getResources().getString(R.string.err_PasswordShouldNotdifferent), Toast.LENGTH_SHORT).show();

            Log.e("error", "password same");
            return;
        }

        int phone = 0;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (Exception e) {
            Log.e("exc", "." + e);
        }


        UserFields user = new UserFields(name, password, email, phone, 1);  // passing parameter 1> user object, 2. User role 1 for user and 0 for admin

        if (dataBaseHelper.addRegistrationDetails(user)) {

            Toast.makeText(context, getResources().getString(R.string.msg_UserSuccessfullyRegistered), Toast.LENGTH_SHORT).show();

            Log.e("successfull", "onSignup btn ");
            redirectToLoginScreen();// redirect to login screen

        } else {
            Log.e("error", "onSignup btn ");
            Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();


        }


    }

    public void onCancelbtnClick(View view) {
        clearAllEditText();

    }

    public void onLoginClick(View view) {
        clearAllEditText();
        redirectToLoginScreen();

    }


    // utility functions

    private void clearAllEditText() {
        etName.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
        etPhone.setText("");
        etEmail.setText("");

    }

    private void redirectToLoginScreen() {
        startActivity(new Intent(context, LoginActivity.class));
        finish();

    }

    private boolean isSamePasswords(String password, String confirmPassword) {
        if (password.equalsIgnoreCase(confirmPassword)) {
            return true;
        }
        return false;
    }


}