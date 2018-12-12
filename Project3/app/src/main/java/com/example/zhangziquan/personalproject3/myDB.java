package com.example.zhangziquan.personalproject3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDB extends SQLiteOpenHelper {
    private static final String DB_NAME= "USER.db";
    private static final String TABLE_NAME = "user_table";
    private static final String TABLE_NAME2 = "comment_table";
    private static final int DB_VERSION = 1;
    public final static String USER_ID = "_id";
    public final static String USER_NAME = "name";
    public final static String USER_PASSWORD = "password";
    public final static String USER_AVATAR = "avatar";
    public final static String COMMENT_ID = "_id";
    public final static String COMMENT_USERNAME = "uname";
    public final static String COMMENT_TIME ="time";
    public final static String COMMENT_CONTENT = "content";
    public final static String COMMENT_LIKES = "likes";
    public final static String COMMENT_LIKELIST = "likelist";


    public myDB(Context context) {
        // TODO Auto-generated constructor stub
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + " (_id INTEGER PRIMARY KEY, name TEXT, password TEXT, avatar BLOB ) ";
        sqLiteDatabase.execSQL(CREATE_TABLE);
        CREATE_TABLE ="CREATE TABLE if not exists "
                + TABLE_NAME2
                +" (_id INTEGER PRIMARY KEY, uname TEXT, content TEXT, time TEXT, likes INTEGER, likelist TEXT ) ";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser(String name, String password, byte[]img) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, name);
        cv.put(USER_PASSWORD, password);
        cv.put(USER_AVATAR, img);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void deleteUser(Integer id){
        SQLiteDatabase db = getWritableDatabase();
        String where = USER_ID + " = ?";
        String[] whereValue ={ Integer.toString(id) };
        db.delete(TABLE_NAME, where, whereValue);
        db.close();
    }

    public void updateUser(int _id, String name, String passowrd){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("<column 1>", "<value 1>");
        cv.put("<column 2>", "<value 2>");
        String whereClause = "<Primary KEY> = ?";
        String[] whereArgs = {"<key_id>"};

        db.update(TABLE_NAME, cv, whereClause, whereArgs);

        //如果更新的主键列值只有一个，也可以这样写
        String whereClause11 = "<Primary KEY>=<key_id>";
        db.update(TABLE_NAME, cv, whereClause11, null);
        db.close();
    }

    public User selectUser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table where name = ?",new String[]{name});
        if(cursor.getCount()==0) {
            cursor.close();
            db.close();
            return null;
        }else {
            cursor.moveToFirst();
            User user = new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getBlob(3));
            cursor.close();
            db.close();
            return user;
        }
    }

    public byte[] selectUserAvatar(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select avatar from user_table where name = ?",new String[]{name});
        byte[] avatar;
        if(cursor.getCount()==0) {
            cursor.close();
            db.close();
            return null;
        }else {
            cursor.moveToFirst();
            avatar = cursor.getBlob(3);
            cursor.close();
            db.close();
            return avatar;
        }
    }

    public int insertComment(String username, String time, String content, int likes, String likelist){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COMMENT_USERNAME, username);
        cv.put(COMMENT_TIME, time);
        cv.put(COMMENT_CONTENT, content);
        cv.put(COMMENT_LIKES,likes);
        cv.put(COMMENT_LIKELIST,likelist);
        db.insert(TABLE_NAME2, null, cv);
        Cursor cursor = db.rawQuery("select last_insert_rowid() from comment_table",null);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            db.close();
            return id;
        }
        return -1;
    }

    public void deleteComment(Integer id){
        SQLiteDatabase db = getWritableDatabase();
        String where = COMMENT_ID + " = ?";
        String[] whereValue ={ Integer.toString(id) };
        db.delete(TABLE_NAME2, where, whereValue);
        db.close();
    }

    public void updateComment(Integer id, Integer likenum, String userlike){
        SQLiteDatabase db = getWritableDatabase();
        String where = COMMENT_ID + " = ?";
        ContentValues cv = new ContentValues();
        cv.put(COMMENT_LIKELIST,userlike);
        cv.put(COMMENT_LIKES,likenum);
        String[] whereValue ={ Integer.toString(id) };
        db.update(TABLE_NAME2,cv,where, whereValue);
        db.close();
    }

    public Cursor selectAllComment() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from comment_table",null);
        return cursor;
    }

}