package c482.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class PriceCellFactory<T, S> implements Callback<TableColumn<S,T>, TableCell<S,T>> {
    
    private final DecimalFormat priceFormat = new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {
            
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText("$" + priceFormat.format(item));
                }
            }
        };
    }
    
}
