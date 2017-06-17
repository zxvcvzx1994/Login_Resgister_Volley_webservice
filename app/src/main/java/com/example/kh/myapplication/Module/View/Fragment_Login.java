package com.example.kh.myapplication.Module.View;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
public class Fragment_Login extends Fragment {
    private String url  = "http://192.168.1.10/DuLieu/login_data.php";
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etUsername)
    EditText etUsername;
    private  String username="", password="";

    private AlertDialog.Builder builder;
    private String TAG="vo cong vinh";

    public Fragment_Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__login, container, false);;

        builder = new AlertDialog.Builder(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        setRetainInstance(true);
    }

    @OnClick(R.id.btnLogin)
    public void login(){
        MyVolley.getInstance(getActivity()).startVolley();
        try{
            username = etUsername.getText().toString().trim();
            password = etPassword.getText().toString().trim();
        }catch (Exception e){

        }
        Log.i(TAG, "login: a");
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i(TAG, "login: a1");
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("Code");
                    String message = jsonObject.getString("Message");

                    if(code.equals("reg_Success")){
                        Log.i(TAG, "login: a2");
                        Intent intent = new Intent(getActivity(), Form.class);
                        String name = jsonObject.getString("Name");
                        String email = jsonObject.getString("Email");
                        intent.putExtra("name",name);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        reset();
                    }else{
                        builder.setTitle(code);
                        builder.setMessage(message);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyVolley.getInstance(getActivity()).stopVolley();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "login: a" + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params  = new HashMap<String,String>();
                params.put("txtUsername",username);
                params.put("txtPassword", password);
                return params;
            }
        };
        MyVolley.getInstance(getActivity()).addRequest(jsonObjectRequest);
    }

    @OnClick(R.id.btnReset)
    public  void reset(){
        etPassword.setText("");
        etUsername.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyVolley.getInstance(context).startVolley();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MyVolley.getInstance(getActivity()).stopVolley();
    }
}
