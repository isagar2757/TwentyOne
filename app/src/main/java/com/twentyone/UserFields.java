package com.twentyone;

public class UserFields {
String    Name,Password, Email;
int    Phone,roleId;

    public UserFields() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public UserFields(String name, String password, String email, int phone, int roleId) {
        Name = name;
        Password = password;
        Email = email;
        Phone = phone;
        this.roleId = roleId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }
}
