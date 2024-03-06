package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);

    }

    public void openContactPage() {
        click(By.linkText("add new"));
    }

    public void createAddressBook(ContactData contact) {
        openContactPage();
        fillContactForm(contact);
        submitContactCreation();
        returnToHomePage();
    }

    private void returnToHomePage() {
        click(By.linkText("home page"));
    }

    private void submitContactCreation() {
        click(By.xpath("(//input[@name=\'submit\'])[2]"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("address"), contact.address());
        type(By.name("mobile"), contact.phone());
        type(By.name("email"), contact.email());
    }

    public void removeContact() {
        openContactPage();
        selectContact();
        removeSelectedContact();
        returnToHomePage();
    }

    private void removeSelectedContact() {

    }

    private void selectContact() {
        click(By.name("selected[]"));
    }


    public boolean isContactPresent() {
        openContactPage();
        return manager.isElementPresent(By.name("selected[]"));
    }
}
