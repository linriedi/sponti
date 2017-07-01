using System.Collections.Generic;

namespace SpontiBL
{
    public class ItemsService
    {
        public IEnumerable<string> GetItems()
        {
            return new List<string>
            {
                "Gabi",
                "Linus"
            };
        }
    }
}
