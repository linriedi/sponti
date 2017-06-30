package coeco.spontiandroid;

import android.media.MediaRecorder;
import android.util.Log;

public class RecorderAdapter {

    private MediaRecorder mRecorder = null;
    private String audioFile;

    public RecorderAdapter(String audioFile){
        this.audioFile = audioFile;
    }

    public void startRecord() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(audioFile);
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
