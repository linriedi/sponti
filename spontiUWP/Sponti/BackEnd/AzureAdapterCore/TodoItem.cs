using Microsoft.WindowsAzure.Storage.Table;

namespace AzureAdapter
{
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
