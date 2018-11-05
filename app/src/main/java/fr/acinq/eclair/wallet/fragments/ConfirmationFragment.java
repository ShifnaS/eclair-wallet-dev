package fr.acinq.eclair.wallet.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.acinq.eclair.wallet.R;
import fr.acinq.eclair.wallet.activities.HomeActivity;
import fr.acinq.eclair.wallet.activities.SendPaymentActivity;
import fr.acinq.eclair.wallet.api.SingletonRequestQueue;
import fr.acinq.eclair.wallet.databinding.FragmentConfirmationBinding;
import fr.acinq.eclair.wallet.events.Message;
import fr.acinq.eclair.wallet.presenter.ConfirmationPresenter;
import fr.acinq.eclair.wallet.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {

    public ConfirmationFragment() {
        // Required empty public constructor
    }
String day="",month="";
  String invoice_id="";

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentConfirmationBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_confirmation, container, false);
        View root=binding.getRoot();
        Bundle bundle = getArguments();
        String amount=bundle.getString("amount");
        invoice_id=bundle.getString("invoice_id");
        day=bundle.getString("day");
        month=bundle.getString("month");
        TextView bt_amount=root.findViewById(R.id.amount);
        Button bt_confirm=root.findViewById(R.id.confirm);

        bt_amount.setText("Confirmation of Payment Details "+amount+"BTC");
   // Toast.makeText(getContext(), ""+invoice_id, Toast.LENGTH_SHORT).show();

    binding.setConfirmationPresenter(new ConfirmationPresenter() {
            @Override
            public void confirm() {
              Toast.makeText(getContext(), "Please wait until transaction complete", Toast.LENGTH_SHORT).show();
              bt_confirm.setEnabled(false);
              Intent intent = new Intent(getContext(), SendPaymentActivity.class);
              intent.putExtra(SendPaymentActivity.EXTRA_INVOICE, "lightning:"+invoice_id);
              intent.putExtra(SendPaymentActivity.EXTRA_D, "confirm");
              startActivity(intent);


               /* Fragment fragment = new PAymentSuccessfullFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_confirm, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }

            @Override
            public void cancel() {

            }
        });





        return root;
    }
  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onEvent(Message event) {
    Toast.makeText(getContext(), "Event "+event.getMessage(), Toast.LENGTH_SHORT).show();
    if(event.getMessage().equals("success"))
    {
      Intent intent = new Intent(getContext(), HomeActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


      //////////////////////////////////////////////////////////////////////////

      VolleyLog.DEBUG = true;
      RequestQueue queue = SingletonRequestQueue.getInstance(getContext()).getRequestQueue();

      final String url = String.format(String.format(Constants.URL_OK+invoice_id));
      JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
        jo -> {
          Log.e("Response", "*******************"+jo.toString());
          // jo=res;
          try
          {
            String response = jo.getString("message");
            boolean error = jo.getBoolean("error");
            if (!error) {
              if (response.equalsIgnoreCase("success"))
              {
                Fragment fragment = new PAymentSuccessfullFragment();
                Bundle bundle = new Bundle();
                bundle.putString("month", month);
                bundle.putString("day", day);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_confirm, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


              } else {
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
              }
            } else {
              Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

            }

          } catch (JSONException e) {
            e.printStackTrace();
          }


        },
        error -> Log.e("Error.Response", "************************"+error.getMessage())
      );
      queue.add(getRequest);


    }
    else
    {
      Intent intent = new Intent(getContext(), HomeActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      Toast.makeText(getContext(), event.getMessage(), Toast.LENGTH_SHORT).show();

    }

  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    EventBus.getDefault().unregister(this);
    super.onStop();
  }
}
