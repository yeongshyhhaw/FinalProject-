package com.example.goldenbooking;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    String userid, name, phone, location, latitude, longitude;
    Spinner sploc;
    TextView tvemail;
    EditText tvphone, tvname, edoldpass, ednewpass;
    CircleImageView imgprofile;
    Button btnUpdate;
    ImageButton btnloc;
    Dialog myDialogMap;
    User user;
    private GoogleMap mMap;
    String slatitude, slongitude;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        imgprofile = findViewById(R.id.imageView4);
        tvphone = findViewById(R.id.txtphone);
        tvname = findViewById(R.id.txtUsername);
        tvemail = findViewById(R.id.txtEmail);
        edoldpass = findViewById(R.id.txtoldpassword);
        ednewpass = findViewById(R.id.txtnewpassword);
        btnUpdate = findViewById(R.id.button5);

        userid = bundle.getString("userid");//email
        name = bundle.getString("username");  //full name
        phone = bundle.getString("phone"); //phone
        tvemail.setText(userid);
        String image_url = "http://www.socstudents.net/GOLDENBOOKING/images/" + phone + ".jpg";
        Picasso.with(this).load(image_url)
                .resize(400, 400).into(imgprofile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadUserProfile();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newphone = tvphone.getText().toString();
                String newname = tvname.getText().toString();
                String oldpass = edoldpass.getText().toString();
                String newpass = ednewpass.getText().toString();
                dialogUpdate(newphone, newname, oldpass, newpass);

            }
        });


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid", userid);
                bundle.putString("name", name);
                bundle.putString("phone", phone);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void loadUserProfile() {
        class LoadUserProfile extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("email",userid );
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest("http://www.socstudents.net/GOLDENBOOKING/load_user.php", hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(ProfileActivity.this, s, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray restarray = jsonObject.getJSONArray("user");
                    JSONObject c = restarray.getJSONObject(0);
                    name = c.getString("name");
                    userid = c.getString("email");

                } catch (JSONException e) {
                    Toast.makeText(ProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
                }

                tvphone.setText(phone);
                tvname.setText(name);

            }
        }
        LoadUserProfile loadUserProfile = new LoadUserProfile();
        loadUserProfile.execute();
    }

    void updateProfile(final String newphone, final String newname, final String oldpass, final String newpass) {
        class UpdateProfile extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("email", userid);
                hashMap.put("name", newname);
                hashMap.put("phone", newphone);
                hashMap.put("opassword", oldpass);
                hashMap.put("npassword", newpass);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest("http://www.socstudents.net/GOLDENBOOKING/updateProfile.php", hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(ProfileActivity.this, s, Toast.LENGTH_SHORT).show();
                if (s.equalsIgnoreCase("success")) {
                    Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userid", userid);
                    bundle.putString("name", name);
                    bundle.putString("phone", phone);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
        UpdateProfile updateProfile = new UpdateProfile();
        updateProfile.execute();
    }




    private void dialogUpdate(final String newemail, final String newname, final String oldpass, final String newpass) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Profile");

        alertDialogBuilder
                .setMessage("Update this profile")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateProfile(newemail, newname, oldpass, newpass);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
