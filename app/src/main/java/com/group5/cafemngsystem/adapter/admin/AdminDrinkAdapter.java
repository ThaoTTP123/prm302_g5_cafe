package com.group5.cafemngsystem.adapter.admin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.group5.cafemngsystem.admin.CreateDrinkActivity;
import com.group5.cafemngsystem.customer.DrinkDetailActivity;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.db.viewModel.DrinkViewModel;
import com.group5.cafemngsystem.dbo.DrinkDto;

import java.util.List;

public class AdminDrinkAdapter extends RecyclerView.Adapter<AdminDrinkAdapter.ViewHolder> {

    private List<DrinkDto> drinkList;

    public AdminDrinkAdapter(List<DrinkDto> drinkList) {
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public com.group5.cafemngsystem.adapter.admin.AdminDrinkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.admin_table_item, parent, false);
        return new AdminDrinkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDrinkAdapter.ViewHolder holder, int position) {
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
        private ImageButton btnDelete;

        private AppCompatActivity context;
        private DrinkViewModel mDrinkViewModel;

        private void bindingView() {
            imgDrink = itemView.findViewById(R.id.img_drink);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnDelete = itemView.findViewById(R.id.imgBtnDelete);

            context = (AppCompatActivity) itemView.getContext();
            mDrinkViewModel = new ViewModelProvider(context).get(DrinkViewModel.class);
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onClickItem);
            btnDelete.setOnClickListener(this::onClickImgBtnDelete);
        }

        private void onClickImgBtnDelete(View view) {
            DrinkDto drink = drinkList.get(getAdapterPosition());
            mDrinkViewModel.delete(drink);
            notifyItemRemoved(getAdapterPosition());
            Toast.makeText(view.getContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
        }

        private void onClickItem(View view) {
            DrinkDto drinkDto = drinkList.get(getAdapterPosition());
            if (drinkDto == null) return;
            Intent intent = new Intent(view.getContext(), CreateDrinkActivity.class);
            intent.putExtra("drinkDto", drinkDto);
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
            txtPrice.setText("$"+ String.valueOf(drink.getPrice()));
        }
    }
}

