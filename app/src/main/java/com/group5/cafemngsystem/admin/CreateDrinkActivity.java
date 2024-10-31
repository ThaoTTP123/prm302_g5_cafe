package com.group5.cafemngsystem.admin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.db.entity.Drink;
import com.group5.cafemngsystem.db.entity.enum1.Category;
import com.group5.cafemngsystem.adapter.admin.ImageAdapter;
import com.group5.cafemngsystem.db.viewModel.DrinkViewModel;
import com.group5.cafemngsystem.dbo.DrinkDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateDrinkActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 100;

    private boolean create = true;
    private int drinkId;

    private ImageView imgDrink;
    private int imgRsrc;
    private TextView txtAddImage;
    private EditText edtDrinkName;
    private Spinner spinnerCategory;
    private EditText edtPrice;
    private Button btnSave;

    private DrinkViewModel mDrinkViewModel;

    private void bindingView() {
        imgDrink = findViewById(R.id.imageViewDrink);
        txtAddImage = findViewById(R.id.textViewAddImage);
        edtDrinkName = findViewById(R.id.editTextDrinkName);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        edtPrice = findViewById(R.id.editTextDrinkPrice);
        btnSave = findViewById(R.id.buttonSaveDrink);

        setupDataSpinner();

        mDrinkViewModel = new ViewModelProvider(this).get(DrinkViewModel.class);
    }

    private void setupDataSpinner() {
        Category[] enumValues = Category.values();
        String[] items = new String[enumValues.length];
        for (int i = 0; i < enumValues.length; i++) {
            items[i] = enumValues[i].name();  // Use name() or toString() for the enum name
        }

        // Step 2: Set up ArrayAdapter with the String array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Step 3: Apply the adapter to the spinner
        spinnerCategory.setAdapter(adapter);
    }

    ;

    private void bindingAction() {
        imgDrink.setOnClickListener(this::setOnClickImgDrink);
        txtAddImage.setOnClickListener(this::setOnClickImgDrink);
        btnSave.setOnClickListener(this::setOnClickSave);
    }

    private void setOnClickSave(View view) {
        String name = edtDrinkName.getText().toString();
        double price = Double.parseDouble(edtPrice.getText().toString());
        Category category = Category.valueOf(spinnerCategory.getSelectedItem().toString());
        Drink drink = new Drink(name, imgRsrc, price, category);
        if (create)
            mDrinkViewModel.insert(drink);
        else{
            drink.setId(drinkId);
            mDrinkViewModel.update(drink);
        }
        Toast.makeText(this, create ? "Drink added" : "Drink updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setOnClickImgDrink(View view) {
        final int[] imageResources = {
                R.mipmap.drink1,
                R.mipmap.drink2,
                R.mipmap.drink3,
                R.mipmap.drink4,
                R.mipmap.drink5,
                R.mipmap.drink6,
                R.mipmap.drink7,
                R.mipmap.drink8,
                R.mipmap.drink9,
                R.mipmap.drink10,
                R.mipmap.drink11,
                R.mipmap.drink12,
        };

        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_image_picker, null);

        // Set up the grid view
        GridView gridView = dialogView.findViewById(R.id.grid_view);
        ImageAdapter adapter = new ImageAdapter(this, imageResources, this::onImageSelected);
        gridView.setAdapter(adapter);

        // Create the AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Choose an Image")
                .setView(dialogView)
                .create();

        // Show the dialog
        dialog.show();
        txtAddImage.setVisibility(View.GONE);
    }

    public void onImageSelected(int resourceId) {
        // Set the selected image to the ImageView
        imgDrink.setImageResource(resourceId);
        imgRsrc = resourceId;
        Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_drink);
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
        DrinkDto drinkDto = (DrinkDto) getIntent().getSerializableExtra("drinkDto");
        if (drinkDto != null) {
            create = false;
            drinkId = drinkDto.getId();
            imgDrink.setImageResource(drinkDto.getImg());
            imgRsrc = drinkDto.getImg();
            edtDrinkName.setText(drinkDto.getName());
            spinnerCategory.setSelection(drinkDto.getCategory().ordinal());
            edtPrice.setText(String.valueOf(drinkDto.getPrice()));
            txtAddImage.setVisibility(View.GONE);
        }
    }
}