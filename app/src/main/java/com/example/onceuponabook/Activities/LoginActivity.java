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

public class LoginActivity extends AppCompatActivity {


    private Button loginButton;
    private TextView newUserTxtView;
    private EditText emailEditTxt,passwordEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton=findViewById(R.id.login_btn);
        newUserTxtView=findViewById(R.id.new_user_txtView);
        emailEditTxt=findViewById(R.id.email_edTxt);
        passwordEditTxt=findViewById(R.id.password_edTxt);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the email & password
                final String email=emailEditTxt.getText().toString();
                final String password=passwordEditTxt.getText().toString();

                if(email.equals("")||password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill in All the Fields", Toast.LENGTH_LONG).show();
                    return;
                }else{

                    final UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(email);
                    userDTO.setPassword(password);
                    Call<UserDTO> responseBodyCall = APIBuilder.createBuilder().createAuthenticationToken(userDTO);
                    responseBodyCall.enqueue(new Callback<UserDTO>() {
                        @Override
                        public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                            UserDTO userDTO = response.body();

                            if(userDTO!=null) {
                                SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(LoginActivity.this);
                                sharedPref.setUserEmail(email);
                                sharedPref.setUserPass(password);
                                sharedPref.setUserName(userDTO.getFirstName() + " " + userDTO.getLastName());
                                sharedPref.setUserToken(userDTO.getJwtToken());
                                sharedPref.setUserRole(UserRole.Customer);

                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                finish();

                                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserDTO> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "User Login Un-Successful!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        newUserTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

}
