package com.example.onceuponabook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.EnumClasses.PaymentMethod;
import com.example.onceuponabook.Models.OrderDTO;
import com.example.onceuponabook.Models.UserDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etFName;
    private EditText etLName;
    private EditText etAddress;
    private EditText etPhoneNo;
    private Spinner spinnerPayment;
    private TextView tvItemCount;
    private TextView tvTotal;
    private Button btncheckout;

    String clickedPaymentMethod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Checkout");
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        etFName = findViewById(R.id.checkoutFname_et);
        etLName = findViewById(R.id.checkoutLname_et);
        etAddress = findViewById(R.id.checkoutAddress_et);
        etPhoneNo = findViewById(R.id.checkoutPhone_et);
        spinnerPayment = findViewById(R.id.spinner1);


        tvItemCount = findViewById(R.id.checkoutItems_tv);
        tvTotal = findViewById(R.id.TotalAmount_tv);

        btncheckout = findViewById(R.id.checkout_btn);

        Intent intent = getIntent();
        final double total = intent.getExtras().getDouble("orderTotal");
        int itemcount = intent.getExtras().getInt("itemCount");
        final int orderId = intent.getExtras().getInt("orderID");

        tvTotal.setText("US$" + (total));
        tvItemCount.setText("Items : " +(itemcount));


        String[] items = new String[] { "COD", "Paypal", "Card" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        spinnerPayment.setAdapter(adapter);

        spinnerPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                clickedPaymentMethod=(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        String userEmail = SharedPrefUtility.getInstance(CheckoutActivity.this).getUserEmail();
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail(userEmail);

        Call<UserDTO> apiClient = APIBuilder.createAuthBuilder(CheckoutActivity.this).GetUserDetails(userDTO);
        apiClient.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.code()==401){
                    SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(CheckoutActivity.this);
                    sharedPref.resetSharedPreferences();
                    Intent intent=new Intent(CheckoutActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    UserDTO userDetails = response.body();
                    if(userDetails!=null){
                        etFName.setText(userDetails.getFirstName());
                        etLName.setText(userDetails.getLastName());
                        etPhoneNo.setText(Integer.toString(userDetails.getPhoneNo()));
                        etAddress.setText(userDetails.getAddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });

        btncheckout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String address=etAddress.getText().toString();

                if(address.equals(""))
                {
                    Toast.makeText(CheckoutActivity.this, "Please Fill in All the Required Fields",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    OrderDTO confirmOrder=new OrderDTO();
                    confirmOrder.setPaymentMethod(PaymentMethod.valueOf(clickedPaymentMethod));
                    confirmOrder.setTotalAmount(total);
                    Call<OrderDTO> apiClient = APIBuilder.createAuthBuilder(CheckoutActivity.this).UpdateOrderCheckOut(orderId,confirmOrder);
                    apiClient.enqueue(new Callback<OrderDTO>() {
                        @Override
                        public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                            if(response.code()==401){
                                SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(CheckoutActivity.this);
                                sharedPref.resetSharedPreferences();
                                Intent intent=new Intent(CheckoutActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else {
                                OrderDTO order = response.body();
                                if (order!=null) { ;
                                    Toast.makeText(CheckoutActivity.this, "Purchase Successful!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent=new Intent(CheckoutActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderDTO> call, Throwable t) {
                            System.err.println(t.getMessage());
                        }
                    });
                }
            }
        });

    }
}
