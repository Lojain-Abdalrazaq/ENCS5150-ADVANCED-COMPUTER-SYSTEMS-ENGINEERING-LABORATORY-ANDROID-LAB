package com.example.courseprojectdraft_24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
public class MainActivity extends AppCompatActivity {
    Button buttonAPIconnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAPIconnect = (Button) findViewById(R.id.button_connect);
        buttonAPIconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://658582eb022766bcb8c8c86e.mockapi.io/api/mock/rest-apis/encs5150/car-types");
            }
        });
    }
    // for the button text to show the status of the connection
    public void setButtonText(String text) {
        buttonAPIconnect.setText(text);
    }
    public void proceedToLoginAndRegistration(List<Car> cars) {
        addCarData();
        Intent intent = new Intent(MainActivity.this, Login_SignUp.class);
        startActivity(intent);
    }
    public void addCarData(){
        CarDataBaseHelper carDataBaseHelper = new CarDataBaseHelper(MainActivity.this);
        Car car = new Car();
        List<Car> cars = ConnectionAsyncTask.cars;
        for(int i =0; i< cars.size();i++){
            switch (i) {
                case 0:
                    car.setId(cars.get(0).getId());
                    car.setType(cars.get(0).getType());
                    car.setModel("Jeep");
                    car.setPrice(62830);
                    car.setOffers(61950);
                    car.setYear(2023);
                    car.setDistance(500);
                    break;

                case 1:
                    car.setId(cars.get(1).getId());
                    car.setType(cars.get(1).getType());
                    car.setModel("Jeep");
                    car.setPrice(73747);
                    car.setOffers(7047);
                    car.setYear(2023);
                    car.setDistance(200);
                    break;

                case 2:
                    car.setId(cars.get(2).getId());
                    car.setType(cars.get(2).getType());
                    car.setModel("Dodge");
                    car.setPrice(31825);
                    car.setOffers(31825);
                    car.setYear(2020);
                    car.setDistance(1000);
                    break;

                case 3:
                    car.setId(cars.get(3).getId());
                    car.setType(cars.get(3).getType());
                    car.setModel("Tesla");
                    car.setPrice(47630);
                    car.setOffers(46000);
                    car.setYear(2024);
                    car.setDistance(50);
                    break;

                case 4:
                    car.setId(cars.get(4).getId());
                    car.setType(cars.get(4).getType());
                    car.setModel("Lamborghini");
                    car.setPrice(310961);
                    car.setOffers(280500);
                    car.setYear(2019);
                    car.setDistance(2000);
                    break;

                case 5:
                    car.setId(cars.get(5).getId());
                    car.setType(cars.get(5).getType());
                    car.setModel("Tesla");
                    car.setPrice(42590);
                    car.setOffers(37590);
                    car.setYear(2021);
                    car.setDistance(1500);
                case 6:
                    car.setId(cars.get(6).getId());
                    car.setType(cars.get(6).getType());
                    car.setModel("Ford");
                    car.setPrice(30920);
                    car.setOffers(28500);
                    car.setYear(2024);
                    car.setDistance(250);
                    break;
                case 7:
                    car.setId(cars.get(7).getId());
                    car.setType(cars.get(7).getType());
                    car.setModel("Ford");
                    car.setPrice(29500);
                    car.setOffers(2500);
                    car.setYear(2018);
                    car.setDistance(2500);
                    break;
                case 8:
                    car.setId(cars.get(8).getId());
                    car.setType(cars.get(8).getType());
                    car.setModel("Alpine A110");
                    car.setPrice(70000);
                    car.setOffers(65000);
                    car.setYear(2019);
                    car.setDistance(200);
                    break;
                case 9:
                    car.setId(cars.get(9).getId());
                    car.setType(cars.get(9).getType());
                    car.setModel("Tesla");
                    car.setPrice(80569);
                    car.setOffers(77385);
                    car.setYear(2016);
                    car.setDistance(4000);
                    break;
                case 10:
                    car.setId(cars.get(10).getId());
                    car.setType(cars.get(10).getType());
                    car.setModel("Honda");
                    car.setPrice(24952);
                    car.setOffers(20753);
                    car.setYear(2017);
                    car.setDistance(5000);
                    break;
                case 11:
                    car.setId(cars.get(11).getId());
                    car.setType(cars.get(11).getType());
                    car.setModel("Tesla");
                    car.setPrice(20268);
                    car.setOffers(18395);
                    car.setYear(2012);
                    car.setDistance(4000);
                    break;
                case 12:
                    car.setId(cars.get(12).getId());
                    car.setType(cars.get(12).getType());
                    car.setModel("Toyota");
                    car.setPrice(32490);
                    car.setOffers(29457);
                    car.setYear(2024);
                    car.setDistance(300);
                    break;
                case 13:
                    car.setId(cars.get(13).getId());
                    car.setType(cars.get(13).getType());
                    car.setModel("Jeep");
                    car.setPrice(65298);
                    car.setOffers(60396);
                    car.setYear(2024);
                    car.setDistance(500);
                    break;
                case 14:
                    car.setId(cars.get(14).getId());
                    car.setType(cars.get(14).getType());
                    car.setModel("Lamborghini");
                    car.setPrice(74707);
                    car.setOffers(70279);
                    car.setYear(2023);
                    car.setDistance(200);
                    break;
                case 15:
                    car.setId(cars.get(15).getId());
                    car.setType(cars.get(15).getType());
                    car.setModel("Lamborghini");
                    car.setPrice(62795);
                    car.setOffers(60593);
                    car.setYear(2022);
                    car.setDistance(500);
                    break;
                case 16:
                    car.setId(cars.get(16).getId());
                    car.setType(cars.get(16).getType());
                    car.setModel("Jeep");
                    car.setPrice(58346);
                    car.setOffers(55824);
                    car.setYear(2020);
                    car.setDistance(399);
                    break;
                case 17:
                    car.setId(cars.get(17).getId());
                    car.setType(cars.get(17).getType());
                    car.setModel("Ford");
                    car.setPrice(47357);
                    car.setOffers(44724);
                    car.setYear(2021);
                    car.setDistance(457);
                    break;
                case 18:
                    car.setId(cars.get(18).getId());
                    car.setType(cars.get(18).getType());
                    car.setModel("Tesla");
                    car.setPrice(52964);
                    car.setOffers(48643);
                    car.setYear(2019);
                    car.setDistance(3400);
                    break;
                case 19:
                    car.setId(cars.get(19).getId());
                    car.setType(cars.get(19).getType());
                    car.setModel("Honda");
                    car.setPrice(70000);
                    car.setOffers(65437);
                    car.setYear(2024);
                    car.setDistance(150);
                    break;
                case 20:
                    car.setId(cars.get(20).getId());
                    car.setType(cars.get(20).getType());
                    car.setModel("Honda");
                    car.setPrice(46784);
                    car.setOffers(43742);
                    car.setYear(2018);
                    car.setDistance(3600);
                    break;
                case 21:
                    car.setId(cars.get(5).getId());
                    car.setType(cars.get(5).getType());
                    car.setModel("Kits");
                    car.setPrice(25234);
                    car.setOffers(22754);
                    car.setYear(2016);
                    car.setDistance(5500);
                    break;
                case 22:
                    car.setId(cars.get(22).getId());
                    car.setType(cars.get(22).getType());
                    car.setModel("Chevrolet");
                    car.setPrice(53273);
                    car.setOffers(49682);
                    car.setYear(2022);
                    car.setDistance(543);
                    break;
                case 23:
                    car.setId(cars.get(23).getId());
                    car.setType(cars.get(23).getType());
                    car.setModel("Jetta");
                    car.setPrice(65734);
                    car.setOffers(61468);
                    car.setYear(2023);
                    car.setDistance(200);
                    break;
                default:
                    break;
            }
            carDataBaseHelper.addCar(car);
        }
    }
}