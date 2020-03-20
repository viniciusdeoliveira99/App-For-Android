package br.com.navegacao;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.navegacao.domain.PersonDBHelper;


public class AddUsuario extends AppCompatActivity {

    private EditText nomeUsuario;
    private EditText idadeUsuario;
    private EditText ocupacaoUsuario;
    private Button botaoAdd;
    private Button botaoCancelar;
    private PersonDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        //init
        nomeUsuario = (EditText)findViewById(R.id.addNome);
        idadeUsuario = (EditText)findViewById(R.id.addIdade);
        ocupacaoUsuario = (EditText)findViewById(R.id.addOcupacao);
        botaoAdd = (Button)findViewById(R.id.addUsuario);
        botaoCancelar = (Button) findViewById(R.id.botaoCancelar);

        //listen to add button click
        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {               
                savePerson();
            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUsuario.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void savePerson(){
        String nome = nomeUsuario.getText().toString();
        String idade = idadeUsuario.getText().toString();
        String ocupacao = ocupacaoUsuario.getText().toString();
        dbHelper = new PersonDBHelper(this);

        if(nome.isEmpty()){
            AlertDialog.Builder adb = new AlertDialog.Builder(AddUsuario.this);
            adb.setTitle("Atenção");
            adb.setMessage("Necessário inserir um nome!");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            adb.show();
        }else if(idade.isEmpty()){
            AlertDialog.Builder adb = new AlertDialog.Builder(AddUsuario.this);
            adb.setTitle("Atenção");
            adb.setMessage("Necessário inserir uma idade!");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            adb.show();
        }else if(ocupacao.isEmpty()){
            AlertDialog.Builder adb = new AlertDialog.Builder(AddUsuario.this);
            adb.setTitle("Atenção");
            adb.setMessage("Necessário inserir uma ocupação!");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            adb.show();
        }else{
            //create new person
            PessoaAcesso person = new PessoaAcesso(nome, idade, ocupacao);
            dbHelper.salvarCadastro(person);
            toast("Usuário: " + person.getNome() + " add.");
            goBackHome();
        }
    }

    private void goBackHome(){
        startActivity(new Intent(AddUsuario.this, MainActivity.class));
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
