package myfab.wildcardenter.com.first_app.helperClasses;

public class FireStoreDeletePictures {
    private static  FireStoreDeletePictures instance=null;

    private FireStoreDeletePictures() {
    }
    public static FireStoreDeletePictures getInstance(){
        if(instance==null){
            instance=new FireStoreDeletePictures();
            return instance;
        }
        else{
            return instance;
        }
    }
    public void deletePreviousPicture(String picName,String pathname){

    }
}
