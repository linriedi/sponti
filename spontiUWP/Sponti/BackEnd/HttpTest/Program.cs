using Microsoft.WindowsAzure.Storage;
using Microsoft.WindowsAzure.Storage.Table;
using System;
using System.Net;
using System.Net.Http;
using System.Threading;

namespace HttpTest
{
    class Program
    {
        private static readonly HttpClient client = new HttpClient();

        static void Main(string[] args)
        {
            PostTodoItem();
            GetAllTodoItems();

            Console.WriteLine("Finished");
            Console.ReadLine();
        }

        private static void GetAllTodoItems()
        {
            Thread.Sleep(10000);
            
            var storageAccount = CloudStorageAccount.Parse("");
            var tableClient = storageAccount.CreateCloudTableClient();
            var table = tableClient.GetTableReference("todoitems");

            var query = new TableQuery<TodoItem>();

            foreach (var entity in table.ExecuteQuery(query))
            {
                Console.WriteLine("{0},{1}", 
                    entity.PartitionKey, 
                    entity.RowKey);
            }
        }

        private static void PostTodoItem()
        {
            using (var client = new WebClient())
            {
                var apiPath = "https://spontibackendwebapp.azurewebsites.net/api/values";
                //var apiPath = "http://localhost:49684/api/values";

                client.Headers[HttpRequestHeader.ContentType] = "text/json";
                var data = "{\"id\":\"lastTest\",\"content\":\"some content\"}";
                var result = client.UploadString(apiPath, "POST", data);
            }
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
