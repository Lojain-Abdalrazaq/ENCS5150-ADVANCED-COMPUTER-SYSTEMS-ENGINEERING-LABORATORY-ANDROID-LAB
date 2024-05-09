package com.example.courseprojectdraft_24;

public class User {
    // attributes
    private String userEmail;
    private String firstName;
    private String lastName;
    private String userGender;
    private String password;
    private String cpassword;
    private String userCountry;
    private String userCity;
    private String userPhone;
    // constructors
    public User(String userEmail, String firstName, String lastName, String userGender, String password, String cpassword, String userCountry, String userCity, String userPhone) {
        this.userEmail = userEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userGender = userGender;
        this.password = password;
        this.cpassword = cpassword;
        this.userCountry = userCountry;
        this.userCity = userCity;
        this.userPhone = userPhone;
    }
    public User() {}
    // setters and getters
    public String getUserEmail() {return userEmail;}
    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getUserGender() {return userGender;}
    public void setUserGender(String userGender) {this.userGender = userGender;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getCpassword() {return cpassword;}
    public void setCpassword(String cpassword) {this.cpassword = cpassword;}
    public String getUserCountry() {return userCountry;}
    public void setUserCountry(String userCountry) {this.userCountry = userCountry;}
    public String getUserCity() {return userCity;}
    public void setUserCity(String userCity) {this.userCity = userCity;}
    public String getUserPhone() {return userPhone;}
    public void setUserPhone(String userPhone) {this.userPhone = userPhone;}
}
