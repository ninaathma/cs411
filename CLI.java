import java.util.ArrayList;
import java.util.Scanner;

import Database.Database;
import Restaurants.Cart;
import Restaurants.Item;
import Restaurants.Order;
import Restaurants.OrderStatus;
import Restaurants.Restaurant;
import Users.RestaurantOwner;
import Users.BasicUser;
import Users.Client;

public class CLI {
  Database db = new Database();
  private int path = 0;

  public static final String WHITE = "\u001B[0m";
  public static final String RED = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";

  // Either one should have a value if logged in
  BasicUser user;

  // If a client starts a new cart or order
  Cart cart;
  Order order;

  // List of commands
  String[] commands = {
      "--- MAIN PAGE ---\n 1. Sign in\n 2. Register\n 3. Exit\n-----------------",
      "--- CLIENT PAGE ---\n 1. Show restaurants\n 2. Start a new order\n 3. Show items in cart\n 4. Add item to order\n 5. Edit item in order\n 6. Checkout & Pay\n 7. Check order status\n 8. Log out\n-------------------",
      "--- RESTAURANT OWNER PAGE ---\n 1. Show restaurant menu\n 2. Add item to menu\n 3. Edit item in menu\n 4. Log out\n-----------------------------"
  };

  public static void printError(String err) {
    System.out.println(RED + err + WHITE);
  }

  public static void printSuccess(String text) {
    System.out.println(GREEN + text + WHITE);
  }

  public static void printBold(String text) {
    System.out.println("\033[1m" + text + "\033[0m");
  }

  public void logout() {
    path = 0;
    user = null;
    cart = null;
    order = null;
  }

  public boolean mainCmds(int cmd, Scanner scanner) {
    boolean shouldExit = false;

    switch (cmd) {
      case 1:
        // Get username, password and if its a restaurant owner
        System.out.print("Username: ");
        String signInUserName = scanner.next();
        System.out.print("Password: ");
        String signInPassword = scanner.next();
        System.out.print("Are you a restaurant owner [y/n]: ");
        boolean signInIsRestaurantOwner = scanner.next().equals("y");

        if (!signInIsRestaurantOwner) {
          // If it is a client, authenticate
          user = db.authenticateClient(signInUserName, signInPassword);
        } else {
          // If it is a restaurant owner, authenticate
          user = db.authenticateRestaurantOwner(signInUserName, signInPassword);
        }

        if (user == null) {
          printError("Login details are incorrect, try again.");
        } else {
          // Print success message and navigate to correct path
          printSuccess("Successfully logged in as " + signInUserName);
          path = signInIsRestaurantOwner ? 2 : 1;
        }
        break;
      case 2:
        // Get username, password and if its a restaurant owner
        System.out.print("Username: ");
        String registerUserName = scanner.next();
        System.out.print("Password: ");
        String registerPassword = scanner.next();
        System.out.print("Are you a restaurant owner [y/n]: ");
        boolean registerIsRestaurantOwner = scanner.next().equals("y");

        boolean success;

        if (!registerIsRestaurantOwner) {
          Client newClient = new Client(registerUserName, registerPassword);
          success = db.addClient(registerUserName, newClient);
          user = newClient;

          // Move to clients page
          path = 1;
        } else {
          // Get restaurants name
          System.out.print("What is the name of your restaurant?: ");
          String registerRestaurantName = scanner.next();

          // Grab restaurant by name
          Restaurant restaurant = new Restaurant(registerRestaurantName);

          // Register
          RestaurantOwner newRestaurantOwner = new RestaurantOwner(registerUserName, registerPassword, restaurant);
          user = newRestaurantOwner;
          success = db.addOwner(registerUserName, newRestaurantOwner);
          db.addRestaurant(registerRestaurantName, restaurant);

          // Move to restaurant owners page
          path = 2;
        }

        if (success)
          printSuccess("Successfully registered with username '" + registerUserName + "'");
        else
          printError("A user already exists with username '" + registerUserName + "'");
        break;
      case 3:
        shouldExit = true;
        break;
    }

    return shouldExit;
  }

  public void userCommands(int cmd, Scanner scanner) {
    switch (cmd) {
      case 1:
        ArrayList<String> restaurantNames = db.getAllRestaurantNames();
        printBold("Restaurant list:");
        for (int i = 0; i < restaurantNames.size(); i++) {
          System.out.println(i + ". " + restaurantNames.get(i));
        }
        break;
      case 2:
        System.out.print("Choose a restaurant: ");
        int newOrderRestaurantId = scanner.nextInt();
        String newOrderRestaurantName = db.getAllRestaurantNames().get(newOrderRestaurantId);
        Restaurant newOrderRestaurant = db.getRestaurantByName(newOrderRestaurantName);

        cart = new Cart(newOrderRestaurant);
        printSuccess("Successfully started a new cart for " + newOrderRestaurantName);
        break;
      case 3:
        printBold("Your cart:");
        cart.displayItems();
        break;
      case 4:
        Restaurant addItemToOrderRestaurant = cart.getRestaurant();
        printBold("Items in menu:");
        addItemToOrderRestaurant.displayMenu();
        System.out.print("Choose an item to add to your order: ");
        int addItemToOrderItemIdx = scanner.nextInt();
        Item addItemToOrderItem = addItemToOrderRestaurant.getItem(addItemToOrderItemIdx);
        cart.addItem(addItemToOrderItem);
        printSuccess("Successfully added " + addItemToOrderItem.getName() + " to order");
        printSuccess("New price " + cart.getTotalPrice() + "$");
        break;
      case 5:
        printBold("Items in cart:");
        ArrayList<Item> editItemsCart = cart.displayItems();
        System.out.print("Choose an item to edit: ");
        int editItemIdx = scanner.nextInt();
        System.out.print("Choose the new quantity (0 deletes it): ");
        int editItemNewQuantity = scanner.nextInt();

        Item editItemItem = editItemsCart.get(editItemIdx);
        cart.editItem(editItemItem, editItemNewQuantity);
        printSuccess("Successfully edited your cart");
        printSuccess("New price " + cart.getTotalPrice() + "$");
        break;
      case 6:
        printBold("Checkout:");

        // Get card details
        System.out.print("Enter your card number: ");
        scanner.next(); // We dont actually need these details
        System.out.print("Enter your card expiry date: ");
        scanner.next(); // We dont actually need these details
        System.out.print("Enter your card CVV: ");
        scanner.next(); // We dont actually need these details

        order = new Order(cart);
        Restaurant placeOrderRestaurant = cart.getRestaurant();
        placeOrderRestaurant.addNewOrder(order);
        printSuccess("Successfully placed your order in position #" + placeOrderRestaurant.getNumOfOrders());
        break;
      case 7:
        OrderStatus checkStatusStatus = order.getStatus();
        if (checkStatusStatus == OrderStatus.ORDERED)
          printSuccess("Your order was just ordered");
        else if (checkStatusStatus == OrderStatus.PREPARING)
          printSuccess("Your order is being prepared");
        else
          printSuccess("Your order is ready");
        break;
      case 8:
        logout();
    }
  }

  public void restaurantOwnerCommands(int cmd, Scanner scanner) {
    switch (cmd) {
    }
  }

  public static void prepopulate(CLI cli) {
    /*
     * PREPOPULATE OUR DATABASE WITH EXAMPLE RESTAURANTS
     */
    Database db = cli.db;
    String[][] userData = {
        { "user1", "1234", "Basho Express" },
        { "user2", "1234", "Open Kitchen" },
        { "user3", "1234", "McDonalds" }
    };

    String[][] itemNameData = {
        { "Sushi Bowl", "Sushi Burrito", "Salad Bowl", "California Rolls" },
        { "3 Chicken Finger Basket", "Packards Corner Sandwich", "5 Finger Orange Ya Happy" },
        { "Happy Meal", "4 Piece Chicken McNuggets", "Big Mac", "Double Hamburger", "McDouble" }
    };

    double[][] itemPriceData = {
        { 15.99, 8.49, 12.49, 13.29 },
        { 4.99, 12.90, 9.29 },
        { 6.99, 7.49, 7.49, 8.29, 11.55 },
    };

    for (int i = 0; i < userData.length; i++) {
      String[] uD = userData[i];

      Restaurant restaurant = new Restaurant(uD[2]);
      RestaurantOwner newRestaurantOwner = new RestaurantOwner(uD[0], uD[1], restaurant);
      db.addOwner(uD[0], newRestaurantOwner);
      db.addRestaurant(uD[2], restaurant);

      String[] itemNames = itemNameData[i];
      double[] itemPrices = itemPriceData[i];
      for (int j = 0; j < itemNames.length; j++) {
        restaurant.addNewItem(itemNames[j], itemPrices[j]);
      }
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    CLI cli = new CLI();
    boolean shouldExit = false;
    prepopulate(cli);

    while (!shouldExit) {
      System.out.println(cli.commands[cli.path]);
      System.out.print("Select an option (number): ");
      int cmd = scanner.nextInt();
      System.out.println();
      switch (cli.path) {
        case 0:
          shouldExit = cli.mainCmds(cmd, scanner);
          break;
        case 1:
          cli.userCommands(cmd, scanner);
          break;
        case 2:
          cli.restaurantOwnerCommands(cmd, scanner);
          break;
      }

      System.out.println();
      System.out.println("------------------------------");
      System.out.println();

    }

    scanner.close();
  }
}
