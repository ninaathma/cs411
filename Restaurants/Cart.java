package Restaurants;

import java.util.ArrayList;
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

  public HashMap<Item, Integer> getItems() {
    return items;
  }

  public void addItem(Item item) {
    int quantity = 1;
    if (items.containsKey(item)) {
      quantity = items.get(item) + 1;
    }

    items.put(item, quantity);
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
    }

    return totalPrice;
  }

  public ArrayList<Item> displayItems() {
    ArrayList<Item> orderedItems = new ArrayList<>();

    for (Entry<Item, Integer> entry : items.entrySet()) {
      Item item = (Item) entry.getKey();
      int quantity = (int) entry.getValue();

      int idx = orderedItems.size();
      orderedItems.add(item);
      System.out.println(idx + ". " + item.getName() + " (" + quantity + ")");
    }
    System.out.println();
    return orderedItems;
  }

  public void editItem(Item item, int newQuantity) {
    if (newQuantity > 0)
      items.put(item, newQuantity);
    else
      removeItem(item);
  }
}
