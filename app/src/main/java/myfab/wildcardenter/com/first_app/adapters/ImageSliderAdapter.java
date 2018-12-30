package myfab.wildcardenter.com.first_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.models.ImageSliderModel;

public class ImageSliderAdapter extends PagerAdapter {
    private Context context;
    private List<ImageSliderModel> list;
    private LayoutInflater inflater;

    public ImageSliderAdapter(Context context, List<ImageSliderModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.image_slider_item_layout,container,false);
        KenBurnsView kenBurnsView=view.findViewById(R.id.ImageSliderImage);
       // RandomTransitionGenerator generator=new RandomTransitionGenerator();
       // kenBurnsView.setTransitionGenerator(generator);
        kenBurnsView.pause();
        Picasso.with(context).load(list.get(position).getSlider_Image_Urls()).fit().into(kenBurnsView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
