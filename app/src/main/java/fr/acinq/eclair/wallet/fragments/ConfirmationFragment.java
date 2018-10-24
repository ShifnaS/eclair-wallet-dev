package fr.acinq.eclair.wallet.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.acinq.eclair.wallet.R;
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
        binding.setConfirmationPresenter(new ConfirmationPresenter() {
            @Override
            public void confirm() {
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
