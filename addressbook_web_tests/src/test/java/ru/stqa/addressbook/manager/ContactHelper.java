package ru.stqa.addressbook.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void addContactToGroup(ContactData contact) {
        openHomePage();
        selectContact(contact);
        selectGroupForContact();
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

    private void selectGroupHomePage(GroupData group) {
        new Select(manager.driver.findElement(By.name("group"))).selectByVisibleText(group.name());
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
        type(By.name("mobile"), contact.mobile());
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
        click(By.xpath(String.format("//a[@href='edit.php?id=%s']", contact.id())));
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

    private void removeSelectedContactsFromGroup() {
        click(By.xpath("//input[@name=\"remove\"]"));
    }

    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[value='%s']", contact.id())));
    }

    private void selectGroupForContact() {
        click(By.name("add"));
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

    public void removeContactFromGroup(GroupData group, ContactData contact) {
        openHomePage();
        selectGroupHomePage(group);
        selectContact(contact);
        removeSelectedContactsFromGroup();
    }

    public ArrayList<ContactData> findContactWithoutGroup(List<ContactData> allContacts, List<ContactData> contactsInGroup) {
        var result = new ArrayList<ContactData>();
        for (int i = 0; i < contactsInGroup.size(); i++) {
            var contactInContactList = allContacts.get(i);
            var contactInGroup = contactsInGroup.get(i);
            if (!contactInContactList.id().equals(contactInGroup.id())) {
                result.add(contactInContactList);
                break;
            }
        }
        return result;
    }

    public String getPhones(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../../td[6]", contact.id()))).getText();
    }

    public String getAddresses(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../../td[4]", contact.id()))).getText();
    }

    public String getEmails(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../../td[5]", contact.id()))).getText();
    }

    public Map<String, String> getPhones() {
        var result = new HashMap<String, String>();
        List<WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("id");
            var phones = row.findElements(By.tagName("td")).get(5).getText();
            result.put(id, phones);
        }
        return result;
    }

    public Map<String, String> getAddresses() {
        var result = new HashMap<String, String>();
        List<WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("id");
            var addresses = row.findElements(By.tagName("td")).get(3).getText();
            result.put(id, addresses);
        }
        return result;
    }

    public Map<String, String> getEmails() {
        var result = new HashMap<String, String>();
        List<WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("id");
            var emails = row.findElements(By.tagName("td")).get(4).getText();
            result.put(id, emails);
        }
        return result;
    }

    public String getPhonesModifyPage(ContactData contact) {
        openHomePage();
        selectInitialContact(contact);
        var home = manager.driver.findElement(By.name("home")).getAttribute("value");
        var mobile = manager.driver.findElement(By.name("mobile")).getAttribute("value");
        var work = manager.driver.findElement(By.name("work")).getAttribute("value");
//        var address = manager.driver.findElement(By.name("address")).getText();
//        var email = manager.driver.findElement(By.name("email")).getText();
//        var email2 = manager.driver.findElement(By.name("email2")).getText();
//        var email3 = manager.driver.findElement(By.name("email3")).getText();
        return Stream.of(home, mobile, work).filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
    }


}
