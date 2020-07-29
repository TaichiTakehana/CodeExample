package se.hig.ndi12erd.projectlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klassen {@link SearchFragment}Fragment som startas och ärver {@link Fragment}
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public class SearchFragment extends Fragment {

    private FragmentListener fragmentListener;
    private ImageView imageView;
    private Spinner spinnerList;
    private TextView searchView;
    private EditText searchEdit;
    private Button btnGo;
    private Document document;
    private List<String> elementList;
    private Map<Integer, String> elementHashMap;
    private ArrayAdapter<String> listAdapter;
    private String url;
    private String newUrl;
    private Bitmap bitmap;
    private View view;

    /**
     * En tom konstruktor.
     */

    public SearchFragment() {}

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
        view = inflater.inflate(R.layout.fragment_search, container, false);
        imageView = view.findViewById(R.id.imageView);
        spinnerList = view.findViewById(R.id.spinner);
        searchView = view.findViewById(R.id.searchView);
        searchEdit = view.findViewById(R.id.searchEdit);
        btnGo = view.findViewById(R.id.btnGo);
        if(savedInstanceState != null){
            searchEdit.setText(savedInstanceState.getString("editText"));
        }
        url = "https://hig.bibkat.se";
        (new ParseURL()).execute(url);
        elementList = new ArrayList<>();
        elementHashMap = new HashMap<>();

        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(position > 0){
                    newUrl = url + document.select("form").attr("action")
                            + "?" + document.select("select").attr("name") + "="
                            + elementHashMap.get(position) + "&q=";
                }
                else{
                    newUrl = url + document.select("form").attr("action")
                            + elementHashMap.get(position) + "?q=";
                }



                btnGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onButtonPressed(newUrl, searchEdit.getText().toString());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    /**
     * Metoden som sparar undan resurs och data.
     *
     * @param outState
     */

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("editText", searchEdit.getText().toString());
    }

    /**
     * Metod som skapar en menu tillval.
     *
     * @param menu
     * @param inflater
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Metod som returnerar vald artikel från menun.
     *
     * @param item
     * @return super.onOptionsItemSelected(item)
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metod som använder metod från Interface fragmentListener.
     *
     * @param searchURL
     * @param searchText
     */

    public void onButtonPressed(String searchURL, String searchText) {
        if (fragmentListener != null) {
            fragmentListener.onButtonClick(searchURL, searchText);
        }
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
     * Privat klass av {@link ParseURL} som ärver {@link AsyncTask}
     * Klassen implementerar hantering av nedladning och manipulering från websidan.
     *
     */


    private class ParseURL extends AsyncTask<String, String, Void> {

        /**
         * Metod som innehåller kod måste utföras i bakgrunden.
         *
         * @param strings
         * @return null
         */

        @Override
        protected Void doInBackground(String... strings){
            try {
                document = Jsoup.connect(strings[0]).get();
                Element img = document.select("img").first();
                String imgSrc = img.absUrl("src");
                InputStream inputStream = new URL(imgSrc).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                Elements dataList = document.select("option");

                for(Element element: dataList ) {
                    elementList.add(element.text());
                }
                for(int i = 0; i < dataList.size(); i++){
                    elementHashMap.put(i, dataList.get(i).attr("value"));
                }
                listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, elementList);
                listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
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

        @Override
        protected void onPostExecute(Void result){
            spinnerList.setAdapter(listAdapter);
            searchView.setText(document.select("label[for=masthead_search]").text());
            searchEdit.setHint(document.select("input#translControl1").attr("title"));
            btnGo.setText(document.select("button#searchsubmit").text());
            imageView.setImageBitmap(bitmap);
        }
    }
}
