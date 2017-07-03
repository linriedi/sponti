using Microsoft.WindowsAzure.Storage;
using Microsoft.WindowsAzure.Storage.Table;
using System.Threading.Tasks;

namespace AzureAdapter
{
    public class StorageTable
    {
        public async Task InsertAsync(TodoItem item)
        {
            var storageAccount = CloudStorageAccount.Parse("");
            var tableClient = storageAccount.CreateCloudTableClient();
            var table = tableClient.GetTableReference("todoitems");

            await table.CreateIfNotExistsAsync();
            var insertOrReplaceOperation = TableOperation.InsertOrReplace(new TodoItem(item.Id, item.Content));

            await table.ExecuteAsync(insertOrReplaceOperation);
        }
    }
}
