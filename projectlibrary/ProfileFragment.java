package se.hig.ndi12erd.projectlibrary;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Klassen {@link ProfileFragment}Fragment som startas och ärver {@link Fragment}
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public class ProfileFragment extends Fragment {

    private SQLiteDatabase database;
    private Cursor cursor;
    private TextView titleView, authorView, editionView, publisherView, pageView, isbnView, alternateTitleView, availabilityView, notifyView;
    private Button favoriteButton;
    private CheckBox notifyCheckBox;
    private Document document;
    private String url;
    private final int NOTIFY_FALSE = 0;
    private final int NOTIFY_TRUE = 1;
    public static final String CHECKED_OUT_TEXT = "Utlånad";

    /**
     * En tom konstruktor.
     */

    public ProfileFragment() {}

    /**
     * Metoden gör att ersätter profileURL till url.
     *
     * @param profileURL
     */

    public void changeProperties(String profileURL){
        url = profileURL;
    }

    /**
     * Metod som skapar och returnerar vyhiearkin som är associerad med fragmentet.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        titleView = view.findViewById(R.id.title);
        authorView = view.findViewById(R.id.author);
        editionView = view.findViewById(R.id.edition);
        publisherView = view.findViewById(R.id.publisher);
        pageView = view.findViewById(R.id.pages);
        isbnView = view.findViewById(R.id.isbn);
        alternateTitleView = view.findViewById(R.id.alternateTitle);
        availabilityView = view.findViewById(R.id.availability);
        notifyView = view.findViewById(R.id.notifyText);
        favoriteButton = view.findViewById(R.id.favorite);
        notifyCheckBox = view.findViewById(R.id.notifyCheckBox);

        SQLiteOpenHelper libraryDatabaseHelper = new LibraryDatabaseHelper(getContext());
        database = libraryDatabaseHelper.getWritableDatabase();

        (new ParseProfile()).execute(url);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteButtonClicked();
            }
        });
        notifyCheckBox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkBoxClicked();
            }
        });

        return view;
    }

    /**
     * Lägger till eller ta bort favorite beroende på dess status
     */

    private void favoriteButtonClicked(){

        if(cursor.moveToFirst()){
            database.delete(getString(R.string.TABLE_LIBRARY), getString(R.string.SELECTION_TITLE), new String[]{document.select(".title").text()});
            favoriteButton.setText(R.string.ADD_TO_FAVORITES);
            showNotifyOption(false);
            cursor = database.query(getString(R.string.TABLE_LIBRARY), new String[]{getString(R.string.TITLE), getString(R.string.NOTIFY)}, getString(R.string.SELECTION_TITLE), new String[]{document.select(".title").text()}, null, null, null );
            Toast.makeText(getContext(), R.string.REMOVED_FROM_FAVORITES, Toast.LENGTH_LONG).show();
        }
        else {
            ContentValues favoriteValues = new ContentValues();
            favoriteValues.put(getString(R.string.TITLE), document.select(".title").text());
            favoriteValues.put(getString(R.string.URL), url);
            favoriteValues.put(getString(R.string.NOTIFY), NOTIFY_FALSE);
            database.insert(getString(R.string.TABLE_LIBRARY), null, favoriteValues);
            favoriteButton.setText(R.string.REMOVE_FROM_FAVORITES);
            cursor = database.query(getString(R.string.TABLE_LIBRARY), new String[]{getString(R.string.TITLE), getString(R.string.NOTIFY)}, getString(R.string.SELECTION_TITLE), new String[]{document.select(".title").text()}, null, null, null );
            showNotifyOption(true);
            Toast.makeText(getContext(), R.string.ADDED_TO_FAVORITES, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Visar checkbox för notifiering om boken är utlånad
     *
     * @param favored
     */

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
                checkBoxClicked();
            }
            notifyView.setVisibility(View.INVISIBLE);
            notifyCheckBox.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Om checkbox klickas. Om icke markerad startar en notifieringsservice om det är den första att ha den statusen.
     * Annars tar bort den som notify.
     */

    public void checkBoxClicked (){
        ContentValues favoriteValues = new ContentValues();
        favoriteValues.put(getString(R.string.TITLE), document.select(".title").text());
        favoriteValues.put(getString(R.string.URL), url);
        Cursor notifyCursor = database.query(getString(R.string.TABLE_LIBRARY), new String[]{getString(R.string.TITLE),getString(R.string.URL)}, getString(R.string.SELECTION_NOTIFY), new String[]{Integer.toString(1)}, null, null, null);
        if(notifyCheckBox.isChecked()){
            favoriteValues.put(getString(R.string.NOTIFY), NOTIFY_TRUE);
            database.update(getString(R.string.TABLE_LIBRARY), favoriteValues,getString(R.string.SELECTION_TITLE), new String[]{document.select(".title").text()} );
            notifyCursor.moveToFirst();
            if (notifyCursor.getCount() == 1){
                Intent intent = new Intent(getContext(), NotificationService.class);
                getActivity().startService(intent);
            }
        }
        else {
            favoriteValues.put(getString(R.string.NOTIFY), NOTIFY_FALSE);
            database.update(getString(R.string.TABLE_LIBRARY), favoriteValues,getString(R.string.SELECTION_TITLE), new String[]{document.select(".title").text()} );
        }
    }

    /**
     * Metod som stänger cursor och database.
     */

    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        database.close();
    }

    /**
     * Privat klass av {@link ParseProfile} som ärver {@link AsyncTask}
     * Klassen implementerar hantering av nedladning och manipulering från websidan.
     *
     */

    private class ParseProfile extends AsyncTask<String, Void, Void> {

        /**
         * Metod som innehåller kod måste utföras i bakgrunden.
         *
         * @param strings
         * @return null
         */

        @Override
        protected Void doInBackground(String... strings) {
            try{
                String url = strings[0];
                document = Jsoup.connect(url).get();
            }
            catch (Throwable t){
                t.printStackTrace();
            }
            return null;
        }

        /**
         * Metoden innehåller koden som körs innan bakgrundmetoden startar.
         */

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        /**
         * Metoden kallades efter doInBackground metoden kompletterar processen.
         * Resultat från doInBackground skickas till denna metod.
         *
         * @param result
         */

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

            cursor = database.query(getString(R.string.TABLE_LIBRARY), new String[]{getString(R.string.TITLE), getString(R.string.NOTIFY)}, getString(R.string.SELECTION_TITLE), new String[]{document.select(".title").text()}, null, null, null );
            if(cursor.moveToFirst()){
                favoriteButton.setText(R.string.REMOVE_FROM_FAVORITES);
                showNotifyOption(true);
            }
            else {
                favoriteButton.setText(R.string.ADD_TO_FAVORITES);
                showNotifyOption(false);
            }
        }
    }
}
