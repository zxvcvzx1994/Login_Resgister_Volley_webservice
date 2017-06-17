package com.example.kh.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.kh.myapplication.Module.View.Fragment_Login;
import com.example.kh.myapplication.Module.View.Fragment_Register;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.txtRegister)
   public  TextView txtRegister;
    private Fragment_Login fragment_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(fragment_login==null) {
            fragment_login = new Fragment_Login();
            getSupportFragmentManager().beginTransaction().add(R.id.lineFragment,fragment_login,"MyFragment").commit();
        }else{
          fragment_login= (Fragment_Login) getSupportFragmentManager().findFragmentByTag("MyFragment");
        }

    }

    @OnClick(R.id.txtRegister)
    public void register( ){
        Fragment_Register fragment_register = new Fragment_Register();
        getSupportFragmentManager().beginTransaction().replace(R.id.lineFragment, fragment_register,"Fragment_Register" ).commit();
        txtRegister.setVisibility(android.view.View.GONE);
    }



}
