package com.example.metroapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.metroapplication.R;
import com.example.metroapplication.apis.ApiClient;
import com.example.metroapplication.apis.ApiInterface;
import com.example.metroapplication.apis.apiModel.MobileVerification;
import com.example.metroapplication.apis.apiModel.MobileVerificationData;
import com.example.metroapplication.apis.apiModel.OtpResponse;
import com.example.metroapplication.constants.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OtpActivity extends AppCompatActivity {

    Button back, verify;
    EditText editText;
    MobileVerification mobileVerification;
    MobileVerificationData mvData;


    String oTP, tokenID;

    ApiInterface apiInterface;

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
        setContentView(R.layout.activity_otp);

        back = findViewById(R.id.backBtn);
        verify = findViewById(R.id.verifyBtn);

        oTP = getIntent().getStringExtra("otp");
        tokenID = getIntent().getStringExtra("tokenId");
        editText = findViewById(R.id.mobile);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressdialog = ProgressDialog.show(
                        OtpActivity.this, "Please wait",
                        "Loading please wait..", true);
                progressdialog.show();
                progressdialog.setCancelable(true);

                loadData();

                mobileVerification = new MobileVerification(1, tokenID, mvData);

                if (oTP.equals(editText.getText().toString())) {

                    apiInterface = ApiClient.getClient().create(ApiInterface.class);

                    Call<OtpResponse> call = apiInterface.validateMobile(mobileVerification);

                    call.enqueue(new Callback<OtpResponse>() {
                        @Override
                        public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {

                            OtpResponse otpResponse = response.body();
                            if (otpResponse.getStatus() == 200) {
                                progressdialog.dismiss();
                                Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(OtpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<OtpResponse> call, Throwable t) {
                            progressdialog.dismiss();

                        }
                    });


//                Call<JsonElement> call = apiInterface.registerUser(registration);
//                call.enqueue(new Callback<JsonElement>() {
//                    @Override
//                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//

//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonElement> call, Throwable t) {
//
//                    }
//                });


                } else {
                    Toast.makeText(OtpActivity.this, "OTP Entered not matched", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void loadData() {
        int otp = Integer.parseInt(editText.getText().toString());
        mvData = new MobileVerificationData(otp, Constants.imei, Constants.ipAddress);

    }
}
