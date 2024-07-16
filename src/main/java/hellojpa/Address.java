package hellojpa;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }

    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipCode;
    }

    public String getCity() {return city;}
    public String getStreet() {return street;}
    public String getZipCode() {return zipcode;}

    private void setCity(String city) {this.city = city;}

    private void setStreet(String street) {this.street = street;}

    private void setZipcode(String zipcode) {this.zipcode = zipcode;}
}
