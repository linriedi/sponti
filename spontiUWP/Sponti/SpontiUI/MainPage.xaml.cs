using SpontiBL;
using System.Collections.ObjectModel;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;

namespace SpontiUI
{
    public sealed partial class MainPage : Page
    {
        private readonly ItemsService itemsService;

        private ObservableCollection<string> _items = new ObservableCollection<string>();
        public ObservableCollection<string> Items
        {
            get { return this._items; }
        }

        public string SelectedItem { get; set; }

        public MainPage()
        {
            this.InitializeComponent();

            var temp = Windows.Storage.ApplicationData.Current.TemporaryFolder;
            this.itemsService = new ItemsService(temp.Path);
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);

            NewMethod();
        }

        private void OnClick(object sender, RoutedEventArgs e)
        {
            this.itemsService.PlayAsync(this.SelectedItem);
        }

        private async System.Threading.Tasks.Task NewMethod()
        {
            var items = await this.itemsService.GetItemsAsync();
            foreach (var item in items)
            {
                Items.Add(item);
            }
        }
    }
}
