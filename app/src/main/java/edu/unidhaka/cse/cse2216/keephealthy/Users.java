package edu.unidhaka.cse.cse2216.keephealthy;

public class Users {
    private String userId;
    private String userName;
    private String userSex;
    private String height;
    private String weight;

    public Users() {

    }

    public Users(String userId, String userName, String userSex, String height, String weight) {
        this.userId = userId;
        this.userName = userName;
        this.userSex = userSex;
        this.height = height;
        this.weight = weight;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }
}
