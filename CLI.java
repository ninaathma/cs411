import java.util.Scanner;

import Database.Database;
import Users.RestaurantOwner;
import Users.Client;

public class CLI {
  Database db = new Database();
  int path = 0;

  // Either one should have a value if logged in
  Client user;
  RestaurantOwner owner;

  // List of commands
  String[] commands = {
      "--- MAIN PAGE ---\n 1. Sign in as a client\n 2. Sign is as a restaurant owner\n 3. Register",
      "--- CLIENT PAGE ---\n 1. Add or edit items in order\n 2. Checkout\n 3. Check order status\n 4. Log out",
      "--- RESTAURANT OWNER PAGE ---\n 1. Add or edit an item in the menu\n 2. Log out"
  };

  public void mainCmds(int cmd) {
    Scanner scanner = new Scanner(System.in);
    switch (cmd) {
      case 1:
        Order order = new Order();
    }
  }

  public void restaurantOwnerCommands(int cmd) {
    Scanner scanner = new Scanner(System.in);
    switch (cmd) {
    }
  }

  public void userCommands(int cmd) {
    Scanner scanner = new Scanner(System.in);
    switch (cmd) {
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    CLI cli = new CLI();

    int test = 5;
    System.out.println(cli.commands[0]);

    while (true) {
      int cmd = scanner.nextInt();
      switch (cli.path) {
        case 0: /* At main page */
          cli.mainCmds(cmd);
          break;
        case 1:
          cli.restaurantOwnerCommands(cmd);
          break;
        case 2: /* At User page; */
          cli.userCommands(cmd);
          break;
      }
    }
  }
}
