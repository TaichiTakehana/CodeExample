package se.hig.ndi12erd.projectlibrary;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Klassen {@link FavoritesFragment}Fragment som startas och ärver {@link Fragment}
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public class FavoritesFragment extends ListFragment {

    private FragmentListener fragmentListener;
    private String url;
    private SQLiteDatabase database;
    private Cursor cursor;
    private CursorAdapter listAdapter;

    /**
     * En tom konstruktor.
     */

    public FavoritesFragment() {}

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

        try {
            SQLiteOpenHelper libraryDatabaseHelper = new LibraryDatabaseHelper(getContext());
            database = libraryDatabaseHelper.getReadableDatabase();
            cursor = database.query(getString(R.string.TABLE_LIBRARY), new String[]{getString(R.string.ID),getString(R.string.TITLE)}, null, null, null, null, null);
            listAdapter = new SimpleCursorAdapter(inflater.getContext(), android.R.layout.simple_list_item_1, cursor, new String[]{getString(R.string.TITLE)}, new int[]{android.R.id.text1}, 0);
            setListAdapter(listAdapter);
        } catch (SQLiteException e){

        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Metod som kallades när fragmentet är associerat med sin aktivity.
     *
     * @param context
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.fragmentListener = (FragmentListener) context;
    }

    /**
     *  Metod som kallades före fragmentet är inte associreat med sin activity.
     */

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    /**
     * Metod som använder metod från Interface fragmentListener.
     */

    public void onFavoriteItemClicked(){
        if(fragmentListener != null){
            fragmentListener.onItemClick(url);
        }
    }

    /**
     * Sätter in databasvärden på listan.
     *
     * @param listView
     * @param itemView
     * @param position
     * @param id
     */

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id){
        try {
            Cursor titleCursor = (Cursor)listView.getItemAtPosition(position);
            cursor = database.query(getString(R.string.TABLE_LIBRARY), new String[]{getString(R.string.URL)}, getString(R.string.SELECTION_TITLE), new String[]{titleCursor.getString(1)}, null, null, null );
            cursor.moveToFirst();
            url = cursor.getString(0);
            if (url != null){
                onFavoriteItemClicked();
            }
        } catch (SQLiteException e) {

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

}
