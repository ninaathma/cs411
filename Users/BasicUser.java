package Users;

public class BasicUser {
  String userName;
  String password;

  public BasicUser(String _userName, String _password) {
    userName = _userName;
    password = _password;
  }

  public String getUserName(){
    return userName;
  }
  public String getPassword(){
    return password;
  }
}
