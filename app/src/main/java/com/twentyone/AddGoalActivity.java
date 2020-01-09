package com.twentyone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.twentyone.LoginActivity.LOGIN_PREFERENCES;

public class AddGoalActivity extends AppCompatActivity {
    private EditText etSetGoal, etToalDaysForGoal;
    private DataBaseHelper dataBaseHelper;
    private Context context = AddGoalActivity.this;
    private SharedPreferences sharedpreferences;
    private UserFields userFields = null;
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        findId();
    }

    private void findId() {

        dataBaseHelper = DataBaseHelper.getInstance(context); //  create instance of db
        etSetGoal = (EditText) findViewById(R.id.etSetGoal);
        etToalDaysForGoal = (EditText) findViewById(R.id.etToalDaysForGoal);


        sharedpreferences = getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();


        email = sharedpreferences.getString(LoginActivity.USERNAME_KEY, "");

        if (email.equalsIgnoreCase("")) { // if no user found or something went wrong it will go to login screen

            editor.clear().commit();
            Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }


    }


    public void onSetGoalBtnClick(View view) {


        String setGoalStr = etSetGoal.getText().toString().trim();
        String totalDaysStr = etToalDaysForGoal.getText().toString().trim();
        if (setGoalStr.equalsIgnoreCase("")) {
            Toast.makeText(context, getResources().getString(R.string.err_PleaseAddGoal)
                    , Toast.LENGTH_SHORT).show();

            return;
        }

        if (totalDaysStr.equalsIgnoreCase("")) {
            Toast.makeText(context, getResources().getString(R.string.err_PleaseAddGoal)
                    , Toast.LENGTH_SHORT).show();

            return;
        }
        int totalDays = Integer.parseInt(totalDaysStr);


        String goalCreationDate = CommonFunctions.getCurrentDate(context);

        String goalEndDate = CommonFunctions.getCustomeDate(context, goalCreationDate, totalDaysStr);
        UserGoalList userGoalList = new UserGoalList();
        userGoalList.setGoal(setGoalStr);
        userGoalList.setTotalDaysOfGoal(totalDays);
        userGoalList.setName(email);
        userGoalList.setIsGoalFinished(0);
        userGoalList.setGoalCreatedDate(goalCreationDate);
        userGoalList.setGoalEndDate(goalEndDate);


        if (dataBaseHelper.addGoalString(userGoalList)) {


            Toast.makeText(context, getResources().getString(R.string.msg_GoalSuccessfully)
                    , Toast.LENGTH_SHORT).show();


            startActivity(new Intent(context, GoalListActivity.class));
            etSetGoal.setText("");
            finish();


        } else {

            Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain)
                    , Toast.LENGTH_SHORT).show();
        }

    }


    public void onCancelbtnClick(View view) {


    }

}