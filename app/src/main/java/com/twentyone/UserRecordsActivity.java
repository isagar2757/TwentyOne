package com.twentyone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import static com.twentyone.CommonFunctions.getUserName;

public class UserRecordsActivity extends AppCompatActivity {

    private ListView lvUserGoalRecords;
    private DataBaseHelper dataBaseHelper;
    private Context context = UserRecordsActivity.this;
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_records);
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
        startActivity(new Intent(context, GoalListActivity.class));
        finish();
        super.onBackPressed();
    }

    private void findId() {
        setupToolBar();
        dataBaseHelper = DataBaseHelper.getInstance(context); //  create instance of db
        lvUserGoalRecords = (ListView) findViewById(R.id.lvUserGoalRecords);

        email = getUserName(context);
//        List<UserGoalList> goalList = dataBaseHelper.getGoalList(email);
        List<UserGoalList> goalList = dataBaseHelper.getAllGoalRecordsList(email);

        if (goalList.size() > 0) {
            ListviewAdapter listviewAdapter = new ListviewAdapter(goalList);
            lvUserGoalRecords.setAdapter(listviewAdapter);
        } else {
            Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
    }


    public class ListviewAdapter extends BaseAdapter {

        private List<UserGoalList> goalRecordsList = new ArrayList<>();


        public ListviewAdapter(List<UserGoalList> goalList) {
            this.goalRecordsList = goalList;
        }


        @Override
        public int getCount() {
            return goalRecordsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("WrongViewCast")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.user_records_adapter, parent, false);

                holder.tvTextAdapter = (TextView) convertView.findViewById(R.id.tvTextAdapter);
                holder.tvGoalStartDate = (TextView) convertView.findViewById(R.id.tvGoalStartDate);
                holder.tvGoalEndDate = (TextView) convertView.findViewById(R.id.tvGoalEndDate);
                holder.tvTotalGoalCompletionDay = (TextView) convertView.findViewById(R.id.tvTotalGoalCompletionDay);
                holder.tvTotalDaysOfGoal = (TextView) convertView.findViewById(R.id.tvTotalDaysOfGoal);
                holder.tvGoalName = (TextView) convertView.findViewById(R.id.tvGoalName);
                holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


// todo fill all texview

            final String goalName = goalRecordsList.get(position).getGoal().toString();

            String goalStartDate = goalRecordsList.get(position).getGoalCreatedDate().toString();
            String goalEndDate = goalRecordsList.get(position).getGoalEndDate().toString();
            int goalCompletionDays = goalRecordsList.get(position).getGoalCompletionDays();
            int totalDaysOfGoal = goalRecordsList.get(position).getTotalDaysOfGoal();


            holder.tvGoalName.setText(goalName);
            holder.tvGoalStartDate.setText(goalStartDate);
            holder.tvGoalEndDate.setText(goalEndDate);
            holder.tvTotalGoalCompletionDay.setText("" + goalCompletionDays);
            holder.tvTotalDaysOfGoal.setText("" + totalDaysOfGoal);
            holder.progressBar.setMax(totalDaysOfGoal);
            holder.progressBar.setProgress(goalCompletionDays);

            final int goalId = goalRecordsList.get(position).getGoalId();


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(context, GoalConfirmation.class).putExtra("goalId", goalId).putExtra("goalName", goalName));
                        finish();
                    } catch (Exception e) {
                        Log.e("error main row adapter", e.getMessage());
                    }
                }
            });

            return convertView;
        }
    }

    public class ViewHolder {
        TextView tvTextAdapter, tvGoalEndDate, tvGoalStartDate, tvTotalGoalCompletionDay, tvTotalDaysOfGoal, tvGoalName;
        ProgressBar progressBar;

    }

}
