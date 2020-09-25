package com.strangecoder.customers.ui.customers;

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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.strangecoder.customers.database.Customer;
import com.strangecoder.customers.utils.DataSource;
import com.strangecoder.customers.R;
import com.strangecoder.customers.database.CustomerDao;
import com.strangecoder.customers.database.CustomerDataSource;
import com.strangecoder.customers.database.CustomerDatabase;
import com.strangecoder.customers.databinding.FragmentCustomerListBinding;
import com.strangecoder.customers.utils.Injection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CustomerListFragment extends Fragment {

    private CustomerListViewModel viewModel;


    private final CompositeDisposable disposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentCustomerListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_list, container, false);

        Application application = this.requireActivity().getApplication();

        // Injection
        DataSource dataSource = Injection.provideCustomerDataSource(application);

        // Initializing ViewModel and ViewModelFactory
        CustomerListViewModelFactory viewModelFactory = new CustomerListViewModelFactory(dataSource, application);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(CustomerListViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Initializing RecyclerView adapter
        CustomerListAdapter.CustomerDiffCallback callback = new CustomerListAdapter.CustomerDiffCallback();
        CustomerListAdapter adapter = new CustomerListAdapter(callback);


        binding.addFab.setOnClickListener(view -> {
            disposable.add(viewModel.onAddClicked()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        NavDirections action = CustomerListFragmentDirections.actionCustomerListFragmentToAddNewFragment(viewModel.getCustomerId());
                        Navigation.findNavController(view).navigate(action);
                    }));

        });

        disposable.add(viewModel.getCustomers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
                    if (customers == null) {
                        binding.customerList.setVisibility(View.GONE);
                        binding.emptyViewText.setVisibility(View.VISIBLE);
                    } else {
                        binding.customerList.setVisibility(View.VISIBLE);
                        binding.emptyView.setVisibility(View.GONE);
                        binding.customerList.setAdapter(adapter);
                        adapter.submitList(customers);
                    }
                }));

        binding.filterFab.setOnClickListener(view ->
                Snackbar.make(view, "Supposed for filtering customers", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        disposable.clear();
    }
}