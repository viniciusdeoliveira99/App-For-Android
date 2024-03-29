package br.com.navegacao.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import br.com.navegacao.R;
import br.com.navegacao.domain.DBHelper;
import br.com.navegacao.fragment.CadastroFragment;
import br.com.navegacao.fragment.ConfigFragment;

public class MainActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected Toolbar toolbar;
    protected DBHelper dbHelper;
    protected NavigationView navView;
    private long receivedPersonId;

    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new UserSession(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        try {
            //get intent to get person id
            receivedPersonId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        SharedPreferences preferences = getSharedPreferences("userPreferences", MODE_PRIVATE);
//        final boolean marcado = preferences.getBoolean("marcadoCheck", false);
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        //CHAMA MÉTODO DO DRAWER LAYOUT
        setupNavDrawer();


        //ADD O FRAGMENT NA TELA
        if (savedInstanceState == null) {
            CadastroFragment frag = new CadastroFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragLayout, frag).commit();
        }
    }


    protected void setupNavDrawer() {
        toolbar.setNavigationIcon(R.drawable.ic_menu_actual);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });


        if (drawerLayout != null) {
            
            //NAV VIEW HEADER
            dbHelper = new DBHelper(this);
            Usuario queriedUsuario = dbHelper.obterUsuario(receivedPersonId);
            
            View headerView = navView.getHeaderView(0);
            TextView usuarioLogado = headerView.findViewById(R.id.usuarioLogado);
            TextView usuarioEmail = headerView.findViewById(R.id.usuarioEmail);

            usuarioLogado.setText(queriedUsuario.getUsuario());
            usuarioEmail.setText(queriedUsuario.getEmail());

            navView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            menuItem.setChecked(true);
                            closeDrawer();
                            // Trata o evento do menu
                            onNavDrawerItemSelected(menuItem);
                            return true;
                        }
                    });
        }
    }


    // Trata o evento do menu lateral
    private void onNavDrawerItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            //MENU HOME
            case R.id.nav_home:
                replaceFragment(new CadastroFragment());
                break;

            //MENU CONFIGURAÇÕES
            case R.id.nav_tools:
                replaceFragment(new ConfigFragment());
                break;

            //MENU SAIR
            case R.id.nav_exit:
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme);
                adb.setTitle("Confirmação");
                adb.setMessage("Deseja sair do sistema?");
                adb.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this, Login.class));
                        finish();
                        session.logoutUser();
                    }
                });
                adb.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_about){
            AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme);
            adb.setTitle("Sobre");
            adb.setMessage("Sistema versão 1.0");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            adb.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Abre o menu lateral
    protected void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // Fecha o menu lateral
    protected void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void replaceFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragLayout, frag).commit();
    }
}