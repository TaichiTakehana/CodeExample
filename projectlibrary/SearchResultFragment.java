package se.hig.ndi12erd.projectlibrary;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klassen {@link SearchResultFragment}Fragment som startas och ärver {@link Fragment}
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public class SearchResultFragment extends Fragment {

    private FragmentListener fragmentListener;
    private TextView resultTextView, pageNbrView;
    private ListView searchResultListView;
    private Button prevPageButton, nextPageButton;
    private Document document;
    private List<String> searchResultList;
    private Map<Integer, String> valuesHashMap;
    private ArrayAdapter<String> listAdapter;
    private View view;
    private Bundle savedState;
    private Elements dataList;
    private Elements values;
    private String newUrl;
    private String pageURL;
    private String urlInput;
    private String inputText;
    private String profileUrl;
    private String resultText;
    private String resultNbrText;
    private int searchNbr = 0;
    private int resultNbr;
    private int totalPages;
    private int currentPageNbr = 1;
    private final int PAGE_RESULT_NUMBER = 20;

    /**
     * Metoden gör att ersätter sökURL och sökTexten till urlInput och inputText.
     *
     * @param searchUrl
     * @param searchText
     */

    public void changeProperties(String searchUrl, String searchText) {
        urlInput = searchUrl;
        inputText = searchText;
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

        view = inflater.inflate(R.layout.fragment_search_result, container, false);
        savedState = savedInstanceState;
        searchResultListView = view.findViewById(R.id.list_view);
        prevPageButton = view.findViewById(R.id.previous_page);
        nextPageButton = view.findViewById(R.id.next_page);
        resultTextView = view.findViewById(R.id.resultNumber_view);
        pageNbrView = view.findViewById(R.id.pageNumber_view);
        if(savedState == null){
            resultNbrText = "";
            resultText = "";
            newUrl = urlInput + inputText;
            pageURL = newUrl + "&offset=" + searchNbr + "&sort_by=pubdate_dsc";

        }
        else{
            resultNbrText = savedState.getString("resultNumber", resultNbrText);
            resultText = savedState.getString("resultText", resultText);
            newUrl = savedState.getString("newURL", newUrl);
            pageURL = savedState.getString("pageURL", pageURL);
        }

        (new ParseSearch()).execute(newUrl);
        searchResultList = new ArrayList<>();
        valuesHashMap = new HashMap<>();

        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                titleClick(position);
            }
        });

        prevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchNbr != 0){
                    pageButtonClicked(true);
                }
            }
        });

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchNbr + PAGE_RESULT_NUMBER < resultNbr) {
                    pageButtonClicked(false);
                }
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
        outState.putString("resultNumber", resultNbrText);
        outState.putString("resultText", resultText);
        outState.putString("newURL", newUrl);
        outState.putString("pageURL", pageURL);
    }

    /**
     * Metod som kallades när fragmentet är associerat med sin activity.
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

    public void onItemClicked(){
        if(fragmentListener != null){
            fragmentListener.onItemClick(profileUrl);
        }
    }

    /**
     * Metod som skapar profileUrl efter man klickar på titeln.
     *
     * @param position
     */

    public void titleClick(int position){
        profileUrl = "https://hig.bibkat.se/cgi-bin/koha/opac-detail.pl" + "?"
                + document.select(".select > input").attr("name") + "="
                + valuesHashMap.get(position)
                +"&query_desc=kw%2Cwrdl%3A " + inputText;
        onItemClicked();
    }

    /**
     * Anropas av både previous och next knapparna. Byter url för 20 resultat bakåt eller framåt, om det är möjligt.
     */

    private void pageButtonClicked(Boolean prevButtonClicked){
        prevPageButton.setEnabled(false);
        nextPageButton.setEnabled(false);
        if(prevButtonClicked){
            searchNbr = searchNbr - PAGE_RESULT_NUMBER;
            currentPageNbr--;
        }
        else{
            searchNbr = searchNbr + PAGE_RESULT_NUMBER;
            currentPageNbr++;
        }
        pageURL = newUrl + "&offset=" + searchNbr + "&sort_by=pubdate_dsc";
        searchResultList = new ArrayList<>();
        (new ParseSearch()).execute(pageURL);

    }

    /**
     * Privat klass av {@link ParseSearch}rch som ärver {@link AsyncTask}
     * Klassen implementerar hantering av nedladning och manipulering från websidan.
     *
     */

    private class ParseSearch extends AsyncTask<String, String, Void> {

        /**
         * Metod som innehåller kod måste utföras i bakgrunden.
         *
         * @param strings
         * @return null
         */

        @Override
        protected Void doInBackground(String... strings) {
            try {
                document = Jsoup.connect(strings[0]).get();
                dataList = document.select(".title");
                for(Element element: dataList){
                    String data = element.text();
                    searchResultList.add(data);
                }

                values = document.select(".select > input");
                for(int i = 0; i < values.size(); i++){
                    valuesHashMap.put(i, values.get(i).attr("value"));
                }
                listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, searchResultList);
                listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                resultText = document.select("#numresults > strong").text();
                resultNbrText = resultText.replaceAll("[\\D]", "");
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

            searchResultListView.setAdapter(listAdapter);
            prevPageButton.setVisibility(View.VISIBLE);
            nextPageButton.setVisibility(View.VISIBLE);
            pageNbrView.setVisibility(View.VISIBLE);
            resultTextView.setVisibility(View.VISIBLE);
            resultTextView.setText(resultText);

            if (resultNbrText != ""){
                resultNbr = Integer.parseInt(resultNbrText);
                totalPages = resultNbr/PAGE_RESULT_NUMBER;
                if (resultNbr % PAGE_RESULT_NUMBER != 0){
                    totalPages++;
                }
                pageNbrView.setText(String.format("%s/%s",Integer.toString(currentPageNbr), Integer.toString(totalPages)));
            }
            prevPageButton.setEnabled(true);
            nextPageButton.setEnabled(true);
        }
    }
}
