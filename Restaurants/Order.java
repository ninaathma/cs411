package Restaurants;

import java.util.HashMap;

public class Order {
  private OrderStatus status;
  private HashMap<Item, Integer> items;
  private double totalPrice;

  public Order(HashMap<Item, Integer> _items, double _totalPrice) {
    items = _items;
    totalPrice = _totalPrice;
    status = OrderStatus.ORDERED;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public HashMap<Item, Integer> getItems() {
    return items;
  }
}
