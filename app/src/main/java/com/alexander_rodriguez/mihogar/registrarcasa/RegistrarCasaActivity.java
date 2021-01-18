package com.alexander_rodriguez.mihogar.registrarcasa;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.menuPrincipal.MenuPricipal;
import com.alexander_rodriguez.mihogar.registrarcasa.details.DetailsView;
import com.alexander_rodriguez.mihogar.registrarcasa.register.RegisterView;

public class RegistrarCasaActivity extends BaseActivity<interfaz.presentador> implements interfaz.view {
    public static final String EXTRA_ONLY_DETAILS = "onlyDetails";
    public static final String EXTRA_MODE = "mode";
    public static final String EXTRA_NEW_USER = "newUser";
    private RegisterView register;
    private DetailsView details;
    private LinearLayout llContainer;
    private Button btNext;
    private TextView tvSubtitle;
    @Override
    protected void iniciarComandos() {
        setTitle("Registro");
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_registrar_casa;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @NonNull
    @Override
    protected interfaz.presentador createPresenter() {
        return new Presentador(this, getIntent());
    }

    @Override
    protected void iniciarViews() {
        llContainer = findViewById(R.id.llContainer);
        btNext = findViewById(R.id.btNext);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        /*
        etCountry = findViewById(R.id.etCountry);
        etRegion = findViewById(R.id.etRegion);
        etAddress = findViewById(R.id.etAddress);
        etZipCode = findViewById(R.id.etZipCode);
        */

        /*Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ip-api.com/json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this::getLocationResponse, this::getLocationErrorResponse);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);*/
    }
/*
    private void getLocationResponse(JSONObject jsonObject) {
        try {
            Geolocation geolocation = new Geolocation(jsonObject);
            if (geolocation.isSuccess()){
                etCountry.setText(geolocation.getCountry());
                etRegion.setText(geolocation.getCity());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getLocationErrorResponse(VolleyError error) {

    }*/

    @Override
    public void salir(){
        //finish();
    }

    @Override
    public void avanzar() {
        startActivity(new Intent(this, MenuPricipal.class));
    }

    @Override
    public void onlyDetailsMode() {
        tvSubtitle.setText(R.string.sDetails);
        btNext.setText(R.string.sGuardar);
        if(register != null) {
            llContainer.removeView(register);
            register = null;
        }
        details = (DetailsView) getLayoutInflater().inflate(R.layout.fragment_details, llContainer, false);
        llContainer.addView(details, 3);
    }

    @Override
    public void newUserMode() {
        tvSubtitle.setText(R.string.sRegistry);
        btNext.setText(R.string.sRegistrar);
        register = (RegisterView) getLayoutInflater().inflate(R.layout.fragment_register, llContainer, false);
        llContainer.addView(register, 3);
    }

    public void ocNext(View view) {
        presenter.ingresar(register, details);
    }

}
