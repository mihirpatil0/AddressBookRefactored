package com.bridgelab.addressbooksystemusingcollections;

/*********************************************************************
 * @author mihir
 *
 * @since 14-July-2021
 * This class has Person details.
 * Like first name, last name, mobile number etc.
 * Also contains getter and setter methods to get and set user details.
 * toString method to print the data as a stream.
 *********************************************************************/

public class PersonDetails
{
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private int zipCode;
    private long phoneNumber;
    private String emailId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "PersonDetails : [" +
                "First-Name ='" + firstName + '\'' +
                ", Last-Name ='" + lastName + '\'' +
                ", Address ='" + address + '\'' +
                ", City ='" + city + '\'' +
                ", State ='" + state + '\'' +
                ", ZipCode =" + zipCode +
                ", Phone-Number =" + phoneNumber +
                ", EmailId ='" + emailId + '\'' +
                ']';
    }
}
