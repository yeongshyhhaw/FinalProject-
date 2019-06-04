package com.example.goldenbooking;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText Email,Pass,Phone,Name;
    Button Reg;
    TextView Login;
    ImageView ProfilePicture;
    User user;
    String hlatitude,hlongitude;
    TextView TandC,Location;
    ImageButton btnmapwindown;
    String EDEmail,EDPass,EDName,EDPhone;
    CheckBox TAndC;
    Dialog myDialogMap;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = findViewById(R.id.email);
        Pass = findViewById(R.id.pass);
        Phone =findViewById(R.id.phoneNum);
        Name = findViewById(R.id.name);
        Reg = findViewById(R.id.buttonReg);
        Login = findViewById(R.id.textViewLogin);
        TAndC = findViewById(R.id.checkBox);
        TandC = findViewById(R.id.TANDC);


        ProfilePicture = findViewById(R.id.imageView);



        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EDEmail = Email.getText().toString();
                EDName = Name.getText().toString();
                EDPass = Pass.getText().toString();
                EDPhone = Phone.getText().toString();
                if(TAndC.isChecked()) {
                    if (TextUtils.isEmpty(EDEmail)) {
                        Email.setError("Please insert email !");
                        return;
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(EDEmail).matches()) {
                        Email.setError("Please enter a valid email address !");
                        return;
                    }
                    if (TextUtils.isEmpty(EDName))
                    {
                        Name.setError("Please insert name");
                        return;

                    }
                    if (TextUtils.isEmpty(EDPass)) {
                        Pass.setError("Please insert password");
                        return;
                    }
                    if (TextUtils.isEmpty(EDPhone))
                    {
                        Phone.setError("Please insert phone number");
                        return;

                    }
                    registerUserInput();

                }
                else
                {

                    Toast.makeText(RegisterActivity.this,"Please Accept Term and condition", Toast.LENGTH_SHORT).show();
                }
            }

        });


        TandC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(RegisterActivity.this);
                a_builder.setMessage
                        ("1. Introduction: purpose of document: governing bookings; express agreement to document upon order; no abridgment of consumer statutory rights.\n\n" +
                                "2.  Interpretation: informal definitions for booking terms and conditions.\n" +
                                "3.  Order process: booking advertisement as invitation to treat; no contracts except in accordance with order process; bookings order process; input error correction and identification.\n\n" +
                                "4.  Prices: prices quoted on website; prices changes on website; amounts inclusive or exclusive of VAT.\n\n" +
                                "5.  Payments: payment of booking prices; methods of paying prices.\n\n" +
                                "6.  Variation of booking: variation of booking by customer; variation of booking by operator.\n\n" +
                                "7.  Cancellation of bookings by us: cancellation windows and refunds; method of cancellation; full refund upon cancellation under section.\n\n" +
                                "8.  Cancellation of bookings by you: rights additional to statutory rights; cancellation windows and refunds; method of cancellation; default no refunds on cancellation.\n\n" +
                                "9.  Distance contracts: cancellation right: cancellation right for consumers; cancellation right for services and digital content; consumer agreement to provision of services; exercise of cancellation right; refund upon services distance contract cancellation; refund method; refund timing for services and digital content.\n\n" +
                                "10. Warranties and representations: customer warranties and representations; warranty relating to bookings; no implied warranties or representations relating to bookings.\n\n" +
                                "11. Limitations and exclusions of liability: caveats to limits of liability (B2C); interpretation of limits of liability; no liability for force majeure; no liability for business losses; aggregate liability cap under contract.\n\n" +
                                "12. Force majeure: obligations suspended for force majeure; force majeure notification and information; mitigation of effects of force majeure.\n" +
                                "13. Variation: revision of document by publishing new version on website; variations govern future contracts.\n\n" +
                                "14. Assignment: assignment by first party (B2C); assignment by second party.\n\n" +
                                "15. No waivers: no unwritten waivers of breach; no continuing waiver.\n\n" +
                                "16. Severability: severability of whole; severability of parts.\n\n" +
                                "17. Third party rights: third party rights: benefit; third party rights: exercise of rights.\n\n" +
                                "18. Entire agreement: entire agreement - bookings.\n\n" +
                                "19. Law and jurisdiction: governing law; jurisdiction.\n\n" +
                                "20. ur details: website operator name; company registration details; place of business; contact information.")
                        .setCancelable(false)
                        .setPositiveButton("Accept",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                TAndC.setChecked(true);
                            }
                        })
                        .setNegativeButton("Decline",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                TAndC.setChecked(false);
                            }
                        }) ;
                AlertDialog alert = a_builder.create();
                alert.setTitle("Alert !!!");
                alert.show();
            }



        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, login_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        ProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap,400,500);
                    Toast.makeText(RegisterActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    ProfilePicture.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = ThumbnailUtils.extractThumbnail(imageBitmap,400,500);
            ProfilePicture.setImageBitmap(imageBitmap);
            ProfilePicture.buildDrawingCache();
            ContextWrapper cw = new ContextWrapper(this);
            File pictureFileDir = cw.getDir("basic", Context.MODE_PRIVATE);
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdir();
            }
            Log.e("FILE NAME", "" + pictureFileDir.toString());
            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                return;
            }
            FileOutputStream outStream = null;
            String photoFile = "profile.jpg";
            File outFile = new File(pictureFileDir, photoFile);
            try {
                outStream = new FileOutputStream(outFile);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                //hasimage = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }




    private void registerUserInput() {
        String email,pass,phone,name;
        email = Email.getText().toString();
        pass = Pass.getText().toString();
        phone = Phone.getText().toString();
        name = Name.getText().toString();
        user = new User(name,phone,email,pass);
        registerUserDialog();


    }

    private void insertData() {
        class RegisterUser extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this,
                        "Registration","...",false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("email",user.Email);
                hashMap.put("password",user.Password);
                hashMap.put("phoneNumber",user.Phone);
                hashMap.put("name",user.Name);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest
                        ("http://www.socstudents.net/GOLDENBOOKING/Registration_User.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equalsIgnoreCase("success")){
                    Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,login_Activity.class);
                    RegisterActivity.this.finish();
                    startActivity(intent);

                }else if (s.equalsIgnoreCase("nodata")){
                    Toast.makeText(RegisterActivity.this, "Please fill in data first", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }

            }
        }

        RegisterUser registerUser = new RegisterUser();
        registerUser.execute();
    }

    private void registerUserDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(this.getResources().getString(R.string.registerfor)+" "+user.Name+"?");

        alertDialogBuilder
                .setMessage(this.getResources().getString(R.string.registerdialognew))
                .setCancelable(false)
                .setPositiveButton(this.getResources().getString(R.string.yesbutton),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //Toast.makeText(getActivity(), "DELETE "+jobid, Toast.LENGTH_SHORT).show()
                        new Encode_image().execute(getDir(),user.Phone+".jpg");
                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.registrationprocess), Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(this.getResources().getString(R.string.nobutton),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public String getDir(){
        ContextWrapper cw = new ContextWrapper(this);
        File pictureFileDir = cw.getDir("basic", Context.MODE_PRIVATE);
        if (!pictureFileDir.exists()) {
            pictureFileDir.mkdir();
        }
        Log.d("GETDIR",pictureFileDir.getAbsolutePath());
        return pictureFileDir.getAbsolutePath()+"/profile.jpg";
    }





    public class Encode_image extends AsyncTask<String,String,Void> {
        private String encoded_string, image_name;
        Bitmap bitmap;

        @Override
        protected Void doInBackground(String... args) {
            String filname = args[0];
            image_name = args[1];
            File file = new File(filname);
            bitmap = BitmapFactory.decodeFile(filname);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            makeRequest(encoded_string, image_name);
        }

        private void makeRequest(final String encoded_string, final String image_name) {
            class UploadAll extends AsyncTask<Void, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(Void... params) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("encoded_string", encoded_string);
                    map.put("image_name", image_name);
                    RequestHandler rh = new RequestHandler();//request server connection
                    String s = rh.sendPostRequest("http://www.socstudents.net/GOLDENBOOKING/UploadPicture.php",map);
                    return s;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (s.equalsIgnoreCase("Success")) {
                        insertData();
                        // Toast.makeText(RegisterActivity.this, "Success Upload Image", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Failed Registration", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            UploadAll uploadall = new UploadAll();
            uploadall.execute();
        }
    }




}
