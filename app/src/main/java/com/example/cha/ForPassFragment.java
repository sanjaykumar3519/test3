package com.example.cha;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;

public class ForPassFragment extends Fragment {

    LoginCallBack loginCallBack;
    TextInputEditText un;
    Button submit,backToLogin;
    ProgressBar progressBar;
    public void setLoginCallBack(LoginCallBack loginCallBack)
    {
        this.loginCallBack = loginCallBack;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_forpass,container,false);
        //progress bar
        progressBar = v.findViewById(R.id.progress);
        submit = v.findViewById(R.id.fp_but);
        un = v.findViewById(R.id.input_username);
        //set title
        loginCallBack.setTitle("Forgot");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit();
            }
        });
        backToLogin = v.findViewById(R.id.fp_login);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginCallBack!=null)
                {
                    loginCallBack.pop();
                }
            }
        });
        return v;
    }

    public void onClickSubmit()
    {
        progressBar.setVisibility(View.VISIBLE);
        String sData = String.valueOf(un.getText());
        String sField = "check_user";
        if(!sData.isEmpty())
        {
            //setting server address
            StringBuilder link = new StringBuilder();
            link.append("http://").append(SplashScreen.ip.getString("ip","none")).append("/hunt/fp.php");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Database database = new Database(String.valueOf(link),sField,sData);
                    if(database.onStart())
                    {
                        if(database.onComp())
                        {
                            String temp = database.getData();
                            if(temp.equals("true"))
                            {
                                if(loginCallBack!=null)
                                {
                                    Toast.makeText(getContext(),"username Found",Toast.LENGTH_SHORT).show();
                                    loginCallBack.setData(sData);
                                    loginCallBack.callBacks("reset");
                                }
                            }
                            else
                            {
                                Toast.makeText(getContext(),temp,Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
        }
        else
        {
            Toast.makeText(requireContext(),"Enter username",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }

    }

}