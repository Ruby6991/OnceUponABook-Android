package com.example.onceuponabook.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.Activities.CheckoutActivity;
import com.example.onceuponabook.Activities.ItemDetailsActivity;
import com.example.onceuponabook.Activities.LoginActivity;
import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.Models.OrderBookDTO;
import com.example.onceuponabook.Models.OrderDTO;
import com.example.onceuponabook.Models.UserDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview= inflater.inflate(R.layout.fragment_profile, container, false);

        final EditText edtTxtupdateFName=rootview.findViewById(R.id.updatefName_et);
        final EditText edtTxtupdateLName=rootview.findViewById(R.id.updatelName_et);
        final EditText edtTxtupdateEmail=rootview.findViewById(R.id.updateemail_et);
        final EditText edtTxtupdatePhoneNo=rootview.findViewById(R.id.updatephone_et);
        final EditText edtTxtupdateDOB=rootview.findViewById(R.id.updatedob_et);
        final EditText edtTxtupdateAddress=rootview.findViewById(R.id.updateaddress_et);
        Button btnUpdateProfile=rootview.findViewById(R.id.update_btn);

        String userEmail= SharedPrefUtility.getInstance(rootview.getContext()).getUserEmail();
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail(userEmail);

        Call<UserDTO> apiClient = APIBuilder.createAuthBuilder(ProfileFragment.this.getActivity()).GetUserDetails(userDTO);
        apiClient.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.code()==401){
                    SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(ProfileFragment.this.getActivity());
                    sharedPref.resetSharedPreferences();
                    Intent intent=new Intent(ProfileFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    UserDTO userDetails = response.body();
                    if(userDetails!=null){
                        edtTxtupdateFName.setText(userDetails.getFirstName());
                        edtTxtupdateLName.setText(userDetails.getLastName());
                        edtTxtupdateEmail.setText(userDetails.getEmail());
                        edtTxtupdatePhoneNo.setText(Integer.toString(userDetails.getPhoneNo()));
                        if(userDetails.getDateOfBirth()!=null){
                            edtTxtupdateDOB.setText(userDetails.getDateOfBirth().toString());
                        }
                        edtTxtupdateAddress.setText(userDetails.getAddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail= SharedPrefUtility.getInstance(rootview.getContext()).getUserEmail();
                final UserDTO userDTO=new UserDTO();
                userDTO.setFirstName(edtTxtupdateFName.getText().toString());
                userDTO.setLastName(edtTxtupdateLName.getText().toString());
                userDTO.setDateOfBirth(new Date(edtTxtupdateDOB.getText().toString()));
                userDTO.setPhoneNo(Integer.parseInt(edtTxtupdatePhoneNo.getText().toString()));
                userDTO.setAddress(edtTxtupdateAddress.getText().toString());
                Call<UserDTO> apiClient = APIBuilder.createAuthBuilder(ProfileFragment.this.getActivity()).UpdateUser(userEmail,userDTO);
                apiClient.enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        if(response.code()==401){
                            SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(ProfileFragment.this.getActivity());
                            sharedPref.resetSharedPreferences();
                            Intent intent=new Intent(ProfileFragment.this.getActivity(),LoginActivity.class);
                            startActivity(intent);
                        }else {
                            UserDTO user = response.body();
                            if (user!=null) {

                                Call<UserDTO> apiClient = APIBuilder.createAuthBuilder(ProfileFragment.this.getActivity()).UpdateUser(userEmail,userDTO);
                                apiClient.enqueue(new Callback<UserDTO>() {
                                    @Override
                                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                                        if(response.code()==401){
                                            SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(ProfileFragment.this.getActivity());
                                            sharedPref.resetSharedPreferences();
                                            Intent intent=new Intent(ProfileFragment.this.getActivity(),LoginActivity.class);
                                            startActivity(intent);
                                        }else {
                                            UserDTO user = response.body();
                                            if (user!=null) {
                                                Toast.makeText(ProfileFragment.this.getActivity(),
                                                        "User Details Successfully Updated!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserDTO> call, Throwable t) {
                                        System.err.println(t.getMessage());
                                    }
                                });

                                Toast.makeText(ProfileFragment.this.getActivity(),
                                        "User Details Successfully Updated!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        System.err.println(t.getMessage());
                    }
                });
            }
        });
        return rootview;
    }

}
