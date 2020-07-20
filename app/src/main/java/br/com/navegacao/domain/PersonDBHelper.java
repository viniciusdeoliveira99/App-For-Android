package br.com.navegacao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import br.com.navegacao.activity.PessoaAcesso;

public class PersonDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pessoas.db";
    private static final int DATABASE_VERSION = 1;
    public static final String NOME_TABELA = "Pessoas";
    public static final String COLUMN_ID = "_id";
    public static final String NOME = "nome";
    public static final String IDADE = "idade";
    public static final String OCUPACAO = "ocupacao";


    public PersonDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + NOME_TABELA + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                NOME + " TEXT NOT NULL, " +
                IDADE + " NUMBER NOT NULL, " +
                OCUPACAO + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);
        this.onCreate(db);
    }


    public void salvarCadastro(PessoaAcesso pessoaAcesso) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NOME, pessoaAcesso.getNome());
            values.put(IDADE, pessoaAcesso.getIdade());
            values.put(OCUPACAO, pessoaAcesso.getOcupacao());
            // insert
            db.insert(NOME_TABELA,null, values);
            db.close();
        }catch (SQLException error){
            error.getMessage();
        }
    }

    /**Query records, give options to filter results**/
    public List<PessoaAcesso> peopleList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + NOME_TABELA;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + NOME_TABELA + " ORDER BY "+ filter;
        }

        List<PessoaAcesso> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        PessoaAcesso pessoaAcesso;

        if (cursor.moveToFirst()) {
            do {
                pessoaAcesso = new PessoaAcesso();

                pessoaAcesso.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                pessoaAcesso.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                pessoaAcesso.setIdade(cursor.getString(cursor.getColumnIndex(IDADE)));
                pessoaAcesso.setOcupacao(cursor.getString(cursor.getColumnIndex(OCUPACAO)));
                personLinkedList.add(pessoaAcesso);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return personLinkedList;
    }

	
	//LISTA OS DADOS
	public List<PessoaAcesso> cadastroList() {
        String query;
        query = "SELECT * FROM " + NOME_TABELA;

        List<PessoaAcesso> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        PessoaAcesso pessoaAcesso;

        if (cursor.moveToFirst()) {
            do {
                pessoaAcesso = new PessoaAcesso();
                pessoaAcesso.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                pessoaAcesso.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                pessoaAcesso.setIdade(cursor.getString(cursor.getColumnIndex(IDADE)));
                pessoaAcesso.setOcupacao(cursor.getString(cursor.getColumnIndex(OCUPACAO)));
                personLinkedList.add(pessoaAcesso);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return personLinkedList;
    }
	

    /**Query only 1 record**/
    public PessoaAcesso getPerson(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + NOME_TABELA + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        PessoaAcesso receivedPerson = new PessoaAcesso();
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();

            receivedPerson.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            receivedPerson.setIdade(cursor.getString(cursor.getColumnIndex(IDADE)));
            receivedPerson.setOcupacao(cursor.getString(cursor.getColumnIndex(OCUPACAO)));
        }
        cursor.close();
        db.close();
        return receivedPerson;
    }


    /**delete record**/
    public void deletarCadastro(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+NOME_TABELA+" WHERE _id='"+id+"'");
        db.close();
    }

    /**update record**/
    public void atualizarCadastro(long personId, Context context, PessoaAcesso updatedperson) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE  "+NOME_TABELA+" SET nome ='"+ updatedperson.getNome() + "', idade ='"
                + updatedperson.getIdade()+ "', ocupacao ='"+ updatedperson.getOcupacao()
                + "'  WHERE _id='" + personId + "'");
        db.close();
    }
}
