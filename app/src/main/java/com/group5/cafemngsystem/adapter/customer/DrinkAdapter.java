package com.group5.cafemngsystem.adapter.customer;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group5.cafemngsystem.customer.DrinkDetailActivity;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.dbo.DrinkDto;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {

    private List<DrinkDto> drinkList;

    public DrinkAdapter(List<DrinkDto> drinkList) {
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.table_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrinkDto drink = drinkList.get(position);
        holder.setData(drink);
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgDrink;
        private TextView txtName;
        private TextView txtPrice;

        private void bindingView() {
            imgDrink = itemView.findViewById(R.id.img_drink);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onClickItem);
        }

        private void onClickItem(View view) {
            DrinkDto drink = drinkList.get(getAdapterPosition());
            if (drink == null) return;
            Intent intent = new Intent(view.getContext(), DrinkDetailActivity.class);
            intent.putExtra("drink", drink);
            view.getContext().startActivity(intent);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setData(DrinkDto drink) {
            imgDrink.setImageResource(drink.getImg());
            txtName.setText(drink.getName());
            txtPrice.setText(String.valueOf(drink.getPrice())+" $");
        }
    }
}
