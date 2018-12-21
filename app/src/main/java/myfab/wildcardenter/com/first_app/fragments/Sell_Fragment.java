package myfab.wildcardenter.com.first_app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.adapters.Sell_fragment_adapter;
import myfab.wildcardenter.com.first_app.models.SellFragmentItemModel;


public class Sell_Fragment extends Fragment {
    RecyclerView recyclerView;
    private List<SellFragmentItemModel> models;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sell_, container, false);
        models=new ArrayList<>();
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        models.add(new SellFragmentItemModel(R.drawable.mind6,"Te Amo my love.."));
        recyclerView=view.findViewById(R.id.sell_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayout.VERTICAL,false));
        recyclerView.setAdapter(new Sell_fragment_adapter(getContext(),models));

        return  view;
    }

}
