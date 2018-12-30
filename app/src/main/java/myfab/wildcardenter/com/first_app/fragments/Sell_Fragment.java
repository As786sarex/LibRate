package myfab.wildcardenter.com.first_app.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.adapters.ImageSliderAdapter;
import myfab.wildcardenter.com.first_app.adapters.Sell_fragment_adapter;
import myfab.wildcardenter.com.first_app.models.ImageSliderModel;
import myfab.wildcardenter.com.first_app.models.SellModel;


public class Sell_Fragment extends Fragment {

    ShimmerRecyclerView recyclerView;
    private List<SellModel> models;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    Sell_fragment_adapter adapter;
    private List<ImageSliderModel> sliderModels;
    private ViewPager viewPager;
    private ImageSliderAdapter sliderAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        models = new ArrayList<>();
        recyclerView = view.findViewById(R.id.sell_recyclerView);
        viewPager=view.findViewById(R.id.ViewPagerSlider);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, GridLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new Sell_fragment_adapter(getContext(), models);
        recyclerView.setAdapter(adapter);
        recyclerView.showShimmerAdapter();
        if (firebaseUser != null) {
            readSellItemList();
        }
        sliderModels=new ArrayList<>();
        sliderAdapter=new ImageSliderAdapter(getContext(),sliderModels);
        viewPager.setAdapter(sliderAdapter);
        viewPager.setPadding(0,10,0,10);
        getImagesFromDatabase();
        return view;
    }

    private void getImagesFromDatabase() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("ImageSliderImages");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sliderModels.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ImageSliderModel sliderModel=snapshot.getValue(ImageSliderModel.class);
                    sliderModels.add(sliderModel);
                }
                sliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readSellItemList() {
        reference = FirebaseDatabase.getInstance().getReference("Sell_posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SellModel sellItems = snapshot.getValue(SellModel.class);
                    if (!sellItems.getPublisher().equals(firebaseUser.getUid())) {
                        models.add(sellItems);
                    }
                }
                recyclerView.hideShimmerAdapter();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error in database fetching", Toast.LENGTH_SHORT).show();

            }
        });


    }

}
