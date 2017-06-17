package com.example.kh.myapplication.Module.View;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kh.myapplication.MainActivity;
import com.example.kh.myapplication.Module.MyVolley;
import com.example.kh.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Register extends Fragment {
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPasswordRetype)
    EditText etPasswordRetype;
    @BindView(R.id.etMail)
    EditText etMail;
    private MainActivity mainActivity;
    private String url  ="http://192.168.1.10/DuLieu/register_data.php";
    private AlertDialog.Builder builder;
    private  String username="", password="", passwordRetype="",name="", email="";
    private AlertDialog dialog;
    private String TAG="voc ong vinh";

    public Fragment_Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        ButterKnife.bind(this, getView());
        builder = new AlertDialog.Builder(getActivity());
    }

    @OnClick(R.id.btnBack)
    public void back(){
        Fragment_Login fragment_login = new Fragment_Login();
        getFragmentManager().beginTransaction().replace(R.id.lineFragment, fragment_login,"MyFragment").commit();
        mainActivity.txtRegister.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.btnSignUp)
    public void signUp(){
        MyVolley.getInstance(getActivity()).startVolley();

        try{
            username = etUsername.getText().toString().trim();
            password = etPassword.getText().toString().trim();
            passwordRetype = etPasswordRetype.getText().toString().trim();
            name = etName.getText().toString().trim();
            email = etMail.getText().toString().trim();
        }catch (Exception e){

        }
        if(!password.equals(passwordRetype)){
            builder.setMessage(" Retype Password and Password are not the same");
            builder.setTitle("reg_Fail");
            dialog = builder.create();
            dialog.show();
            return;
        }
        Log.i(TAG, "signUp: a");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        private  String code="";
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "signUp: "+response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                     code = jsonObject.getString("Code");

                    String message = jsonObject.getString("Message");
                    builder.setMessage(message);
                    builder.setTitle(code);
                    dialog = builder.create();
                    dialog.show();
                    Log.i(TAG, "onResponse: aaa   "+code);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(code.equals("reg_Fail")) {
                    Log.i(TAG, "onResponse: "+111);
                    return;
                }else
                    back();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "signUp: a"+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                Log.i(TAG, "getParams: "+name);
                params.put("txtName",name);
                params.put("txtPassword",password);
                params.put("txtEmail",email);
                params.put("txtUsername",username);
                return params;
            }
        };
        MyVolley.getInstance(getActivity()).addRequest(stringRequest);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity=null;
    }
}
