package com.twentyone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GoalConfirmation extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private Context context = GoalConfirmation.this;
    private int goalId = -1;
    private String name = "", goalName = "";
    private TextView tvGoalOfUser, tvGoalCompletedDays;
    private String goalActionDate, goalEndDate = "";
    private int totalDaysOfGoal, goalCompletionDays, isGoalFinished;
    private boolean isVotedToDay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_confirmation);

        findId();
    }


    private void setupToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.YourGoalList));
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_users, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (menuItem.getItemId() == R.id.ic_logout) {

            Toast.makeText(context, getResources().getString(R.string.LogOut), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(context, LoginActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        redirectToGoalListActivity("");
        super.onBackPressed();
    }


    private void findId() {
        setupToolBar();
        Intent intent = getIntent();
        goalId = intent.getIntExtra("goalId", -1);
        goalName = intent.getStringExtra("goalName");

        name = CommonFunctions.getUserName(context);

        if (goalId == -1) { // if it's -1 , something wrong happened
            Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(GoalConfirmation.this, GoalListActivity.class));
            finish();
        }
        if (name.equalsIgnoreCase("")) {
            startActivity(new Intent(GoalConfirmation.this, GoalListActivity.class));
            finish();
        }

        dataBaseHelper = DataBaseHelper.getInstance(context); //  create instance of db

        tvGoalOfUser = (TextView) findViewById(R.id.tvGoalOfUser);
        tvGoalCompletedDays = (TextView) findViewById(R.id.tvGoalCompletedDays);

        if (!goalName.equalsIgnoreCase("")) {
            tvGoalOfUser.setText(goalName);
        }


        UserGoalList userGoalList = dataBaseHelper.getOneGoalRecord(goalId);
        if (userGoalList == null) {
            redirectToGoalListActivity(getResources().getString(R.string.err_pleaseTryAgain));
        }

        if (isGoalFinished == CommonFunctions.IS_GOAL_FINISHED) {
            Toast.makeText(context, getResources().getString(R.string.msg_GoalActionSuccessfully), Toast.LENGTH_SHORT).show();
            redirectToGoalListActivity(getResources().getString(R.string.err_youAlreadyFinishedYourGoal));
        }


        goalEndDate = userGoalList.getGoalEndDate().toString();
        goalActionDate = CommonFunctions.getCurrentDate(context);//  goal action date is same as current date

        totalDaysOfGoal = userGoalList.getTotalDaysOfGoal();
        goalCompletionDays = userGoalList.getGoalCompletionDays();
        isGoalFinished = userGoalList.getIsGoalFinished();

        tvGoalCompletedDays.setText(goalCompletionDays + "");

        isVotedToDay = isVoted();


    }

    private void redirectToGoalListActivity(String message) {
        Toast.makeText(context, "." + message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(context, GoalListActivity.class));
        finish();
    }


    public void onYesBtnClick(View view) {

//        if (!isVoted()) { // allows to set goal action only if user has not voted
        if (!isVotedToDay) { // allows to set goal action only if user has not voted

            didUserFinishedGoal(true);
        }
    }

    public void onNoBtnClick(View view) {
        //      if (!isVoted()) { // allows to set goal action only if user has not voted
        if (!isVotedToDay) { // allows to set goal action only if user has not voted


            didUserFinishedGoal(false);
        }
    }

    private boolean isVoted() {

        try {

            List<Integer> goalIdList = dataBaseHelper.isVotedOnGoalActionDate(goalActionDate); // goal action date means today's date i.e when user click yes/no button
            if (goalIdList != null) {
                if (goalIdList.size() > 0) { // if didnt find any record
                    for (int i = 0; i < goalIdList.size(); i++) {
                        if (goalIdList.get(i).equals(goalId)) { // checks the entry for current goalid with all id
                            isVotedToDay = true;
                            Toast.makeText(context, getResources().getString(R.string.err_youAlreadyAetActionForYourGoal), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            } else {
                isVotedToDay = false;
            }

        } catch (Exception e) {
            Log.e("err isvoted fn", e.getMessage());
            redirectToGoalListActivity(getResources().getString(R.string.err_pleaseTryAgain));
        }

        return isVotedToDay;
    }

    // after pressing yes or no button, this method will invoke
    private void didUserFinishedGoal(boolean isGoalCompleted) {

        int goalAction = 0;
        if (isGoalCompleted) {
            goalAction = 1;
        }

        // todo check the condition if date is null or not


        UserGoalRecord goalRecord = new UserGoalRecord(goalId, name, goalAction, goalActionDate);

        if (dataBaseHelper.setGoalAction(goalRecord)) {
            isVotedToDay = true; // if user press yes or no it will not allow him to vote again

            if (goalAction == 1) {
                boolean flag = dataBaseHelper.updateGoalCompletionDays(goalId, goalCompletionDays + 1);// this method will update completion days in UserGoalList
                if (flag) {
                    tvGoalCompletedDays.setText(goalCompletionDays + "");
                    // todo m update goal finished flag
                    redirectToGoalListActivity(getResources().getString(R.string.msg_GoalActionSuccessfully));
                } else { // if any err comes --> redirect to main activity

                    redirectToGoalListActivity(getResources().getString(R.string.err_pleaseTryAgain));
                }

            }
        } else {
            Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }


    public void onCancelBtnClick(View view) {
        redirectToGoalListActivity("");


    }


}
