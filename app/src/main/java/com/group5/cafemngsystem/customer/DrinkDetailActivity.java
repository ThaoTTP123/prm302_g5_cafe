package com.group5.cafemngsystem.customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.db.entity.OrderDetail;
import com.group5.cafemngsystem.db.entity.enum1.Size;
import com.group5.cafemngsystem.db.entity.enum1.Topping;
import com.group5.cafemngsystem.db.viewModel.OrderDetailViewModel;
import com.group5.cafemngsystem.db.viewModel.OrderViewModel;
import com.group5.cafemngsystem.dbo.DrinkDto;
import com.group5.cafemngsystem.dbo.OrderDetailDto;
import com.group5.cafemngsystem.dbo.OrderDto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DrinkDetailActivity extends AppCompatActivity {

    private static final String APP_NAME = "cafemngsystem";

    private OrderDetailDto orderDetailDto;

    private int quantity = 1;
    private double price = 0;
    private int orderId;

    private ImageView imgDink;

    private TextView txtDrinkName;
    private RadioGroup rgSize;
    private RadioButton rdoSmall, rdoMedium, rdoLarge;
    private RadioGroup rgTopping;
    private RadioButton rdoBoba, rdoAlmond, rdoCheese;
    private Button btnMinus, btnPlus, btnAddToCart;

    private TextView txtQuantity;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private OrderDetailViewModel orderDetailViewModel;
    private OrderViewModel mOrderViewModel;

    private void bindingView() {
        imgDink = findViewById(R.id.imgDink);
        txtDrinkName = findViewById(R.id.txtDrinkName);
        rgSize = findViewById(R.id.rgSize);
        rdoSmall = findViewById(R.id.rdoSmall);
        rdoMedium = findViewById(R.id.rdoMedium);
        rdoLarge = findViewById(R.id.rdoLarge);
        rgTopping = findViewById(R.id.rgTopping);
        rdoBoba = findViewById(R.id.rdoBoba);
        rdoAlmond = findViewById(R.id.rdoAlmond);
        rdoCheese = findViewById(R.id.rdoCheese);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        txtQuantity = findViewById(R.id.txtQuantity);

        gson = new Gson();
        sharedPreferences = getSharedPreferences(APP_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        orderDetailViewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);
        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
    }

    private void bindingAction() {
        rgSize.setOnCheckedChangeListener(this::onSizeChanged);
        rgTopping.setOnCheckedChangeListener(this::onToppingChanged);
        btnMinus.setOnClickListener(this::onMinusClicked);
        btnPlus.setOnClickListener(this::onPlusClicked);
        btnAddToCart.setOnClickListener(this::onAddToCartClicked);
    }

    private void onUpdateCartClicked(View view) {
        orderDetailDto.setQuantity(quantity);
        if (orderDetailDto.getQuantity() == 0) {
            updateData();
            orderDetailViewModel.delete(orderDetailDto);
        } else {
            orderDetailViewModel.update(orderDetailDto, orderId);
            updateData();
        }
        finish();
    }

    private void updateData() {
        String orderDtoJson = sharedPreferences.getString("orderDto", null);
        OrderDto orderDto = gson.fromJson(orderDtoJson, OrderDto.class);
        List<OrderDetailDto> orderDetailDtos = orderDto.getOrderDetails();
        for (int i = 0; i < orderDetailDtos.size(); i++) {
            if (orderDetailDtos.get(i).getId() == orderDetailDto.getId()) {
                orderDetailDtos.set(i, orderDetailDto);
                break;
            }
        }
        orderDto.setOrderDetails(orderDetailDtos);
        orderDtoJson = gson.toJson(orderDto);
        editor.putString("orderDto", orderDtoJson);
        editor.apply();
    }

    private void onAddToCartClicked(View view) {
        orderDetailDto.setQuantity(quantity);
        OrderDetail orderDetail = new OrderDetail(
                orderDetailDto.getQuantity(),
                orderDetailDto.getSize(),
                orderDetailDto.getTopping(),
                orderDetailDto.getDrink().getId(),
                -1
        );
        String orderDtoJson = sharedPreferences.getString("orderDto", null);
        OrderDto orderDto = gson.fromJson(orderDtoJson, OrderDto.class);
        OrderDetail orderDetail1 = new OrderDetail(
                quantity,
                orderDetailDto.
                getSize(),
                orderDetailDto.getTopping(),
                orderDetailDto.getDrink().getId(),
                orderId
        );
        List<OrderDetailDto> orderDetailDtos;
        if(orderDto == null){
            orderDto = new OrderDto();
        }
        if (orderDto.getOrderDetails() == null) {
            long orderId = mOrderViewModel.insert(orderDto);
            orderDto.setId((int) orderId);
            orderDetail1.setOrderId((int) orderId);

            orderDetailDtos = new ArrayList<>();
            orderDetailDtos.add(orderDetailDto);
        } else {
            orderDetailDtos = orderDto.getOrderDetails();
            orderDetailDtos.add(orderDetailDto);
            orderDetail1.setOrderId(orderDto.getId());
        }


        orderDetailViewModel.insert(orderDetail1);
        orderDto.setOrderDetails(orderDetailDtos);
        orderDtoJson = gson.toJson(orderDto);
        editor.putString("orderDto", orderDtoJson);
        editor.apply();
        finish();
    }

    private void onMinusClicked(View view) {
        if (quantity >= 1) {
            quantity--;
            txtQuantity.setText(String.valueOf(quantity));
        }
        if (quantity == 0) {
            btnMinus.setEnabled(false);
            btnAddToCart.setEnabled(false);
        }
        orderDetailDto.setQuantity(quantity);
        btnAddToCart.setText("$" + calcalatePrice() + " Cart");
    }

    private void onPlusClicked(View view) {
        quantity++;
        txtQuantity.setText(String.valueOf(quantity));
        btnMinus.setEnabled(true);
        btnAddToCart.setEnabled(true);
        btnAddToCart.setText("$" + calcalatePrice() + " Cart");
        orderDetailDto.setQuantity(quantity);
    }

    private void onToppingChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.rdoNone)
            orderDetailDto.setTopping(Topping.None);
        if (i == R.id.rdoBoba)
            orderDetailDto.setTopping(Topping.Boba);
        if (i == R.id.rdoAlmond)
            orderDetailDto.setTopping(Topping.Almond);
        if (i == R.id.rdoCheese)
            orderDetailDto.setTopping(Topping.Cheese);

        btnAddToCart.setText("$" + calcalatePrice() + " Cart");
    }

    private void onSizeChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.rdoSmall)
            orderDetailDto.setSize(Size.S);
        if (i == R.id.rdoMedium)
            orderDetailDto.setSize(Size.M);
        if (i == R.id.rdoLarge)
            orderDetailDto.setSize(Size.L);


        btnAddToCart.setText("$" + calcalatePrice() + " Cart");
    }

    private String calcalatePrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedNumber = decimalFormat.format((price + orderDetailDto.getTopping().getValue()) * quantity * orderDetailDto.getSize().getValue());
        return formattedNumber;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        receiveIntent();
    }

    private void receiveIntent() {
        DrinkDto drink = (DrinkDto) getIntent().getSerializableExtra("drink");
        if (drink != null) {
            orderDetailDto = new OrderDetailDto();
            orderDetailDto.setDrink(drink);
            orderDetailDto.setSize(Size.S);
            orderDetailDto.setTopping(Topping.None);
            orderDetailDto.setQuantity(1);
            imgDink.setImageResource(drink.getImg());
            txtDrinkName.setText(drink.getName());
            price = drink.getPrice();
            txtQuantity.setText(String.valueOf(quantity));
            btnAddToCart.setText("$" + calcalatePrice() + " Cart");
        } else {
            orderDetailDto = (OrderDetailDto) getIntent().getSerializableExtra("orderDetailDto");
            orderId = getIntent().getIntExtra("orderId", -1);

            imgDink.setImageResource(orderDetailDto.getDrink().getImg());
            txtDrinkName.setText(orderDetailDto.getDrink().getName());
            txtQuantity.setText(String.valueOf(orderDetailDto.getQuantity()));
            price = orderDetailDto.getDrink().getPrice();
            quantity = orderDetailDto.getQuantity();
            btnAddToCart.setOnClickListener(this::onUpdateCartClicked);
            btnAddToCart.setText("$" + calcalatePrice() + " Cart");

        }
    }
}