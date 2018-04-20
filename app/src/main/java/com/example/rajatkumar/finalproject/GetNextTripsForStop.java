package com.example.rajatkumar.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GetNextTripsForStop extends Activity {

    HashMap<String, ArrayList<String>> mData;
    protected static final String ACTIVITY_NAME = "GetNextTripsForStop";
    ListView view1;
    ArrayList<String> routeDestinations = new ArrayList();
    ArrayList<String> routeLatitude = new ArrayList();
    ArrayList<String> routeLongitude = new ArrayList();
    ArrayList<String> gpsSpeed = new ArrayList();
    ArrayList<String> StartTime = new ArrayList();
    ArrayList<String> AdjustedScheduleTime = new ArrayList();
    TextView no_bus;

    ProgressBar pb_Route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_next_trips_for_stop);
        view1 = (ListView) findViewById(R.id.list1);
        no_bus = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        String Stop_NO = intent.getExtras().getString("StopNO");
        String Route_No = intent.getExtras().getString("RouteNO");
        pb_Route = (ProgressBar) findViewById(R.id.progressBar_for_route);
        Log.i(ACTIVITY_NAME, "stop number =" + Stop_NO);
        Log.i(ACTIVITY_NAME, "Route number =" + Route_No);
        OCTranspoDestination fQuery = new OCTranspoDestination();  // creating object for inner class
        String url_xml = "https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=" + Stop_NO + "&routeNo=" + Route_No;
        fQuery.execute(url_xml);

    }


    public class OCTranspoDestination extends AsyncTask<String, Integer, String> {
        String text;
        String Route_from_Xml = null;
        ArrayAdapter<String> arrayAdapter;
        // ArrayList<Trip> tripArray = new ArrayList<>();
        String lat, lon, sp, tm;

        //        lat = lon = sp = tm = "";
        @Override
        protected String doInBackground(String... string) {
            // mData = new HashMap<String, ArrayList<String>>();
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
                    String s1 = null;
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;
                        case XmlPullParser.END_TAG:
                            if (tagname2.matches("TripDestination")) {

                                Route_from_Xml = text; // get the text
                                if ((Route_from_Xml == null) && routeDestinations.contains(Route_from_Xml) == false)
                                    Route_from_Xml = "N/A";
                                routeDestinations.add(Route_from_Xml);

                              publishProgress(25);
                                Log.i(ACTIVITY_NAME, " Destination label is " + Route_from_Xml);
                            } else if (tagname2.equals("Latitude")) {
                                lat = text;
                                if ((lat == null) && routeLatitude.contains(lat) == false)
                                    lat = "N/A";
                                routeLatitude.add(lat);


                            } else if (tagname2.equals("Longitude")) {
                                lon = text;
                                if ((lon == null) && routeLongitude.contains(lon) == false)
                                    lon = "N/A";
                                routeLongitude.add(lon);

                            } else if (tagname2.equals("GPSSpeed")) {


                                sp = text;
                                if ((sp == null) && gpsSpeed.contains(sp) == false)
                                    sp = "N/A";
                                gpsSpeed.add(sp);

                                publishProgress(50);

                            } else if (tagname1.equals("TripStartTime")) {
                                tm = text;

                                if ((tm == null) && StartTime.contains(tm) == false)
                                    tm = "N/A";
                                StartTime.add(tm);


                            } else if (tagname1.equals("AdjustedScheduleTime")) {
                                tm = text;

                                if ((tm == null) && AdjustedScheduleTime.contains(tm) == false)
                                    tm = "N/A";
                                AdjustedScheduleTime.add(tm);
                                publishProgress(75);

                            }

                            break;


                        default:
                            break;
                    }
                    eventType = parser.next();
                }
                Log.i(ACTIVITY_NAME, "dest array is" + routeDestinations);
                Log.i(ACTIVITY_NAME, "longit array is" + routeLongitude);
                Log.i(ACTIVITY_NAME, "lati array is" + routeLatitude);
                Log.i(ACTIVITY_NAME, "speed array is" + gpsSpeed);
                Log.i(ACTIVITY_NAME, "time array is" + StartTime);
                Log.i(ACTIVITY_NAME, "time array is" + AdjustedScheduleTime);


            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(100);

            return "value";
        }


                public void onProgressUpdate(Integer... data) {


                    pb_Route.setVisibility(View.VISIBLE);          // seting visibilty of progress bar

                    pb_Route.setProgress(data[0]);                // progress for progressbar


                }

        public void onPostExecute(String result) {

            if (Route_from_Xml == null) {

                no_bus.setText("No Buses Secheduled");
                Log.i(ACTIVITY_NAME,"NO BUS"+ no_bus);

            } else {
                BusAdapter bAdapter = new BusAdapter(GetNextTripsForStop.this);
                view1.setAdapter(bAdapter);
                bAdapter.notifyDataSetChanged();
            }
            pb_Route.setVisibility(View.INVISIBLE);        // seting visibilty of progress bar

        }

        private class BusAdapter extends ArrayAdapter<String> {

            public BusAdapter(Context ctx) {
                super(ctx, 0);
            }

            public int getCount() {
                return routeDestinations.size();
            }

            public long getItemId(int i) {
                return i;
            }

            public View getView(int i, View view, ViewGroup viewGroup) {

                LayoutInflater inflater = GetNextTripsForStop.this.getLayoutInflater();
                View show_view = view;
                if (view == null)
                    show_view = inflater.inflate(R.layout.bus_summary, viewGroup, false);

                routeDestinations.size();

                TextView tv = (TextView) show_view.findViewById(R.id.bus_destination);  //look at setContentView in onCreate
                tv.setText("Destination:" + routeDestinations.get(i));


                routeLatitude.size();
                TextView tv1 = (TextView) show_view.findViewById(R.id.bus_destination2);
                tv1.setText("Latitude:" + routeLatitude.get(i));

                routeLongitude.size();
                TextView tv2 = (TextView) show_view.findViewById(R.id.bus_destination3);
                tv2.setText("Longitude:" + routeLongitude.get(i));


                gpsSpeed.size();
                TextView tv3 = (TextView) show_view.findViewById(R.id.bus_destination4);
                tv3.setText("Gps Speed:" + gpsSpeed.get(i));

                StartTime.size();
                TextView tv4 = (TextView) show_view.findViewById(R.id.bus_destination5);
                tv4.setText("Start Time :" + StartTime.get(i));

                AdjustedScheduleTime.size();
                TextView tv5 = (TextView) show_view.findViewById(R.id.bus_destination6);
                tv5.setText("Adjusted Schedule Time :" + AdjustedScheduleTime.get(i));

                return show_view;

            }
        }
    }
}