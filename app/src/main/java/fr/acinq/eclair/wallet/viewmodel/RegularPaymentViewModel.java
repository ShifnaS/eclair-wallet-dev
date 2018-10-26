package fr.acinq.eclair.wallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Observable;

import fr.acinq.eclair.wallet.api.ApiUrl;
import fr.acinq.eclair.wallet.api.SingletonRequestQueue;

public class RegularPaymentViewModel extends Observable {
  int numberOfRequestsCompleted;
  //ArrayList<ScheduleList> mScheduleDataList = new ArrayList<>();
  JSONObject jo=new JSONObject();
  private Context context;
  public final ObservableField<String > invoice_id = new ObservableField<>("");

  public RegularPaymentViewModel(Context context)
  {
    this.context = context;
  }
  public JSONObject sendInvoiceId(String invoice_id)
  {
    String res="";
   // mScheduleDataList.clear();
    //Toast.makeText(context, ""+invoice_id, Toast.LENGTH_SHORT).show();

    numberOfRequestsCompleted = 0;
    VolleyLog.DEBUG = true;
    RequestQueue queue = SingletonRequestQueue.getInstance(context).getRequestQueue();
    final String url = String.format(ApiUrl.BASE_URL + "/getschedulesdetails/"+invoice_id);
    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
      new Response.Listener<JSONObject>()
      {
        @Override
        public void onResponse(JSONObject response) {
          Log.e("Response", "*******************"+response.toString());
         // Toast.makeText(context, "response "+response.toString(), Toast.LENGTH_SHORT).show();
          GsonBuilder builder = new GsonBuilder();
          Gson mGson = builder.create();
          jo=response;
          ++numberOfRequestsCompleted;

        }
      },
      new Response.ErrorListener()
      {
        @Override
        public void onErrorResponse(VolleyError error) {
          Log.e("Error.Response", "************************"+error.getMessage());
        }
      }
    );
    queue.add(getRequest);
    queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

      @Override
      public void onRequestFinished(Request<Object> request) {
        try {
          if (request.getCacheEntry() != null) {
            String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
            Log.d("API123", request.getCacheKey() + " " + cacheValue);

          }
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }

        if (numberOfRequestsCompleted == 1) {
          numberOfRequestsCompleted = 0;

        }
      }
    });

    return jo;
  }

}
