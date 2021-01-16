package com.alexander_rodriguez.mihogar.mainactivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.menuPrincipal.MenuPricipal;
import com.alexander_rodriguez.mihogar.registrarcasa.RegistrarCasaActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends BaseActivity<Interface.presenter> implements Interface.view {
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "errorLoginWithGoogle";
    private EditText etUser;
    private EditText etPass;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void iniciarComandos() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        SignInButton signInButton = findViewById(R.id.sigin_google);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this::signInWithGoogle);
    }

    private void updateUI(GoogleSignInAccount account) {

    }

    @Override
    protected void onResume() {
        super.onResume();

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
            firebaseAuthWithGoogle(account);
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        presenter.signInWithGoogle(account);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected Interface.presenter createPresenter() {
        return new Presenter(this);
    }

    protected void iniciarViews(){
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
    }

    public void ocForgotPassword(View view){
        startActivity(new Intent(this, RegistrarCasaActivity.class));
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
        etUser.setText(s);
    }

    public void signIn(View view){
        presenter.signIn(etUser.getText().toString(), etPass.getText().toString());
    }

    public void signInWithGoogle(View view){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}

/*
public class MusicIntentReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG))
        {
            int state = intent.getIntExtra("state", -1);
            switch (state)
            {
                case 0:
                    Utilidades.mostrarToastText(context, "Auricular conectado");
                    break;
                case 1:
                    Utilidades.mostrarToastText(context, "Auricular desconectado");
                    break;
                default:
                    Utilidades.mostrarToastText(context, "Estado desconocido");
                    break;
            }
        }
    }
}*/