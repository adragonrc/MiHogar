package com.alexander_rodriguez.mihogar.mainactivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.forgot_password.ForgotPasswordFragment;
import com.alexander_rodriguez.mihogar.menu_main.MenuPricipal;
import com.alexander_rodriguez.mihogar.registrarcasa.RegistrarCasaActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;

public class MainActivity extends BaseActivity<Interface.presenter> implements Interface.view, LoginFragment.Interface {
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "errorLoginWithGoogle";
    private GoogleSignInClient mGoogleSignInClient;
    private LoginFragment loginFragment;

    @Override
    protected void iniciarComandos() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
/*
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            presenter.signInWithGoogle(account);
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected Interface.presenter createPresenter() {
        FirebaseApp.initializeApp(this);
        return new Presenter(this);
    }

    protected void iniciarViews(){

    }

    @Override
    public void showLogin(){
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        loginFragment = LoginFragment.newInstance(this);//the fragment you want to show

        fragmentTransaction
                .replace(R.id.layout, loginFragment);//R.id.content_frame is the layout you want to replace
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreate(View v) {
        //loginFragment.setEtUserText(presenter.getUser());
        loginFragment.getBtSignIn().setOnClickListener(this::signInWithGoogle);
    }

    public void ocForgotPassword(View view){
        presenter.ocForgotPassword();
    }

    public void ocSignUp(View view){
        /*
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment profileFragment = RegisterFragment.newInstance(RegisterFragment.EXTRA_ONLY_DETAILS);//the fragment you want to show

        fragmentTransaction
                .replace(R.id.layout, profileFragment);//R.id.content_frame is the layout you want to replace
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        */
        goToRegister(RegistrarCasaActivity.EXTRA_NEW_USER);
    }
    @Override
    public void ingresar() {
        startActivity(new Intent(this, MenuPricipal.class));
        finish();
    }

    @Override
    public void negarIngreso() {
        showMessage("Autenticacion fallida");
    }

    @Override
    public void setID(String s){
        loginFragment.setEtUserText(s);
    }

    @Override
    public void goToRegister(String mode) {
        Intent i = new Intent(this, RegistrarCasaActivity.class);
        i.putExtra(RegistrarCasaActivity.EXTRA_MODE, mode);
        startActivity(i);
    }

    @Override
    public void showFragment(ForgotPasswordFragment forgotPass, String name) {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, forgotPass).addToBackStack("").commit();

    }

    public void signIn(View view){
        LoginFragment.Data data = loginFragment.getData();
        presenter.signIn(data.getEmail(), data.getPassword());
    }

    public void signInWithGoogle(View view){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}

/*
public static String getCountryDialCode(){
    String contryId = null;
    String contryDialCode = null;

    TelephonyManager telephonyMngr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

    contryId = telephonyMngr.getSimCountryIso().toUpperCase();
    String[] arrContryCode=this.getResources().getStringArray(R.array.DialingCountryCode);
    for(int i=0; i<arrContryCode.length; i++){
        String[] arrDial = arrContryCode[i].split(",");
        if(arrDial[1].trim().equals(CountryID.trim())){
            contryDialCode = arrDial[0];
            break;
        }
    }
    return contryDialCode;
}
}*/