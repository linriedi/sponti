package coeco.spontiandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button recordButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void addListeners() {
        recordButton = (Button) findViewById(R.id.recordButton);

        recordButton.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    TextView textView = (TextView) findViewById(R.id.text);
                    textView.setText(R.string.speak_string);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    TextView textView = (TextView) findViewById(R.id.text);
                    textView.setText(R.string.pushAndSpeak_string);
                }
                return true;
            }
        });
    }
}
