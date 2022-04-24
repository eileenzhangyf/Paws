package edu.neu.madcourse.paws;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class DownloadUrl {
    public String retirveveUrl(String url) throws IOException {
        String urlData = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            URL getUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) getUrl.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            urlData = sb.toString();
            Log.d("downloadUrl",urlData.toString());
            bufferedReader.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        return urlData;
    }
}
