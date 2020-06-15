package br.com.navegacao.fragment;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import br.com.navegacao.R;

public class ConfigFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
