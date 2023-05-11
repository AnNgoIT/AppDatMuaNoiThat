package ute.fit.noithatapp.Model;

public class UserModel {

    private int id;
    private String name;
    private String username;
    private String password;


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
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setId(int userId) {
        this.id = userId;
    }

    public int getId() {
        return id;
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

