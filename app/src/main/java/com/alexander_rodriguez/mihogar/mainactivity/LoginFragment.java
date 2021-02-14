package com.alexander_rodriguez.mihogar.mainactivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alexander_rodriguez.mihogar.R;
import com.google.android.gms.common.SignInButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText etUser;
    private EditText etPass;
    private SignInButton btSignIn;

    private View view;

    private Interface mInterface;

    public LoginFragment(Interface mInterface) {
        this.mInterface = mInterface;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(Interface mInterface) {
        return new LoginFragment(mInterface);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        btSignIn = view.findViewById(R.id.sigin_google);
        btSignIn.setSize(SignInButton.SIZE_STANDARD);
        etUser = view.findViewById(R.id.etUser);
        etPass = view.findViewById(R.id.etPass);
        mInterface.onCreate(view);
        return view;
    }

    public Data getData(){
        return new Data(etUser.getText().toString(), etPass.getText().toString());
    }

    public SignInButton getBtSignIn() {
        return btSignIn;
    }

    public void setEtUserText(String s) {
        etUser.setText(s);
    }

    public static class Data{
        String email;
        String password;

        public Data(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    interface Interface{
        void onCreate(View v);
    }
}