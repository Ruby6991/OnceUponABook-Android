package com.example.onceuponabook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.Activities.HistoryItemsActivity;
import com.example.onceuponabook.Activities.LoginActivity;
import com.example.onceuponabook.EnumClasses.OrderStatus;
import com.example.onceuponabook.Models.OrderDTO;
import com.example.onceuponabook.Models.UserDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;

import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {



    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_history, container, false);

        final ListView lvHistory=rootView.findViewById(R.id.listview_history);
        String userEmail= SharedPrefUtility.getInstance(rootView.getContext()).getUserEmail();
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail(userEmail);

        final Call<List<OrderDTO>> apiClient = APIBuilder.createAuthBuilder(getActivity()).getOrderListByUser(userDTO);
        apiClient.enqueue(new Callback<List<OrderDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                if(response.code()==401){
                    SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(getActivity());
                    sharedPref.resetSharedPreferences();
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    List<OrderDTO> orderList = response.body();

                    if(orderList.size()>0) {
                        String[] arr=new String[orderList.size()];
                        Arrays.fill(arr, "");
                        int i=0;
                        for(final OrderDTO order: orderList){
                            if(order.getStatus().equals(OrderStatus.Confirmed)){
                                String cartId="Cart ID : "+Long.toString(order.getId())+" ";
                                String date="Purchased Date : "+order.getPurchasedDate();
                                String total="Total : Rs."+Double.toString(order.getTotalAmount());
                                arr[i]=cartId+"\n"+date+"\n"+total;
                                i++;

                            }else{
                                String[] noCarts=new String[1];
                                noCarts[0]="You haven't made any Purchases!";
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, noCarts);

                                lvHistory.setAdapter(adapter);
                            }
                        }
                        if(arr[0]!=null ){
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_list_item_1, android.R.id.text1, arr);

                            lvHistory.setAdapter(adapter);

                            lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // ListView Clicked item index
                                    int itemPosition= position;

                                    // ListView Clicked item value
                                    String  itemValue= (String) lvHistory.getItemAtPosition(position);
                                    String[] arrSplit=itemValue.split(" ");
                                    String cartid=arrSplit[3];

                                    Intent intent = new Intent(getActivity(), HistoryItemsActivity.class);
                                    intent.putExtra("cartID",cartid);
                                    startActivity(intent);

                                }
                            });
                        }
                    }else{
                        String[] noCarts=new String[1];
                        noCarts[0]="You haven't made any Purchases!";
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, noCarts);

                        lvHistory.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });
        return rootView;
    }
}
