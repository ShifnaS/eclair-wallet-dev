package fr.acinq.eclair.wallet.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.acinq.eclair.wallet.R;
import fr.acinq.eclair.wallet.activities.HomeActivity;
import fr.acinq.eclair.wallet.databinding.FragmentPaymentSuccessfullBinding;
import fr.acinq.eclair.wallet.presenter.PaymentSuccesfullPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class PAymentSuccessfullFragment extends Fragment {


  public PAymentSuccessfullFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    FragmentPaymentSuccessfullBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_payment_successfull,container,false);
    View root=binding.getRoot();
    binding.setPaymentSuccesfullPresenter(new PaymentSuccesfullPresenter() {
      @Override
      public void ok() {
        Intent i=new Intent(getContext(), HomeActivity.class);
        startActivity(i);
        // getActivity().finish();

      }
    });
    return root;

  }

}
