package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GooglePage {
	private WebDriver driver;
	private By fieldSearch = By.name("q");
	private By searchBtn = By.name("btnK");

	public GooglePage(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public void sendKeyWord(String key) {
		driver.findElement(fieldSearch).sendKeys(key);
	}

	public HomePage clickSearchBtn() {
		driver.findElement(searchBtn).submit();
		return new HomePage(driver);
	}
}
