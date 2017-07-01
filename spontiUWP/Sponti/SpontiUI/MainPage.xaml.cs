using SpontiBL;
using System.Collections.ObjectModel;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;

namespace SpontiUI
{
    public sealed partial class MainPage : Page
    {
        private readonly ItemsService itemsService = new ItemsService();

        private ObservableCollection<string> _items = new ObservableCollection<string>();

        public MainPage()
        {
            this.InitializeComponent();
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);

            NewMethod();
        }

        private async System.Threading.Tasks.Task NewMethod()
        {
            var items = await this.itemsService.GetItemsAsync();
            foreach (var item in items)
            {
                Items.Add(item);
            }
        }

        public ObservableCollection<string> Items
        {
            get { return this._items; }
        }
    }
}
