package com.example.hitcapp.managers;

public class SessionManager {
    public static int userId = 0;
    public static String email = null;
    public static String password = null;
    public static String userName = null;

    public static void clear() {
        userId = 0;
        email = null;
        password = null;
        userName = null;
    }
}
