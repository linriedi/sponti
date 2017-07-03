using Microsoft.WindowsAzure.Storage;
using Microsoft.WindowsAzure.Storage.Blob;
using Microsoft.WindowsAzure.Storage.Table;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SpontiBL
{
    public class ItemsService
    {
        private string installedLocation;

        public ItemsService(string installedLocation)
        {
            this.installedLocation = installedLocation;
        }

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

        public async Task<IEnumerable<string>> GetTodoItemsAsync()
        {
            var storageAccount = CloudStorageAccount.Parse(Settings.ConnectionString);
            var tableClient = storageAccount.CreateCloudTableClient();
            var table = tableClient.GetTableReference("todoitems");

            var query = new TableQuery<TodoItem>();
            TableContinuationToken token = null;
            var list = new List<string>();
            do
            {
                var resultSegment = await table.ExecuteQuerySegmentedAsync(query, token);
                token = resultSegment.ContinuationToken;

                foreach (var entity in resultSegment.Results)
                {
                    list.Add(entity.Id);
                }
            } while (token != null);

            return list;
        }

        public async Task PlayAsync(string selectedItem)
        {
            // Retrieve storage account from connection string.
            CloudStorageAccount storageAccount = CloudStorageAccount.Parse(Settings.ConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.CreateCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.GetContainerReference("voice");

            // Retrieve reference to a blob named "photo1.jpg".
            CloudBlockBlob blockBlob = container.GetBlockBlobReference(selectedItem);

            var filePath = installedLocation + @"\test.3gp";
            System.IO.File.Delete(filePath);
            using (var fileStream = System.IO.File.OpenWrite(filePath))
            {
                var test1 = fileStream.CanWrite;
                await blockBlob.DownloadToStreamAsync(fileStream);
            }
            var test = 0;
        }
    }

    public class TodoItem : TableEntity
    {
        public TodoItem(string id, string content)
        {
            this.Id = id;
            this.Content = content;

            this.PartitionKey = id;
            this.RowKey = content;
        }

        public TodoItem() { }

        public string Id { get; set; }

        public string Content { get; set; }

        public string Additional { get; set; }
    }
}
