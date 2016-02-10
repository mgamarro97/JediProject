package projectjedi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;import java.lang.Override;import java.lang.String;

public class DBHelper extends SQLiteOpenHelper {

    //Declaracion del nombre de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Declaracion global de la version de la base de datos
    public static final String DATABASE_NAME = "project";

    //Declaracion del nombre de la tabla
    public static final String LOGIN_TABLE ="login";

    //sentencia global de cracion de la base de datos
    public static final String LOGIN_TABLE_CREATE = "CREATE TABLE " + LOGIN_TABLE + " (user TEXT, pass STRING, score INTEGER,street TEXT);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LOGIN_TABLE_CREATE);
    }

    //obtener una lista de coches
    public int[] getScores() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                LOGIN_TABLE,  // The table to query
                null,         // The columns to return
                null,        // The columns for the WHERE clause
                null,           // The values for the WHERE clause
                null,            // don't group the rows
                null,            // don't filter by row groups
                "score" + " ASC"             // The sort order
        );
        c.moveToFirst();
        int n = c.getCount();
        int[] scores = new int[n];
        for (int i=0; i<n;i++){
            if(i != 0) c.moveToNext();
            int userscore = c.getInt(c.getColumnIndex("score"));
            scores[i] = userscore;
        }
        c.close();
        return scores;
    }

    public String[] getUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                LOGIN_TABLE,  // The table to query
                null,         // The columns to return
                null,        // The columns for the WHERE clause
                null,           // The values for the WHERE clause
                null,            // don't group the rows
                null,            // don't filter by row groups
                "score" + " ASC"             // The sort order
        );
        c.moveToFirst();
        int n = c.getCount();
        String[] users = new String[n];
        for (int i=0; i<n;i++){
            if(i != 0) c.moveToNext();
            String username = c.getString(c.getColumnIndex("user"));
            users[i] = username;
        }
        c.close();
        return users;
    }

    public int numberOfUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                LOGIN_TABLE,  // The table to query
                null,         // The columns to return
                null,        // The columns for the WHERE clause
                null,           // The values for the WHERE clause
                null,            // don't group the rows
                null,            // don't filter by row groups
                null             // The sort order
        );
        c.moveToFirst();
        int n = c.getCount() + 1;
        c.close();
        return n;
    }

    public Cursor getProfile(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"user","score","street"};
        String[] where = {user};
        Cursor c = db.query(
                LOGIN_TABLE,          // The table to query
                columns,            // The columns to return
                "user=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );
        return c;
    }

    public Cursor getUserPassword(String user, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"user"};
        String[] where = {user, password};
        Cursor c = db.query(
                LOGIN_TABLE,          // The table to query
                columns,            // The columns to return
                "user=? AND pass=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );
        return c;
    }

    public void createDB (ContentValues values, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(
                tableName,
                null,
                values);
    }
    public void updateDB(String tableName, ContentValues values, String where, String[] whereargs){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(LOGIN_TABLE,
                values ,
                where,
                whereargs
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}