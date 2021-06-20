package com.example.cha;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    Button login,signup,forgot;
    TextInputEditText username,password;
    String[] sField,sData;
    LoginCallBack loginCallBack;
    public void setLoginCallBack(LoginCallBack loginCallBack)
    {
        this.loginCallBack = loginCallBack;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_login,container,false);
        //set Title
        if(loginCallBack!=null)
        {
            loginCallBack.setTitle("login");
            Log.i("login","title");
        }

        login = v.findViewById(R.id.login);
        signup = v.findViewById(R.id.signup);
        forgot = v.findViewById(R.id.forgot);
        username = v.findViewById(R.id.un_input);
        password = v.findViewById(R.id.pass_input);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sField = new String[2];
                sData = new String[2];
                sField[0] = "username";
                sField[1] = "password";
                sData[0] = String.valueOf(username.getText());
                sData[1] = String.valueOf(password.getText());
                //login task
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        Database database = new Database("http://192.168.244.209/hunt/Login.php", sField, sData);
                        if(database.onStart())
                        {
                            if(database.onComp())
                            {
                                String temp = database.getData();
                                if(temp.equals("Login Success"))
                                {
                                    if(loginCallBack!=null)
                                    {
                                        Toast.makeText(getContext(),temp,Toast.LENGTH_SHORT).show();
                                        loginCallBack.setData(sData[0]);
                                        loginCallBack.callBacks("intent");
                                    }
                                }
                                else
                                {
                                    Toast.makeText(v.getContext(),temp,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginCallBack!=null){
                    loginCallBack.callBacks("signup");
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginCallBack!=null){
                    loginCallBack.callBacks("forgot");
                }
            }
        });
        return v;
    }
}
