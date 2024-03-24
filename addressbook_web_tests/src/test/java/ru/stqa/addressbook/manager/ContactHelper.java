package ru.stqa.addressbook.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createContact(ContactData contact) {
        openContactPageCreation();
        fillContactForm(contact);
        submitContactCreation();
        openHomePage();
    }

    public void createContact(ContactData contact, GroupData group) {
        openContactPageCreation();
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreation();
        openHomePage();
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }

    public void removeContact(ContactData contact) {
        openHomePage();
        selectContact(contact);
        removeSelectedContacts();
        openHomePage();
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("address"), contact.address());
        type(By.name("mobile"), contact.phone());
        type(By.name("email"), contact.email());
        attach(By.name("photo"), contact.photo());
    }

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openHomePage();
        selectInitialContact(contact);
        fillContactForm(modifiedContact);
        initContactModification();
        openHomePage();
    }

    private void initContactModification() {
        click(By.xpath("(//input[@name=\"update\"])[2]"));
    }

    private void selectInitialContact(ContactData contact) {
        click(By.xpath(String.format("//a[contains(@href, \"%s\")]//*[@title=\"Edit\"]", contact.id())));
    }

    private void openHomePage() {
        click(By.linkText("home"));
    }

    private void submitContactCreation() {
        click(By.xpath("(//input[@name=\"submit\"])[2]"));
    }

    private void removeSelectedContacts() {
        click(By.xpath("//input[@value=\"Delete\"]"));
    }

    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[value='%s']", contact.id())));
    }

    public void openContactPageCreation() {
        click(By.linkText("add new"));
    }

    public int getCount() {
        openHomePage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void removeAllContacts() {
        openHomePage();
        selectAllContacts();
        removeSelectedContacts();
    }

    private void selectAllContacts() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    public List<ContactData> getList() {
        openHomePage();
        var contacts = new ArrayList<ContactData>();
        var entries = manager.driver.findElements(By.cssSelector("[id=\"maintable\"] tr[name=\"entry\"]"));
        for (var entry : entries) {
            var firstName = entry.findElement(By.cssSelector("td:nth-child(3)"));
            var lastName = entry.findElement(By.cssSelector("td:nth-child(2)"));
            var checkbox = entry.findElement(By.name("selected[]"));
            var id = checkbox.getAttribute("id");
            contacts.add(new ContactData()
                    .withId(id)
                    .withFirstName(firstName.getText())
                    .withLastName(lastName.getText()));
        }
        return contacts;
    }
}
