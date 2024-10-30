package com.group5.cafemngsystem.adapter.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.group5.cafemngsystem.customer.DrinkDetailActivity;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.db.viewModel.OrderDetailViewModel;
import com.group5.cafemngsystem.dbo.OrderDetailDto;
import com.group5.cafemngsystem.dbo.OrderDto;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private OnItemChangeListener mListener;

    public interface OnItemChangeListener {
        void onItemChanged();  // Customize as per your data type
    }

    private List<OrderDetailDto> orderDetailList;
    private int orderId;

    public OrderDetailAdapter(List<OrderDetailDto> orderDetailList, int orderId, OnItemChangeListener listener) {
        this.orderDetailList = orderDetailList;
        this.orderId = orderId;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.checkout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailDto orderDetailDto = orderDetailList.get(position);

        holder.setData(orderDetailDto);
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgDrink;
        private TextView txtODName, txtTopping, txtSize, txtCOQuantity;
        private Button btnCOMinus, btnCOPlus, btnCODelete;

        private OrderDetailDto orderDetail;

        private SharedPreferences sharedPreferences;
        private SharedPreferences.Editor editor;
        private Gson gson;

        private OrderDetailViewModel mOrderDetailViewModel;

        private Context context;


        private void bindingView(View itemView) {
            imgDrink = itemView.findViewById(R.id.imgDrink);
            txtODName = itemView.findViewById(R.id.txtODName);
            txtTopping = itemView.findViewById(R.id.txtTopping);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtCOQuantity = itemView.findViewById(R.id.txtCOQuantity);
            btnCOMinus = itemView.findViewById(R.id.btnCOMinus);
            btnCOPlus = itemView.findViewById(R.id.btnCOPlus);
            btnCODelete = itemView.findViewById(R.id.btnCODelete);

            gson = new Gson();
            sharedPreferences = itemView.getContext().getSharedPreferences("cafemngsystem", Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();

            context = itemView.getContext();
            mOrderDetailViewModel = new ViewModelProvider((AppCompatActivity) context).get(OrderDetailViewModel.class);
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onClickItem);
            btnCOMinus.setOnClickListener(this::onClickBtnMinus);
            btnCOPlus.setOnClickListener(this::onClickBtnPlus);
            btnCODelete.setOnClickListener(this::onClickBtnDelete);
        }

        private void onClickBtnMinus(View view) {
            int quantity = orderDetail.getQuantity();
            quantity--;
            if (quantity > 0) {
                orderDetail.setQuantity(quantity);

                mOrderDetailViewModel.update(orderDetail, orderId);
                updateData();
            } else {
                mOrderDetailViewModel.delete(orderDetail);
                orderDetailList.remove(getAdapterPosition());
                updateData();
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), orderDetailList.size());
            }
            txtCOQuantity.setText(String.valueOf(orderDetail.getQuantity()));
        }

        private void onClickBtnDelete(View view) {
            orderDetailList.remove(getAdapterPosition());
            updateData();
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), orderDetailList.size());
        }

        private void onClickBtnPlus(View view) {
            orderDetail.setQuantity(orderDetail.getQuantity() + 1);
            mOrderDetailViewModel.update(orderDetail, orderId);
            updateData();
        }

        private void updateData() {
            txtCOQuantity.setText(String.valueOf(orderDetail.getQuantity()));

            String orderJson = sharedPreferences.getString("orderDto", null);
            OrderDto order = gson.fromJson(orderJson, OrderDto.class);
            order.setOrderDetails(orderDetailList);
            editor.putString("orderDto", gson.toJson(order));
            editor.apply();

            mListener.onItemChanged();
        }

        private void onClickItem(View view) {
            Intent intent = new Intent(view.getContext(), DrinkDetailActivity.class);
            intent.putExtra("orderDetailDto", orderDetailList.get(getAdapterPosition()));
            intent.putExtra("orderId", orderId);
            view.getContext().startActivity(intent);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView(itemView);
            bindingAction();
        }

        public void setData(OrderDetailDto orderDetail) {

            this.orderDetail = orderDetail;

            imgDrink.setImageResource(orderDetail.getDrink().getImg());
            txtODName.setText(orderDetail.getDrink().getName());
            txtTopping.setText(orderDetail.getTopping().toString());
            txtSize.setText(orderDetail.getSize().toString());
            txtCOQuantity.setText(String.valueOf(orderDetail.getQuantity()));
        }
    }
}

