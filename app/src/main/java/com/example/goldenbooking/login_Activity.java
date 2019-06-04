package com.example.goldenbooking;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
public class login_Activity extends AppCompatActivity {
    TextView TvReg;
    TextView TvForgetPassword;
    EditText EdEmail,EdPassword;
    Button BtnLogin;
    SharedPreferences sharedPreferences;
    CheckBox CbRmbMe;
    Dialog dialogforgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        EdEmail = findViewById(R.id.Email);
        EdPassword = findViewById(R.id.Password);
        BtnLogin = findViewById(R.id.Login);
        TvReg = findViewById(R.id.RegisterHere);
        CbRmbMe = findViewById(R.id.RmbMe);
        TvForgetPassword = findViewById(R.id.ForgetPassword);

        TvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog();
            }
        });
        TvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EdEmail.getText().toString();
                String pass = EdPassword.getText().toString();
                if(TextUtils.isEmpty(email)) { // User's email
                    EdEmail.setError("Please insert email !");
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    EdEmail.setError("Please enter a valid email address !");
                    return;
                }
                if (TextUtils.isEmpty(pass)) { // User's email
                    EdPassword.setError("Please insert email !");
                    return;
                }

                loginUser(email,pass);
            }
        });
        CbRmbMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CbRmbMe.isChecked()){
                    String email = EdEmail.getText().toString();
                    String pass = EdPassword.getText().toString();
                    savePref(email,pass);
                }
            }
        });
        loadPref();
    }

    private void savePref(String e, String p) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", e);
        editor.putString("password", p);
        editor.commit();
        Toast.makeText(this, "Preferences has been saved", Toast.LENGTH_SHORT).show();
    }

    private void loadPref() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String premail = sharedPreferences.getString("email", "");
        String prpass = sharedPreferences.getString("password", "");
        if (premail.length()>0){
            CbRmbMe.setChecked(true);
            EdEmail.setText(premail);
            EdPassword.setText(prpass);
        }
    }

    private void loginUser(final String email, final String pass) {
        class LoginUser extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(login_Activity.this,
                        "Login user","...",false,false);
            }
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("Email",email);
                hashMap.put("Password",pass);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest("http://www.socstudents.net/GOLDENBOOKING/login.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(login_Activity.this,s,Toast.LENGTH_LONG).show();
                loading.dismiss();
                if(s.equalsIgnoreCase("failed")){
                    Toast.makeText(login_Activity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }else if  (s.length()>7){
                    //Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
                    String[] val = s.split(",");
                    Intent intent = new Intent(login_Activity.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userid",email);
                    bundle.putString("name",val[0]);
                    bundle.putString("phone",val[1]);
                    User.setEmail(email);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }
        LoginUser loginUser = new LoginUser();
        loginUser.execute();
    }

    void forgotPasswordDialog(){
        dialogforgotpass = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//Theme_DeviceDefault_Dialog_NoActionBar
        dialogforgotpass.setContentView(R.layout.activity_forget_password);
        dialogforgotpass.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final EditText edemail = dialogforgotpass.findViewById(R.id.email);
        Button btnsendemail = dialogforgotpass.findViewById(R.id.reset);
        btnsendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forgotemail =  edemail.getText().toString();
                sendPassword(forgotemail);
            }
        });
        dialogforgotpass.show();

    }

    private void sendPassword(final String forgotemail) {
        class SendPassword extends AsyncTask<Void,String,String>{

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("email",forgotemail);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest("http://www.socstudents.net/GOLDENBOOKING/verify_email.php",hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(login_Activity.this, s, Toast.LENGTH_SHORT).show();
                if (s.equalsIgnoreCase("success")){
                    Toast.makeText(login_Activity.this, "Success. Check your email", Toast.LENGTH_LONG).show();
                    dialogforgotpass.dismiss();
                }else{
                    Toast.makeText(login_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPassword sendPassword = new SendPassword();
        sendPassword.execute();
    }
}









