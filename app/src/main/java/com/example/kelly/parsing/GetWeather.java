package com.example.kelly.parsing;

import android.os.AsyncTask;
import android.util.Xml;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kelly on 13/02/2018.
 */

public class GetWeather extends AsyncTask<String,Void,String> {

    private TextView textView;
    String script;
    String q;
    private InputStream stream;

    public GetWeather(TextView textView,String q){
        this.textView = textView;
        this.q = q;
    }

    @Override
    protected String doInBackground(String... strings) {
        String s="";
        try {

            //getHTML(s);

            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + this.q + "&mode=xml&appid=855114b6016a9acccdb5871893a7971f");

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(getInputStream(url),"UTF_8");

            int eventType = parser.getEventType();
            String x = "";

            while(eventType != XmlPullParser.END_DOCUMENT){
               if(eventType == XmlPullParser.START_TAG){
                   if (parser.getName().equals("temperature")){
                       s ="\nValue :"+ parser.getAttributeValue(null,"value") +"\nMin :";
                       s += parser.getAttributeValue(null,"min") +"\nMax:";
                       s+= parser.getAttributeValue(null,"max") +"\nUnit:";
                       s +=parser.getAttributeValue(null,"unit");
                   }
               }
               eventType = parser.next();
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText(s);
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }


    private void getHTML(String s) throws IOException {

        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+this.q+"&mode=xml&appid=855114b6016a9acccdb5871893a7971f");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();

        stream = new BufferedInputStream(httpURLConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String inputString;

        while((inputString = bufferedReader.readLine()) != null) {
            s += inputString;
        }

        httpURLConnection.disconnect();
    }

}
