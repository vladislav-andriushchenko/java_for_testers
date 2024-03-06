package model;

public record ContactData(String firstName, String lastName, String address, String phone, String email) {

    public ContactData() {
        this("", "", "", "", "");
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(firstName, this.lastName, this.address, this.phone, this.email);
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(lastName, this.firstName, this.address, this.phone, this.email);
    }

    public ContactData withAddress(String address) {
        return new ContactData(address, this.lastName, this.firstName, this.phone, this.email);
    }

    public ContactData withPhone(String phone) {
        return new ContactData(phone, this.lastName, this.address, this.firstName, this.email);
    }

    public ContactData withEmail(String email) {
        return new ContactData(email, this.lastName, this.address, this.firstName, this.phone);
    }

}