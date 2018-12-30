package myfab.wildcardenter.com.first_app.models;

public class SellModel {
    private String bookDescription;
    private String bookName;
    private String bookPrice;
    private String forDepartment;
    private String for_Semester;
    private String postId;
    private String postImage;
    private String publisher;

    public SellModel(String postImage, String bookDescription, String bookName,
                     String bookPrice, String forDepartment, String for_Semester, String postId, String publisher) {
        this.bookDescription = bookDescription;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.forDepartment = forDepartment;
        this.for_Semester = for_Semester;
        this.postId = postId;
        this.postImage = postImage;
        this.publisher = publisher;
    }

    public SellModel() {
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getForDepartment() {
        return forDepartment;
    }

    public void setForDepartment(String forDepartment) {
        this.forDepartment = forDepartment;
    }

    public String getFor_Semester() {
        return for_Semester;
    }

    public void setFor_Semester(String for_Semester) {
        this.for_Semester = for_Semester;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
