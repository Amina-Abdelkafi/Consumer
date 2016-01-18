package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
public class DBOpenHelper extends SQLiteOpenHelper
{
  public DBOpenHelper(Context paramContext)
  {
    super(paramContext, "urion", null, 3);
  }

  public DBOpenHelper(Context paramContext, String paramString, CursorFactory paramCursorFactory, int paramInt)
  {
    super(paramContext, paramString, paramCursorFactory, paramInt);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {

      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS product (id integer primary key autoincrement,name varchar(30),category varchar(10),price double,description varchar(100),thumbnail blob, videoDemo blob,address varchar(30),city varchar(10))");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
      paramSQLiteDatabase.execSQL("drop table if exists user");

    onCreate(paramSQLiteDatabase);
  }
}

