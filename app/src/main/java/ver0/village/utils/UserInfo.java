package ver0.village.utils;

import android.util.Log;

public class UserInfo {
    private Universe universityInfo;
    private String userName;
    private String phoneNumber;
    private String userNickname;
    private String userEmail;
    private int userKey;

    public void logprtint(){
        Log.d("UserInfo -- ", "universityinfo: "+ universityInfo.getUniverseName()+"//" + universityInfo.getUniverseDomain() +"//"+ universityInfo.getAddress() +
                "//"+universityInfo.getUniverseKey()+"\nuserName: " +
                userName +"\nuserNickname: " + userNickname + "\nphoneNumber: " + phoneNumber + "\n");
    }

    public Universe getUniversityInfo() {return universityInfo;}

    public void setUniversityInfo(Universe universityInfo) {this.universityInfo = universityInfo;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserKey() {
        return userKey;
    }

    public void setUserKey(int userKey) {
        this.userKey = userKey;
    }
}
