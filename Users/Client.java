package Users;

public class Client extends BasicUser {
  public Client(String _userName, String _password) {
    super(_userName, _password);
  }
  public String getUserName(){
    return userName;
  }
  public String getPassword(){
    return password;
  }
}
