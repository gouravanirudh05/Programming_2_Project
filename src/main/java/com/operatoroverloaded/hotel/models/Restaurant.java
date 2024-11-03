import java.util.ArrayList;
import java.util.List;
import com.operatoroverloaded.hotel.stores.dishstore.*;
import com.operatoroverloaded.hotel.stores.tablestore.*;
public class Restaurant {
    private DishStore dishStore = DishStore.getInstance();
    private TableStore tableStore = TableStore.getInstance();
}