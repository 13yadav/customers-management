package com.strangecoder.customers.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.strangecoder.customers.R;
import com.strangecoder.customers.databinding.FragmentDetailsBinding;
import com.strangecoder.customers.utils.DataSource;
import com.strangecoder.customers.utils.Injection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsFragment extends Fragment {

    private DetailsViewModel detailsViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);

        // SafeArgs Fragment arguments
        assert getArguments() != null;
        DetailsFragmentArgs args = DetailsFragmentArgs.fromBundle(getArguments());

        DataSource dataSource = Injection.provideCustomerDataSource(this.getContext());

        DetailsViewModelFactory viewModelFactory = new DetailsViewModelFactory(args.getCustomerId(), dataSource);
        detailsViewModel = new ViewModelProvider(this, viewModelFactory).get(DetailsViewModel.class);
        binding.setViewModel(detailsViewModel);
        binding.setLifecycleOwner(this);

        binding.editFab.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(DetailsFragmentDirections.actionDetailsFragmentToAddNewFragment(args.getCustomerId())));

        mDisposable.add(detailsViewModel.getCustomerById().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customer -> {
                    binding.customerName.setText(customer.getName());
                    binding.address.setText(customer.getAddress());
                    binding.phoneNumber.setText(customer.getPhone());
                    binding.contactPerson.setText(customer.getContactPerson());
                    binding.personRole.setText(customer.getContactPersonRole());
                    binding.contactPersonPhone.setText(customer.getContactPersonPhone());
                    binding.email.setText(customer.getContactPersonEmail());

                }, throwable -> Log.e("MR.J", "Failed to get customer" + throwable)));

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDisposable.clear();
    }
}