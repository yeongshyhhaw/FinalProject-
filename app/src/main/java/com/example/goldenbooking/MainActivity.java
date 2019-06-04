package com.example.goldenbooking;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    Dialog myDialogWindow;
    ListView lvHomeStay;
    Spinner Location;
    Spinner Parent;
    Spinner Child;
    Spinner Rooms;
    String userid,name,phone;
    EditText CheckInDate;
    ArrayList<HashMap<String, String>> HomeStayList;
    EditText CheckOutDate;
    ImageView Instragram;
    ImageView facebook;
    final Calendar myCalendar = Calendar.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        loadHomeStay(Location.getSelectedItem().toString());


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        CheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this,date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };

        CheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this,date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        Location.setSelection(0,false);
        Location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadHomeStay(Location.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /* change text color of location spinner*/
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.location, R.layout.spinner_text_color);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Location.setAdapter(adapter);



        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/lestle.yeong");
                Intent i= new Intent(Intent.ACTION_VIEW,uri);
                i.setPackage("com.facebook.android");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/lestle.yeong")));
                }
            }

        });

        Instragram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/yeonglestle/");
                Intent i= new Intent(Intent.ACTION_VIEW,uri);
                i.setPackage("com.instagram.android");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/yeonglestle/")));
                }
            }
        });



        lvHomeStay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadWindow(position);
            }
        });


    }

    private void loadWindow(int p) {
        myDialogWindow = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//Theme_DeviceDefault_Dialog_NoActionBar
        myDialogWindow.setContentView(R.layout.dialog_window);
        Button btnOrder = myDialogWindow.findViewById(R.id.button2);
        TextView myname,myphone,myaddress,myloc;
        myname = myDialogWindow.findViewById(R.id.textView);
        myphone = myDialogWindow.findViewById(R.id.textView2);
        myaddress = myDialogWindow.findViewById(R.id.textView3);
        myloc = myDialogWindow.findViewById(R.id.textView4);
        myname.setText(HomeStayList.get(p).get("HSNAME"));
        myphone.setText(HomeStayList.get(p).get("HSPHONE"));
        myaddress.setText(HomeStayList.get(p).get("HSADDRESS"));
        myloc.setText(HomeStayList.get(p).get("HSLOCATION"));

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder
                        .setMessage("Are you sure")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MainActivity.this,PaymentActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                myDialogWindow.dismiss();
            }
        });
        myDialogWindow.show();
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        CheckInDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel1() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        CheckOutDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void loadHomeStay(final String loc) {
        class LoadHomeStay extends AsyncTask<Void,Void,String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("HSLOCATION",loc);
                RequestHandler rh = new RequestHandler();
                HomeStayList = new ArrayList<>();
                String s = rh.sendPostRequest
                        ("http://www.socstudents.net/GOLDENBOOKING/loadHomeStay.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                HomeStayList.clear();
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray restarray = jsonObject.getJSONArray("homestay");
                    Log.e("ERROR",jsonObject.toString());
                    for (int i=0;i<restarray.length();i++){
                        JSONObject r = restarray.getJSONObject(i);
                        String HSID = r.getString("HSID");
                        String HSName = r.getString("HSNAME");
                        String HSPhone = r.getString("HSPHONE");
                        String HSAddress = r.getString("HSADDRESS");
                        String Location = r.getString("HSLOCATION");
                        HashMap<String,String> HomeStaylisthash = new HashMap<>();
                        HomeStaylisthash.put("HSID",HSID);
                        HomeStaylisthash.put("HSNAME",HSName);
                        HomeStaylisthash.put("HSPHONE",HSPhone);
                        HomeStaylisthash.put("HSADDRESS",HSAddress);
                        HomeStaylisthash.put("HSLOCATION",Location);
                        HomeStayList.add(HomeStaylisthash);
                    }
                }catch (final JSONException e){
                    Log.e("JSONERROR",e.toString());
                }

                ListAdapter adapter = new customAdapter(
                        MainActivity.this, HomeStayList,
                        R.layout.cus_window, new String[]
                        {"HSNAME","HSPHONE","HSADDRESS","HSLOCATION"}, new int[]
                        {R.id.textView,R.id.textView2,R.id.textView3,R.id.textView4});
                lvHomeStay.setAdapter(adapter);
            }

        }
        LoadHomeStay loadHomeStay = new LoadHomeStay();
        loadHomeStay.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.myProfile:
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("username",name);
                bundle.putString("phone",phone);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.myhistory:
                loadHistoryOrderData();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initView() {
        Location = findViewById(R.id.location);
        Instragram = findViewById(R.id.instragram);
        facebook = findViewById(R.id.facebook);
        lvHomeStay = findViewById(R.id.listViw);
        CheckInDate = findViewById(R.id.From);
        CheckOutDate = findViewById(R.id.To);

    }


}








