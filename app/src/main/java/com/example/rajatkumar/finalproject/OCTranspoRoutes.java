
package com.example.rajatkumar.finalproject;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ListView;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class OCTranspoRoutes extends Activity {

    protected static final String ACTIVITY_NAME = "OCTranspoRoutes";
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo_routes);

        Intent intent = getIntent();
        String stop = intent.getExtras().getString("key");
      tv = (TextView)findViewById(R.id.STOP);

        Log.i(ACTIVITY_NAME, "stop number =" + stop);

        // seting visibilty of progress bar
        OCTranspoRoute fQuery = new OCTranspoRoute();  // creating object for inner class

        String s ="https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="+stop;

        fQuery.execute(s);
    }


    public class OCTranspoRoute extends AsyncTask<String, Integer, String> {
        static final String KEY_ITEM = "Route";
        String text ;
        String KEY_NAME = "RouteHeading";
        String current_temp;


            // return

        @Override
        protected String doInBackground(String... string) {
            Log.i(ACTIVITY_NAME, "doInBackground is started");
            XmlPullParser parser = Xml.newPullParser(); // creating parser object to parse xml
            XmlPullParserFactory factory = null;

            try {
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                URL url = new URL(string[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // establishing connection
                InputStream stream = conn.getInputStream();
                //XmlPullParser parser = Xml.newPullParser(); // creating parser object to parse xml
                parser.setInput(stream, null);
                ArrayList<HashMap<String, String>> articlesList = new ArrayList<HashMap<String, String>>();
                int eventType = parser.getEventType();

                while (parser.next() != XmlPullParser.END_DOCUMENT) {

                    String tagname = parser.getName();
                    if (parser.getEventType() != XmlPullParser.START_TAG || parser.getEventType() != XmlPullParser.TEXT) {
                        continue;
                    }
                    if (parser.getEventType() != XmlPullParser.END_TAG) {

                        if (tagname.equalsIgnoreCase("RouteNo")) {
                            String val = null;

                            val = text; // get the text
                            Log.i("", " text is " + val);
                        }

                    }


                    }
                eventType = parser.next();


            } catch(Exception e) {
                e.printStackTrace();
            }
            return "value";
        }
        public void onProgressUpdate(Integer... data) {



        }

        public void onPostExecute(String result) {

            tv.setText(text); // passing values to textview

        }


    }
}
