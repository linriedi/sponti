package coeco.spontiandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TodoActivity extends AppCompatActivity {

    private Button generalButton = null;
    private Button sendButton = null;
    private EditText editText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        generalButton = (Button) findViewById(R.id.generalButton);
        generalButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                onTodoButtonClick();
            }
        });

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                onSendButton();
            }
        });
    }

    private void onSendButton() {
        editText = (EditText) findViewById(R.id.editText);
        editText.setText("Hi stupid");

        new GetTask()
                .execute("", "", "");
    }

    private void onTodoButtonClick() {
        Intent k = new Intent(this, MainActivity.class);
        startActivity(k);
    }

    private class GetTask extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... urls) {
            try {
                URL url =  new URL("https://spontibackendwebapp.azurewebsites.net/api/values");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "text/json");
                urlConnection.setRequestProperty("Accept", "text/json");
                urlConnection.setRequestMethod("POST");

                JSONObject parent = new JSONObject();
                parent.put("id", "firstNewThree");
                parent.put("content", "some content from mobile app");

                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write( parent.toString() );
                wr.flush();
                wr.close();

                StringBuilder sb = new StringBuilder();
                int HttpResult = urlConnection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println("" + sb.toString());
                } else {
                    System.out.println(urlConnection.getResponseMessage());
                }
            }
            catch(Exception e){
                Exception test = e;
            };

            return Long.valueOf(0);
        }

        private String convertStreamToString(InputStream is) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }
}
