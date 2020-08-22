package com.exercise.guangqi.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SignUpPage {
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "submitSignUp")
    private WebElement submitButton;

    public void submit(){
        this.submitButton.submit();
    }

    public SignUpPage(WebDriver dirver) {
        PageFactory.initElements(dirver, this);
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public void setUsernameField(String usernameField) {
        this.usernameField.sendKeys(usernameField);
    }

    public String getPasswordField() {
        return passwordField.getText();
    }

    public void setPasswordField(String passwordField) {
        this.passwordField.sendKeys(passwordField);
    }

    public String getFirstNameField() {
        return firstNameField.getText();
    }

    public void setFirstNameField(String firstNameField) {
        this.firstNameField.sendKeys(firstNameField);
    }

    public String getLastNameField() {
        return lastNameField.getText();
    }

    public void setLastNameField(String lastNameField) {
        this.lastNameField.sendKeys((lastNameField));
    }
}
