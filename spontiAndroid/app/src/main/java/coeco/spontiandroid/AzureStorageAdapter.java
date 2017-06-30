package coeco.spontiandroid;

import android.os.AsyncTask;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AzureStorageAdapter {

    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;" +
            "AccountName=spontistorage;" +
            "AccountKey=" + Keys.AccountKey +
            "EndpointSuffix=core.windows.net";

    private final String audioFile;

    public AzureStorageAdapter(String audioFile) {
        this.audioFile = audioFile;
    }

    public void upLoad() {
        new UploadTask()
                .execute("", "", "");
    }

    private static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private class UploadTask extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... urls) {
            try
            {
                // Retrieve storage account from connection-string.
                CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

                // Create the blob client.
                CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                // Retrieve reference to a previously created container.
                CloudBlobContainer container = blobClient.getContainerReference("voice");

                // Define the path to a local file.
                final String filePath = audioFile;

                // Create or overwrite the "myimage.jpg" blob with contents from a local file.
                CloudBlockBlob blob = container.getBlockBlobReference("voice" + getCurrentTimeStamp() +".3gp");
                File source = new File(filePath);
                blob.upload(new FileInputStream(source), source.length());
            }
            catch (Exception e)
            {
                // Output the stack trace.
                e.printStackTrace();
            }

            return Long.valueOf(0);
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }
}
