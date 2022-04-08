package Users;

import Restaurants.Restaurant;

public class RestaurantOwner extends BasicUser {
  Restaurant restaurant;

  public RestaurantOwner(String _userName, String _password, Restaurant _restaurant) {
    super(_userName, _password);
    restaurant = _restaurant;
  }
}
