
package com.example.rajatkumar.finalproject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class GetRouteSummaryForStop extends Activity {

    protected static final String ACTIVITY_NAME = "GetRouteSummaryForStop";

    ListView view;
    ArrayList<String> Routes = new ArrayList();
    ArrayList<String> Distinct_Routes = new ArrayList();
    String stop;
    Intent intent;
    TextView stop_n;
    ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo_routes);
        view = (ListView) findViewById(R.id.list11);
        pg = (ProgressBar) findViewById(R.id.progress_bar);

        stop_n = (TextView) findViewById(R.id.stop_name);
      //  no_icon = (ImageView) findViewById(R.id.no_bus_icon);
        intent = getIntent();
        stop = intent.getExtras().getString("RouteNo");
        Log.i(ACTIVITY_NAME, "stop number =" + stop);
        OCTranspoRoute fQuery = new OCTranspoRoute();  // creating object for inner class
        String url_xml = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=" + stop;
        fQuery.execute(url_xml);
        CoordinatorLayout c = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        stop_n.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Snackbar.make(c, " Route Number "+stop, Snackbar.LENGTH_LONG).show();

            }
        });
    }



    public class OCTranspoRoute extends AsyncTask<String, Integer, String> {
        String text;
        String Route_from_Xml;
        String stop_name_text;

        ArrayAdapter<String> arrayAdapter;
        String route;

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
                parser.setInput(stream, null);
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname1 = parser.getName();
                    String tagname2 = parser.getName();

                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;
                        case XmlPullParser.END_TAG:
                            if (tagname1.matches("RouteNo")) {
                                publishProgress(33);
                                Route_from_Xml = text; // get the text
                                Log.i(ACTIVITY_NAME, " ROUTE NO is " + Route_from_Xml);
                            }

                            if (tagname1.matches("StopDescription")) {
                                stop_name_text = text;
                                publishProgress(67);

                            }
                            break;
                        default:
                            break;
                    }
                    eventType = parser.next();
                    if (Route_from_Xml != null)
                        Routes.add(Route_from_Xml);
                    for (String item : Routes) {
                        // If Route is not in Distinct_Routes, add it to  the Distinct_Routes
                        if (!Distinct_Routes.contains(item)) {
                            Distinct_Routes.add(item);
                            Log.i(ACTIVITY_NAME, "ARRAY IS" + Distinct_Routes);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(100);

            return "value";
        }


        public void onProgressUpdate(Integer... data) {
            pg.setVisibility(View.VISIBLE);        // seting visibilty of progress bar

            pg.setProgress(data[0]);  // progress for progressbar

        }

        public void onPostExecute(String result) {

            if(stop_name_text== null){
                stop_n.setText("Invalid Route");

            }
           else
            stop_n.setText(stop_name_text);

            pg.setVisibility(View.INVISIBLE);        // seting visibilty of progress bar



           arrayAdapter = new ArrayAdapter<String>(GetRouteSummaryForStop.this, R.layout.activity_octranspo_routeinfo, Distinct_Routes);
           Log.i(ACTIVITY_NAME, " text is " + Distinct_Routes);
view.setAdapter(arrayAdapter);

           view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
               public void onItemClick(AdapterView<?> adapterView, View view1, int position, long l) {
                route = (String) view.getAdapter().getItem(position);
                    Log.i(ACTIVITY_NAME, "ROUTE Number clicked =" + route);
                   Intent intent = new Intent(GetRouteSummaryForStop.this, GetNextTripsForStop.class);
                  intent.putExtra("RouteNO", route);
                  intent.putExtra("StopNO", stop);
                  startActivity(intent);
              }
           });

        }

    }


}


