import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases {

	WebDriver driver = new ChromeDriver();
	String url = "https://magento.softwaretestingboard.com/";
	Actions actions = new Actions(driver);

	@BeforeTest
	public void setUp() {

		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));// Set implicit wait time

	}

	@Test(enabled = false, description = "implemented just one")
	public void signUp() {

		WebElement createAnAccount = driver.findElement(
				By.xpath("//a[@href='https://magento.softwaretestingboard.com/customer/account/create/']"));
		createAnAccount.click();

		driver.findElement(By.id("firstname")).sendKeys("Rama");
		driver.findElement(By.id("lastname")).sendKeys("Odeh");
		driver.findElement(By.id("email_address")).sendKeys("ramaabdullah@gmail.com");
		driver.findElement(By.id("password")).sendKeys("rama#Odeh33");
		driver.findElement(By.id("password-confirmation")).sendKeys("rama#Odeh33");
		driver.findElement(By.cssSelector(".action.submit.primary")).click();

	}

	@Test(priority = 1)
	public void signIn() {

		WebElement signIn = driver.findElement(By.xpath(
				"//a[@href='https://magento.softwaretestingboard.com/customer/account/login/referer/aHR0cHM6Ly9tYWdlbnRvLnNvZnR3YXJldGVzdGluZ2JvYXJkLmNvbS8%2C/']"));
		signIn.click();

		driver.findElement(By.id("email")).sendKeys("ramaabdullah@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("rama#Odeh33");
		driver.findElement(By.id("send2")).click();

	}

	@Test(priority = 2)
	public void goToBagsSection() {

		WebElement gear = driver.findElement(By.id("ui-id-6"));
		actions.moveToElement(gear).perform();

		driver.findElement(By.id("ui-id-25")).click();

	}

	@Test(priority = 3)
	public void bagsSection() throws InterruptedException {

		List<WebElement> allItems = driver.findElements(By.cssSelector(".item.product.product-item"));
		System.out.println(allItems.size());

		for (int i = 0; i < allItems.size(); i = i + 2) {
			WebElement items = allItems.get(i);
			System.out.println(i);

			actions.moveToElement(items).perform();
			Thread.sleep(2000);

			WebElement element = items.findElement(By.cssSelector(".action.tocart.primary"));

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));

			actions.moveToElement(element).click().perform();
			System.out.println("Clicked on item: " + i);

			Thread.sleep(3000);
		}
		Thread.sleep(2000);
	}

	@Test(priority = 4)
	public void totalNumberOfBags() {

		WebElement items = driver.findElement(By.cssSelector(".products.list.items.product-items"));
		List<WebElement> allItems = items.findElements(By.cssSelector(".item.product.product-item"));
		int SizeAllItems = allItems.size() / 2;

		WebElement numberOfBags = driver.findElement(By.cssSelector(".counter-number"));
		String numberOfBagsAsString = numberOfBags.getText();
		int numberOfBagsAsInt = Integer.parseInt(numberOfBagsAsString);

		Assert.assertEquals(numberOfBagsAsInt == SizeAllItems, true);
	}

}
