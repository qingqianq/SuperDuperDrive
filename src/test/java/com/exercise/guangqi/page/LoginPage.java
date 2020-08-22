package com.exercise.guangqi.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginPage {
    @FindBy(id="inputUsername")
    private WebElement username;
    @FindBy(id="inputPassword")
    private WebElement password;
    @FindBy(id = "submitLogin")
    private WebElement submit;

    public void submit(){
        submit.click();
    }

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setUsername(String username) {
        this.username.sendKeys(username);
    }

    public void setPassword(String password) {
        this.password.sendKeys(password);
    }
}
