package pages;

import org.openqa.selenium.WebDriver;

public class HomePage {
	private WebDriver driver;
	private String title;

	public HomePage(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public String getTitle() {

		title = driver.getTitle();
		return title;
	}
}
