package com.example.onceuponabook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.EnumClasses.UserRole;
import com.example.onceuponabook.Models.UserDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private Button signUpButton;
    private EditText fNameEditTxt,lNameEditTxt,emailEditTxt,phoneNoEditTxt,addressEditTxt,passwordEditTxt,confirmPswEditTxt;
    private TextView oldUserTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton=findViewById(R.id.sign_up_btn);

        fNameEditTxt=findViewById(R.id.fName_edTxt);
        lNameEditTxt=findViewById(R.id.lName_edTxt);
        emailEditTxt=findViewById(R.id.email_edTxt);
        phoneNoEditTxt=findViewById(R.id.phone_no_edTxt);
        addressEditTxt=findViewById(R.id.address_edTxt);
        passwordEditTxt=findViewById(R.id.password_edTxt);
        confirmPswEditTxt=findViewById(R.id.confirm_psw_edTxt);

        oldUserTxtView=findViewById(R.id.old_user_txtView);

        oldUserTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fName=fNameEditTxt.getText().toString();
                final String lName=lNameEditTxt.getText().toString();
                int phoneNo=Integer.parseInt(phoneNoEditTxt.getText().toString());
                final String email=emailEditTxt.getText().toString();
                String address=addressEditTxt.getText().toString();
                final String password=passwordEditTxt.getText().toString();
                String confirmPsw=confirmPswEditTxt.getText().toString();

                //check if any of the fields are vacant
                if(fName.equals("")||lName.equals("")||email.equals("") ||phoneNoEditTxt.getText().toString().equals("")||address.equals("")
                        ||password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill in All the Fields", Toast.LENGTH_LONG).show();
                    return;
                }else if(!password.equals(confirmPsw)){
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    final UserDTO userDTO = new UserDTO(fName,lName,phoneNo,email,address,password,UserRole.Customer);
                    Call<Boolean> apiClient = APIBuilder.createBuilder().registerUser(userDTO);
                    apiClient.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean status = response.body();

                            if(status){
                                Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();

                                Call<UserDTO> responseBodyCall = APIBuilder.createBuilder().createAuthenticationToken(userDTO);
                                responseBodyCall.enqueue(new Callback<UserDTO>() {
                                    @Override
                                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                                        UserDTO userDTO = response.body();

                                        if(userDTO!=null) {
                                            SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(SignUpActivity.this);
                                            sharedPref.setUserEmail(email);
                                            sharedPref.setUserPass(password);
                                            sharedPref.setUserName(userDTO.getFirstName() + " " + userDTO.getLastName());
                                            sharedPref.setUserToken(userDTO.getJwtToken());
                                            sharedPref.setUserRole(UserRole.Customer);

                                            Toast.makeText(SignUpActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                            finish();

                                            Intent intent=new Intent(SignUpActivity.this,HomeActivity.class);
                                            startActivity(intent);

                                        }else{
                                            Toast.makeText(SignUpActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserDTO> call, Throwable t) {
                                        Toast.makeText(SignUpActivity.this, "User Login Un-Successful!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{
                                Toast.makeText(SignUpActivity.this, "Sign up Failed", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            System.err.println("error "+t.getMessage());
                            Toast.makeText(SignUpActivity.this, "Sign up Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}
