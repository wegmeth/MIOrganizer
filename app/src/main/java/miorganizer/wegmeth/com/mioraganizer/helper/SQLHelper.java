package miorganizer.wegmeth.com.mioraganizer.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import miorganizer.wegmeth.com.mioraganizer.models.Group;
import miorganizer.wegmeth.com.mioraganizer.models.Module;
import miorganizer.wegmeth.com.mioraganizer.models.Semester;

public class SQLHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "MIOrganizer.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_SEMESTER =
            "CREATE TABLE " + Semester.TABLE_NAME + " (" +
                    Semester._ID + " INTEGER PRIMARY KEY," +
                    Semester.COLUMN_NUMBER + " INTEGER )";


    private static final String SQL_CREATE_MODULE =
            "CREATE TABLE " + Module.TABLE_NAME + " ( " +
                    Module._ID + " INTEGER PRIMARY KEY, " +
                    Module.COLUMN_SEMESTER_ID + " INTEGER, " +
                    Module.COLUMN_NAME + " INTEGER, " +
                    Module.COLUMN_CREDITS + " INTEGER, " +
                    Module.COLUMN_INSTRUCTOR + " TEXT, " +
                    Module.COLUMN_DESCRIPTION + " TEXT, " +
                    Module.COLUMN_SHORT_NAME + " TEXT, " +
                    Module.COLUMN_PRAKTIKUM + " BOOLEAN ," +
                    Module.COLUMN_DONE + " BOOLEAN, " +
                    Module.COLUMN_PRAKTIKUM_DONE + " BOOLEAN, " +
                    Module.COLUMN_NOTE + " DOUBLE " +
                    ")";

    private static final String SQL_CREATE_GROUP =
            "CREATE TABLE " + Group.TABLE_NAME + " ( " + Group._ID + " INTEGER PRIMARY KEY, " + Group.COLUMN_NAME + " TEXT , " + Group.COLUMN_NOTES + " TEXT )";

    private Context context;


    public SQLHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_GROUP);
        db.execSQL(SQL_CREATE_SEMESTER);
        db.execSQL(SQL_CREATE_MODULE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);

    }
}
