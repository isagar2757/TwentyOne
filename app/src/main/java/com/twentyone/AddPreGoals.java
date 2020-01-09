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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddPreGoals extends AppCompatActivity {


    private DataBaseHelper dataBaseHelper;
    private Context context = AddPreGoals.this;

    private TextView etAddNewQuotes;
    private ListView lvQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pre_goals);

        findId();
    }

    private void findId() {
        setupToolBar();

        dataBaseHelper = DataBaseHelper.getInstance(context); //  create instance of db

        etAddNewQuotes = (TextView) findViewById(R.id.etAddNewQuotes);
        lvQuotes = (ListView) findViewById(R.id.lvQuotes);




    }

    private void setupToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_admin, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            redirectToLoginPage("");
        }
        if (menuItem.getItemId() == R.id.ic_logout) {
            redirectToLoginPage(getResources().getString(R.string.LogOut));
        }


        return super.onOptionsItemSelected(menuItem);
    }

    private void redirectToLoginPage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }


    public void onAddPreGoalsBtnClick(View view) {

        String quoteStr = etAddNewQuotes.getText().toString().trim();

        if (quoteStr.equalsIgnoreCase("")) {
            Toast.makeText(context, getResources().getString(R.string.err_PleaseAddQuote)
                    , Toast.LENGTH_SHORT).show();
            return;
        }


        if (dataBaseHelper.addPreGoalString(quoteStr)) {


            Toast.makeText(context, getResources().getString(R.string.msg_QuoteaddedSuccessfully)
                    , Toast.LENGTH_SHORT).show();




        } else {

            Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain)
                    , Toast.LENGTH_SHORT).show();
        }

    }



    public void onCancelbtnClick(View view) {
        etAddNewQuotes.setText("");
    }

    public void onRefreshBtnClick(View view) {
        etAddNewQuotes.setText("");
        startActivity(new Intent(context, AdminAddQuotesActivity.class));
        finish();

    }

    public void onAdminAddQuotesClick(View view) {
        startActivity(new Intent(context, AdminAddQuotesActivity.class));
        finish();

    }






}
