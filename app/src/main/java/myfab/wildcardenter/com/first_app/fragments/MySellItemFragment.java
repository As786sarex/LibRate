package myfab.wildcardenter.com.first_app.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import myfab.wildcardenter.com.first_app.adapters.MySellItemAdpter;
import myfab.wildcardenter.com.first_app.helperClasses.FireStoreDeletePictures;
import myfab.wildcardenter.com.first_app.models.SellModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySellItemFragment extends Fragment {

    private ShimmerRecyclerView mySellItemRecyclerView;
    private List<SellModel> mylist;
    private FirebaseUser user;
    private MySellItemAdpter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_sell_item, container, false);
        mylist=new ArrayList<>();
        user=FirebaseAuth.getInstance().getCurrentUser();
        mySellItemRecyclerView=view.findViewById(R.id.My_sell_item_recyclerView);
        readMySellItemList();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayout.VERTICAL,false);
        mySellItemRecyclerView.setLayoutManager(layoutManager);
        adapter=new MySellItemAdpter(getContext(),mylist);
        mySellItemRecyclerView.setAdapter(adapter);
        mySellItemRecyclerView.showShimmerAdapter();
        return view;
    }
    private void readMySellItemList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Sell_posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mylist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SellModel sellItems = snapshot.getValue(SellModel.class);
                    if (sellItems.getPublisher().equals(user.getUid())) {
                        mylist.add(sellItems);
                    }
                }
                adapter.notifyDataSetChanged();
                mySellItemRecyclerView.hideShimmerAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error in database fetching", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
