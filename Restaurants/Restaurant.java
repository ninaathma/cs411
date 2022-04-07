package Restaurants;

import java.util.Queue;

public class Restaurant {
  private String name;
  private Item[] menu;
  private Queue<Order> orders;

  public void addNewOrder(Order _order) {
    orders.add(_order);
  }
}
