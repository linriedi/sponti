using SpontiBL;
using System;
using System.Collections.ObjectModel;
using System.Threading;
using System.Threading.Tasks;
using Windows.Media.Core;
using Windows.Media.Playback;
using Windows.Storage;
using Windows.UI.Core;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;

namespace SpontiUI
{
    public sealed partial class MainPage : Page
    {
        private readonly ItemsService itemsService;
        private MediaPlayer m = new MediaPlayer();

        private ObservableCollection<string> _items = new ObservableCollection<string>();
        private ObservableCollection<string> _todoItems = new ObservableCollection<string>();
        private string temp;
        private MediaSource test1;

        public ObservableCollection<string> Items
        {
            get { return this._items; }
        }

        public ObservableCollection<string> TodoItems
        {
            get { return this._todoItems; }
        }

        public string SelectedItem { get; set; }
        public string SelectedTodoItem { get; set; }

        public MainPage()
        {
            this.InitializeComponent();

            this.temp = Windows.Storage.ApplicationData.Current.TemporaryFolder.Path;
            this.itemsService = new ItemsService(temp);
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);

            GetItems();
            GetTodoItems();
        }

        private void OnClick(object sender, RoutedEventArgs e)
        {
            this.button.IsEnabled = false;
            NewMethod1();
        }

        private async Task NewMethod1()
        {
            await this.itemsService.PlayAsync(this.SelectedItem);
            try
            {
                m = new MediaPlayer();
                //{
                //var wait = new AutoResetEvent(false);

                m.MediaEnded += this.OnStateChanged;

                //m.Source = MediaSource.CreateFromUri(new Uri(this.temp + @"/test.3gp"));
                this.test1 = MediaSource.CreateFromUri(new Uri(this.temp + @"/test.3gp"));
                m.Source = test1;
                m.Play();

                

                //m.Play();

                //wait.WaitOne();
                //wait.Dispose();
                
                //m.Pause();
                //m.Source = null;
                //m.Close();
                //}

                var test = 99;
                
            }
            catch(Exception e)
            {

            }

            
        }

        private void OnStateChanged(MediaPlayer sender, object args)
        {
            m.Source = null;
            test1.Dispose();
            m.Dispose();
            Dispatcher.RunAsync(CoreDispatcherPriority.Normal, () =>
            {
                this.button.IsEnabled = true;
            });
        }

        private async Task GetItems()
        {
            var items = await this.itemsService.GetItemsAsync();
            foreach (var item in items)
            {
                Items.Add(item);
            }
        }

        private async Task GetTodoItems()
        {
            var items = await this.itemsService.GetTodoItemsAsync();
            foreach (var item in items)
            {
                TodoItems.Add(item);
            }
        }
    }
}
