package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createAddressBook(ContactData contact) {
        openContactPageCreation();
        fillContactForm(contact);
        submitContactCreation();
        openHomePage();
    }

    public void removeContact() {
        openHomePage();
        selectContact();
        removeSelectedContact();
        openHomePage();
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("address"), contact.address());
        type(By.name("mobile"), contact.phone());
        type(By.name("email"), contact.email());
    }

    private void openHomePage() {click(By.linkText("home"));}

    private void submitContactCreation() {
        click(By.xpath("(//input[@name=\'submit\'])[2]"));
    }

    private void removeSelectedContact() {click(By.xpath("//input[@value=\'Delete\']"));}

    private void selectContact() {click(By.name("selected[]"));}

    public void openContactPageCreation() {click(By.linkText("add new"));}

    public boolean isContactPresent() {
        openHomePage();
        return manager.isElementPresent(By.name("selected[]"));
    }
}
