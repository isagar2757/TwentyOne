package com.twentyone;

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
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.twentyone.CommonFunctions.getUserName;

public class GoalListActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private Context context = GoalListActivity.this;
    private ListView lvGoalList;
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);
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
            // TODO set null value to sp

        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, LoginActivity.class));
        finish();
        super.onBackPressed();
    }





    private void findId() {
        setupToolBar();
        dataBaseHelper = DataBaseHelper.getInstance(context); //  create instance of db
        lvGoalList = (ListView) findViewById(R.id.lvGoalList);

        email = getUserName(context);
        List<UserGoalList> goalList = dataBaseHelper.getGoalList(email);

        if (goalList.size() > 0) {
            ListviewAdapter listviewAdapter = new ListviewAdapter(goalList);
            lvGoalList.setAdapter(listviewAdapter);
        }
    }



    public void onAddNewGoal(View view) {
        startActivity(new Intent(context, AddGoalActivity.class));
        finish();
    }

    public void onViewRecords(View view) {
        startActivity(new Intent(context, UserRecordsActivity.class));
        finish();
    }


    public class ListviewAdapter extends BaseAdapter {

        private List<UserGoalList> goalList = new ArrayList<>();

        public ListviewAdapter(List<UserGoalList> goalList) {
            this.goalList = goalList;
        }


        @Override
        public int getCount() {
            return goalList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.simple_text_adapter, parent, false);

                holder.tvTextAdapter = (TextView) convertView.findViewById(R.id.tvTextAdapter);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String goalName = goalList.get(position).getGoal().toString();
            holder.tvTextAdapter.setText(goalName);

            //todo

            final int goalId = goalList.get(position).getGoalId();

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(context, GoalConfirmation.class).putExtra("goalId", goalId).putExtra("goalName", goalName));
                    } catch (Exception e) {
                        Log.e("error main row adapter", e.getMessage());
                    }
                }
            });

            return convertView;
        }
    }

    public class ViewHolder {
        TextView tvTextAdapter;
    }


}
