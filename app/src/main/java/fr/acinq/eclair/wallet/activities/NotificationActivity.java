package fr.acinq.eclair.wallet.activities;

import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.acinq.eclair.wallet.R;
import fr.acinq.eclair.wallet.adapters.ManageRegularPaymentAdapter;
import fr.acinq.eclair.wallet.adapters.NotificationAdapter;
import fr.acinq.eclair.wallet.api.SingletonRequestQueue;
import fr.acinq.eclair.wallet.events.RecyclerTouchListener;
import fr.acinq.eclair.wallet.fragments.InviceScheduleDetailsFragment;
import fr.acinq.eclair.wallet.utils.Constants;

public class NotificationActivity extends AppCompatActivity {
  RecyclerView recyclerView;
  private JSONArray list;
  private NotificationAdapter mAdapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification);
    recyclerView=findViewById(R.id.recyclerview);

    final String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    Date cDate = new Date();
    String date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));




    VolleyLog.DEBUG = true;
    RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();

    final String url = String.format(String.format(Constants.URL+date+"/"+deviceId));
    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
      jo -> {


        try
        {
          Log.e("RESPonse ","********** n "+jo);
          String status = jo.getString("status");
          boolean error = jo.getBoolean("error");
          if (!error)
          {
            if (status.equalsIgnoreCase("success"))
            {
              list=jo.getJSONArray("message");
              mAdapter = new NotificationAdapter(jo.getJSONArray("message"),getApplicationContext());
              recyclerView.setAdapter(mAdapter);


            }
            else
            {
              Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
            }
          }
          else
          {
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

          }

        } catch (JSONException e)
        {
          e.printStackTrace();
        }


      },
      error -> Log.e("Error.Response", "************************"+error.getMessage())
    );
    queue.add(getRequest);





  }
}
