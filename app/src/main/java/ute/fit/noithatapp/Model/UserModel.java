package ute.fit.noithatapp.Model;

public class UserModel {

    private int userId;
    private String name;
    private String username;
    private String password;


    public UserModel(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserModel(int userId, String name, String username, String password) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
    }
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

