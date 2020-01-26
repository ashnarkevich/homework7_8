package com.gmail.petrikov05.app.service.model;

public class AddedUserDTO {

    private static String userName;

    public static void setUserName(String userName) {
        AddedUserDTO.userName = userName;
    }

    public static String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return  getUserName();
    }

}
