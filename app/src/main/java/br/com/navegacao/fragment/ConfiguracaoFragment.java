package br.com.navegacao.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import br.com.navegacao.R;
import br.com.navegacao.util.PrefsUtils;

public class ConfiguracaoFragment extends android.preference.PreferenceFragment{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //boolean b = PrefsUtils.isCheckPushOn(getContext());
    }
}
