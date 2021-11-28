package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MultiTest {
    static WebDriver driver;

    public static void clickOnElement(By by) { //declared ClickOnElement Method
        driver.findElement(by).click();
    }

    public static void typeText(By by, String text) {//declared typeText Method
        driver.findElement(by).sendKeys(text);
    }

    public static String getTextFromElement(By by) { //declared getTextFromElement Method
        String s1=  driver.findElement(by).getText();
        System.out.println(s1);
        return s1;
    }

    public static String currentTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmss");
        return sdf.format(date);

    }
    public static void waitForClickable(By by, int timeInSeconds){

        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void waitForVisible(By by, int timeInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));

    }
    @BeforeMethod
    public static void openBrowswer(){
        System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");// System Property for Chrome Driver
        driver = new ChromeDriver();//instantiate ChromeDriver class
        driver.manage().window().maximize();// open window fullscreen
        driver.get("https://demo.nopcommerce.com/");// launch website
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS) ;//Applied wait time

    }



    @Test
    public void verifyUserShouldBeAbleToRegisterSuccessfully() {
        clickOnElement(By.xpath("//a[@class='ico-register']"));
        Assert.assertTrue(driver.getCurrentUrl().contains("register"));
        typeText(By.name("FirstName"), "Bhawana");
        typeText(By.name("LastName"), "Satyal");
        Select selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
        //select Day from dropdown
        selectDay.selectByVisibleText("15");
        //select month from dropdown
        Select selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        selectMonth.selectByValue("4");
        //select year from dropdown
        Select selectYear = new Select(driver.findElement(By.name("DateOfBirthYear")));
        selectYear.selectByVisibleText("2000");
        String email = "bhawana+" + currentTimeStamp() + "@gmail.com";
        System.out.println(email);
        typeText(By.name("Email"), email);
        clickOnElement(By.id("Newsletter"));
        typeText(By.id("Password"), "testtest");
        typeText(By.id("ConfirmPassword"), "testtest");
        waitForClickable(By.name("register-button"), 10);
        clickOnElement(By.name("register-button"));
        String actualRegistrationSuccessMessage = getTextFromElement(By.xpath("//div[@class='result']"));
        String expectedRegistrationSuccessMessage = "Your registration completed";
        Assert.assertEquals(actualRegistrationSuccessMessage,expectedRegistrationSuccessMessage);

    }

    @Test
    public void verifyUserShouldBeAbleToNavigateToBuildYourComputerPage()
    {

        clickOnElement(By.xpath("//ul[@class='top-menu notmobile']/Li/a[@href='/computers']"));// click on computers
        clickOnElement(By.xpath("//img[@alt='Picture for category Desktops']")); // click on desktops
        clickOnElement(By.xpath("//div/div/h2/a[@href='/build-your-own-computer']"));// click on build your own computer
        String expectedResult = "COMP_CUST";
        String actualResult = getTextFromElement(By.id("sku-1"));
        Assert.assertEquals(actualResult,expectedResult);

    }
    @Test

    public void VerifyOnlyRegisteredUserShouldBeAbleToReferAProductToAFriend()
    {

        clickOnElement(By.xpath("//ul[@class='top-menu notmobile']/Li/a[@href='/computers']"));
        clickOnElement(By.xpath("//img[@alt='Picture for category Desktops']"));
        clickOnElement(By.xpath("//div/div/h2/a[@href='/build-your-own-computer']"));
        clickOnElement(By.xpath("//div[@class='email-a-friend']/button"));
        typeText(By.id("FriendEmail"), "xyz@hotmail.com");
        typeText(By.id("YourEmailAddress"),"Bhawana@gmail.com");
        typeText(By.id("PersonalMessage"), "Amazing Product Highly Recommended");
        clickOnElement(By.xpath("//button[@type='submit' and @name]"));
        String expectedResult1 = "Only registered customers can use email a friend feature";
        String actualResult1 = getTextFromElement(By.xpath("//div[@class='message-error validation-summary-errors']/ul/li"));
        Assert.assertEquals(actualResult1,expectedResult1);



    }
    @Test

    public void verifyUserShouldBeAbleToAddComment()
    {
        clickOnElement(By.xpath("//li/a[@href='/news']"));// click on news
        clickOnElement(By.xpath("//a[@href='/nopcommerce-new-release' and @class='read-more']"));//click on details
        typeText(By.id("AddNewComment_CommentTitle"),"Abcde");// click on title text box and add value
        typeText(By.id("AddNewComment_CommentText"),"Amazing Product");// click on comment text box and add value
        clickOnElement(By.xpath("//button[@type='submit' and @name='add-comment']"));// click on new comment
        String expectedResult2= "News comment is successfully added.";
        String actualResult2 = getTextFromElement(By.xpath("//div[@class='result']"));
        Assert.assertEquals(actualResult2,expectedResult2); // compares two conditions

    }

    @AfterMethod
    public void closeBrowser()
    {
        driver.close();//closing the browser
    }
}
