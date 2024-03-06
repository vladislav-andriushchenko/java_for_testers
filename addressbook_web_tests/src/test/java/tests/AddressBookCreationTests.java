package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class AddressBookCreationTests extends TestBase {

    @Test
    public void canCreateAddressBook() {
        app.contacts().createAddressBook(new ContactData("firstName", "lastName", "Address", "14882280", "test@test.test"));
    }

    @Test
    public void canCreateAddressBookWithEmptyName() {
        app.contacts().createAddressBook(new ContactData().withFirstName("some name"));
    }
}
