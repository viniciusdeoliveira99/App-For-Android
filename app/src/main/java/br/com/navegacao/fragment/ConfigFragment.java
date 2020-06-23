package br.com.navegacao.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Objects;

import br.com.navegacao.R;
import br.com.navegacao.util.PrefsUtils;

import static android.content.Context.MODE_PRIVATE;

public class ConfigFragment extends BaseFragment{

    private Switch aSwitch;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_config, container, false);
        aSwitch = (Switch)view.findViewById(R.id.aswitch);
		
		// Saving state of our app
        // using SharedPreferences

        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        final boolean isChecked = sharedPreferences.getBoolean("isChecked", false);


        if (isDarkModeOn || isChecked) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
                    aSwitch.setChecked(true);
        }else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }

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
                }
            }
        });
        return view;
    }
}
