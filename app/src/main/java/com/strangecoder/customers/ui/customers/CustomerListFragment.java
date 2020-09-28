package com.strangecoder.customers.ui.customers;

import android.app.Application;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.material.snackbar.Snackbar;
import com.strangecoder.customers.MainActivity;
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

        int[] ATTRS = new int[]{android.R.attr.listDivider};

        // Vertical line dividers with margin
        TypedArray a = binding.customerList.getContext().obtainStyledAttributes(ATTRS);
        Drawable divider = a.getDrawable(0);
        int inset = getResources().getDimensionPixelSize(R.dimen.margin_16);
        InsetDrawable insetDivider = new InsetDrawable(divider, inset, 0, inset, 0);
        a.recycle();
        DividerItemDecoration itemDecoration = new DividerItemDecoration(binding.customerList.getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(insetDivider);

        disposable.add(viewModel.getCustomers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
                    if (customers.isEmpty()) {
                        binding.customerList.setVisibility(View.GONE);
                        binding.emptyViewText.setVisibility(View.VISIBLE);
                    } else {
                        binding.customerList.setVisibility(View.VISIBLE);
                        binding.emptyViewText.setVisibility(View.GONE);
                        binding.customerList.setAdapter(adapter);
                        binding.customerList.addItemDecoration(itemDecoration);
                        adapter.submitList(customers);
                    }
                }));

        binding.filterFab.setOnClickListener(view ->
                Snackbar.make(view, "Supposed for filtering customers", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

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

    @Override
    public void onStop() {
        super.onStop();
        disposable.clear();
    }
}