import java.util.Scanner;

import Database.Database;
import Restaurants.Restaurant;
import Users.RestaurantOwner;
import Users.BasicUser;
import Users.Client;

public class CLI {
  Database db = new Database();
  private int path = 0;

  // Either one should have a value if logged in
  BasicUser user;

  // List of commands
  String[] commands = {
      "--- MAIN PAGE ---\n 1. Sign in\n 2. Register\n 3. Exit\n-----------------",
      "--- CLIENT PAGE ---\n 1. Add an item to the order\n 2. Edit items in order\n 3. Checkout\n 4. Check order status\n 5. Log out\n-------------------",
      "--- RESTAURANT OWNER PAGE ---\n 1. Add or edit an item in the menu\n 2. Log out\n-----------------------------"
  };

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
        boolean signInIsRestaurantOwner = scanner.next() == "y";

        if(!signInIsRestaurantOwner){
          // If it is a client, authenticate
          user = db.authenticateClient(signInUserName, signInPassword);
        } else {
          // If it is a restaurant owner, authenticate
          user = db.authenticateRestaurantOwner(signInUserName, signInPassword);
        }

        if(user == null) {
          System.out.println("Login details are incorrect, try again.");
        } else {
          // Print success message and navigate to correct path
          System.out.println("Successfully logged in as " + signInUserName);
          path = signInIsRestaurantOwner ? 2 : 1;
        }
        break;
      case 2:
        // Get username, password and if its a restaurant owner
        System.out.print("Username: ");
        String registerUserName = scanner.next();
        System.out.print("Password: ");
        String registerPassword = scanner.next();
        System.out.print("Are you a restaurant owner [Y/n]: ");
        boolean registerIsRestaurantOwner = scanner.next() == "y";

        if(!registerIsRestaurantOwner){
          Client newClient = new Client(registerUserName, registerPassword);
          user = newClient;
          path = 1;
        } else {
          // Get restaurants name
          System.out.print("What is the name of your restaurant?: ");
          String registerRestaurantName = scanner.next();

          // Grab restaurant by name
          Restaurant restaurant = db.getRestaurantByName(registerRestaurantName);

          // Register
          RestaurantOwner newRestaurantOwner = new RestaurantOwner(registerUserName, registerPassword, restaurant);
          user = newRestaurantOwner;
          path = 2;
        }

        System.out.println("Successfully registered with username " + registerUserName);
        break;
      case 3:
        shouldExit = true;
        break;
    }

    return shouldExit;
  }

  public void userCommands(int cmd, Scanner scanner) {
    switch (cmd) {
    }
  }

  public void restaurantOwnerCommands(int cmd, Scanner scanner) {
    switch (cmd) {
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    CLI cli = new CLI();
    boolean shouldExit = false;

    while (!shouldExit) {
      System.out.println(cli.commands[cli.path]);
      int cmd = scanner.nextInt();
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
