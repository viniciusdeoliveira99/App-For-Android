package br.com.navegacao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.navegacao.PessoaAcesso;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "TESTE BANCO";
    public static final String DATABASE_NAME = "acesso.db";
    private static final int DATABASE_VERSION = 1 ;
    public static final String TABLE_NAME = "acesso";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USUARIO = "usuario";
    public static final String COLUMN_SENHA = "senha";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_EMAIL = "email";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_USUARIO + " TEXT NOT NULL, " +
                COLUMN_SENHA + " TEXT NOT NULL," +
                COLUMN_TELEFONE + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT NOT NULL);");
        Log.d(TAG, "Tabela criada!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    //EXECUTAR SQL QUERY
    public void execSQL(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql);
        }finally {
            db.close();
        }
    }


    //INSERIR OS DADOS
    public boolean create(PessoaAcesso pessoaAcesso){
        SQLiteDatabase db = getWritableDatabase();

        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_USUARIO, pessoaAcesso.getUsuario());
            contentValues.put(COLUMN_SENHA, pessoaAcesso.getSenha());
            contentValues.put(COLUMN_TELEFONE, pessoaAcesso.getTelefone());
            contentValues.put(COLUMN_EMAIL, pessoaAcesso.getEmail());

            long rows = db.insert(TABLE_NAME, null, contentValues);
            Log.d(TAG, "DADOS INSERIDOS!");
            return rows > 0;
        }catch (SQLException error){
            error.getMessage();
        }finally {
            db.close();
        }
        return false;
    }


    public List<PessoaAcesso> findAll(){
        List<PessoaAcesso> pa = new ArrayList<>();

        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from acesso", null);
            if(cursor.moveToFirst()){

                do{
                    PessoaAcesso pessoaAcesso = new PessoaAcesso();
                    pessoaAcesso.setId(cursor.getLong(0));
                    pessoaAcesso.setUsuario(cursor.getString(1));
                    pessoaAcesso.setSenha(cursor.getString(2));

                    pa.add(pessoaAcesso);

                }while (cursor.moveToNext());
            }
            db.close();

        }catch(Exception e){
            e.getMessage();
        }
        return pa;
    }

    public List<PessoaAcesso> buscarDados(){
        List<PessoaAcesso> listarDados = new ArrayList<>();

        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("select " + COLUMN_ID + ", " + COLUMN_USUARIO + ", " + COLUMN_SENHA + " from " + TABLE_NAME, null);

            if(cursor.moveToFirst()){

                do{
                    PessoaAcesso pessoaAcesso = new PessoaAcesso();
                    pessoaAcesso.setId(cursor.getLong(0));
                    pessoaAcesso.setUsuario(cursor.getString(1));
                    pessoaAcesso.setSenha(cursor.getString(2));

                    listarDados.add(pessoaAcesso);

                }while(cursor.moveToNext());
            }
            db.close();

        }catch(SQLException error){
            error.getMessage();
        }
        return listarDados;
    }


    public PessoaAcesso getDados(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        PessoaAcesso receivedPerson = new PessoaAcesso();
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            receivedPerson.setUsuario(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO)));
            receivedPerson.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
        }
        return receivedPerson;
    }
}
