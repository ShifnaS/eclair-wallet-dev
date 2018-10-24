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
import fr.acinq.eclair.wallet.databinding.FragmentManageRegularPamentBinding;
import fr.acinq.eclair.wallet.presenter.ManageRegularPaymentPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageRegularPamentFragment extends Fragment {


    public ManageRegularPamentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentManageRegularPamentBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_manage_regular_pament,container,false);
        View root=binding.getRoot();
        binding.setManageRegularPaymentPresenter(new ManageRegularPaymentPresenter() {
            @Override
            public void view() {
                Fragment fragment = new InviceScheduleDetailsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return  root;
    }

}
