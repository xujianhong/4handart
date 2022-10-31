package com.daomingedu.art.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDataHelper extends SQLiteOpenHelper {
    static String DATABASENAME = "fourhandmusic";
    static int VERSION = 3;

    // 本地作品
    static String CONTACT = "create table contact(_id integer not null primary key autoincrement," +
            "type integer not null, " +
            "name varchar(50) not null, " +
            "path varchar(255) not null," +
            "shareId varchar(255)," +
            "scoreId varchar(255)," +
            "totalScore integer," +
            "scoreName varchar(255)," +
            "createtime date not null)";

    // 活动专区
    static String ACTIVITYCONTACT = "create table activity(_id integer not null primary key autoincrement," +
            "acticityid varchar(255) not null, createtime date not null)";


   public static String UPLOAD = "create table upload(_id integer not null primary key autoincrement," +
            "uploadid varchar(255) not null, " +
            "path varchar(255) not null, " +
            "keyname varchar(50) not null, " +
            "type integer not null, " +
            "name varchar(50) not null, "+
            "homeworkid varchar(255) not null, "+
            "createtime date not null)";

    public BaseDataHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

        arg0.execSQL(CONTACT);
        arg0.execSQL(ACTIVITYCONTACT);
        arg0.execSQL(UPLOAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        switch (arg1) {//判断版本
            case 1://版本1时CONTACT没有shareId
                arg0.execSQL("alter table contact ADD shareId varchar(255)");
                arg0.execSQL("alter table contact ADD scoreId varchar(255)");
                arg0.execSQL("alter table contact ADD scoreName varchar(255)");
                arg0.execSQL("alter table contact ADD totalScore integer");

                break;
            case 2:
                arg0.execSQL(UPLOAD);
                break;
        }


    }

}
