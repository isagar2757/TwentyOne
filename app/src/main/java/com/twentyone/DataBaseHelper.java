package com.twentyone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase sqliteDb;
    private static DataBaseHelper instance;
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TwentyOneDB.db";

    private Context context;


    DataBaseHelper(Context context, String name, CursorFactory factory,
                   int version) {
        super(context, name, factory, version);
        this.context = context;

    }

    private static void initialize(Context context, String databaseName) {
        if (instance == null) {

            if (!checkDatabase(context, databaseName)) {

                try {
                    copyDataBase(context, databaseName);
                } catch (IOException e) {

                    System.out.println(databaseName
                            + " does not exists ");
                }
            }

            instance = new DataBaseHelper(context, databaseName, null,
                    DATABASE_VERSION);
            sqliteDb = instance.getWritableDatabase();

            System.out.println("instance of  " + databaseName + " created ");
        }
    }

    public static final DataBaseHelper getInstance(Context context) {
        initialize(context, DATABASE_NAME);
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return sqliteDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void copyDataBase(Context aContext, String databaseName)
            throws IOException {

        InputStream myInput = aContext.getAssets().open(databaseName);

        String outFileName = getDatabasePath(aContext, databaseName);

        File f = new File("/data/data/" + aContext.getPackageName()
                + "/databases/");
        if (!f.exists())
            f.mkdir();

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

        System.out.println(databaseName + " copied");
    }

    public static boolean checkDatabase(Context aContext, String databaseName) {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = getDatabasePath(aContext, databaseName);
            try {
                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READWRITE);
                checkDB.close();

            } catch (Exception e) {
            }
        } catch (SQLiteException e) {

            System.out.println(databaseName + " does not exists");
        }

        return checkDB != null ? true : false;
    }

    private static String getDatabasePath(Context aContext, String databaseName) {
        return "/data/data/" + aContext.getPackageName() + "/databases/"
                + databaseName;
    }

    ////
    public static void rawQuery(String query) {
//        try {
//            if (sqliteDb.isOpen()) {
//                sqliteDb.close();
//            }
//            sqliteDb = instance.getWritableDatabase();
//
//            cursor = null;
//            cursor = sqliteDb.rawQuery(query, null);
//        } catch (Exception e) {
//            System.out.println("DB ERROR  " + e.getMessage());
//            e.printStackTrace();
//        }
//        return cursor;

    }

    public static void execute(String query) {
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);
        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
    }


    // User Define Functions

    //////// INSERT

    //Enter User registration
    public static boolean addRegistrationDetails(UserFields user) {
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }

            if (user == null) {
                return false;
            }

            String name = user.getName();
            String password = user.getPassword();
            String email = user.getEmail();
            int roleId = user.getRoleId();
            int phone = user.getPhone();

            String query = "insert into User(Name, Password, Email, Phone,RoleId) values('" + name + "','" + password + "','" + email + "','" + phone + "','" + roleId + "');";
            Log.e("quary user", "." + query);
            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);

        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean addGoalString(UserGoalList goalList) {

        UserGoalList userGoalList = goalList;

        if (userGoalList == null) {
            return false;
        }

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }


            String goalStr = userGoalList.getGoal().toString();
            String name = userGoalList.getName().toString();
            String goalStartDate = userGoalList.getGoalCreatedDate().toString();
            String goalEndDate = userGoalList.getGoalEndDate().toString();

            int totalDays = userGoalList.getTotalDaysOfGoal();
            int isGoalFinished = userGoalList.getIsGoalFinished();


            String query = "insert into UserGoalList(Name, Goal, GoalCreatedDate,GoalEndDate, IsGoalFinished,TotalDaysOfGoal) values('" + name + "','" + goalStr + "','" + goalStartDate + "','" + goalEndDate + "','" + isGoalFinished + "','" + totalDays + "');";

            Log.e("quary user goal list", "." + query);

            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);

        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean setGoalAction(UserGoalRecord record) {
        UserGoalRecord goalRecord = record;

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }


            String name = goalRecord.getName();
            int goalId = goalRecord.getGoalId();
            String goalActionDate = goalRecord.getGoalActionDate();
            int goalAction = goalRecord.getGoalAction();

            String query = "insert into UserGoalRecord(GoalId,Name, GoalAction, GoalActionDate) " +
                    "values('" + goalId + "','" + name + "','" + goalAction + "', '" + goalActionDate + "');";

            Log.e("query: user goal record", "." + query);
            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);

        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;


    }

    public boolean addQuoteString(String quoteStr) {

        if (quoteStr.equalsIgnoreCase("")) {
            return false;
        }

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }


            String query = "insert into Quotes(quotes) values('" + quoteStr + "');";

            Log.e("admin add quotestring", "." + query);

            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);

        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean addPreGoalString(String preGoalStr) {

        if (preGoalStr.equalsIgnoreCase("")) {
            return false;
        }

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }


            String query = "insert into PredefinedGoals(preGoal) values('" + preGoalStr + "');";

            Log.e("admin add predefined goal string", "." + query);

            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);

        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }
    //////////  UPDATE

    public boolean updateGoalCompletionDays(int goalId, int goalCompletionDay) {
        try {
            String query = "UPDATE UserGoalList SET GoalCompletionDays = '" + goalCompletionDay + "' WHERE GoalId = '" + goalId + "' ";
            sqliteDb.execSQL(query);

        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }


    ////////    SELECT
    public static UserFields checkCredentials(String email, String password) {

        UserFields userFields = null;
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select * from User where  Email='" + email + "' and password='" + password + "';";
            Log.e("lgoin search query", "." + query);
            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor != null) {
                userFields = new UserFields();

                while (cursor.moveToNext()) {
                    if (cursor.moveToFirst()) {

                        //     Name, Password, Email, Phone,RoleId)

                        userFields.setName(cursor.getString(1));
                        userFields.setPassword(cursor.getString(2));
                        userFields.setEmail(cursor.getString(3));
                        userFields.setPhone(cursor.getInt(4));
                        userFields.setRoleId(cursor.getInt(5));
                    }
                }
                cursor.close();
            } else {
                Log.e("error in db", "cursor is null at checkcredential function");
            }

        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
        return userFields;

    }

    public List<UserGoalList> getGoalList(String email) {

        ArrayList<UserGoalList> goalList = new ArrayList<>();

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();


            String query = "select * from UserGoalList where Name =" + email + ";";


            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor != null) {

//                while (cursor.moveToNext()) {
//                    if (cursor.moveToFirst()) {
//                        goalList.add(cursor.getString(cursor.getColumnIndex("Goal"))); //add the item
//                    }
//                }

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    int id = cursor.getInt(cursor.getColumnIndex("GoalId"));

                    String name = cursor.getString(cursor.getColumnIndex("Name"));
                    String goal = cursor.getString(cursor.getColumnIndex("Goal"));
                    UserGoalList userGoalList = new UserGoalList(id, name, goal);

                    goalList.add(userGoalList); //add the item
                    cursor.moveToNext();

                }

                cursor.close();
            } else {
                Log.e("error in db", "cursor is null at goallist function");
            }

        } catch (
                Exception e)

        {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
        return goalList;

    }

    public UserGoalList getOneGoalRecord(int goalId) {

        UserGoalList userGoalList = null;

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();


            String query = "select * from UserGoalList where GoalId ='" + goalId + "';";
            Log.e("search quary ", "." + query);

            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor != null) {


//                if (cursor.isFirst()) {
                //cursor.moveToFirst();

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("GoalId"));
                    String name = cursor.getString(cursor.getColumnIndex("Name"));
                    String goal = cursor.getString(cursor.getColumnIndex("Goal"));
                    String goalCreatedDate = cursor.getString(cursor.getColumnIndex("GoalCreatedDate"));
                    int isGoalFinished = cursor.getInt(cursor.getColumnIndex("IsGoalFinished"));
                    int totalDaysOfGoal = cursor.getInt(cursor.getColumnIndex("TotalDaysOfGoal"));
                    int goalCompletionDays = cursor.getInt(cursor.getColumnIndex("GoalCompletionDays"));
                    String goalEndDate = cursor.getString(cursor.getColumnIndex("GoalEndDate"));


                    userGoalList = new UserGoalList();
                    userGoalList.setGoalId(id);
                    userGoalList.setName(name);
                    userGoalList.setGoal(goal);
                    userGoalList.setGoalCreatedDate(goalCreatedDate);
                    userGoalList.setIsGoalFinished(isGoalFinished);
                    userGoalList.setTotalDaysOfGoal(totalDaysOfGoal);
                    userGoalList.setGoalCompletionDays(goalCompletionDays);
                    userGoalList.setGoalEndDate(goalEndDate);

                }
                cursor.close();
            } else {
                Log.e("error in db", "cursor is null at goallist function");
            }
        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }

        return userGoalList;

    }

    public List<Integer> isVotedOnGoalActionDate(String goalActionDate) {

        List<Integer> goalIdList = null;
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select GoalId from UserGoalRecord where GoalActionDate ='" + goalActionDate + "';";
            Log.e("getting id from date quary ", "." + query);

            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor.getCount() == 0) {

                cursor.close();
                return goalIdList;
            } else {
                goalIdList = new ArrayList<>();
            }


            if (cursor != null) {


                while (cursor.moveToNext()) {
                    if (cursor.moveToFirst()) {
                        int id = cursor.getInt(cursor.getColumnIndex("GoalId"));
                        goalIdList.add(id);
                    }
                }


                cursor.close();
            } else {
                Log.e("error in db", "cursor is null at goallist function");
            }
        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }

        return goalIdList;

    }

    public List<UserGoalList> getAllGoalRecordsList(String email) {

        List<UserGoalList> userGoalRecordList = null;
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select * from UserGoalList where Name ='" + email + "';";
            Log.e("getting user goal records from date query ", "." + query);

            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor.getCount() == 0) {

                cursor.close();
                return userGoalRecordList;
            } else {
                userGoalRecordList = new ArrayList<>();
            }


            if (cursor != null) {


                while (cursor.moveToNext()) {
                    if (cursor.moveToFirst()) {

                        int goalId = cursor.getInt(cursor.getColumnIndex("GoalId"));

                        String emailStr = cursor.getString(cursor.getColumnIndex("Name"));
                        String goal = cursor.getString(cursor.getColumnIndex("Goal"));
                        String goalCreatedDate = cursor.getString(cursor.getColumnIndex("GoalCreatedDate"));
                        int isGoalFinished = cursor.getInt(cursor.getColumnIndex("IsGoalFinished"));
                        int goalCompletionDays = cursor.getInt(cursor.getColumnIndex("GoalCompletionDays"));
                        int totalDaysOfGoal = cursor.getInt(cursor.getColumnIndex("TotalDaysOfGoal"));
                        String goalEndDate = cursor.getString(cursor.getColumnIndex("GoalEndDate"));


                        UserGoalList userGoalList = new UserGoalList();
                        userGoalList.setGoalId(goalId);
                        userGoalList.setName(emailStr);
                        userGoalList.setGoal(goal);
                        userGoalList.setGoalCreatedDate(goalCreatedDate);
                        userGoalList.setIsGoalFinished(isGoalFinished);
                        userGoalList.setGoalCompletionDays(goalCompletionDays);
                        userGoalList.setTotalDaysOfGoal(totalDaysOfGoal);
                        userGoalList.setGoalEndDate(goalEndDate);


                        userGoalRecordList.add(userGoalList); //add the item
                        cursor.moveToNext();

                    }
                }


                cursor.close();
            } else {
                Log.e("error in db", "cursor is null at goallist function");
            }
        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }

        return userGoalRecordList;


    }


//    Quotes
//    quotesId


    public List<Quotes> getQuotes() {

        List<Quotes> quotesList = null;
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select * from Quotes;";
            Log.e("getting all quotes query ", "." + query);

            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor.getCount() == 0) {

                cursor.close();
                return quotesList;
            } else {
                quotesList = new ArrayList<>();
            }


            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    do {
                        int quotesId = cursor.getInt(cursor.getColumnIndex("quotesId"));
                        String quotesStr = cursor.getString(cursor.getColumnIndex("quotes"));

                        Quotes quotes = new Quotes(quotesId, quotesStr);
                        quotesList.add(quotes);
                    } while (cursor.moveToNext());
                }


                cursor.close();
            } else {
                Log.e("error in db", "cursor is null at getquotes function");
            }
        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
        return quotesList;
    }









    public List<PredefinedGoals> getPreGoal() {

        List<PredefinedGoals> preGoalList = null;
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select * from PredefinedGoals;";
            Log.e("getting all Pre Goals query ", "." + query);

            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor.getCount() == 0) {

                cursor.close();
                return preGoalList;
            } else {
                preGoalList = new ArrayList<>();
            }


            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    do {
                        int preGoalID = cursor.getInt(cursor.getColumnIndex("preGoalID"));
                        String preGoalStr = cursor.getString(cursor.getColumnIndex("preGoal"));

                        PredefinedGoals predefinedGoals = new PredefinedGoals(preGoalID, preGoalStr);
                        preGoalList.add(predefinedGoals);
                    } while (cursor.moveToNext());
                }


                cursor.close();
            } else {
                Log.e("error in db", "cursor is null at getquotes function");
            }
        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
        return preGoalList;
    }

    public boolean updateQuotes(int quoteId, String quotes) {
        try {
            String query = "UPDATE Quotes SET quotes = '" + quotes + "' WHERE quotesId = '" + quoteId + "' ";
            Log.e("update query of quotes", "." + query);
            sqliteDb.execSQL(query);

        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteQuotes(int quoteId) {
        try {

            String query = "delete from Quotes where quotesId ='" + quoteId + "';";
            Log.e("delete query of quotes", "." + query);
            sqliteDb.execSQL(query);

        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
