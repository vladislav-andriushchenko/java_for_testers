package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddressBookRemovalTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        if (driver == null) {
            driver = new ChromeDriver();
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get("http://localhost/addressbook/");
            driver.manage().window().setSize(new Dimension(945, 1020));
            driver.findElement(By.name("user")).click();
            driver.findElement(By.name("user")).sendKeys("admin");
            driver.findElement(By.name("pass")).sendKeys("secret");
            driver.findElement(By.name("pass")).sendKeys(Keys.ENTER);
        }
    }

    @Test
    public void canRemoveAddressBook() {
        if (!isElementPresent(By.xpath("//*[@name=\"selected[]\"]"))) {
            driver.findElement(By.linkText("add new")).click();
            driver.findElement(By.name("firstname")).click();
            driver.findElement(By.name("firstname")).sendKeys("a");
            driver.findElement(By.name("lastname")).click();
            driver.findElement(By.name("lastname")).sendKeys("a");
            driver.findElement(By.name("address")).click();
            driver.findElement(By.name("address")).sendKeys("a");
            driver.findElement(By.name("home")).click();
            driver.findElement(By.name("home")).sendKeys("a");
            driver.findElement(By.name("mobile")).click();
            driver.findElement(By.name("mobile")).sendKeys("2");
            driver.findElement(By.name("work")).click();
            driver.findElement(By.name("work")).sendKeys("3");
            driver.findElement(By.name("fax")).click();
            driver.findElement(By.name("fax")).sendKeys("4");
            driver.findElement(By.name("email")).click();
            driver.findElement(By.name("email")).sendKeys("1");
            driver.findElement(By.name("email2")).click();
            driver.findElement(By.name("email2")).sendKeys("2");
            driver.findElement(By.name("email3")).click();
            driver.findElement(By.name("email3")).sendKeys("3");
            driver.findElement(By.xpath("(//input[@name=\'submit\'])[2]")).click();
            driver.findElement(By.linkText("home page")).click();
        }
        driver.findElement(By.xpath("//table[@id=\'maintable\']/tbody/tr[2]/td/input")).click();
        driver.findElement(By.xpath("//input[@value=\'Delete\']")).click();
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }
}


