package br.com.navegacao.activity;

import android.content.Context;
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

import br.com.navegacao.R;
import br.com.navegacao.domain.DBHelper;

public class Login extends AppCompatActivity {

    private static final String TAG = "TESTE";
    private Button button;
    private EditText usuario;
    private EditText senha;
    private DBHelper dbHelper;
    private Usuario usuarioLogin;
    private List<Usuario> usuarioList;

    UserSession session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //CRIANDO SESSÃO DO USUARIO
        session = new UserSession(getApplicationContext());

        button = (Button) findViewById(R.id.login);
        usuario = (EditText) findViewById(R.id.user);
        senha = (EditText) findViewById(R.id.pass);


        //CRIANDO TABELA DE DADOS ACESSO
        dbHelper = new DBHelper(this);
        usuarioList = dbHelper.buscarDados();


        //PREFERÊNCIAS MODO NOTURNO
        SharedPreferences shPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final boolean isDarkModeOn = shPreferences.getBoolean("isDarkModeOn", false);
        final boolean isChecked = shPreferences.getBoolean("isChecked", false);

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

        if(preferences.contains("checked") && preferences.getBoolean("checked",false) && preferences.contains("Name") && preferences.contains("txtPassword")) {
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

        if (!user.isEmpty() && !pass.isEmpty() && pass.length() >= 4) {

            for (int i = 0; i < usuarioList.size(); i++) {

                usuarioLogin = usuarioList.get(i);

                if (user.equalsIgnoreCase(usuarioLogin.getUsuario()) && pass.equalsIgnoreCase(usuarioLogin.getSenha())) {                   
                    getUserData(usuarioLogin.getId());
					
					

                }else{
                    AlertDialog.Builder adb = new AlertDialog.Builder(Login.this, R.style.MyDialogTheme);
                    adb.setTitle("Atenção!");
                    adb.setMessage("Dados incorretos!");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();
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

    //MÉTODO PARA OBTER DADOS DO USUÁRIO
    private void getUserData(long usuarioId){
        Intent goToMain = new Intent(this, MainActivity.class);
        goToMain.putExtra("USER_ID", usuarioId);
        this.startActivity(goToMain);
    }
}
