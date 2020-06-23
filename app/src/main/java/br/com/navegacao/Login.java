package br.com.navegacao;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

import br.com.navegacao.domain.DBHelper;

public class Login extends AppCompatActivity {

    private Button button;
    private EditText usuario;
    private EditText senha;
    private TextView inserir;
    private DBHelper dbHelper;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.login);
        usuario = (EditText) findViewById(R.id.user);
        senha = (EditText) findViewById(R.id.pass);
        inserir = (TextView)findViewById(R.id.inserir);
        checkBox = (CheckBox)findViewById(R.id.check);


        //PREFERÊNCIAS MODO NOTURNO
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        final boolean isChecked = sharedPreferences.getBoolean("isChecked", false);

        if (isDarkModeOn || isChecked) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }

//        //PREFERÊNCIAS CHECKBOX
//        SharedPreferences preferences = getSharedPreferences("prefsShared", MODE_PRIVATE);
//        final SharedPreferences.Editor editor = preferences.edit();
//        final boolean checked = preferences.getBoolean("checked", false);
//
//        if(checked){
//            editor.putBoolean("checked", true);
//            editor.apply();
//        }else{
//            editor.putBoolean("checked", false);
//            editor.apply();
//        }
//
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(checked){
//                    editor.putBoolean("checked", false);
//                    editor.apply();
//
//                }else{
//                    editor.putBoolean("checked", true);
//                    editor.apply();
//                    checkBox.setChecked(true);
//                }
//            }
//        });


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
                    toast("Bem-vindo(a) (" + pessoaAcesso.getUsuario().toUpperCase() + ")");


                }else{
                    AlertDialog.Builder adb = new AlertDialog.Builder(Login.this, R.style.MyDialogTheme);
                    adb.setTitle("ERRO");
                    adb.setMessage("Dados incorretos!");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();
                    errorAlert();
                }
            }

        }else {
            AlertDialog.Builder adb = new AlertDialog.Builder(Login.this, R.style.MyDialogTheme);
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
            errorAlert();
        }
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void errorAlert(){
        usuario.setText("");
        senha.setText("");
        usuario.setHintTextColor(Color.RED);
        senha.setHintTextColor(Color.RED);
        usuario.requestFocus();
    }
}
