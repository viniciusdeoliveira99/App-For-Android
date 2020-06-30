package br.com.navegacao;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

import br.com.navegacao.domain.DBHelper;

public class Login extends AppCompatActivity {

    private static final String TAG = "TESTE";
    private Button button;
    private EditText usuario;
    private EditText senha;
    private DBHelper dbHelper;
    private Usuario usuarioLogin;
    private Usuario queriedUser;

    private long receivedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.login);
        usuario = (EditText) findViewById(R.id.user);
        senha = (EditText) findViewById(R.id.pass);

        dbHelper = new DBHelper(this);

        try {
            //get intent to get person id
            receivedUserId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Usuario queriedUser = dbHelper.getUser(receivedUserId);


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

        //CHECKBOX PARA MANTER USUÁRIO LOGADO
        final CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        SharedPreferences preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor ed = preferences.edit();

        if(preferences.contains("checked") && preferences.getBoolean("checked",false)) {
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()) {
                    ed.putBoolean("checked", true);
                    ed.apply();
                }else{
                    ed.putBoolean("checked", false);
                    ed.apply();
                }
            }
        });

        //BOTÃO LOGAR
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });
    }


    //MÉTODO LOGAR DO APP
    public void logar() {
        String user = usuario.getText().toString();
        String pass = senha.getText().toString();

        dbHelper = new DBHelper(this);

        if (!user.isEmpty() && !pass.isEmpty() && pass.length() >= 4) {

            List<Usuario> lista = dbHelper.buscarDados();

            for (int i = 0; i < lista.size(); i++) {

                usuarioLogin = lista.get(i);

                if (user.equals(usuarioLogin.getUsuario()) && pass.equals(usuarioLogin.getSenha())){
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                    Log.d(TAG, "ID: " + queriedUser);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_superior, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.criarUsuario) {
            startActivity(new Intent(Login.this, Cadastro.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //ERROR ALERT
    public void errorAlert(){
        usuario.setText("");
        senha.setText("");
        usuario.setHintTextColor(Color.RED);
        senha.setHintTextColor(Color.RED);
        usuario.requestFocus();
    }
}
