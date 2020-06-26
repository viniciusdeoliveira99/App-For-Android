package br.com.navegacao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.navegacao.Usuario;

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
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                COLUMN_USUARIO + " TEXT NOT NULL, " +
                COLUMN_SENHA + " TEXT NOT NULL," +
                COLUMN_TELEFONE + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT NOT NULL);");
        Log.d(TAG, "TABELA ACESSO CRIADA!");
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
    public void create(Usuario usuarioCadastro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(COLUMN_ID, usuarioCadastro.getId());
        valores.put(COLUMN_USUARIO, usuarioCadastro.getUsuario());
        valores.put(COLUMN_SENHA, usuarioCadastro.getSenha());
        valores.put(COLUMN_TELEFONE, usuarioCadastro.getTelefone());
        valores.put(COLUMN_EMAIL, usuarioCadastro.getEmail());

        db.insert(TABLE_NAME, null, valores);
        db.close();
        Log.d(TAG, "CADASTRO DE USUÁRIO REALIZADO!");
    }

//    public List<PessoaAcesso> findAll(){
//        List<PessoaAcesso> pa = new ArrayList<>();
//
//        try{
//            SQLiteDatabase db = getWritableDatabase();
//            Cursor cursor = db.rawQuery("select * from acesso", null);
//            if(cursor.moveToFirst()){
//
//                do{
//                    PessoaAcesso pessoaAcesso = new PessoaAcesso();
//                    pessoaAcesso.setId(cursor.getLong(0));
//                    pessoaAcesso.setUsuario(cursor.getString(1));
//                    pessoaAcesso.setSenha(cursor.getString(2));
//
//                    pa.add(pessoaAcesso);
//
//                }while (cursor.moveToNext());
//            }
//            db.close();
//
//        }catch(Exception e){
//            e.getMessage();
//        }
//        return pa;
//    }

    //MÉTODO PARA BUSCAR OS DADOS DO USUÁRIO NO BANCO
    public List<Usuario> buscarDados(){
        List<Usuario> carregarListaUsuario = new ArrayList<>();

        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("select " + COLUMN_ID + ", "
                    + COLUMN_USUARIO + ", "
                    + COLUMN_SENHA + " from "
                    + TABLE_NAME, null);

            if(cursor.moveToFirst()){
                do{
                    Usuario user = new Usuario();
                    user.setId(cursor.getLong(0));
                    user.setUsuario(cursor.getString(1));
                    user.setSenha(cursor.getString(2));

                    carregarListaUsuario.add(user);

                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();

        }catch(SQLException error){
            error.getMessage();
        }
        return carregarListaUsuario;
    }


    public Usuario getDados(long id){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Usuario usuarioRecebido = new Usuario();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            usuarioRecebido.setUsuario(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO)));
            usuarioRecebido.setSenha(cursor.getString(cursor.getColumnIndex(COLUMN_SENHA)));
        }
        cursor.close();
        db.close();
        return usuarioRecebido;
    }
}
