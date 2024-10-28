package com.group5.cafemngsystem.db.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.group5.cafemngsystem.db.entity.Drink;
import com.group5.cafemngsystem.db.entity.enum1.Category;
import com.group5.cafemngsystem.db.repository.DrinkRepository;
import com.group5.cafemngsystem.dbo.DrinkDto;

import java.util.List;

public class DrinkViewModel extends AndroidViewModel {

    private DrinkRepository mRepository;
    private final LiveData<List<Drink>> mAllDrinks;

    public DrinkViewModel(Application application) {
        super(application);
        mRepository = new DrinkRepository(application);
        mAllDrinks = mRepository.getAllDrinks();
    }

    public LiveData<List<Drink>> getAllDrinks() {
        return mAllDrinks;
    }

    public void insert(Drink drink) {
        mRepository.insert(drink);
    }

    public LiveData<Drink> getDrinkById(int drinkId) {
        return mRepository.getDrinkById(drinkId);
    }

    public void delete(DrinkDto drinkDto) {
        String name = drinkDto.getName();
        int img = drinkDto.getImg();
        double price = drinkDto.getPrice();
        Category category = drinkDto.getCategory();
        Drink drink = new Drink(name, img, price, category);
        drink.setId(drinkDto.getId());
        mRepository.delete(drink);
    }

    public void update(Drink drink) {
        mRepository.update(drink);
    }
}
