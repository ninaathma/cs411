package Database;

import java.util.HashMap;

import Restaurants.Restaurant;
import Users.RestaurantOwner;
import Users.Client;

public class Database {
  private HashMap<String, RestaurantOwner> ownerDatabase;
  private HashMap<String, Client> clientDatabase;
  private HashMap<String, Restaurant> restaurantDatabase;

  public Restaurant getRestaurantByName(String restaurantName) {return null;}

  public Client getClientByUserName(String userName) {return null;}

  public RestaurantOwner getRestaurantOwnerByUserName(String userName) {return null;}

  public Client authenticateClient(String userName, String password) {return null;}

  public RestaurantOwner authenticateRestaurantOwner(String userName, String password) {return null;}

  public void addClient(Client client) {}

  public void addRestaurantOwner(RestaurantOwner restOwner) {}

  public void addRestaurant(Restaurant rest) {}

}
