package ru.stqa.addressbook.tests;

import ru.stqa.addressbook.model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModificationTests extends TestBase {

    @Test
    void canModifyContact() {
        if (app.hmb().getContactCount() == 0) {
            app.contacts().createContact(new ContactData("", "firstName", "lastName", "Address", "14882280", "test@test.test", ""));
        }
        var oldContacts = app.hmb().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var testData = new ContactData()
                .withFirstName("modified firstName")
                .withLastName("modified lastName")
                .withAddress("modified address")
                .withPhone("111222333")
                .withEmail("modifiedemail@test.ru");
        app.contacts().modifyContact(oldContacts.get(index), testData);
        var newContacts = app.hmb().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.set(index, testData.withId(oldContacts.get(index).id()));
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }
}
