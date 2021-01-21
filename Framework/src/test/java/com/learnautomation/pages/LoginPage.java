package com.learnautomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	// This is a comment from SiriRavi
	
	WebDriver driver;
	
	public LoginPage(WebDriver ldriver) 
	{
		this.driver=ldriver;
	}
	
	
	@FindBy(xpath="//*[@id='txtUsername']") WebElement username;
	
	@FindBy(xpath="//*[@id=\"txtPassword\"]") WebElement password;
	
	@FindBy(xpath="//*[@id=\"btnLogin\"]") WebElement loginButton;
	
	public void LoginToApp(String uname, String pass)
	{
		username.sendKeys(uname);
		password.sendKeys(pass);
		loginButton.click();
		
	}
	

}
