using Microsoft.WindowsAzure.Storage;
using Microsoft.WindowsAzure.Storage.Blob;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SpontiBL
{
    public class ItemsService
    {
        public async Task<IEnumerable<string>> GetItemsAsync()
        {
            // Retrieve storage account from connection string.
            CloudStorageAccount storageAccount = CloudStorageAccount.Parse(Settings.ConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.CreateCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.GetContainerReference("voice");

            // Loop over items within the container and output the length and URI.
            BlobContinuationToken continuationToken = null;
            var list = await container.ListBlobsSegmentedAsync(continuationToken);

            return list
                .Results
                .OfType<CloudBlockBlob>()
                .Select(b => b.Name);
        }
    }
}
