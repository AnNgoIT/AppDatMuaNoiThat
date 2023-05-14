package ute.fit.noithatapp.Model;

public class UserModel {

    private int id;
    private String name;
    private String username;
    private String password;

<<<<<<< HEAD
    public UserModel(String username, String password, CartModel cartModel) {
        this.username = username;
        this.password = password;
        this.cartModel = cartModel;
    }

    private CartModel cartModel;
=======
>>>>>>> origin/main

    public UserModel(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserModel(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
<<<<<<< HEAD

    public String getUsername() {
        return username;
=======
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setId(int userId) {
        this.id = userId;
>>>>>>> origin/main
    }

    public int getId() {
        return id;
    }

<<<<<<< HEAD
    public void setUser(String username) {
        this.username = username;
    }

=======
>>>>>>> origin/main
    public String getName() {
        return name;
    }

<<<<<<< HEAD
=======
    public String getUsername() {
        return username;
    }

>>>>>>> origin/main
    public String getPassword() {
        return password;
    }

<<<<<<< HEAD
    public void setId(int id) {
        this.id = id;
    }

=======
>>>>>>> origin/main
    public void setName(String name) {
        this.name = name;
    }

<<<<<<< HEAD
    public void setPassword(String password) {
        this.password = password;
    }
    public UserModel(String user, String password) {
        this.username = user;
        this.password = password;
    }
=======
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
>>>>>>> origin/main
}

