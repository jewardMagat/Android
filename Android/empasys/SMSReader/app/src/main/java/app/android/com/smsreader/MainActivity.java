package app.android.com.smsreader;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvSMS = (ListView)findViewById(R.id.lvSMS);

        if(fetchInbox()!=null){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this,android.R.layout.simple_list_item_1, fetchInbox());
            lvSMS.setAdapter(adapter);
        }
    }

    private ArrayList<String>fetchInbox(){
        ArrayList<String> sms= new ArrayList<String>();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, new String[]
                {"_id","address","date","body"},null,null,null);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            String address=cursor.getString(1);
            String body = cursor.getString(3);

            if(address.equals("GLOBE")){
                sms.add("Address: "+address+"\n SMS: "+body);
                //add to database
                //delete message
                //http://stackoverflow.com/questions/9389740/delete-an-sms-from-inbox
            }

        }
        return sms;
    }
}
