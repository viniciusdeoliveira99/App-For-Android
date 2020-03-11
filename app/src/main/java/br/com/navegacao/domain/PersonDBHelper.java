package br.com.navegacao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.LinkedList;
import java.util.List;

import br.com.navegacao.PessoaAcesso;


public class PersonDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "people.db";
    private static final int DATABASE_VERSION = 1 ;
    public static final String TABLE_NAME = "People";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PERSON_NAME = "name";
    public static final String COLUMN_PERSON_AGE = "age";
    public static final String COLUMN_PERSON_OCCUPATION = "occupation";


    public PersonDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_PERSON_NAME + " TEXT NOT NULL, " +
                COLUMN_PERSON_AGE + " NUMBER NOT NULL, " +
                COLUMN_PERSON_OCCUPATION + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }


    public void saveNewPerson(PessoaAcesso pessoaAcesso) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSON_NAME, pessoaAcesso.getName());
        values.put(COLUMN_PERSON_AGE, pessoaAcesso.getAge());
        values.put(COLUMN_PERSON_OCCUPATION, pessoaAcesso.getOccupation());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<PessoaAcesso> peopleList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<PessoaAcesso> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        PessoaAcesso pessoaAcesso;

        if (cursor.moveToFirst()) {
            do {
                pessoaAcesso = new PessoaAcesso();

                pessoaAcesso.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                pessoaAcesso.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_NAME)));
                pessoaAcesso.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_AGE)));
                pessoaAcesso.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_OCCUPATION)));
                personLinkedList.add(pessoaAcesso);

            } while (cursor.moveToNext());
        }
        return personLinkedList;
    }

    /**Query only 1 record**/
    public PessoaAcesso getPerson(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        PessoaAcesso receivedPerson = new PessoaAcesso();
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();

            receivedPerson.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_NAME)));
            receivedPerson.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_AGE)));
            receivedPerson.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_OCCUPATION)));
        }
        return receivedPerson;
    }


    /**delete record**/
    public void deletePersonRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        //Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    /**update record**/
    public void updatePersonRecord(long personId, Context context, PessoaAcesso updatedperson) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE  "+TABLE_NAME+" SET name ='"+ updatedperson.getName() + "', age ='"
                + updatedperson.getAge()+ "', occupation ='"+ updatedperson.getOccupation()
                + "'  WHERE _id='" + personId + "'");
    }
}
