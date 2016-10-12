package miorganizer.wegmeth.com.mioraganizer.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import miorganizer.wegmeth.com.mioraganizer.activities.MainActivity;

public abstract class Downloader extends AsyncTask {

    private Context context;

    public Downloader(Context context) {
        this.context = context;
    }

    abstract protected void onPostExecute(Object o);

    protected Object doInBackground(Object[] params) {

        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;

        try {

            URL url = new URL(MainActivity.XML_URL);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                int fileLength = connection.getContentLength();
                input = connection.getInputStream();
                output = context.openFileOutput("mi.xml", Context.MODE_PRIVATE);

                byte data[] = new byte[4096];
                long total = 0;
                int count;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }

            }

        } catch (Exception e) {
            Log.e("Download", "", e);
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (Exception ignored) {
            }
            connection.disconnect();

            return null;
        }
    }

}
