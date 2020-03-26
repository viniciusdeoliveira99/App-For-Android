package br.com.navegacao;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.navegacao.domain.DBHelper;

public class Login extends AppCompatActivity {

    private Button button;
    private EditText usuario;
    private EditText senha;
    private TextView inserir;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.login);
        usuario = (EditText) findViewById(R.id.user);
        senha = (EditText) findViewById(R.id.pass);
        inserir = (TextView)findViewById(R.id.inserir);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });

        inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent);
            }
        });
    }

    public void logar() {
        String user = usuario.getText().toString();
        String pass = senha.getText().toString();

        dbHelper = new DBHelper(this);

        if (!user.isEmpty() && !pass.isEmpty() && pass.length() >= 4) {
            List<PessoaAcesso> lista = dbHelper.buscarDados();
            for (int i = 0; i < lista.size(); i++) {
                PessoaAcesso pessoaAcesso = (PessoaAcesso) lista.get(i);

                if (user.equals(pessoaAcesso.getUsuario()) && pass.equals(pessoaAcesso.getSenha())){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    toast("Bem-vindo(a) " + pessoaAcesso.getUsuario());

                }else{
                    AlertDialog.Builder adb = new AlertDialog.Builder(Login.this);
                    adb.setTitle("ERRO");
                    adb.setMessage("Dados incorretos ou não existem!");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();
                    erro();
                }
            }

        }else {
            AlertDialog.Builder adb = new AlertDialog.Builder(Login.this);
            adb.setTitle("Atenção!");
            adb.setMessage("Preencher usuario e senha"
                    + "\nA senha deve conter 4 caracteres!");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            adb.show();
            erro();
        }
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void erro(){
        usuario.setText("");
        senha.setText("");
        usuario.setHintTextColor(Color.RED);
        senha.setHintTextColor(Color.RED);
        usuario.requestFocus();
    }
}
