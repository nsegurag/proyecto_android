package com.ns.app_proyecto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class ConfiguracionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        // Obtener el spinner del layout
        Spinner themeSpinner = findViewById(R.id.theme_spinner);

        // Cargar la preferencia guardada y aplicar el tema
        loadSavedTheme(themeSpinner);

        // Configurar el listener para el spinner
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, android.view.View selectedItemView, int position, long id) {
                // Obtener la opción seleccionada
                String selectedTheme = getResources().getStringArray(R.array.theme_values)[position];

                // Guardar la preferencia en SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ConfiguracionActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("theme_preference", selectedTheme);
                editor.apply();

                // Aplicar el tema seleccionado
                applyTheme(selectedTheme);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No hacer nada si no se selecciona nada
            }
        });
    }

    private void loadSavedTheme(Spinner themeSpinner) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String themePreference = sharedPreferences.getString("theme_preference", "default");

        // Aplicar el tema guardado
        applyTheme(themePreference);

        // Establecer la selección del spinner
        String[] themeValues = getResources().getStringArray(R.array.theme_values);
        for (int i = 0; i < themeValues.length; i++) {
            if (themeValues[i].equals(themePreference)) {
                themeSpinner.setSelection(i);
                break;
            }
        }
    }

    private void applyTheme(String themePreference) {
        switch (themePreference) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "default":
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }
}
