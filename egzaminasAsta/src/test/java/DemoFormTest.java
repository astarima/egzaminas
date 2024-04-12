import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DemoFormTest {
    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get("https://qavalidation.com/demo-form/");
        driver.manage().window().maximize();
    }

    @AfterEach
    void close() {
        driver.quit();
    }

    @Test
    void formSubmitAllFieldsFill() {
        List<WebElement> inputText = driver.findElements(By.cssSelector(".grunion-field"));
        inputText.get(0).sendKeys("Jurgis Jurgaitis");
        inputText.get(1).sendKeys("JurgisJurgis@gmail.com");
        inputText.get(2).sendKeys("07917106071");
        driver.findElements(By.cssSelector(".select")).get(1).click();
        driver.findElement(By.cssSelector("[value='NA']")).click();
        driver.findElements(By.cssSelector("[type='radio']")).get(2).click();
        List<WebElement> checkbox = driver.findElements(By.cssSelector("[type='checkbox']"));
        checkbox.get(0).click();
        checkbox.get(1).click();
        checkbox.get(2).click();
        driver.findElements(By.cssSelector(".select")).get(2).click();
        driver.findElement(By.cssSelector("[value='Appium']")).click();
        inputText.get(15).sendKeys("Geros dienos");
        driver.findElement(By.cssSelector(".wp-block-button__link")).click();
        String message = driver.findElement(By.cssSelector("#contact-form-success-header")).getText();
        assertEquals("Your message has been sent", message);
    }

    @Test
    void formSubmitOnlyMandatoryFieldsFill() {
        List<WebElement> inputText = driver.findElements(By.cssSelector(".grunion-field"));
        inputText.get(0).sendKeys("Jurgis Jurgaitis");
        inputText.get(1).sendKeys("JurgisJurgis@gmail.com");
        driver.findElement(By.cssSelector(".wp-block-button__link")).click();
        String message = driver.findElement(By.cssSelector("#contact-form-success-header")).getText();
        assertEquals("Your message has been sent", message);
    }

    @Test
    void formNotSubmitWithAllFieldsEmpty() {
        driver.findElement(By.cssSelector(".wp-block-button__link")).click();
        String warning = driver.findElement(By.xpath(
                "//span[text()='Užpildykite šį lauką.']")).getText();
        assertEquals(warning, "Užpildykite šį lauką.");
        String message = driver.findElement(By.xpath(
                "//span[text()='Please make sure all fields are valid. You need to fix 2 errors.']")).getText();
        assertEquals(message, "Please make sure all fields are valid. You need to fix 2 errors.");
        String text = driver.findElement(By.cssSelector("a[href=\"#g4072-fullname\"]")).getText();
        assertEquals(text, "Full Name(required)");
        String text2 = driver.findElement(By.cssSelector("a[href=\"#g4072-email\"]")).getText();
        assertEquals(text2, "Email(required)");
    }

    @Test
    void formNotSubmitWithWrongEmail() {
        List<WebElement> inputText = driver.findElements(By.cssSelector(".grunion-field"));
        inputText.get(0).sendKeys("Jurgis Jurgaitis");
        inputText.get(1).sendKeys("JurgisJurgisgmail.com");
        driver.findElement(By.cssSelector(".wp-block-button__link")).click();
        String warning = driver.findElement(By.xpath(
                "//span[text()='Į el. pašto adresą įtraukite „@“. „JurgisJurgisgmail.com“ trūksta „@“.']")).getText();
        assertEquals(warning, "Į el. pašto adresą įtraukite „@“. „JurgisJurgisgmail.com“ trūksta „@“.");
        String message = driver.findElement(By.xpath(
                "//span[text()='Please make sure all fields are valid. You need to fix 1 error.']")).getText();
        assertEquals(message, "Please make sure all fields are valid. You need to fix 1 error.");
        String text = driver.findElement(By.cssSelector("a[href=\"#g4072-email\"]")).getText();
        assertEquals(text, "Email(required)");

    }
}
