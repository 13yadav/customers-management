package com.strangecoder.customers.ui.customers;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.strangecoder.customers.MainActivity;
import com.strangecoder.customers.database.Customer;
import com.strangecoder.customers.databinding.ListItemCustomerBinding;

class CustomerListAdapter extends ListAdapter<Customer, CustomerListAdapter.ViewHolder> {


    protected CustomerListAdapter(@NonNull DiffUtil.ItemCallback<Customer> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer item = getItem(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(
                Navigation
                        .createNavigateOnClickListener(CustomerListFragmentDirections
                                .actionCustomerListFragmentToDetailsFragment(item.customerId))
        );
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ListItemCustomerBinding binding;

        public ViewHolder(ListItemCustomerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Customer item) {
            binding.companyNameText.setText(item.name);
            binding.cityNameText.setText(item.address);
            binding.phoneText.setText(item.phone);
            binding.executePendingBindings();
        }

        public static ViewHolder from(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ListItemCustomerBinding binding = ListItemCustomerBinding.inflate(inflater, parent, false);
            return new ViewHolder(binding);
        }

    }

    static class CustomerDiffCallback extends DiffUtil.ItemCallback<Customer> {

        @Override
        public boolean areItemsTheSame(@NonNull Customer oldItem, @NonNull Customer newItem) {
            return oldItem.getCustomerId().equals(newItem.getCustomerId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Customer oldItem, @NonNull Customer newItem) {
            return oldItem.equals(newItem);
        }
    }
}
