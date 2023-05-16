package ute.fit.noithatapp.Model;

public class UserModel {

    private int id;
    private String name;
    private String username;
    private String password;

    private String image;

    private String address;
    private String address2;
    private String address3;

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    private String role;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserModel(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserModel(int id, String name, String username, String password, String image, String address, String address2, String address3, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.image = image;
        this.address = address;
        this.address2 = address2;
        this.address3 = address3;
        this.role = role;
    }

    public UserModel(int id, String name, String username, String password, String image, String address, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.image = image;
        this.address = address;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

