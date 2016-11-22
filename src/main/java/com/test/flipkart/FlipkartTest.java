package com.test.flipkart;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlipkartTest {
	public static String url = "http://www.flipkart.com";
	public static WebDriver driver;
	
	/**
	 * Function to setup driver and start browser with given URL
	 */
	public static void setup(){
		try{
			System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(url);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Function to finish the execution
	 */
	public static void teardown(){
		driver.quit();
	}

	/**
	 * Function to wait the page load.
	 * @param timeOut
	 * @throws InterruptedException
	 */
	public static void waitForPageLoad(int timeOut) throws InterruptedException{
		driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
	}

	/**
	 * Function to find element which by default have explicit wait for visibility og element
	 * @param locator
	 * @return
	 * @throws InterruptedException
	 */
	public static WebElement findElement(By locator) throws InterruptedException{
		WebElement element = null;
		waitForPageLoad(60);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		/*boolean wait = fluentWait(driver, 100, driver.findElement(locator));
		if(wait){
			element = driver.findElement(locator);
		}*/
		return element;
	}

	/**
	 * Function use to open Electronic menu and click on DSLR
	 */
	public static void openMenuOption(){
		try{
			WebElement electronicElement = findElement(By.cssSelector("a[title='Electronics']"));
//			Actions action = new Actions(driver);
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor) driver).executeScript(mouseOverScript,electronicElement);
			Thread.sleep(2000);
			WebElement dslrElement = findElement(By.cssSelector("a[title='DSLR']"));//driver.findElement(By.cssSelector("a[title='DSLR']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click()",dslrElement);
//			action.moveToElement(dslrElement).click().build().perform();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Function to get the mega pixel menu
	 * @return
	 */
	public static List<WebElement> megaPixelMenuOption(){
		List<WebElement> pixelList = null;
		try{
			WebElement megaPixelArrowElement = findElement(By.cssSelector("section[data-aid='filter-section_Mega Pixel'] svg"));
			WebElement previousElement = findElement(By.xpath("//section[@data-aid='filter-section_Offers']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", previousElement);
			String classValue = megaPixelArrowElement.getAttribute("class");
			System.out.println(classValue);
			if (classValue.equalsIgnoreCase("_2vKQKr")) {
				//				WebDriverWait wait = new WebDriverWait(driver, 60);
				//				WebElement finalElement = wait.until(ExpectedConditions.elementToBeClickable(megaPixelArrowElement));
				Thread.sleep(6000);
				//				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				megaPixelArrowElement.click();
				pixelList = driver.findElements(By.cssSelector("section[data-aid='filter-section_Mega Pixel'] div[class='_4IiNRh _-9cJSB']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", megaPixelArrowElement);
				System.out.println(pixelList.size());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return pixelList;
	}

	/**
	 * Function use to select mega pixel option
	 * @param pixelList
	 * @param titleToSelect
	 * @throws InterruptedException
	 */
	public static void selectMegapixelOption(List<WebElement> pixelList, String titleToSelect) throws InterruptedException{
		for(int i =0; i < pixelList.size() ; i++){
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pixelList.get(i));
			String title = pixelList.get(i).getAttribute("title");
			System.out.println(title);
			if(title.contains(titleToSelect)){
				WebElement element = pixelList.get(i).findElement(By.xpath("//div[@class='_2wQvxh _1WV8jE']/div"));
				String classValue = element.getAttribute("class");
				if(classValue.equalsIgnoreCase("_2kFyHg _-9cJSB")){
					Thread.sleep(3000);
//					element.click();
//					element.findElement(By.xpath("//label")).click();
//					By.xpath("//input[@type='checkbox']")
					WebElement ele = driver.findElement(By.xpath("//div[text()='" + title + "']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
					System.out.println("sadasdasd");
//					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element.findElement(By.xpath("//div[@class='_1p7h2j']")));
//					element.findElement(By.xpath("//input/following-sibling::div")).click();
				}
				break;
			}
		}
	}
	
	/**
	 * Function use to wait for list of option to load
	 * @return
	 * @throws InterruptedException
	 */
	public static List<WebElement> waitForSearchResult() throws InterruptedException{
		List<WebElement> list = driver.findElements(By.xpath("//div[@data-aid='searchresults_products-container']/div[@data-aid='search-options']/following-sibling::div/div[@class='_2SxMvQ']/div"));
		for(int i =0; i< 60; i++){
			if(list.size()>1){
				System.out.println(list.size());
				break;
			}
			else{
				Thread.sleep(1000);
				list = driver.findElements(By.xpath("//div[@data-aid='searchresults_products-container']/div[@data-aid='search-options']/following-sibling::div/div[@class='_2SxMvQ']/div"));
			}
		}
		
		return list;
	}
	
	/**
	 * Function use to get price of camera
	 * @param locator
	 * @return
	 * @throws InterruptedException
	 */
	public static String getPrice(By locator) throws InterruptedException{
		String price = null;
		WebElement sortElement = findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", sortElement);
		String classValue = sortElement.getAttribute("class");
		for(int i =0; i< 60; i++){
			System.out.println(classValue);
			if(classValue.equalsIgnoreCase("_2Ylh8t")){
				Thread.sleep(1000);
				classValue = sortElement.getAttribute("class");
			}
			if(classValue.equalsIgnoreCase("_2Ylh8t _3q0Dpv")){
				break;
			}
			
		}
		List<WebElement> list = waitForSearchResult();
		if(list.size()>1){
			price = driver.findElement(By.xpath("//div[@class='_1vC4OE _2rQ-NK']")).getText();
			System.out.println(price);
		}
		return price;
	}

	public static void main(String[] args) throws InterruptedException {
		setup();
		openMenuOption();
		List<WebElement> pixelList = megaPixelMenuOption();
		selectMegapixelOption(pixelList, "Above");
		selectMegapixelOption(pixelList, "Below");
		waitForSearchResult();
		String lowestPrice = getPrice(By.xpath("//li[text()='Price -- Low to High']"));
		lowestPrice = lowestPrice.split("₹")[1];
		lowestPrice = lowestPrice.replace(",", "");
		
		String highestPrice = getPrice(By.xpath("//li[text()='Price -- High to Low']"));
		highestPrice = highestPrice.split("₹")[1];
		highestPrice = highestPrice.replace(",", "");
		
		
		System.out.println(lowestPrice + " : " + highestPrice);
		int lowPrice = Integer.parseInt(lowestPrice);
		int highPrice = Integer.parseInt(highestPrice);
		System.out.print("Price difference:- ");
		System.out.println(highPrice - lowPrice);
		teardown();
	}
}
