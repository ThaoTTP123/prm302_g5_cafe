package com.group5.cafemngsystem.adapter.admin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.admin.EditOrderStatusActivity;
import com.group5.cafemngsystem.db.entity.Order;
import com.group5.cafemngsystem.db.viewModel.OrderDetailViewModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.setData(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtIndex;
        private TextView txtStatus;
        private TextView txtDate;

        private AppCompatActivity context;
        private OrderDetailViewModel mOrderDetailViewModel;

        private void bindingView(View itemView) {
            txtIndex = itemView.findViewById(R.id.textViewOrderIndex);
            txtStatus = itemView.findViewById(R.id.textViewOrderStatus);
            txtDate = itemView.findViewById(R.id.textViewOrderDate);

            context = (AppCompatActivity) itemView.getContext();
            mOrderDetailViewModel = new ViewModelProvider(context).get(OrderDetailViewModel.class);
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onClickItem);
        }

        private void onClickItem(View view) {
            Order order = orderList.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), EditOrderStatusActivity.class);
            intent.putExtra("order", order);
            view.getContext().startActivity(intent);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView(itemView);
            bindingAction();
        }

        public void setData(Order order) {
            int position = getAdapterPosition();
            txtIndex.setText("Order #" + (position + 1));
            txtStatus.setText(order.getOrderStatus().toString());
            txtDate.setText(order.getDate().toString());
        }
    }
}

