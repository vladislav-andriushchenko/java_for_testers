package ru.stqa.addressbook.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();
//        for (var firstName : List.of("", "first name")) {
//            for (var lastName : List.of("", "last name")) {
//                for (var address : List.of("", "address")) {
//                    for (var phone : List.of("", "14882280")) {
//                        for (var email : List.of("", "test@test.test")) {
//                            result.add(new ContactData()
//                                    .withFirstName(firstName)
//                                    .withLastName(lastName)
//                                    .withAddress(address)
//                                    .withPhone(phone)
//                                    .withEmail(email)
//                                    .withId("")
//                                    .withPhoto(""));
//                        }
//                    }
//                }
//            }
//        }
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(new File("contacts.json"), new TypeReference<List<ContactData>>() {
        });
        result.addAll(value);
        return result;
    }

    public static List<ContactData> negativeContactProvider() {
        var result = new ArrayList<ContactData>(List.of(
                new ContactData("", "First Name'", "Last Name", "", "", "", "", "", "", "", "", "")
        ));
        return result;
    }

    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContacts(ContactData contact) {
        var oldContacts = app.hmb().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.hmb().getContactList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id())
                .withPhoto(""));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void cannotCreateContactsUI(ContactData contact) {
        var oldContacts = app.contacts().getList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getList();
        Assertions.assertEquals(newContacts, oldContacts);
    }

    @Test
    void canCreateContact() {
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));
        app.contacts().createContact(contact);
    }

    @Test
    void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));
        if (app.hmb().getGroupCount() == 0) {
            app.hmb().createGroup(new GroupData("", "name", "header", "footer"));
        }
        var group = app.hmb().getGroupList().get(0);
        var oldRelated = app.hmb().getContactsInGroup(group);
        app.contacts().createContact(contact, group);
        var newRelated = app.hmb().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }

    @Test
    void canCreateContactInGroupUI() {
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));
        if (app.hmb().getContactCount() == 0) {
            app.contacts().createContact(contact);
        }

        if (app.hmb().getGroupCount() == 0) {
            app.hmb().createGroup(new GroupData("", "name", "header", "footer"));
        }

        var currentContactList = app.hmb().getContactList();
        var group = app.hmb().getGroupList().get(0);
        List<ContactData> contactsInGroup = app.hmb().getContactsInGroup(group);


        if (currentContactList.size() == contactsInGroup.size()) {
            app.contacts().createContact(contact, new GroupData().withName(CommonFunctions.randomString(10)));
        }

        var result = app.contacts().findContactWithoutGroup(currentContactList, contactsInGroup);

        app.contacts().addContactToGroup(result.get(0));
        var newRelated = app.hmb().getContactsInGroup(group);
        Assertions.assertEquals(contactsInGroup.size() + 1, newRelated.size());

    }
}
