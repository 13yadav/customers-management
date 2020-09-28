package com.strangecoder.customers.ui.details;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

    @Override
    public void onStop() {
        super.onStop();
        mDisposable.clear();
    }
}