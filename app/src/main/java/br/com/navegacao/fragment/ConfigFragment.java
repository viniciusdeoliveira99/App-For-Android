package br.com.navegacao.fragment;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Objects;

import br.com.navegacao.R;

import static android.content.Context.MODE_PRIVATE;

public class ConfigFragment extends BaseFragment{

    private Switch aSwitch;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_config, container, false);
		
        aSwitch = (Switch)view.findViewById(R.id.aswitch);

		SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences("isDarkModeOn", MODE_PRIVATE);
		final SharedPreferences.Editor editor = preferences.edit();
        final boolean isDarkModeOn = preferences.getBoolean("isDarkModeOn", false);

        //CLICK BOTÃO NIGHT MODE
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDarkModeOn) {
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();

                }else {
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES);

                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();
                    aSwitch.setChecked(true);
                }
            }
        });

        return view;
    }
}
