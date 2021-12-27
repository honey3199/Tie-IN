package com.example.demo;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.data.Status;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.room.model.Vendor;
import com.example.demo.storage.LocalStorage;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    VendorRepository vendorRepository;
    LocalStorage localStorage;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        localStorage = new LocalStorage(this.getApplication());
        vendorRepository = new VendorRepository(this);
        userRepository = new UserRepository(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        List<Vendor> vendors = new ArrayList<>();
        vendors.add(new Vendor("1",
                "Mr. Hemil Shah",
                "Anant Paper Mart",
                "UL-5, Fairdeal House, CG Road, Ahmedabad",
                "",
                "peper, stationary",
                "hemilshah@gmail.com",
                "+919427953343",
                4.5F));

        vendors.add(new Vendor("2",
                "Mr. Bharat Patel",
                "Sanderi Hardware",
                "10,avishkar complex, Ahmedabad",
                "",
                "Hardware",
                "Bharatgami@gmail.com",
                "+919725837414",
                4.3F));

        vendors.add(new Vendor("3",
                "Mr. Karan Sharma",
                "Lijjat Khaman House",
                "Lijjat Khaman House ,Rambaug Kankariya Rd, opposite Kankariya, Prankunj Society, Kankaria, Maninagar, Ahmedabad, Gujarat 380008",
                "lijjat_khaman_house",
                "Street food",
                "Karan@gmail.com",
                "+919104812154",
                4.5F));

        vendors.add(new Vendor("4",
                "Mrs. Jia Bansal",
                "Monginis Cake Shop",
                "13/1, Ground Floor, Badrikashram Complex, Maninagar Cross Roads, Balvatika, Maninagar, Ahmedabad, Gujarat 380008",
                "monginis_cake_shop",
                "Cake, cookies, pestries",
                "Jia@yahoo.com",
                "+919898287893",
                4.1F));

        vendors.add(new Vendor("5",
                "Mr. Raghav Bansal",
                "Farki",
                "Farki,Krishna Kanan Complex, Near brts Bus Stand, Lala Lajpat Rai Marg, Near Krishna Baug, Maninagar, Ahmedabad, Gujarat 380008",
                "farki",
                "Dessert,Milkshake,Lassi",
                "raghav@yahoo.com",
                "+917405013252",
                4.0F));

        vendors.add(new Vendor("6",
                "Mr. Keshav Singh",
                "Mithai and More",
                "bus stop, Unit 3 Bhavin Complex, Opposite Jawaharchowk Chowk BRTS, Bhairavnath Rd, Maninagar, Ahmedabad, Gujarat 380008",
                "mithai_and_more",
                "Sweets",
                "keshav@gmail.com",
                "+919375054051",
                4.1F));
        vendors.add(new Vendor("7",
                "Mr. Karan Joshi",
                "Jai Bhavani Vadapav",
                "Municipal Market, Chimanlal Girdharlal Rd, Mithakhali, Navrangpura, Ahmedabad, Gujarat 380009",
                "jay_bhavani",
                "Steet food",
                "karanjoshi@gmail.com",
                "+919104812154",
                3.3F));
        vendors.add(new Vendor("8",
                "Mr. Gopal Shah",
                "Gopal Emporium Private Limited",
                "Gopal Tower, 107-108-109-107, Lala Lajpat Rai Marg, Pushpkunj, Maninagar, Ahmedabad, Gujarat 380008",
                "gopal",
                "Clothes",
                "gopal@gmail.com",
                "+919327170171",
                2.8F));
        vendors.add(new Vendor("9",
                "Mr. Shrinath Shah",
                "Shreenath Emporium",
                "Gopal Tower, 120, Station Road, Maninagar, Ahmedabad, Gujarat 380008",
                "shreenath_emporium",
                "clothes",
                "shrinath@gmail.com",
                "+919898685622",
                4.1F));
        vendors.add(new Vendor("10",
                "Mr. Ram Kapoor",
                "Kim's Collection",
                "Opp. Nr.Jain Derasar Temple, Maninagar East, Ahmedabad, Gujarat 380008",
                "kims_collection",
                "Clothes",
                "ram@gmail.com",
                "+919374658392",
                3.2F));

        vendors.add(new Vendor("11",
                "Mr. Kundra Shah",
                "Muskan Gift - The Utility Shop",
                "celler 4 gopal plaza,, opp HDFC bank ,, maninagar, Ahmedabad, Gujarat 380008",
                "muskan_gift",
                "Gifts",
                "kundra@gmail.com",
                "+919558919847",
                2.8F));
        vendors.add(new Vendor("12",
                "Mr. Daya Shah",
                "Feelings",
                "Kesharkunj Complex, Krishna Baug Cross Road, Maninagar, Ahmedabad - 380008,",
                "feelings",
                "Gifts",
                "daya@gmail.com",
                "+919104812154",
                3.3F));

        vendors.add(new Vendor("13",
                "Mr.Haresh Nayi",
                "Indrani",
                "Dev Arcade, 8,nr.naranpura railway crossing, Ahmedabad-380013",
                "",
                "Woman's Cloths",
                "indrani08@gmail.com",
                "+07927681116",
                1.2F));

        vendors.add(new Vendor("14",
                "Mr.Suresh Patel",
                "Sanderi Electronics",
                "4,avishkar Complex,Motera,Ahmedabad-380005",
                "",
                "Electronics",
                "sanderielectronics@gmail.com",
                "+91910469625",
                2.6F));


        vendorRepository.insertVendors(vendors);

        new Handler().postDelayed(() -> {
            Intent mainIntent;
            if (localStorage.getPhone() != null && localStorage.getPassword() != null && userRepository.login(localStorage.getPhone(), localStorage.getPassword()) == Status.SUCCESS)
                mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
            else
                mainIntent = new Intent(SplashActivity.this, SignInActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}