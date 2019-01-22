package edu.unidhaka.cse.cse2216.keephealthy;

public class Users {
    private String userName;
    private String userSex;
    private String height;
    private String weight;

    public Users() {

    }

    public Users(String userName, String userSex, String height, String weight) {
        this.userName = userName;
        this.userSex = userSex;
        this.height = height;
        this.weight = weight;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
