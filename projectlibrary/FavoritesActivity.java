package se.hig.ndi12erd.projectlibrary;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class FavoritesActivity extends ListActivity {

    private SQLiteDatabase database;
    private Cursor cursor;
    CursorAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView favoritesList = getListView();

        try {
            SQLiteOpenHelper libraryDatabaseHelper = new LibraryDatabaseHelper(this);
            database = libraryDatabaseHelper.getReadableDatabase();
            cursor = database.query("LIBRARYDB", new String[]{"_id","TITLE"}, null, null, null, null, null);
            listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"TITLE"}, new int[]{android.R.id.text1}, 0);
            favoritesList.setAdapter(listAdapter);
        } catch (SQLiteException e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id){
        Intent intent = new Intent(FavoritesActivity.this, ProfileActivity.class);
        try {
            Cursor titleCursor = (Cursor)listView.getItemAtPosition(position);
            cursor = database.query("LIBRARYDB", new String[]{"URL"}, "TITLE = ?", new String[]{titleCursor.getString(1)}, null, null, null );
            cursor.moveToFirst();
            String url = cursor.getString(0);
            if (url != null){
                intent.putExtra("url", url);
                startActivity(intent);
            }
        } catch (SQLiteException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        database.close();
    }


}
