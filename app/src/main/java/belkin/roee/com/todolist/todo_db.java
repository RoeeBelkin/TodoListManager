package belkin.roee.com.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLData;
import java.util.ArrayList;

/**
 * Created by Roee on 09/05/2016.
 */
public class todo_db extends SQLiteOpenHelper {

    SQLiteDatabase DB;


    public todo_db(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.DB = this.getWritableDatabase();
    }

    public ArrayList<ListViewItems> getDBItems(){
        ArrayList<ListViewItems> itemsList = new ArrayList<ListViewItems>();
        Cursor cursor = DB.rawQuery("SELECT * FROM todoList", null);
        if (cursor.moveToFirst()) {
            do {
                itemsList.add(new ListViewItems(cursor.getString(cursor.getColumnIndex("todo")), cursor.getLong(cursor.getColumnIndex("dueDate"))));
            } while (cursor.moveToNext());
        }
        return itemsList;
    }

    public void insertRow(String itemTitle, long dueDate) {
        DB.execSQL("INSERT INTO todoList (`todo`, `dueDate`) VALUES ('" + itemTitle + "','" + dueDate + "');");
    }

    public void deleteRow(String itemTitle, long dueDate){
        DB.execSQL("DELETE FROM todoList WHERE todo ='" + itemTitle + "' AND dueDate ='" + dueDate + "';");
    }

    public void dropTable(){
        DB.execSQL("DROP TABLE todoList");
    }

    public void createNewTable(){
        DB.execSQL("CREATE TABLE IF NOT EXISTS todoList(`_id` INTEGER PRIMARY KEY AUTOINCREMENT,`todo` TEXT, `dueDate` REAL);");
    }

    public boolean isTableExist() {
        Cursor cursor = DB.rawQuery("SELECT * FROM sqlite_master WHERE name ='todoList' and type='table'; ", null);

        return cursor.getCount()==1? true:false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
