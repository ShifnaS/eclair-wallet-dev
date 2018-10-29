package fr.acinq.eclair.wallet.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.acinq.eclair.wallet.R;
import fr.acinq.eclair.wallet.databinding.FragmentSummaryPurchaseBinding;
import fr.acinq.eclair.wallet.models.ScheduleDataList;
import fr.acinq.eclair.wallet.presenter.SummaryPurchasePresenter;
import fr.acinq.eclair.wallet.viewmodel.SummaryPurchaseViewModel;
import scala.Int;


public class SummaryPurchaseFragment extends Fragment {

    String[] day;
    String dayy="1";
    ScheduleDataList dataList;
    String f="";
    String s="";
    private SummaryPurchaseViewModel summaryPurchaseViewModel,summaryPurchaseViewModel1,summaryPurchaseViewModel2;

    public SummaryPurchaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSummaryPurchaseBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_summary_purchase, container, false);
        View root=binding.getRoot();
        summaryPurchaseViewModel = new SummaryPurchaseViewModel(getContext());
        Bundle bundle = getArguments();
        binding.setSummarypurchase(summaryPurchaseViewModel);
        binding.day.setVisibility(View.INVISIBLE);

        String id=bundle.getString("_id");
        String service_name=bundle.getString("service_name");
        String immediate_cost=bundle.getString("immediate_cost");
        String amount=bundle.getString("amount");
        String frequency=bundle.getString("frequency");
        String payment_day=bundle.getString("payment_date");
        String qr_code=bundle.getString("qr_code");
        String schedule_type=bundle.getString("schedule_type");
        String payment_month=bundle.getString("payment_month");
        String days="";

        day = new String[28];
        for(int i=0;i<28;i++)
        {
          int d=i+1;
          day[i]=""+d;
        }
        if(schedule_type.equals("1"))
        {

          binding.day.setVisibility(View.VISIBLE);
        }
        else
        {

          binding.day.setVisibility(View.INVISIBLE);
        }


        if(frequency.equals("1"))
        {
          f="monthly";
          if(service_name.equalsIgnoreCase("A"))
          {
            if(schedule_type.equals("1"))
            {
              s="Summary of Purchase \n Name – Service "+service_name+" \n \n" +
                "Cost = "+amount +"\n Frequency = "+f+"\n\n ";
                dayy="Payment Date = 1 of the month";
            }
            else
            {
              s="Summary of Purchase \n Name – Service "+service_name+" \n \n" +
                "Cost = "+amount +"\n Frequency = "+f+"\n\n";
              dayy="Payment Date = "+payment_day+" of the "+payment_month;

            }


          }
          else
          {
            if(schedule_type.equals("1"))
            {
              s="Summary of Purchase \n Name – Service "+service_name+"  \n\n" +
                "Cost = "+amount +"\n Frequency = "+f+"\n\n " ;
              dayy="Payment Date = 1 of the month";

            }
            else
            {
              s="Summary of Purchase \n Name – Service "+service_name+" \n Immediate Payment Cost = "+immediate_cost+" \n\n" +
                "Cost = "+amount +"\n Frequency = "+f+"\n\n" ;
              dayy="Payment Date = "+payment_day+" of the "+payment_month;
            }


          }
        }
        else
        {
          f="annually";
          if(service_name.equalsIgnoreCase("A"))
          {
            if(schedule_type.equals("1"))
            {
              s="Summary of Purchase \n Name – Service "+service_name+" \n \n Cost = "+amount +"\n Frequency = "+f+"\n\n" ;
              dayy="Payment Date = 1 of the month";
            }
            else
            {
              s="Summary of Purchase \n Name – Service "+service_name+" \n \n Cost = "+amount +"\n Frequency = "+f+"\n\n";
              dayy="Payment Date = "+payment_day+" of the "+payment_month;

            }



          }
          else
          {
            if(schedule_type.equals("1"))
            {
              s="Summary of Purchase \n Name – Service "+service_name+"  \n\n" +
                "Cost = "+amount +"\n Frequency = "+f+"\n\n " ;
              dayy="Payment Date = 1 of the month";
            }
            else
            {
              s="Summary of Purchase \n Name – Service "+service_name+" \n Immediate Payment Cost = "+immediate_cost+" \n\n" +
                "Cost = "+amount +"\n Frequency = "+f+"\n\n" ;
              dayy="Payment Date = "+payment_day+" of the "+payment_month;
            }



          }
        }


      summaryPurchaseViewModel=new SummaryPurchaseViewModel(s,dayy);
      binding.setSummarypurchase(summaryPurchaseViewModel);

     /* summaryPurchaseViewModel=new SummaryPurchaseViewModel(dayy,"");
      binding.setSummarypurchase(summaryPurchaseViewModel);*/

      binding.np.setMinValue(0); //from array first value
      binding.np.setMaxValue(day.length-1); //to array last value

      binding.np.setDisplayedValues(day);

      binding.np.setWrapSelectorWheel(true);



      binding.setSummaryPurchasePresenter(new SummaryPurchasePresenter() {
            @Override
            public void confirm() {
               // Toast.makeText(getContext(), "hii", Toast.LENGTH_SHORT).show();
              // String response=re
                Fragment fragment = new ConfirmationFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_summary, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

            @Override
            public void cancel() {

            }

        @Override
        public void onValChange(int old, int newval) {
          /*if(frequency.equals("1"))
          {

            dayy="Payment Date = "+day[newval]+" of the month";
          }
          else
          {
            dayy="Payment Date = "+payment_day+" of the "+payment_month;

          }*/


          if(frequency.equals("1"))
          {
            f="monthly";
            if(service_name.equalsIgnoreCase("A"))
            {
              if(schedule_type.equals("1"))
              {
                s="Summary of Purchase \n Name – Service "+service_name+" \n \n" +
                  "Cost = "+amount +"\n Frequency = "+f+"\n\n ";
                dayy="Payment Date = "+day[newval]+" of the month";
              }
              else
              {
                s="Summary of Purchase \n Name – Service "+service_name+" \n \n" +
                  "Cost = "+amount +"\n Frequency = "+f+"\n\n";
                dayy="Payment Date = "+payment_day+" of the "+payment_month;

              }


            }
            else
            {
              if(schedule_type.equals("1"))
              {
                s="Summary of Purchase \n Name – Service "+service_name+" \n\n" +
                  "Cost = "+amount +"\n Frequency = "+f+"\n\n " ;
                dayy="Payment Date = "+day[newval]+" of the month";

              }
              else
              {
                s="Summary of Purchase \n Name – Service "+service_name+" \n Immediate Payment Cost = "+immediate_cost+" \n\n" +
                  "Cost = "+amount +"\n Frequency = "+f+"\n\n" ;
                dayy="Payment Date = "+payment_day+" of the "+payment_month;
              }


            }
          }
          else
          {
            f="annually";
            if(service_name.equalsIgnoreCase("A"))
            {
              if(schedule_type.equals("1"))
              {
                s="Summary of Purchase \n Name – Service "+service_name+" \n \n Cost = "+amount +"\n Frequency = "+f+"\n\n" ;
                dayy="Payment Date = "+day[newval]+" of the month";
              }
              else
              {
                s="Summary of Purchase \n Name – Service "+service_name+" \n \n Cost = "+amount +"\n Frequency = "+f+"\n\n";
                dayy="Payment Date = "+payment_day+" of the "+payment_month;

              }



            }
            else
            {
              if(schedule_type.equals("1"))
              {
                s="Summary of Purchase \n Name – Service "+service_name+"  \n\n" +
                  "Cost = "+amount +"\n Frequency = "+f+"\n\n " ;
                dayy="Payment Date = "+day[newval]+" of the month";
              }
              else
              {
                s="Summary of Purchase \n Name – Service "+service_name+" \n Immediate Payment Cost = "+immediate_cost+" \n\n" +
                  "Cost = "+amount +"\n Frequency = "+f+"\n\n" ;
                dayy="Payment Date = "+payment_day+" of the "+payment_month;
              }



            }
          }







         // Toast.makeText(getContext(), ""+dayy, Toast.LENGTH_SHORT).show();
          summaryPurchaseViewModel=new SummaryPurchaseViewModel(s,dayy);
          binding.setSummarypurchase(summaryPurchaseViewModel);
        }


      });

        return root;
    }



}
