package Restaurants;

import java.util.HashMap;
import java.util.Map.Entry;

public class Cart {
  private HashMap<Item, Integer> items = new HashMap<Item, Integer>();
  private Restaurant restaurant;

  public Cart(Restaurant _restaurant) {
    restaurant = _restaurant;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void addItem(Item item) {
    items.put(item, 1);
  }

  public void removeItem(Item item) {
    items.remove(item);
  }

  public void setItemQuantity(Item item, int _quantity) {
    if (_quantity > 0) {
      items.replace(item, _quantity);
    } else {
      removeItem(item);
    }
  }

  public double getTotalPrice() {
    double totalPrice = 0;

    for (Entry<Item, Integer> entry : items.entrySet()) {
      Item item = (Item) entry.getKey();
      int quantity = (int) entry.getValue();
      totalPrice += quantity * item.getPrice();
      System.out.println(item.getName() + " " + item.getPrice());
    }

    return totalPrice;
  }
}
