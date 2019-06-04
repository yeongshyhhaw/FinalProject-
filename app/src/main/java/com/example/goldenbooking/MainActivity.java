package com.example.goldenbooking;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Dialog myDialogWindow,myDialogHistory;
    ListView lvHomeStay;
    Spinner Location,rooms;
    String userid,name,phone;
    double totalhistory;
    EditText CheckInDate;
    ArrayList<HashMap<String, String>> HomeStayList;
    ArrayList<HashMap<String, String>> HomeStayList1;
    ArrayList<HashMap<String, String>> orderhistorylist;
    EditText CheckOutDate;
    ImageView Instragram;
    ImageView facebook;
    Context context = this;
    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    DatePickerDialog picker;
    public static Random RANDOM = new Random();

    final Calendar myCalendar = Calendar.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        orderhistorylist= new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userid = bundle.getString("userid");
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        loadHomeStay(Location.getSelectedItem().toString());

        CheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                CheckInDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                            }
                        }, year, month, day);
                picker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        CheckInDate.setText("");
                        picker.dismiss();
                    }
                });
                picker.show();
            }


        });


        CheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                CheckOutDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, year, month, day);
                picker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        CheckOutDate.setText("");
                        picker.dismiss();
                    }
                });
                picker.show();
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

    private double calculate(String To,String From, String price,String rooms) {
        double total=0.00;
        DateFormat formatter = new SimpleDateFormat("yyyyy-MM-dd");
        double Price= Double.parseDouble(price);
        double room = Double.parseDouble(rooms);
        try {
        String from = String.valueOf(CheckInDate.getText());
        String  to =  String.valueOf(CheckOutDate.getText());

        Date From1 = formatter.parse(from);
        Date To1 =formatter.parse(to);

        long milliseconds = To1.getTime()-From1.getTime();
        int days = (int) (milliseconds / (1000 * 60 * 60 * 24));
        total= (days*Price)*room;
        } catch (java.text.ParseException e) {
            Toast.makeText(MainActivity.this, "Please select check out date and check in date", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return  total;
    }

    private void loadWindow(int p) {
        myDialogWindow = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//Theme_DeviceDefault_Dialog_NoActionBar
        myDialogWindow.setContentView(R.layout.dialog_window);
        final Button btnbook = myDialogWindow.findViewById(R.id.button);
        final TextView myname,myphone,myaddress,myloc,mydescription,myPrice;
        myname = myDialogWindow.findViewById(R.id.textView);
        myphone = myDialogWindow.findViewById(R.id.textView2);
        myaddress = myDialogWindow.findViewById(R.id.textView3);
        final ImageView imgrest =myDialogWindow.findViewById(R.id.imageView);
        myloc = myDialogWindow.findViewById(R.id.textView4);
        mydescription = myDialogWindow.findViewById(R.id.textView5);
        myPrice = myDialogWindow.findViewById(R.id.textView6);
        final String HomeStayID = (HomeStayList.get(p).get("HSID"));
        final String HomeStayName = (HomeStayList.get(p).get("HSNAME"));
        myname.setText(HomeStayList.get(p).get("HSNAME"));
        myphone.setText(HomeStayList.get(p).get("HSPHONE"));
        myaddress.setText(HomeStayList.get(p).get("HSADDRESS"));
        myloc.setText(HomeStayList.get(p).get("HSLOCATION"));
        mydescription.setText(HomeStayList.get(p).get("HSDESCRIPTION"));
        final String price =HomeStayList.get(p).get("HSPRICE");
        final String date1 = CheckInDate.getText().toString();
        final String date2 = CheckOutDate.getText().toString();
        String room = rooms.getSelectedItem().toString();

        if(room.equals("null")){
            Toast.makeText(MainActivity.this, "Please select room number", Toast.LENGTH_SHORT).show();
            myPrice.setText("-");
        }
        else
        {
            Double stringdouble= (calculate(date1,date2,price,room));
            if(stringdouble<=0.00)
            {
                myPrice.setText("-");
                Toast.makeText(MainActivity.this, "Please select check out date and check in date", Toast.LENGTH_SHORT).show();
            }
            else
            {
                String stringdouble1= Double.toString(calculate(date1,date2,price,room));
                myPrice.setText(stringdouble1);
            }

        }


        String image_url = "http://www.socstudents.net/GOLDENBOOKING/HomeStayPicture/"+HomeStayID+".jpg";
        Picasso.with(this).load(image_url)
                .fit().into(imgrest);


        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Do you really want to book?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String price1 = (String) myPrice.getText();
                                if(price1.equals("-"))
                                {
                                    dialog.cancel();
                                    Toast.makeText(MainActivity.this, "Please select check out date, check in date and rooms number", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                   String OrderID=randomString(7);
                                   insertData(OrderID,price1,date1,date2,HomeStayID,HomeStayName);
                                    dialog.cancel();
                                }
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();



            }
        });
        myDialogWindow.show();
    }
    private void insertData(final String OrderID, final String total,final String from,final  String to,final String ID,final String name) {
        class RegisterUser extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,
                        "Booking","...",false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("email",userid);
                hashMap.put("HomeStayID",ID);
                hashMap.put("PhoneNumber",phone);
                hashMap.put("orderid",OrderID);
                hashMap.put("total",total);
                hashMap.put("from",from);
                hashMap.put("to",to);
                hashMap.put("HomeStayName",name);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest
                        ("http://www.socstudents.net/GOLDENBOOKING/insert_Order.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                loading.dismiss();
                if (s.equalsIgnoreCase("success")){
                    Toast.makeText(MainActivity.this, "Booking Success", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(MainActivity.this, "Booking Failed", Toast.LENGTH_SHORT).show();
                }

            }
        }

        RegisterUser registerUser = new RegisterUser();
        registerUser.execute();
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
                        String Description = r.getString("HSDESCRIPTION");
                        String Price = r.getString("HSPRICE");
                        HashMap<String,String> HomeStaylisthash = new HashMap<>();
                        HomeStaylisthash.put("HSID",HSID);
                        HomeStaylisthash.put("HSNAME",HSName);
                        HomeStaylisthash.put("HSPHONE",HSPhone);
                        HomeStaylisthash.put("HSADDRESS",HSAddress);
                        HomeStaylisthash.put("HSLOCATION",Location);
                        HomeStaylisthash.put("HSDESCRIPTION",Description);
                        HomeStaylisthash.put("HSPRICE",Price);
                        HomeStayList.add(HomeStaylisthash);
                    }
                }catch (final JSONException e){
                    Log.e("JSONERROR",e.toString());
                }

                ListAdapter adapter = new customAdapter(
                        MainActivity.this, HomeStayList,
                        R.layout.cus_window, new String[]
                        {"HSNAME","HSPHONE","HSADDRESS","HSLOCATION","HSDESCRIPTION"}, new int[]
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
            case R.id.myprofile:
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
                return true;
            case R.id.About:
                Intent intent1 = new Intent(MainActivity.this,about.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("userid",userid);
                bundle1.putString("username",name);
                bundle1.putString("phone",phone);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                return true;

            case R.id.logout:
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.logout, null);
                Button yes = (Button) mView.findViewById(R.id.yesBTN);
                Button cancel = (Button) mView.findViewById(R.id.noBTN);
                mBuilder.setView(mView);
                final AlertDialog dialog1 = mBuilder.create();
                dialog1.show();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            System.exit(0);
                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MainActivity.this.finish();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "logout cancel", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                    }
                });
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void loadHistoryOrderData() {
        class LoadOrderData extends AsyncTask<Void,String,String>{

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("userid",userid);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest("http://www.socstudents.net/GOLDENBOOKING/load_order_history.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                orderhistorylist.clear();
                totalhistory = 0;
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray ordarray = jsonObject.getJSONArray("history");

                    for (int i=0;i<ordarray  .length();i++) {
                        JSONObject c = ordarray  .getJSONObject(i);
                        String jsorderid = c.getString("orderid");
                        String jstotal = c.getString("total");
                        String jsfrom = c.getString("fromDate");
                        String jsto = c.getString("toDate");
                        String jsname = c.getString("HomeStayName");
                        HashMap<String,String> histlisthash = new HashMap<>();
                        histlisthash  .put("orderid",jsorderid);
                        histlisthash  .put("total",jstotal);
                        histlisthash  .put("fromDate",jsfrom);
                        histlisthash  .put("toDate",jsto);
                        histlisthash  .put("HomeStayName",jsname);
                        orderhistorylist.add(histlisthash);
                        totalhistory = Double.parseDouble(jstotal) + totalhistory;
                    }
                }catch (JSONException e){}
                super.onPostExecute(s);
                if (orderhistorylist.size()>0){
                    loadHistoryWindow();
                }else{
                    Toast.makeText(MainActivity.this, "No order history", Toast.LENGTH_SHORT).show();
                }

            }

        }
        LoadOrderData loadOrderData = new LoadOrderData();
        loadOrderData.execute();
    }

    private void loadHistoryWindow() {
        myDialogHistory = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//Theme_DeviceDefault_Dialog_NoActionBar
        myDialogHistory.setContentView(R.layout.hist_window);
        myDialogHistory.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ListView lvhist = myDialogHistory.findViewById(R.id.lvhistory);
        TextView tvtotal = myDialogHistory.findViewById(R.id.textViewTotal);
        Button btnclose = myDialogHistory.findViewById(R.id.btnClose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogHistory.dismiss();
            }
        });
        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, orderhistorylist,
                R.layout.hist_order_list, new String[]
                {"orderid","total","fromDate","toDate","HomeStayName"}, new int[]
                {R.id.textView,R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView23});
        lvhist.setAdapter(adapter);
        tvtotal.setText("RM"+totalhistory);
        myDialogHistory.show();
    }

    public String convertime24h(String value) {
        String _12hourformat = "";
        try {
            //Log.e("DATE", value);
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date = dt.parse(value.substring(0, 16));
            SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            return _12hourformat = dt1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _12hourformat;
    }



    private void initView() {
        Location = findViewById(R.id.location);
        Instragram = findViewById(R.id.instragram);
        facebook = findViewById(R.id.facebook);
        lvHomeStay = findViewById(R.id.listViw);
        CheckInDate = findViewById(R.id.From);
        CheckOutDate = findViewById(R.id.To);
        rooms = findViewById(R.id.rooms);

    }
    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }
        return sb.toString();
    }


}








