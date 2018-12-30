package myfab.wildcardenter.com.first_app.models;

public class UserModel {
    private String userId;
    private String username;
    private String email;
    private String imageUri;
    private String contactNumber;
    private String profileImgDbName;

    public UserModel(String userId, String username, String email, String imageUri,String contactNumber,String profileImgDbName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.imageUri = imageUri;
        this.contactNumber=contactNumber;
        this.profileImgDbName=profileImgDbName;
    }

    public UserModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getProfileImgDbName() {
        return profileImgDbName;
    }

    public void setProfileImgDbName(String profileImgDbName) {
        this.profileImgDbName = profileImgDbName;
    }
}
