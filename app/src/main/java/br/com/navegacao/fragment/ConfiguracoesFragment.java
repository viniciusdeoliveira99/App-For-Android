package br.com.navegacao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import br.com.navegacao.R;

public class ConfiguracoesFragment extends BaseFragment{

    private Switch sw;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.config_fragment, container, false);

        sw = (Switch)view.findViewById(R.id.configReceberEmail);

        Boolean state = sw.isChecked();

        if(state){
            toast("True");
        }

        return view;
    }

    public void toast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
