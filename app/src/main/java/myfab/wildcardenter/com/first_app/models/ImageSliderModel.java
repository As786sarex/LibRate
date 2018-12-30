package myfab.wildcardenter.com.first_app.models;

public class ImageSliderModel {
    String slider_Image_Urls;

    public ImageSliderModel(String slider_Image_Urls) {
        this.slider_Image_Urls = slider_Image_Urls;
    }

    public ImageSliderModel() {
    }

    public String getSlider_Image_Urls() {
        return slider_Image_Urls;
    }

    public void setSlider_Image_Urls(String slider_Image_Urls) {
        this.slider_Image_Urls = slider_Image_Urls;
    }
}
