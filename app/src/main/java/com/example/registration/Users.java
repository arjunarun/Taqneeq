package com.example.registration;
public class Users {
    String userId;
    String userName;
    String userAge;
    String userPhone;
    String userEmail;
    String pass;
    String userType;
    String userStream;
    public Users(){

    }

    public Users(String userId, String userName, String userAge, String userPhone, String userEmail, String pass, String userType, String userStream) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.pass = pass;
        this.userType = userType;
        this.userStream = userStream;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserStream() {
        return userStream;
    }

    public void setUserStream(String userStream) {
        this.userStream = userStream;
    }
}
