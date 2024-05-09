package com.example.courseprojectdraft_24;

public class Admin {
    private String adminEmail;
    private String adminFirstName;
    private String adminLastName;
    private String adminGender;
    private String adminPassword;
    private String adminConfirmPassword;
    private String adminCountry;
    private String adminCity;
    private String adminPhone;
    public Admin() {
        this.adminEmail = "alaa@admin.com";
        this.adminFirstName = "ala";
        this.adminLastName = "mah";
        this.adminGender = "Female";
        this.adminPassword = "alaa123#";
        this.adminConfirmPassword = "alaa123#";
        this.adminCountry = "Palestine";
        this.adminCity = "Jerusalem";
        this.adminPhone = "0097123456789";
    }
    public Admin(String adminEmail, String adminFirstName, String adminLastName, String adminGender, String adminPassword, String adminConfirmPassword, String adminCountry, String adminCity, String adminPhone) {
        this.adminEmail = adminEmail;
        this.adminFirstName = adminFirstName;
        this.adminLastName = adminLastName;
        this.adminGender = adminGender;
        this.adminPassword = adminPassword;
        this.adminConfirmPassword = adminConfirmPassword;
        this.adminCountry = adminCountry;
        this.adminCity = adminCity;
        this.adminPhone = adminPhone;
    }
    public String getAdminEmail() {
        return adminEmail;
    }
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }
    public String getAdminFirstName() {
        return adminFirstName;
    }
    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }
    public String getAdminLastName() {
        return adminLastName;
    }
    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    }
    public String getAdminGender() {
        return adminGender;
    }
    public void setAdminGender(String adminGender) {
        this.adminGender = adminGender;
    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    public String getAdminConfirmPassword() {
        return adminConfirmPassword;
    }
    public String getAdminCountry() {
        return adminCountry;
    }
    public void setAdminCountry(String adminCountry) {
        this.adminCountry = adminCountry;
    }
    public String getAdminCity() {
        return adminCity;
    }
    public void setAdminCity(String adminCity) {
        this.adminCity = adminCity;
    }
    public String getAdminPhone() {
        return adminPhone;
    }
    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }
    public void setAdminConfirmPassword(String adminConfirmPassword) {this.adminConfirmPassword = adminConfirmPassword;}

    @Override
    public String toString() {
        return "Admin{" +
                "adminEmail='" + adminEmail + '\'' +
                ", adminFirstName='" + adminFirstName + '\'' +
                ", adminLastName='" + adminLastName + '\'' +
                ", adminGender='" + adminGender + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminConfirmPassword='" + adminConfirmPassword + '\'' +
                ", adminCountry='" + adminCountry + '\'' +
                ", adminCity='" + adminCity + '\'' +
                ", adminPhone='" + adminPhone + '\'' +
                '}';
    }
}
