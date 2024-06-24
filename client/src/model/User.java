package model;

public class User {
    private int id;
    private String username;
    private String password;
    private int cost;

    public User() {
    }

    public User(String username, String password, int cost) {
        this.username = username;
        this.password = password;
        this.cost = cost;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, int cost) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
