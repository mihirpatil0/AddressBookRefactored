package com.bridgelab.addressbooksystemusingcollections;

import java.util.List;
import java.util.Objects;

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
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private int zipCode;
    private long phoneNumber;
    private String emailId;
    private List<String> groups;

    public PersonDetails()
    {

    }

    public PersonDetails(int id, String firstName, String lastName, String address, String city, String state, int zipCode, long phoneNumber, String emailId, List<String> groups) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.groups = groups;
    }

    public PersonDetails(int id, String first, String last, String address, String city, String state, String zip, int phoneNumber, String email, List<String> groups) { }

    public PersonDetails(int id, String firstName, String lastname, String address, String city, String state, String zip, int phoneNumber, String email) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonDetails other = (PersonDetails) obj;
        return Objects.equals(address, other.address) && Objects.equals(city, other.city)
                && Objects.equals(emailId, other.emailId) && Objects.equals(firstName, other.firstName) && id == other.id
                && Objects.equals(lastName, other.lastName) && phoneNumber == other.phoneNumber
                && Objects.equals(state, other.state) && Objects.equals(zipCode, other.zipCode);
    }
}
