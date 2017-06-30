package coeco.spontiandroid;

import android.media.MediaRecorder;
import android.util.Log;

public class RecorderAdapter {

    private MediaRecorder mRecorder = null;
    private String mFileName;

    public RecorderAdapter(String externalCacheDir){
        mFileName = externalCacheDir + "/" + R.string.setting_audioRecordFileName;
    }

    public void startRecord() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (Exception e) {
            Log.e("RecorderAdapter", "prepare() failed", e);
        }

        mRecorder.start();
    }

    public void stopRecord() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}
