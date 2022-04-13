package Database;

import java.util.HashMap;
import Users.RestaurantOwner;
import Users.Client;
import Restaurants.Restaurant;

public class Database {
  private HashMap<String, RestaurantOwner> ownerDatabase = new HashMap<>();
  private HashMap<String, Client> clientDatabase = new HashMap<>();
  private HashMap<String, Restaurant> restaurantDatabase = new HashMap<>();

  // GET METHODS
  public Client getClientByUserName (String userName) {
    return clientDatabase.get(userName);
  }
  public Restaurant getRestaurantByName (String restName) {
    return restaurantDatabase.get(restName);
  }
  public RestaurantOwner getRestaurantOwnerByName (String userName) {
    return ownerDatabase.get(userName);
  }

  // ADD METHODS
  public boolean addClient(String userName, Client clientObject){
    // Check if the user is already in the database
    Client userExists = getClientByUserName(userName);

    // if user is not, add user
    if (userExists == null){
      clientDatabase.put(userName, clientObject);
      return true;
    }
    // otherwise, skip
    else{
      return false;
      }
  }
  public boolean addOwner(String userName, RestaurantOwner ownerObject){
    // Check if the owner is already in the database
    RestaurantOwner ownerExists = getRestaurantOwnerByName(userName);

    // if owner is not, add owner
    if (ownerExists == null){
      ownerDatabase.put(userName, ownerObject);
      return true;
    }
    // otherwise, skip
    else{
      return false;
      }
  }  
  public boolean addRestaurant(String userName, Restaurant restaurantObject){
    // Check if the restaurant is already in the database
    Client userExists = getClientByUserName(userName);

    // if rest is not, add it
    if (userExists == null){
      restaurantDatabase.put(userName, restaurantObject);
      return true;
    }
    // otherwise, skip
    else{
      return false;
      }
  }

  //AUTH METHODS
  public boolean authenticateClient(String userName, String password){
    Client clientObject = getClientByUserName(userName);

    if (clientObject.getUserName() == userName && clientObject.getPassword() == password){
      return true;
    }
    else{
      return false;
    }
  }
  public boolean authenticateRestaurantOwner(String userName, String password){
    RestaurantOwner ownerObject = getRestaurantOwnerByName(userName);

    if (ownerObject.getUserName() == userName && ownerObject.getPassword() == password){
      return true;
    }
    else{
      return false;
    }
  }
}

