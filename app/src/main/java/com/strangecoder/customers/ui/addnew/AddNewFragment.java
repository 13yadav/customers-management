package com.strangecoder.customers.ui.addnew;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

        // Injection
        DataSource dataSource = Injection.provideCustomerDataSource(this.getContext());

        // Initializing ViewModel and ViewModelFactory
        AddNewViewModelFactory viewModelFactory = new AddNewViewModelFactory(args.getCustomerId(), dataSource);
        addNewViewModel = new ViewModelProvider(this, viewModelFactory).get(AddNewViewModel.class);
        binding.setViewModel(addNewViewModel);
        binding.setLifecycleOwner(this);

        binding.saveFab.setOnClickListener(v -> updateCustomer(binding, args));

        mDisposable.add(addNewViewModel.getCustomerById().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customer -> {
                    binding.customerName.setText(customer.name);
                    binding.address.setText(customer.address);
                    binding.phoneNumber.setText(customer.phone);
                    binding.contactPerson.setText(customer.contactPerson);
                    binding.personRole.setText(customer.contactPersonRole);
                    binding.contactPersonPhone.setText(customer.contactPersonPhone);
                    binding.email.setText(customer.contactPersonEmail);

                }, throwable -> Log.e("MR.J", "Failed to get customer" + throwable)));


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
    }


    private void updateCustomer(FragmentAddNewBinding binding, AddNewFragmentArgs args) {
        String customerId = args.getCustomerId();
        String customerName = binding.customerName.getText().toString().trim();
        String address = binding.address.getText().toString().trim();
        String phoneNumber = binding.phoneNumber.getText().toString().trim();
        String contactPerson = binding.contactPerson.getText().toString().trim();
        String personRole = binding.personRole.getText().toString().trim();
        String email = binding.email.getText().toString().trim();
        String contactPersonPhone = binding.contactPersonPhone.getText().toString().trim();

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