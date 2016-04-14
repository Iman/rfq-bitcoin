package order;

import java.util.List;

public interface LiveOrderBoard {
    List<Order> ordersFor(String currency);
}
