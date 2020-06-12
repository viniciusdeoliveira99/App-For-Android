package br.com.navegacao;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class ConfiguracoesActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        // Adiciona o fragment de configurações
        if (savedInstanceState == null) {
            PrefsFragment pref = new PrefsFragment();
            pref.setArguments(getIntent().getExtras());

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            //transaction.replace(R.id.container, pref);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }

    // Fragment que carrega o layout com as configurações
    public static class PrefsFragment extends android.preference.PreferenceFragment {
        public void onCreatePreferences(Bundle bundle, String s) {
            // Carrega as configurações
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}