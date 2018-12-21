package myfab.wildcardenter.com.first_app.models;

import android.widget.ImageView;

public class SellFragmentItemModel {
    int imageSource;
    String details;

    public SellFragmentItemModel(int imageSource, String details) {
        this.imageSource = imageSource;
        this.details = details;
    }

    public SellFragmentItemModel() {
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
