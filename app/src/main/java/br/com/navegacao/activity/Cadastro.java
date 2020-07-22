package br.com.navegacao.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.com.navegacao.R;
import br.com.navegacao.domain.DBHelper;

public class Cadastro extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private Button cadastrar;
    private Button cancelar;
    private DBHelper dbHelper;
    private EditText telefone;
    private EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        usuario = (EditText)findViewById(R.id.usuario);
        senha = (EditText)findViewById(R.id.senha);
        telefone = (EditText)findViewById(R.id.telefone);
        email = (EditText)findViewById(R.id.email);
        cadastrar = (Button)findViewById(R.id.botaoCadastrarUsuario);
        cancelar = (Button)findViewById(R.id.botaoCancelarCadastro);


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCadastro();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cadastro.super.onBackPressed();
//                Intent intent = new Intent(Cadastro.this, Login.class);
//                startActivity(intent);
            }
        });
    }

    public void salvarCadastro(){

        String user = usuario.getText().toString();
        String pass = senha.getText().toString();
        String phone = telefone.getText().toString();
        String mail = email.getText().toString();

        dbHelper = new DBHelper(this);
        Usuario usuarioLogin = new Usuario();

        usuarioLogin.setUsuario(usuario.getText().toString());
        usuarioLogin.setSenha(senha.getText().toString());
        usuarioLogin.setTelefone(telefone.getText().toString());
        usuarioLogin.setEmail(email.getText().toString());


        if(!user.isEmpty() && !pass.isEmpty() && !phone.isEmpty() && !mail.isEmpty() && pass.length() >= 4) {
            dbHelper.create(usuarioLogin);

            goBackMain();
            toast("Usuário: (" + usuarioLogin.getUsuario() + ") cadastrado(a)");
            
        }else{
            AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this, R.style.MyDialogTheme);
            adb.setTitle("Atenção");
            adb.setMessage("Preencher todos os dados! \n" + "A senha deve conter 4 caracteres!");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            adb.show();
            usuario.setHintTextColor(Color.RED);
            senha.setHintTextColor(Color.RED);
            telefone.setHintTextColor(Color.RED);
            email.setHintTextColor(Color.RED);
            usuario.requestFocus();
        }
    }

    public void goBackMain(){
        Intent intent = new Intent(Cadastro.this, Login.class);
        startActivity(intent);
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}