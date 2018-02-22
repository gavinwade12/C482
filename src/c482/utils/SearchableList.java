package c482.utils;

import c482.models.Part;
import c482.utils.SearchableList.NamedItem;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;

public class SearchableList<T extends NamedItem> {
    
    private final FilteredList<T> filteredData;
    private final SortedList<T> sortedData;
    
    public SearchableList(ObservableList<T> data) {
        this.filteredData = new FilteredList<>(data, p -> true);
        this.sortedData = new SortedList<>(filteredData);
    }
    
    public void bind(TableView tableView) { 
        Bindings.bindContent(tableView.getItems(), sortedData);
    }
    
    public void filter(String term) {
        String lowercaseTerm = term.toLowerCase();
        filteredData.setPredicate(item -> {
            if (StringUtils.isEmpty(lowercaseTerm)) {
                return true;
            }

            return item.getName().toLowerCase().contains(lowercaseTerm);
        });
    }
    
    public interface NamedItem {
        String getName();
    }
    
}
