package coeco.spontiandroid;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeProvider {
    public String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());

            return currentDateTime;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
