package se.hig.ndi12erd.projectlibrary;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;


/**
 * Klassen {@link MainFragment}ment som startas och ärver {@link Fragment}
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public class MainFragment extends Fragment {

    private View view;
    private TextView infoTextView;
    private ImageView schoolImageView;
    private Document document;
    private String url;
    private StringBuilder stringBuilder;
    private Bitmap bitmap;

    /**
     * En tom konstruktor.
     */

    public MainFragment() {}


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
        view = inflater.inflate(R.layout.fragment_main, container, false);
        infoTextView = view.findViewById(R.id.information);
        schoolImageView = view.findViewById(R.id.schoolImage);
        stringBuilder = new StringBuilder();
        url = "https://hig.bibkat.se";

        (new ParseMain()).execute(url);
        return view;
    }


    /**
     * Privat klass av ParseMain som ärver {@link AsyncTask}
     * Klassen implementerar hantering av nedladning och manipulering från websidan.
     *
     */

    private class ParseMain extends AsyncTask<String, Void, Void>{

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
                Elements infoElements = document.select("#opacnav");
                for(Element infoElement: infoElements){
                    stringBuilder.append(infoElement.text());
                }

                String imageSource = "https://www.hig.se/images/18.321e5b271641cca8464af69/1530266015455/(4)%20biblioteksvy.jpg%22/";
                InputStream inputStream = new URL(imageSource).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
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
            String infoURL = document.select("a").get(11).attr("href");
            String info = stringBuilder.substring(0, 12) + "\n"
                    + stringBuilder.substring(13, 45) + "\n"
                    + stringBuilder.substring(46, 69) + "\n"
                    + infoURL;
            infoTextView.setText(info);
            schoolImageView.setImageBitmap(bitmap);
            Toast.makeText(getContext(), R.string.title_university, Toast.LENGTH_SHORT).show();
        }
    }
}
