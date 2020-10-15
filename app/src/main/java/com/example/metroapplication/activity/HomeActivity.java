package com.example.metroapplication.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.metroapplication.R;
import com.example.metroapplication.helper.IncreamentDecreament;
import com.example.metroapplication.myDataBase.MYdb;
import com.example.metroapplication.utils.MenuActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends MenuActivity {

    Spinner fromSpinner, toSpinner;
    Button addAdult, minusAdult, previewBtn, back;
    TextView adultCount, adultFare, discountFare, totalFare, actualFare;
    TextView joourneyTIck, amountToPay;

    float disc, Actual;
    float Total;
    int adult;

    String jType;

    int fare = 40;

    RadioButton singleTrip, returnTrip;

    IncreamentDecreament mID;

    @Override       // for font style
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/caviar_dreams.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Ticket");
        } else {
            getSupportActionBar().setTitle("Ticket");
        }

        mID = new IncreamentDecreament(this);

        singleTrip = findViewById(R.id.single_radio_home);
        returnTrip = findViewById(R.id.return_radio_home);
        addAdult = findViewById(R.id.increase_adult_home);
        minusAdult = findViewById(R.id.decrease_adult_home);

        adultCount = findViewById(R.id.adult_number_home);

        previewBtn = findViewById(R.id.preview_home);
        adultFare = findViewById(R.id.adult_amount_home);

        discountFare = findViewById(R.id.discounted_fare_home);
        totalFare = findViewById(R.id.total_amount_home);
        actualFare = findViewById(R.id.actual_amount_home);
        back = findViewById(R.id.back_home);
        joourneyTIck = findViewById(R.id.juorneyTick);
        fromSpinner = findViewById(R.id.spinner_from);
        toSpinner = findViewById(R.id.spinner_to);
        amountToPay = findViewById(R.id.amounttopay_home);

        loadSpinnerData();


        int type = getIntent().getIntExtra("type", 0);

        if (type == 2) {
            returnTrip.setChecked(true);
            jType="RJT";

        }else
        {jType="SJT";}

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams_Bold.ttf");
        joourneyTIck.setTypeface(typeface);
        actualFare.setTypeface(typeface);
        amountToPay.setTypeface(typeface);

        adultFare.setText("Rs 0.00");

        totalFare.setText("Rs 0.00");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mID.increase(adultCount);
                UpdateFare();
            }
        });

        minusAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mID.decrease(adultCount);
                UpdateFare();

            }
        });

        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isvalidation()) {

                    int adultCounting= Integer.parseInt(adultCount.getText().toString().trim());

                    Intent intent = new Intent(HomeActivity.this, JourneyPreviewActivity.class);
                    intent.putExtra("count", adult);
                    intent.putExtra("total", Total);
                    intent.putExtra("tiketType", jType);
                    intent.putExtra("disc", disc);
                    intent.putExtra("fare",fare);
                    intent.putExtra("amount", Actual);
                    startActivity(intent);
                }

            }
        });

    }

    private void UpdateFare() {
        adult = Integer.parseInt(adultCount.getText().toString());
        float adultFare = fare * adult;
         Total = adultFare;
        disc = Total * 10 / 100;
        Actual = Total - disc;

        this.adultFare.setText(String.format("Rs %.2f", adultFare));

        this.totalFare.setText(String.format("Rs %.2f", Total));
        this.discountFare.setText(String.format("Rs %.2f", disc));
        this.actualFare.setText(String.format("Rs %.2f", Actual));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public boolean isvalidation() {

        String name1 = fromSpinner.getSelectedItem().toString();
        String name2 = toSpinner.getSelectedItem().toString();

        if (name1.equals("Null")) {
            ((TextView) fromSpinner.getChildAt(0)).setError("Select Location");
            return false;
        }
        if (name2.equals("Null")) {
            ((TextView) toSpinner.getSelectedView()).setError("Select Location");
            return false;
        }

        return true;
    }

    private void loadSpinnerData() {
        MYdb db = new MYdb(getApplicationContext());
        List<String> labels = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        fromSpinner.setSelection(0);
        toSpinner.setSelection(0);
        fromSpinner.setAdapter(dataAdapter);
        toSpinner.setAdapter(dataAdapter);

    }
}
