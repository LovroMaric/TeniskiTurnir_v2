package Util;

import Model.User;

public class Session {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole().name().equals("ADMIN");
    }

    public static void logout() {
        currentUser = null;
    }
}
