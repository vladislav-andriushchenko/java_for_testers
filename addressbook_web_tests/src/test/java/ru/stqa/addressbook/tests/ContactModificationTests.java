package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ContactModificationTests extends TestBase {

    @Test
    void canModifyContact() {
        if (app.hmb().getContactCount() == 0) {
            app.contacts().createContact(new ContactData()
                    .withFirstName(CommonFunctions.randomString(5))
                    .withLastName(CommonFunctions.randomString(5))
                    .withAddress(CommonFunctions.randomString(5))
                    .withMobile(CommonFunctions.randomString(5))
                    .withEmail(CommonFunctions.randomString(5))
                    .withPhoto(randomFile("src/test/resources/images")));
        }
        var oldContacts = app.hmb().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var testData = new ContactData()
                .withFirstName(CommonFunctions.randomString(5))
                .withLastName(CommonFunctions.randomString(5))
                .withAddress(CommonFunctions.randomString(5))
                .withMobile(CommonFunctions.randomString(5))
                .withEmail(CommonFunctions.randomString(5))
                .withPhoto(randomFile("src/test/resources/images"));
        app.contacts().modifyContact(oldContacts.get(index), testData);
        var newContacts = app.hmb().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.set(index, testData
                .withId(oldContacts.get(index).id()).withPhoto(""));

        Assertions.assertEquals(Set.copyOf(newContacts), Set.copyOf(expectedList));
    }
}
