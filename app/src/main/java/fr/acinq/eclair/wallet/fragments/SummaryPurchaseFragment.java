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

import fr.acinq.eclair.wallet.R;
import fr.acinq.eclair.wallet.databinding.FragmentSummaryPurchaseBinding;
import fr.acinq.eclair.wallet.presenter.SummaryPurchasePresenter;


public class SummaryPurchaseFragment extends Fragment {


    public SummaryPurchaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSummaryPurchaseBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_summary_purchase, container, false);
        View root=binding.getRoot();
        binding.setSummaryPurchasePresenter(new SummaryPurchasePresenter() {
            @Override
            public void confirm() {
               // Toast.makeText(getContext(), "hii", Toast.LENGTH_SHORT).show();
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
        });
        return root;
    }


}
