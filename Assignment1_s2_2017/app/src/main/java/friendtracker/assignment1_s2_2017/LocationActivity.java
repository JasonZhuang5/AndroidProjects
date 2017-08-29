package friendtracker.assignment1_s2_2017;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import friendtracker.assignment1_s2_2017.entities.Location;
import friendtracker.assignment1_s2_2017.services.LocationManager;

public class LocationActivity extends AppCompatActivity
{

    private ListView lv_location;
    private List<HashMap<String, String>> locationList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        lv_location = (ListView)findViewById(R.id.lv_location);
        ArrayList<Location> tempList = LocationManager.getLocationList();
        for(int i=0;i<tempList.size();i++)
        {
            HashMap<String, String> h = new HashMap<String, String>();
            h.put("latitude",tempList.get(i).getLatitude());
            h.put("longitude",tempList.get(i).getLongitude());
            locationList.add(h);
        }
        SimpleAdapter adapter = new SimpleAdapter(this
                ,locationList
                ,R.layout.location_item
                ,new String[] {"latitude", "longitude"}
                ,new int[] {R.id.tv_latitude,R.id.tv_longitude});

        lv_location.setAdapter(adapter);

        lv_location.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Toast.makeText(LocationActivity.this,"you clicked "+(position+1)
                 //   +"item",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LocationActivity.this,MeetingDetailActivity.class);
                intent.putExtra("latitude",locationList.get(position).get("latitude"));
                intent.putExtra("longitude",locationList.get(position).get("longitude"));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
