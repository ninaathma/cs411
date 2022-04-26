package Restaurants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Restaurant {
  private String name;
  private ArrayList<Item> menu;
  private Queue<Order> orders;

  public Restaurant(String _name) {
    name = _name;
    menu = new ArrayList<Item>();
    orders = new LinkedList<Order>();
  }

  public String getRestaurantName() {
    return name;
  }

  public void addNewOrder(Order _order) {
    orders.add(_order);
  }

  public void addNewItem(String name, double price) {
    Item newItem = new Item(name, price);
    menu.add(newItem);
  }

  public void addNewItem(Item newItem) {
    menu.add(newItem);
  }

  public void displayMenu() {
    for (int i = 0; i < menu.size(); i++) {
      Item item = menu.get(i);
      System.out.println(i + ". " + item.getName() + " - " + item.getPrice() + "$");
    }
    System.out.println();
  }

  public Item getItem(int idx) {
    return menu.get(idx);
  }

  public int getNumOfOrders() {
    return orders.size();
  }

  public void setItem(Item i, int itemIdx){
      menu.set(itemIdx, i);
  }

  public void deleteItem(int itemIdx){
      menu.remove(itemIdx);
  }
}
