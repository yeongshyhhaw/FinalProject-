package com.example.goldenbooking;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.SimpleAdapter;

public class customAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater=null;
    public customAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        try{
            if(convertView==null)
                vi = inflater.inflate(R.layout.cus_window, null);
            HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
            TextView tvHSname = vi.findViewById(R.id.textView);
            TextView tvphone = vi.findViewById(R.id.textView2);
            TextView tvadd = vi.findViewById(R.id.textView3);
            TextView tvloc = vi.findViewById(R.id.TextView15);
            CircleImageView imgrest =vi.findViewById(R.id.imageView);
            String dname = (String) data.get("HSNAME");//hilang
            String dphone =(String) data.get("HSPHONE");
            String dadd =(String) data.get("HSADDRESS");
            String dloc =(String) data.get("HSLOCATION");
            String drid=(String) data.get("HSID");
            tvHSname.setText(dname);
            tvphone.setText(dphone);
            tvadd.setText(dadd);
            tvloc.setText(dloc);
            String image_url = "http://www.socstudents.net/GOLDENBOOKING/HomeStayPicture/"+drid+".jpg";
            Picasso.with(mContext).load(image_url)
                    .fit().into(imgrest);

        }catch (IndexOutOfBoundsException e){

        }

        return vi;
    }
}

