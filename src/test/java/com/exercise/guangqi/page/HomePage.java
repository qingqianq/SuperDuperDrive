package com.exercise.guangqi.page;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class HomePage {

    @FindBy(id="nav-notes-tab")
    private WebElement noteTab;
    @FindBy(id="nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id="addCredential")
    private WebElement addCredentialButton;
    @FindBy(id="credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(id="credential-url")
    private WebElement credentialUrl;
    @FindBy(id="credential-username")
    private WebElement credentialUsername;
    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    @FindBy(id="addNote")
    private WebElement addNoteButton;
    @FindBy(id="noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id="note-title")
    private WebElement noteTitle;
    @FindBy(id = "note-description")
    private WebElement noteDescription;


    private WebDriver driver;
    WebDriverWait wait;
    private JavascriptExecutor js;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
//        wait = new WebDriverWait(driver, 1);
        js = (JavascriptExecutor) driver;
    }

    public void testAddCredential(){
//        wait.until(driver -> driver.findElement(By.id("nav-credentials-tab")));
//        credentialTab.click();
//        wait.until(driver -> driver.findElement(By.id("addCredential")));
//        addCredentialButton.click();
//        wait.until(driver -> driver.findElement(By.id("credential-url")));
//        credentialUrl.sendKeys("test.google.com");
//        wait.until(driver -> driver.findElement(By.id("credential-username")));
//        credentialUsername.sendKeys("hello");
//        wait.until(driver -> driver.findElement(By.id("credential-password")));
//        credentialPassword.sendKeys("world");
//        wait.until(driver -> driver.findElement(By.id("credentialSubmit")));
//        credentialSubmitButton.click();

        js.executeScript("arguments[0].click();",addCredentialButton);
        js.executeScript("arguments[0].value='test.google.com';",credentialUrl);
        js.executeScript("arguments[0].value='hello';",credentialUsername);
        js.executeScript("arguments[0].value='world';",credentialPassword);
        js.executeScript("arguments[0].click();",credentialSubmitButton);
        credentialTab.click();
    }
    @Test
    public void testAddNote() {
//        wait.until(driver -> driver.findElement(By.id("nav-notes-tab")));
//        noteTab.click();
//        wait.until(driver -> driver.findElement(By.id("addNote")));
//        addNoteButton.click();
//        wait.until(webDriver->webDriver.findElement(By.id("note-title")));
//        noteTitle.sendKeys("note test");
//        wait.until(webDriver->webDriver.findElement(By.id("note-description")));
//        noteDescription.sendKeys("This is a note description");
//        wait.until(webDriver->webDriver.findElement(By.id("noteSubmit")));
//        noteSubmitButton.click();
        credentialTab.click();
        js.executeScript("arguments[0].click();",addNoteButton);
        js.executeScript("arguments[0].value='Note title';",noteTitle);
        js.executeScript("arguments[0].value='This is a test of note description';",noteDescription);
        js.executeScript("arguments[0].click();",noteSubmitButton);
        credentialTab.click();
    }



}
