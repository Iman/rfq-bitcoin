package order;

public class Order {
    private final Direction direction;
    private final double price;
    private final Currency currency;
    private final int qty;

    public Order(Direction direction, double price, Currency currency, int qty) {
        this.direction = direction;
        this.price = price;
        this.currency = currency;
        this.qty = qty;
    }

    public Boolean isBuy() {

        if (Direction.BUY == direction) {
            return true;
        } else {
            return false;
        }
    }

    public double getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public int getAmountOfUnit() {
        return qty;
    }
}

