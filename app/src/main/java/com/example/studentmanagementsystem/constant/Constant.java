package com.example.studentmanagementsystem.constant;

public final class Constant {
    public static final String VIEW_NAME ="view_name";
    public static final String VIEW_ROLL ="view_roll";
    public static final String VIEW ="view";
    public static final String EDIT ="edit";
    public static final String NORMAL ="normal";
    public static final String MODE ="mode";
    public static final String NAME ="name";
    public static final String ROLL_NO ="rollNo";
    public static final String NAME_MATCH ="^[a-zA-Z\\s]+$";
    public static final String ROLL_MATCH ="[+-]?[0-9][0-9]*";

    public static final String TABLE_NAME = "student";
    public static final String COLUMN_ROLL_NO = "roll";
    public static final String COLUMN_NAME = "name";

    public static final int VIEW_DATA=0;
    public static final int EDIT_DATA=1;
    public static final int DELETE_DATA=2;
    public static final int RC_VIEW=1;
    public static final int RC_EDIT=2;

    public static final int ASYNC=0;
    public static final int SERVICE=1;
    public static final int INTENT_SERVICE=2;
    public static final String OPERATION ="operation";

    public static final String DB_NAME = "student_db";
    public static final int DB_VERSION=1;

    public static final String ACTION = "broadcast";

}
