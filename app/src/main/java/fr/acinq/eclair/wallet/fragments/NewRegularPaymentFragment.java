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
                 ScheduleDataList data ;
                 List<ScheduleDataList> scheduleDataListList;
                 final String invoice_id = binding.invoiceId.getText().toString().trim();
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
                       data=new ScheduleDataList();
                       scheduleDataListList=new ArrayList<ScheduleDataList>();
                       JSONArray jsonArray = jo.getJSONArray("responds");
                       //now looping through all the elements of the json array
                       for (int i = 0; i < jsonArray.length(); i++) {

                         Fragment fragment = new SummaryPurchaseFragment();
                         JSONObject jsonObject = jsonArray.getJSONObject(i);

                         String _id=jsonObject.getString("_id");
                         String service_name=jsonObject.getString("service_name");
                         Integer immediate_cost=jsonObject.getInt("immediate_cost");
                         Integer amount=jsonObject.getInt("amount");
                         Integer frequency=jsonObject.getInt("frequency");
                         Integer payment_date=jsonObject.getInt("payment_day");
                         String qr_code=jsonObject.getString("qr_code");
                         Integer schedule_type=jsonObject.getInt("schedule_type");
                         String schedule_id=jsonObject.getString("schedule_id");
                         String payment_month=jsonObject.getString("payment_month");

                       /*data.set_id(_id);
                       data.setService_name(service_name);
                       data.setImmediate_cost(immediate_cost);
                       data.setAmount(amount);
                       data.setFrequency(frequency);
                       data.setPayment_day(payment_date);
                       data.setQr_code(qr_code);
                       data.setSchedule_type(schedule_type);
                       data.setSchedule_id(schedule_id);
                       data.setPayment_month(payment_month);*/

                         // data = new ScheduleDataList(_id,service_name,immediate_cost,amount,frequency,payment_date,qr_code,schedule_type,schedule_id,payment_month);
                         //  scheduleDataListList.add(data);
                         Bundle bundle = new Bundle();
                         bundle.putString("_id",  _id);
                         bundle.putString("service_name",  service_name);
                         bundle.putInt("immediate_cost",  immediate_cost);
                         bundle.putInt("amount",  amount);
                         bundle.putInt("frequency",  frequency);
                         bundle.putInt("payment_date",  payment_date);
                         bundle.putString("qr_code",  qr_code);
                         bundle.putInt("schedule_type",  schedule_type);
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
