package com.amesingstore.model;

public class User {
    private int id;
    private String fullname;
    private String email;
    private String password;
    private String role;
    
    private String phone;
    private String street;
    private String village;
    private String city;
    private String landmark;
    private String pincode;
    
    
    private String profileImage;
    public User() {}
    public User(int id, String fullname, String email, String password, String role) {
        this.id = id; this.fullname = fullname; this.email = email;
        this.password = password; this.role = role;
    }
    // getters & setters
    
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getLandmark() { return landmark; }
    public void setLandmark(String landmark) { this.landmark = landmark; }
    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}