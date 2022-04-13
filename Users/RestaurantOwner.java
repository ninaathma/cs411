package Users;

import Restaurants.Restaurant;

public class RestaurantOwner extends BasicUser {
  Restaurant restaurant;

  public RestaurantOwner(String _userName, String _password) {
    super(_userName, _password);
  }
  public String getUserName(){
    return userName;
  }
  public String getPassword(){
    return password;
  }
}
