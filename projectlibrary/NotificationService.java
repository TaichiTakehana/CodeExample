package se.hig.ndi12erd.projectlibrary;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Klassen {@link NotificationService} som startas och ärver {@link IntentService}
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public class NotificationService extends IntentService {

    SQLiteOpenHelper libraryDatabaseHelper;
    private SQLiteDatabase database;
    private Cursor cursor;
    private Document document;
    private final int NOTIFY_FALSE = 0;
    private final int NOTIFY_TRUE = 1;
    final Handler handler = new Handler();
    private static final int NOTIFICATION_ID = 1234;
    private final String AVAILABLE_TEXT = "Tillgängliga";
    private final String NOTIFICATION_TITLE_TEXT = "Library";
    private final String NOTIFICATION_CONTENT_TEXT = "New book available in the library: ";
    private final String SPAN_ITEM_STATUS = "span.item-status";


    /**
     * Tråd som loopar 10 sekunder om databas har tillvar för notify = 1 returnerar resultat.
     */

    Runnable runnable = new Runnable() {

        @Override
        public void run() {

            try{
                database = libraryDatabaseHelper.getReadableDatabase();
                cursor = database.query(getString(R.string.TABLE_LIBRARY), new String[]{getString(R.string.TITLE),getString(R.string.URL)}, getString(R.string.SELECTION_NOTIFY), new String[]{Integer.toString(NOTIFY_TRUE)}, null, null, null);
                if (cursor.moveToFirst()){
                    (new NotificationAvailability()).execute();
                    handler.postDelayed(this, 10000);
                }
                else {
                    handler.removeCallbacks(runnable);
                }

            }
            catch (Exception e) {

            }
            finally{

            }
        }
    };

    /**
     * Skapar titel för NotificationService
     */

    public NotificationService(){
        super("NotificationService");
    }

    /**
     * Metod som utför en intent.
     *
     * @param intent
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        libraryDatabaseHelper = new LibraryDatabaseHelper(this);
        handler.post(runnable);
    }

    /**
     * Metod som stänger cursor och database.
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }

    /**
     * Privat klass av {@link NotificationAvailability} som ärver {@link AsyncTask}
     * Klassen implementerar hantering av nedladning och manipulering från notification.
     *
     */

    private class NotificationAvailability extends AsyncTask<Void, Void, Void> {

        /**
         * Metod som innehåller kod måste utföras i bakgrunden.
         *
         * @param strings
         * @return null
         */

        @Override
        protected Void doInBackground(Void... avoid) {
            try{
                checkAvailability();
            }
            catch (Exception e) {

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

        }


        /**
         * Connects to a url for a book profile page on BIBKAT and checks if it is available
         */

        private void checkAvailability (){
            do {
                try {
                    document = Jsoup.connect(cursor.getString(1)).get();
                } catch (Throwable t){
                    t.printStackTrace();
                }
                if(document.select(SPAN_ITEM_STATUS).text().equals(AVAILABLE_TEXT)){
                    ContentValues favoriteValues = new ContentValues();
                    favoriteValues.put(getString(R.string.TITLE), cursor.getString(0));
                    favoriteValues.put(getString(R.string.URL), cursor.getString(1));
                    favoriteValues.put(getString(R.string.NOTIFY), NOTIFY_FALSE);
                    database.update(getString(R.string.TABLE_LIBRARY), favoriteValues,getString(R.string.SELECTION_TITLE), new String[]{cursor.getString(0)} );
                    buildSendNotification();
                }
            } while (cursor.moveToNext());
        }

        /**
         * Skapar och skickar en notification.
         */
        private void buildSendNotification(){

            Intent intent = new Intent(NotificationService.this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification availableNotification = new Notification.Builder(NotificationService.this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(NOTIFICATION_TITLE_TEXT)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_LOW)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    .setContentText(NOTIFICATION_CONTENT_TEXT + cursor.getString(0))
                    .build();

            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, availableNotification);
        }
    }
}
