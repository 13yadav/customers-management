package com.strangecoder.customers.ui.addnew;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.strangecoder.customers.R;
import com.strangecoder.customers.database.Customer;
import com.strangecoder.customers.databinding.FragmentAddNewBinding;
import com.strangecoder.customers.utils.DataSource;
import com.strangecoder.customers.utils.Injection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddNewFragment extends Fragment {

    private AddNewViewModel addNewViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentAddNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new, container, false);

        // SafeArgs Fragment arguments
        assert getArguments() != null;
        AddNewFragmentArgs args = AddNewFragmentArgs.fromBundle(getArguments());

        Application application = this.requireActivity().getApplication();
        // Injection
        DataSource dataSource = Injection.provideCustomerDataSource(application);

        // Initializing ViewModel and ViewModelFactory
        AddNewViewModelFactory viewModelFactory = new AddNewViewModelFactory(dataSource, application);
        addNewViewModel = new ViewModelProvider(this, viewModelFactory).get(AddNewViewModel.class);
        binding.setViewModel(addNewViewModel);
        binding.setLifecycleOwner(this);

        binding.saveFab.setOnClickListener(v -> {
            updateCustomer(binding, args);
        });

        return binding.getRoot();
    }

    private void updateCustomer(FragmentAddNewBinding binding, AddNewFragmentArgs args) {
        String customerId = args.getCustomerId();
        String customerName = binding.customerName.getText().toString();
        String address = binding.address.getText().toString();
        String phoneNumber = binding.phoneNumber.getText().toString();
        String contactPerson = binding.contactPerson.getText().toString();
        String personRole = binding.personRole.getText().toString();
        String email = binding.email.getText().toString();
        String contactPersonPhone = binding.contactPersonPhone.getText().toString();

        Customer customer = new Customer(customerId, customerName, address, phoneNumber, contactPerson, personRole, email, contactPersonPhone);

        mDisposable.add(addNewViewModel.updateCustomer(customer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Navigation.findNavController(binding.saveFab)
                        .navigate(AddNewFragmentDirections.actionAddNewFragmentToDetailsFragment(args.getCustomerId())),
                        throwable -> Log.e("MR.J", "Customer update failed" + throwable)));
    }

    @Override
    public void onStop() {
        super.onStop();
        mDisposable.clear();
    }
}