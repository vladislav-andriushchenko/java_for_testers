package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class AddressBookRemovalTests extends TestBase {
    @Test
    public void canRemoveAddressBook() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createAddressBook(new ContactData("firstName", "lastName", "Address", "14882280", "test@test.test"));
        }
        app.contacts().removeContact();
    }
}


