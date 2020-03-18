package com.example.bnb.blood_bank;

public class UserInformation {

 private    String Username,id, Gender, Email, MobileNumber, Date_Birth, UserAddress, Usercity, UserState, UserCountry, userpin, BloodGroup;


    public UserInformation() {


    }

    public UserInformation(String id,String Username, String Gender, String Email, String MobileNumber, String Date_Birth, String UserAddress, String Usercity, String UserState, String UserCountry, String userpin, String BloodGroup) {
        this.Username = Username;
        this.id=id;
        this.Gender = Gender;
        this.Email = Email;

        this.MobileNumber = MobileNumber;
        this.Date_Birth = Date_Birth;
        this.UserAddress = UserAddress;
        this.Usercity = Usercity;
        this.UserState = UserState;
        this.UserCountry = UserCountry;
        this.userpin = userpin;
        this.BloodGroup = BloodGroup;


    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public void setDate_Birth(String date_Birth) {
        Date_Birth = date_Birth;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public void setUsercity(String usercity) {
        Usercity = usercity;
    }

    public void setUserState(String userState) {
        UserState = userState;
    }

    public void setUserCountry(String userCountry) {
        UserCountry = userCountry;
    }

    public void setUserpin(String userpin) {
        this.userpin = userpin;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getUsername() {
        return Username;
    }

    public String getUserid() {
        return id;
    }

    public String getGender() {
        return Gender;
    }

    public String getEmail() {
        return Email;
    }

 //   public String getPassword() {
   //     return Password;
    //}

    //public String getRe_enterPassword() {
      //  return Re_enterPassword;
    //}

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getDate_Birth() {
        return Date_Birth;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public String getUsercity() {
        return Usercity;
    }

    public String getUserState() {
        return UserState;
    }

    public String getUserCountry() {
        return UserCountry;
    }

    public String getUserpin() {
        return userpin;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }
}


