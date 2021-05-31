package core;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;

public class MainPage  {
	
	static Properties info = new Properties();

	public static void main(String[] args) throws Exception {
	
	info.load(new FileInputStream("./elements.properties"));
	Common.open(info.getProperty("browser"), info.getProperty("uri"));
	Common.leftClickOnElement(By.xpath(info.getProperty("el_01")));
	
	Common.leftClickOnElement(By.xpath(info.getProperty("el_02")));
	Common.setValue(By.xpath(info.getProperty("el_02")), info.getProperty("login"));
	Common.setValue(By.xpath(info.getProperty("el_03")), info.getProperty("password"));
	Common.leftClickOnElement(By.xpath("//button[text()='Sign In']"));	
	System.out.println(Common.getNameOfTitle().equals("Netflix") ? "PASSED" : "FAILED");

	Common.stopDriver();
}}


