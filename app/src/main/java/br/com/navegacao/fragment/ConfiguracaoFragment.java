package br.com.navegacao.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import br.com.navegacao.R;

public class ConfiguracaoFragment extends Fragment {

    protected Switch aSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_configuracao, container, false);
        aSwitch = (Switch) view.findViewById(R.id.alertaEmail);
        return view;
    }
}
