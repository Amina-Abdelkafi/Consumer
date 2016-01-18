package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import server.model.Product;

/**
 * Created by lenovo on 01/09/2015.
 */
public class ProductDb {
    private DBOpenHelper dbHelper;
    private SQLiteDatabase dbs;

    public ProductDb(Context paramContext) {
        this.dbHelper = new DBOpenHelper(paramContext);
    }

    public List<Product> getAllProduct() throws Exception

    {

        this.dbs = this.dbHelper.getReadableDatabase();
        List<Product> products = new ArrayList<Product>();
        Cursor localCursor = this.dbs.rawQuery("select id , name, category from product", null);

        while (localCursor.moveToNext()) {
            Product product = new Product();

            product.setId(localCursor.getInt(localCursor.getColumnIndex("id")));
            product.setName(localCursor.getString(localCursor.getColumnIndex("name")));
            product.setCategory(localCursor.getString(localCursor.getColumnIndex("category")));
            products.add(product);
        }

        localCursor.close();
        this.dbs.close();
        return products;
    }


    public Product getProductId(int id) throws Exception

    {
        this.dbs = this.dbHelper.getReadableDatabase();
        Product product = new Product();

        Cursor localCursor = this.dbs.rawQuery("select * from product where id = ? ", new String[]{"" + id});

        while (true) {

            if (localCursor.moveToNext()) {

                product.setName(localCursor.getString(localCursor.getColumnIndex("name")));
                product.setCategory(localCursor.getString(localCursor.getColumnIndex("category")));
               // product.setCity(localCursor.getString(localCursor.getColumnIndex("city")));
              //  product.setAddress(localCursor.getString(localCursor.getColumnIndex("address")));
                product.setDescription(localCursor.getString(localCursor.getColumnIndex("description")));
                product.setPrice(localCursor.getDouble(localCursor.getColumnIndex("price")));
                product.setThumbnail(localCursor.getBlob(localCursor.getColumnIndex("thumbnail")));
                product.setVideoDemo(localCursor.getBlob(localCursor.getColumnIndex("videoDemo")));

                localCursor.close();
                this.dbs.close();
                return product;
            }
        }
    }

    public void insert(Product product)
            throws Exception
    {this.dbs = this.dbHelper.getWritableDatabase();
        SQLiteDatabase localSQLiteDatabase = this.dbs;
        ContentValues values = new ContentValues();
        values.put("name", product.getName()); // Contact Name
        values.put("category",product.getCategory()); // Contact Phone
        values.put("price", product.getPrice()); // Contact Name
        values.put("description",product.getDescription()); // Contact Phone
        values.put("thumbnail", product.getThumbnail()); // Contact Name
        values.put("videoDemo",product.getVideoDemo()); // Contact Phone

        long g=localSQLiteDatabase.insert("product", null, values);
        localSQLiteDatabase.close();


    }
    public void deleteEntry(int id) {
        this.dbs = this.dbHelper.getWritableDatabase();
        dbs.delete("product", "id" + "=" + id , null);

    }
}
