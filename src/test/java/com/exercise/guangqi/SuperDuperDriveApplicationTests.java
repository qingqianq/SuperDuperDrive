package com.exercise.guangqi;

import com.exercise.guangqi.page.LoginPage;
import com.exercise.guangqi.page.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SuperDuperDriveApplicationTests {
	private LoginPage loginPage;
	private SignUpPage signUpPage;
	private static WebDriver driver;

	@BeforeAll
	public static void beforeAll(){
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Test
	void contextLoads() {
		signUp();
		login();
	}

	@Test
	void signUp(){
		driver.get("http://localhost:8080/signup");
		signUpPage = new SignUpPage(driver);
		signUpPage.setFirstNameField("a");
		signUpPage.setLastNameField("a");
		signUpPage.setUsernameField("a");
		signUpPage.setPasswordField("a");
		signUpPage.submit();
	}

	@Test
	void login(){
		driver.get("http://localhost:8080/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername("a");
		loginPage.setPassword("a");
		loginPage.submit();
	}

}
