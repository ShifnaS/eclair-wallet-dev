package fr.acinq.eclair.wallet.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import fr.acinq.eclair.wallet.R;
import fr.acinq.eclair.wallet.activities.SendPaymentActivity;
import fr.acinq.eclair.wallet.databinding.FragmentConfirmationBinding;
import fr.acinq.eclair.wallet.presenter.ConfirmationPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {

    public ConfirmationFragment() {
        // Required empty public constructor
    }



  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentConfirmationBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_confirmation, container, false);
        View root=binding.getRoot();
        Bundle bundle = getArguments();
        String amount=bundle.getString("amount");
        String invoice_id=bundle.getString("invoice_id");
        TextView bt_amount=root.findViewById(R.id.amount);
        bt_amount.setText("Confirmation of Payment Details "+amount+"BTC");
   // Toast.makeText(getContext(), ""+invoice_id, Toast.LENGTH_SHORT).show();

    binding.setConfirmationPresenter(new ConfirmationPresenter() {
            @Override
            public void confirm() {

              Intent intent = new Intent(getContext(), SendPaymentActivity.class);
              intent.putExtra(SendPaymentActivity.EXTRA_INVOICE, invoice_id);
              startActivity(intent);

                Fragment fragment = new PAymentSuccessfullFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_confirm, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void cancel() {

            }
        });
        return root;
    }

}
