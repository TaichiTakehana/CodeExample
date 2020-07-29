package se.hig.ndi12erd.projectlibrary;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SearchResultActivity extends AppCompatActivity {

    private TextView resultTextView, pageNbrView;
    private ListView searchResultListView;
    private Button prevPageButton, nextPageButton;
    private Document document;
    private List<String> searchResultList;
    private HashMap<Integer, String> valuesHashMap;
    private String newUrl;
    private String urlInput;
    private String inputText;
    private String profileUrl;
    private int searchNbr = 0;
    private int resultNbr;
    private int totalPages;
    private int currentPageNbr = 1;
    private final int PAGE_RESULT_NUMBER = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        searchResultListView = findViewById(R.id.list_view);
        prevPageButton = findViewById(R.id.previous_page);
        nextPageButton = findViewById(R.id.next_page);
        resultTextView = findViewById(R.id.resultNumber_view);
        pageNbrView = findViewById(R.id.pageNumber_view);


        Intent intent = getIntent();
        urlInput = intent.getStringExtra("url");
        inputText = intent.getStringExtra("searchText");
        newUrl = urlInput + inputText;
        (new ParseSearch()).execute(newUrl);
        searchResultList = new ArrayList<>();
        valuesHashMap = new HashMap<>();

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                titleClick(position);
            }
        };

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

        searchResultListView.setOnItemClickListener(itemClickListener);
    }

    public void titleClick(int position){
        profileUrl = "https://hig.bibkat.se/cgi-bin/koha/opac-detail.pl" + "?"
                + document.select(".select > input").attr("name") + "="
                + valuesHashMap.get(position)
                +"&query_desc=kw%2Cwrdl%3A " + inputText;
        Toast.makeText(this, profileUrl, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("url", profileUrl);
        intent.putExtra("position", position);
        startActivity(intent);
    }

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
        newUrl = urlInput + inputText + "&offset=" + searchNbr + "&sort_by=pubdate_dsc";
        searchResultList = new ArrayList<>();
        (new ParseSearch()).execute(newUrl);

    }

    private class ParseSearch extends AsyncTask<String, String, List<String>>{

        @Override
        protected List<String> doInBackground(String... strings) {
            try {
                document = Jsoup.connect(strings[0]).get();
                Elements dataList = document.select(".title");
                for(Element element: dataList){
                    String data = element.text();
                    searchResultList.add(data);
                }

                Elements values = document.select(".select > input");
                for(int i = 0; i < values.size(); i++){
                    valuesHashMap.put(i, values.get(i).attr("value"));
                }
            }
            catch (Throwable t){
                t.printStackTrace();
            }
            return searchResultList;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<String> elementList){
            ArrayAdapter<String> listAdapter = new ArrayAdapter<>(SearchResultActivity.this, android.R.layout.simple_spinner_item, elementList);
            listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            searchResultListView.setAdapter(listAdapter);
            prevPageButton.setVisibility(View.VISIBLE);
            nextPageButton.setVisibility(View.VISIBLE);
            pageNbrView.setVisibility(View.VISIBLE);
            resultTextView.setVisibility(View.VISIBLE);
            String resultText = document.select("#numresults > strong").text();
            resultTextView.setText(resultText);
            String resultNbrText = resultText.replaceAll("[\\D]", "");
            if (resultNbrText != ""){
                resultNbr = Integer.parseInt(resultNbrText);
                totalPages = resultNbr/PAGE_RESULT_NUMBER;
                if (resultNbr % PAGE_RESULT_NUMBER != 0){
                    totalPages++;
                }
                pageNbrView.setText(Integer.toString(currentPageNbr) + "/" + Integer.toString(totalPages));
            }
            prevPageButton.setEnabled(true);
            nextPageButton.setEnabled(true);
        }
    }
}
