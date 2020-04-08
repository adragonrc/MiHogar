package com.alexander_rodriguez.mihogar;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class Preferencias extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private AppCompatDelegate mDelegate;

    protected void onCreate(Bundle saveInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(saveInstanceState);
        super.onCreate(saveInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenciasFragment()).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }


    public ActionBar getSupportActionBar(){
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar){
        getDelegate().setSupportActionBar(toolbar);
    }


    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view,params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    public void invalidateOptionsMenu(){
        getDelegate().invalidateOptionsMenu();
    }
    public AppCompatDelegate getDelegate(){
        if (mDelegate == null){
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    public static class MyPreferenciasFragment extends PreferenceFragment {

        private CheckBoxPreference cpCasa;
        private CheckBoxPreference cpHotel;
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);


            cpCasa =(CheckBoxPreference) findPreference(getString(R.string.cpCasa));
            cpHotel =(CheckBoxPreference) findPreference(getString(R.string.cpHotel));

            cpCasa.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    /*if ((Boolean)newValue){
                        cpHotel.setChecked(false);
                        findPreference("psModHotel").setEnabled(false);
                    }else{
                        cpHotel.setChecked(true);
                        findPreference("psModHotel").setEnabled(true);
                    }
                     */
                    return false;
                }
            });
            cpHotel.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    /*if ((Boolean)newValue){
                        cpCasa.setChecked(false);
                        findPreference("psModHotel").setEnabled(true);
                    }else{
                        cpCasa.setChecked(true);
                        findPreference("psModHotel").setEnabled(false);
                    }*/
                    return false;
                }
            });
        }
    }

}
