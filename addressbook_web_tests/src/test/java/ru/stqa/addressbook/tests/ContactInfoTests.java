package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.model.ContactData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoTests extends TestBase {

    @Test
    void testPhones() {
        var contacts = app.hmb().getContactList();
        var expected = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
                Stream.of(contact.home(), contact.mobile(), contact.work(), contact.secondary())
                        .filter(s -> s != null && !"".equals(s))
                        .collect(Collectors.joining("\n"))
        ));
        var phones = app.contacts().getPhones();
        Assertions.assertEquals(expected, phones);
    }


    @Test
    void testContactDataHome() {
        var contacts = app.hmb().getContactList();
        var phones = app.contacts().getPhones();
        var addresses = app.contacts().getAddresses();
        var emails = app.contacts().getEmails();

        var expectedPhones = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
                Stream.of(contact.home(), contact.mobile(), contact.work(), contact.secondary())
                        .filter(s -> s != null && !"".equals(s))
                        .collect(Collectors.joining("\n"))));

        var expectedAddresses = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
                Stream.of(contact.address())
                        .filter(s -> s != null && !"".equals(s))
                        .collect(Collectors.joining("\n"))));

        var expectedEmails = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
                Stream.of(contact.email(), contact.email2(), contact.email3())
                        .filter(s -> s != null && !"".equals(s))
                        .collect(Collectors.joining("\n"))));

        Assertions.assertEquals(expectedPhones, phones);
        Assertions.assertEquals(expectedAddresses, addresses);
        Assertions.assertEquals(expectedEmails, emails);
    }

    @Test
    void testContactHomeAgainstContactModify() {
        var contacts = app.hmb().getContactList();
        var contact = contacts.get(4);

        var phones = app.contacts().getPhonesModifyPage(contact);
        var expectedPhones = Stream.of(contact.home(), contact.mobile(), contact.work(), contact.secondary())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));

//        var expectedAddresses = Stream.of(contact.address())
//                .filter(s -> s != null && !"".equals(s))
//                .collect(Collectors.joining("\n"));
//
//        var expectedEmails = Stream.of(contact.email(), contact.email2(), contact.email3())
//                .filter(s -> s != null && !"".equals(s))
//                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expectedPhones, phones);
    }

}

