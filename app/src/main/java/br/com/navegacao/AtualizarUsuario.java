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


public class AtualizarUsuario extends AppCompatActivity {

    private EditText usuarioNome;
    private EditText usuarioIdade;
    private EditText usuarioOcupacao;
    private Button botaoAtualizar;
    private Button botaoCancelar;

    private PersonDBHelper dbHelper;
    private long receivedPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);

        //init
        usuarioNome = (EditText)findViewById(R.id.atualizaNome);
        usuarioIdade = (EditText)findViewById(R.id.atualizaIdade);
        usuarioOcupacao = (EditText)findViewById(R.id.atualizaOcupacao);
        botaoAtualizar = (Button)findViewById(R.id.botaoAtualizar);
        botaoCancelar = (Button) findViewById(R.id.botaoCancelar);

        dbHelper = new PersonDBHelper(this);

        try {
            //get intent to get person id
            receivedPersonId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PessoaAcesso queriedPerson = dbHelper.getPerson(receivedPersonId);
        //set field to this user data
        usuarioNome.setText(queriedPerson.getNome());
        usuarioIdade.setText(queriedPerson.getIdade());
        usuarioOcupacao.setText(queriedPerson.getOcupacao());


        //listen to add button click to update
        botaoAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updatePerson();
            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AtualizarUsuario.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updatePerson(){
        String nome = usuarioNome.getText().toString();
        String idade = usuarioIdade.getText().toString();
        String ocupacao = usuarioOcupacao.getText().toString();

        if(nome.isEmpty()){
            AlertDialog.Builder adb = new AlertDialog.Builder(AtualizarUsuario.this);
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
            AlertDialog.Builder adb = new AlertDialog.Builder(AtualizarUsuario.this);
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
            AlertDialog.Builder adb = new AlertDialog.Builder(AtualizarUsuario.this);
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
            PessoaAcesso updatedPerson = new PessoaAcesso(nome, idade, ocupacao);
            dbHelper.atualizarCadastro(receivedPersonId, this, updatedPerson);
            toast("Usuário: " + updatedPerson.getNome() + " atualizado com sucesso!");
            goBackHome();
        }
    }

    private void goBackHome(){
        startActivity(new Intent(AtualizarUsuario.this, MainActivity.class));
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
