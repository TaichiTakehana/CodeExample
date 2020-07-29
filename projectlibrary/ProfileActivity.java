package se.hig.ndi12erd.projectlibrary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private Cursor cursor;
    private TextView titleView, authorView, editionView, publisherView, pageView, isbnView, alternateTitleView, availabilityView, notifyView;
    private Button favoriteButton;
    private CheckBox notifyCheckBox;
    private Document document;
    private String url;
    private final int NOTIFY_FALSE = 0;
    private final int NOTIFY_TRUE = 1;
    private final String ADD_FAVORITE_TEXT = "ADD TO FAVORITE";
    private final String REMOVE_FAVORITE_TEXT = "REMOVE FROM FAVORITE";
    public static final String CHECKED_OUT_TEXT = "Utlånad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        titleView = findViewById(R.id.title);
        authorView = findViewById(R.id.author);
        editionView = findViewById(R.id.edition);
        publisherView = findViewById(R.id.publisher);
        pageView = findViewById(R.id.pages);
        isbnView = findViewById(R.id.isbn);
        alternateTitleView = findViewById(R.id.alternateTitle);
        availabilityView = findViewById(R.id.availability);
        notifyView = findViewById(R.id.notifyText);
        favoriteButton = findViewById(R.id.favorite);
        notifyCheckBox = findViewById(R.id.notifyCheckBox);

        SQLiteOpenHelper libraryDatabaseHelper = new LibraryDatabaseHelper(this);
        database = libraryDatabaseHelper.getWritableDatabase();

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        (new ParseProfile()).execute(url);



        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteButtonClicked();
            }
        });

    }

    private void favoriteButtonClicked(){

        if(cursor.moveToFirst()){
            database.delete("LIBRARYDB", "TITLE = ?", new String[]{document.select(".title").text()});
            favoriteButton.setText(ADD_FAVORITE_TEXT);
            showNotifyOption(false);
            cursor = database.query("LIBRARYDB", new String[]{"TITLE", "NOTIFY"}, "TITLE = ?", new String[]{document.select(".title").text()}, null, null, null );
            Toast.makeText(this, "Removed from favorites!", Toast.LENGTH_LONG).show();
        }
        else {
            ContentValues favoriteValues = new ContentValues();
            favoriteValues.put("TITLE", document.select(".title").text());
            favoriteValues.put("URL", url);
            favoriteValues.put("NOTIFY", NOTIFY_FALSE);
            database.insert("LIBRARYDB", null, favoriteValues);
            favoriteButton.setText(REMOVE_FAVORITE_TEXT);
            cursor = database.query("LIBRARYDB", new String[]{"TITLE", "NOTIFY"}, "TITLE = ?", new String[]{document.select(".title").text()}, null, null, null );
            showNotifyOption(true);
            Toast.makeText(this, "Added to favorites!", Toast.LENGTH_LONG).show();
        }
    }

    private void showNotifyOption(Boolean favored){

        if (favored == true && document.select("span.item-status").text().equals(CHECKED_OUT_TEXT)){
            notifyView.setVisibility(View.VISIBLE);
            notifyCheckBox.setVisibility(View.VISIBLE);
            cursor.moveToFirst();
            if (cursor.getInt(1) == NOTIFY_TRUE){
                notifyCheckBox.setChecked(true);
            }
        }
        else {
            if (notifyCheckBox.isChecked()){
                notifyCheckBox.setChecked(false);
                checkBoxClicked(notifyCheckBox);
            }
            notifyView.setVisibility(View.INVISIBLE);
            notifyCheckBox.setVisibility(View.INVISIBLE);
        }
    }

    public void checkBoxClicked (View v){
        ContentValues favoriteValues = new ContentValues();
        favoriteValues.put("TITLE", document.select(".title").text());
        favoriteValues.put("URL", url);
        Cursor notifyCursor = database.query("LIBRARYDB", new String[]{"TITLE","URL"}, "NOTIFY = ?", new String[]{Integer.toString(1)}, null, null, null);
        if(notifyCheckBox.isChecked()){
            favoriteValues.put("NOTIFY", NOTIFY_TRUE);
            database.update("LIBRARYDB", favoriteValues,"TITLE = ?", new String[]{document.select(".title").text()} );
            notifyCursor.moveToFirst();
            Log.v("ZVARRI!Profile", Integer.toString(notifyCursor.getCount()));
            if (notifyCursor.getCount() == 1){
                Intent intent = new Intent(this, NotificationService.class);
                startService(intent);
            }
        }
        else {
            favoriteValues.put("NOTIFY", NOTIFY_FALSE);
            database.update("LIBRARYDB", favoriteValues,"TITLE = ?", new String[]{document.select(".title").text()} );
        }
    }



    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        database.close();
    }

    private class ParseProfile extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            try{
                document = Jsoup.connect(strings[0]).get();
            }
            catch (Throwable t){
                t.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected void onPostExecute(Void result){
            titleView.setText("Titel: " + document.select(".title").text());
            Elements elements = document.select(".author");
            if(elements.size() > 1) {
                authorView.setText(elements.first().text() + "\n" + elements.get(1).text());
            }
            else {
                authorView.setText(elements.text());
            }
            publisherView.setText(document.select(".results_summary").get(0).text());
            editionView.setText(document.select(".edition").text());
            pageView.setText(document.select(".description").text());
            isbnView.setText(document.select(".isbn").text());
            alternateTitleView.setText(document.select(".other_title").text());
            availabilityView.setText("Available: " + document.select("span.item-status").text());
            favoriteButton.setVisibility(View.VISIBLE);

            cursor = database.query("LIBRARYDB", new String[]{"TITLE", "NOTIFY"}, "TITLE = ?", new String[]{document.select(".title").text()}, null, null, null );
            if(cursor.moveToFirst()){
                favoriteButton.setText(REMOVE_FAVORITE_TEXT);
                showNotifyOption(true);
            }
            else {
                favoriteButton.setText(ADD_FAVORITE_TEXT);
                showNotifyOption(false);
            }
        }
    }
}
