package coeco.spontiandroid;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static boolean PERMISION_TO_RECORD_ACCEPTED = false;

    private Button recordButton = null;
    private RecorderAdapter recroderAdapter = null;
    private AzureStorageAdapter azureStorageAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String mFileName = getExternalCacheDir().getAbsolutePath() + "/" + R.string.setting_audioRecordFileName;

        recroderAdapter = new RecorderAdapter(mFileName);
        azureStorageAdapter = new AzureStorageAdapter(mFileName);

        addListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                PERMISION_TO_RECORD_ACCEPTED  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!PERMISION_TO_RECORD_ACCEPTED ) finish();

    }

    private void addListeners() {
        recordButton = (Button) findViewById(R.id.recordButton);

        recordButton.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    TextView textView = (TextView) findViewById(R.id.text);
                    textView.setText(R.string.speak_string);
                    recroderAdapter.startRecord();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    recroderAdapter.stopRecord();
                    azureStorageAdapter.upLoad();
                    TextView textView = (TextView) findViewById(R.id.text);
                    textView.setText(R.string.pushAndSpeak_string);
                }
                return true;
            }
        });
    }
}
