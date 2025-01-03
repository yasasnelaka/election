package com.lk.election.init;

public class UserTypes {
    public static final int ADMIN = 0;
    public static final int DEC = 1;
    public static final int GNO = 2;

    public static String[] ALL_TYPES_SHORT = new String[]{
            "Admin",
            "DEC",
            "GNO"
    };

    public static String[] ALL_TYPES_MIDDLE = new String[]{
            "Admin",
            "DE Commissioner",
            "GN Officer"
    };

    public static String[] ALL_TYPES_FULL = new String[]{
            "Admin",
            "Divisional Election Commissioner",
            "Grama Niladhari Officer"
    };
}