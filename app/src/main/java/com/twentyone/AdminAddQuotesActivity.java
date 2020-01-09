package com.twentyone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class AdminAddQuotesActivity extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;
    private Context context = AdminAddQuotesActivity.this;

    private TextView etAddNewQuotes;
    private ListView lvQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_quotes);

        findId();
    }

    private void findId() {
        setupToolBar();

        dataBaseHelper = DataBaseHelper.getInstance(context); //  create instance of db

        etAddNewQuotes = (TextView) findViewById(R.id.etAddNewQuotes);
        lvQuotes = (ListView) findViewById(R.id.lvQuotes);

        fillQuoteListView();


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


    public void onAddNewQuotesBtnClick(View view) {

        String quoteStr = etAddNewQuotes.getText().toString().trim();

        if (quoteStr.equalsIgnoreCase("")) {
            Toast.makeText(context, getResources().getString(R.string.err_PleaseAddQuote)
                    , Toast.LENGTH_SHORT).show();
            return;
        }


        if (dataBaseHelper.addQuoteString(quoteStr)) {


            Toast.makeText(context, getResources().getString(R.string.msg_QuoteaddedSuccessfully)
                    , Toast.LENGTH_SHORT).show();

            fillQuoteListView();


        } else {

            Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain)
                    , Toast.LENGTH_SHORT).show();
        }

    }

    private void fillQuoteListView() {
        List<Quotes> quoteList = dataBaseHelper.getQuotes();
        ListviewAdapter listviewAdapter = new ListviewAdapter(quoteList);

        if (quoteList != null) {
            lvQuotes.setAdapter(listviewAdapter);

        } else {
            Toast.makeText(context, getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();
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

    public void onAddPreGoalsClick(View view) {
        startActivity(new Intent(context, AddPreGoals.class));
        finish();

    }


    public class ListviewAdapter extends BaseAdapter {

        private List<Quotes> quoteList = new ArrayList<>();

        public ListviewAdapter(List<Quotes> quoteList) {
            this.quoteList = quoteList;
        }


        @Override
        public int getCount() {
            return quoteList.size();
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

            final String quoteName = quoteList.get(position).getQuotes().toString();
            final int quoteId = quoteList.get(position).getQuotesId();

            holder.tvTextAdapter.setText(quoteName);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(context, AdminQuoteDeleteActivity.class).putExtra("quoteId", quoteId).putExtra("quoteName", quoteName));
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
        TextView tvTextAdapter;
    }


}
