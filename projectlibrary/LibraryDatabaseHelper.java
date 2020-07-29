package se.hig.ndi12erd.projectlibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Klassen {@link LibraryDatabaseHelper} som startas och ärver {@link SQLiteOpenHelper}
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public class LibraryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "libraryDatabase";
    private static final int DATABASE_VERSION = 1;

    /**
     * Ärver till superklassen SQLiteOpenHelper.
     *
     * @param context
     */

    LibraryDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metod som skapar databas.
     * @param database
     */

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL("CREATE TABLE LIBRARYDB (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "TITLE TEXT," + "URL TEXT," + "NOTIFY NUMERIC);");
    }

    /**
     * Metod som uppgraderar databasen.
     *
     * @param database
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade (SQLiteDatabase database, int oldVersion, int newVersion){

    }

}
