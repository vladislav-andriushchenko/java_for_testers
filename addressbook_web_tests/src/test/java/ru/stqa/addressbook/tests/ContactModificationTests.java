package ru.stqa.addressbook.tests;

import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ContactModificationTests extends TestBase {

    @Test
    void canModifyContact() {
        if (app.hmb().getContactCount() == 0) {
            app.contacts().createContact(new ContactData("", "firstName", "lastName", "Address", "14882280", "test@test.test", "", "", "", "", "", ""));
        }
        var oldContacts = app.hmb().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var testData = new ContactData()
                .withFirstName(CommonFunctions.randomString(5))
                .withLastName(CommonFunctions.randomString(5))
                .withAddress(CommonFunctions.randomString(5))
                .withMobile(CommonFunctions.randomString(5))
                .withEmail(CommonFunctions.randomString(5));
        app.contacts().modifyContact(oldContacts.get(index), testData);
        var newContacts = app.hmb().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        Assertions.assertEquals(Set.copyOf(newContacts), Set.copyOf(expectedList));
    }
}
