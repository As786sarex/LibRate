package myfab.wildcardenter.com.first_app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import myfab.wildcardenter.com.first_app.R;
public class MyWishlistItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_my_wishlist_item, container, false);

        return view;
    }

}
