package fr.acinq.eclair.wallet.fragments;


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
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.acinq.eclair.wallet.R;
import fr.acinq.eclair.wallet.databinding.FragmentNewRegularPaymentBinding;
import fr.acinq.eclair.wallet.models.ScheduleDataList;
import fr.acinq.eclair.wallet.presenter.NewRegularPaymentPresenter;
import fr.acinq.eclair.wallet.viewmodel.RegularPaymentViewModel;
import scala.Int;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRegularPaymentFragment extends Fragment {

  private IntentIntegrator qrScan;
  private RegularPaymentViewModel regularPaymentViewModel;
  FragmentNewRegularPaymentBinding binding;
    public NewRegularPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,
               R.layout.fragment_new_regular_payment, container, false);
        View root = binding.getRoot();
        regularPaymentViewModel = new RegularPaymentViewModel(getContext());
        binding.setRegularpayment(regularPaymentViewModel);

        qrScan  = new IntentIntegrator(this.getActivity()).forSupportFragment(this);

        binding.setNewRegularPaymentPresenter(new NewRegularPaymentPresenter() {
            @Override
            public void scan() {

              qrScan.setPrompt(getActivity().getString(R.string.scan_bar_code));
              qrScan.setBeepEnabled(true);
              qrScan.setOrientationLocked(false);
              qrScan.setCameraId(0);  // Use a specific camera of the device
              qrScan.initiateScan();

            }

            @Override
            public void confirm()  {
               try
               {
                 final String invoice_id = binding.invoiceId.getText().toString().trim();
                 Toast.makeText(getContext(), "invoice id "+invoice_id, Toast.LENGTH_SHORT).show();
                 if(invoice_id.equals(""))
                 {
                   Toast.makeText(getContext(), "Please enter an invoice id or scan an invoice", Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                   JSONObject jo=regularPaymentViewModel.sendInvoiceId(invoice_id);
                   String response=jo.getString("message");
                  // Toast.makeText(getContext(), "message "+response, Toast.LENGTH_SHORT).show();
                   boolean error=jo.getBoolean("error");
                   if(!error)
                   {
                     if(response.equalsIgnoreCase("success"))
                     {

                       JSONArray jsonArray = jo.getJSONArray("response");
                       //now looping through all the elements of the json array
                       for (int i = 0; i < jsonArray.length(); i++) {

                         Fragment fragment = new SummaryPurchaseFragment();
                         JSONObject jsonObject = jsonArray.getJSONObject(i);

                         String service_name="",immediate_cost="",payment_month="",payment_date;

                         String _id=jsonObject.getString("_id");
                         if(jsonObject.has("service_name"))
                         {
                           service_name=jsonObject.getString("service_name");
                         }
                         else
                         {
                           service_name="";
                         }
                         if(jsonObject.has("immediate_cost"))
                         {
                           immediate_cost=jsonObject.getString("immediate_cost");
                         }
                         else
                         {
                           immediate_cost="";
                         }
                         if(jsonObject.has("payment_month"))
                         {
                           payment_month=jsonObject.getString("payment_month");
                         }
                         else
                         {
                           payment_month="";
                         }
                         if(jsonObject.has("payment_date"))
                         {
                           payment_date=jsonObject.getString("payment_date");
                         }
                         else
                         {
                           payment_date="";
                         }

                         String amount=jsonObject.getString("amount");
                         String frequency=jsonObject.getString("frequency");
                         String qr_code=jsonObject.getString("qr_code");
                         String schedule_type=jsonObject.getString("schedule_type");
                         String schedule_id=jsonObject.getString("schedule_id");


                         Bundle bundle = new Bundle();
                         bundle.putString("_id",  _id);
                         bundle.putString("service_name",  service_name);
                         bundle.putString("immediate_cost",  immediate_cost);
                         bundle.putString("amount",  amount);
                         bundle.putString("frequency",  frequency);
                         bundle.putString("payment_date",  payment_date);
                         bundle.putString("qr_code",  qr_code);
                         bundle.putString("schedule_type",  schedule_type);
                         bundle.putString("schedule_id",  schedule_id);
                         bundle.putString("payment_month",  payment_month);
                         fragment.setArguments(bundle);

                         FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         fragmentTransaction.replace(R.id.content_regular, fragment);
                         fragmentTransaction.addToBackStack(null);
                         fragmentTransaction.commit();
                       }


                     }
                     else
                     {
                       Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                     }
                   }
                   else
                   {
                     Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                   }
                 }



               }
               catch (Exception e)
               {
                 e.printStackTrace();
                 Log.e("Error/////////","//////////////////"+e.getMessage());
               }



            }
        });
        return root;
    }

  //Getting the scan results
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if (result != null) {
      //if qrcode has nothing in it
        if (result.getContents() == null)
        {
          Toast.makeText(getContext(), "Result Not Found", Toast.LENGTH_LONG).show();
        }
        else
        {
        try
        {
          Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
          binding.invoiceId.setText(""+result.getContents());

        }
        catch (Exception e)
        {
          e.printStackTrace();
          binding.invoiceId.setText(""+result.getContents());
          //  Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
        }
      }
    }
    else
      {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }

}
