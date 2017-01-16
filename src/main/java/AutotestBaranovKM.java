import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Константин on 13.01.2017.
 */
public class AutotestBaranovKM {

    WebDriver driver;
    String idTopMenu;
    String idSubscribeButton;
    String idSubscribeEmail;
    String idSearchField;
    String xpathSearchButton;
    String idResourcesGroup;
    String idLeftMenu;


    @Before
    public void init() {
        //Settings before test
        System.setProperty("webdriver.chrome.driver", "F:\\Selenium\\chromedriver.exe");//path of the webdriver
        driver = new ChromeDriver();//select browser
        idTopMenu = "links-site";//ID top navigation menu
        idResourcesGroup = "homepage-links";//ID Resources sub-header
        idLeftMenu = "sidebar";//ID “Resources For” menu on the left
        idSubscribeButton = "id31";//ID button from subscription form
        idSubscribeEmail = "EmailAddress";//ID input field for email
        idSearchField = "query";//ID search input form
        xpathSearchButton = ".//*[@id='links-site']/form/fieldset/input[2]";//the way to the search button
    }

    @Test(timeout = 120000)
    public void autotest() {
        /*
         *Test logic and steps from test case
         */

        //Step 1
        openURL("http://www.wiley.com/WileyCDA/");
        checkLinksTopNavigationMenu(Arrays.asList("Home", "Subjects", "About Wiley", "Contact Us", "Help"));

        //Step 2
        checkResourcesItemsAmount(9);
        checkResourcesItemsTitles(Arrays.asList("Students", "Authors", "Instructors", "Librarians", "Societies", "Conferences", "Booksellers", "Corporations", "Institutions"));

        //Step 3
        clickOnResourcesItem("Students");
        checkOpenedURL("http://eu.wiley.com/WileyCDA/Section/id-404702.html");
        checkResourcesHeader("Students");

        //Step 4
        checkMenuItemsAmount(8);
        checkMenuItemsTitles(Arrays.asList("Authors", "Librarians", "Booksellers", "Instructors", "Students", "Societies", "Corporate Partners"));

        //Step 5
        checkMenuItemIsSelected("Students");

        //Step 6
        clickOnLinkNavigationMenu("Home");

        //Step 7
        clickSubscriptionButton();
        checkAlertAppeared();
        checkAlertText("Please enter email address");

        //Step 8
        inputSubscribersEmail("someInvalidEmail.com");
        checkAlertAppeared();
        checkAlertText("Invalid email address.");

        //Step 9
        inputSearchText("for dummies");
        checkSearchResultList();

        //Step 10
        checkClickOnRandomLink();

        //Step 11
        clickOnLinkNavigationMenu("Home");

        //Step 12
        clickOnResourcesItem("Institutions");
        checkOpenNewTab("https://edservices.wiley.com/");

    }

    @After
    public void closeBrowser() {
        driver.quit();//close browser after test
    }
/*
 * Technical realization of the test
 */

    private void checkOpenNewTab(String link) {
        //Dance with a tambourine for check new tab :)
        String parentWindowID = driver.getWindowHandle();//saved parent window ID
        Set set = driver.getWindowHandles();//get list of windows(there should be only two)
        for (Object o : set) {
            String windowID = (String) o;
            if (!windowID.contentEquals(parentWindowID)) {
                driver.switchTo().window(windowID);//get focus on the child window
                assertTrue("New tab is not open",
                        //verified that the current window is not equal to the parent
                        parentWindowID != driver.getWindowHandle());
                System.out.println("Parent tab ID : " + parentWindowID);
                System.out.println("Opened(child) tab ID : " + driver.getWindowHandle());
                System.out.println("New tab open is checked.");
                break;
            }
        }
        //Check opened link
        delay(3);
        assertEquals("Link is not equal", link, driver.getCurrentUrl());
        System.out.println("Link equal " + link);
    }

    private void checkClickOnRandomLink() {
        //Click random item link (link with book title)
        List list = driver.findElements(By.className("product-title"));
        WebElement element = (WebElement) list.get(new Random().nextInt(list.size()));
        String bookTitle = element.getText();
        element.findElement(By.linkText(bookTitle)).click();
        delay(3);
        System.out.println("Select book with title : " + bookTitle);

        //Check that page with header equal to the title clicked link
        assertEquals("Open the page with another title",
                bookTitle, driver.findElement(By.className("productDetail-title")).getText());
        System.out.println("Random link is checked");
    }

    private void checkSearchResultList() {
        //Check that list of items appeared
        List result = driver.findElements(By.cssSelector(".product-listing.size100"));
        //If the list is empty then the test falls
        assertFalse("Search result is  not displayed", result.isEmpty());
        System.out.println("Books founds : " + result.size());
        delay(3);
    }

    private void inputSearchText(String text) {
        //input text in the search field and click button
        driver.findElement(By.xpath("//*[@id='" + idSearchField + "']")).sendKeys(text);
        driver.findElement(By.xpath(xpathSearchButton)).click();
        System.out.println("Search text: " + text);
        delay(3);
    }

    private void inputSubscribersEmail(String text) {
        //Enter text in the field for email
        driver.findElement(By.xpath("//*[@id='" + idSubscribeEmail + "']")).sendKeys(text);
        System.out.println("Input email : " + text);
        clickSubscriptionButton();
    }

    private void checkAlertAppeared() {
        assertTrue("Subscribe alert is not displayed",
                //verify that the alert is not empty
                driver.switchTo().alert() != null);
        System.out.println("Subscribe alert is checked");
    }

    private void checkAlertText(String text) {
        Alert alert = driver.switchTo().alert();
        assertEquals("Email alert text is not equal",
                //Compares text from receipt of the alert
                text, alert.getText());
        delay(3);
        alert.accept();//close alert
        System.out.println("Text from email alert OK");
    }

    private void clickSubscriptionButton() {
        // Do not enter anything and click arrow button
        driver.findElement(By.xpath("//*[@id='" + idSubscribeButton + "']")).click();
        delay(1);
    }

    private void clickOnLinkNavigationMenu(String item) {
        //Click link at the top navigation menu
        driver.findElement(By.xpath("//*[@id='" + idTopMenu + "']//*[text()='" + item + "']")).click();
        System.out.println("Link '" + item + "' in top navigation menu is clicked");
        delay(3);

    }

    private void checkMenuItemIsSelected(String item) {
        //Check the item has different style
        String xpath = "//*[@id='" + idLeftMenu + "']//*[text()='" + item + "']";
        String className = "autonavItem";//class other elements of menu
        assertTrue("Item '" + item + "' has not different classname with other elements", !className.contentEquals(
                //Compares class element classes other menu items and terminates the test if there is a match
                driver.findElement(By.xpath(xpath + "//..")).getAttribute("class")));
        //Check item is not clickable
        assertTrue("The item '" + item + "'is a reference",
                //Proof(by tag) that the item is a not reference
                !new String("a").contentEquals(driver.findElement(By.xpath(xpath)).getTagName())
        );
        System.out.println("Selected item '" + item + "' is checked");
    }

    private void checkMenuItemsTitles(List<String> menuItems) {
        //Check items names in the menu “Resources For”
        reviseElementsExist(idLeftMenu, menuItems);
    }


    private void checkMenuItemsAmount(int amount) {
        //Check amount items are displayed in the menu “Resources For”
        reviseElementAmount(idLeftMenu, ++amount);//add +1 for consider link in the menu's header
    }

    private void reviseElementAmount(String grupID, int amount) {
        int count = driver.findElement(By.id(grupID)).findElements(By.tagName("a")).size();
        assertEquals("Amount of items is not equal ", amount, count);
        System.out.println("Items (" + amount + ") is checked");
    }

    private void checkResourcesHeader(String header) {
        //Check that “Students” header is displayed
        assertEquals("Header is not equal", header, driver.findElement(By.id("page-title")).getText());
        System.out.println("Header : " + header);
    }

    private void checkOpenedURL(String url) {
        //Check current url is correct
        assertEquals("Opened page is not correct", url, driver.getCurrentUrl());
        System.out.println("Opened page " + driver.getCurrentUrl());
    }

    private void clickOnResourcesItem(String item) {
        //click on the item in Resources
        WebElement element = driver.findElement(By.id(idResourcesGroup)).findElement(By.linkText(item));
        element.click();
        System.out.println("Click on th item : " + item);
        delay(3);

    }

    private void delay(int seconds) {
        //delay for page load
        try {
            Thread.currentThread().sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkResourcesItemsTitles(List<String> titles) {
        //Check items under Resources sub-header
        reviseElementsExist(idResourcesGroup, titles);
    }

    private void reviseElementsExist(String groupID, List<String> items) {
        //Goes through a list of items and terminates the test if the item was not found
        for (String item : items) {
            assertFalse("Element with name '" + item + "' not found",
                    //Search element by name and id of parent container. It return array with element inside.
                    //If the array is empty then test fall
                    driver.findElement(By.id(groupID)).findElements(By.xpath("//*[text()='" + item + "']")).isEmpty());
            System.out.println("'" + item + "' is checked");
        }
    }

    private void checkResourcesItemsAmount(int amount) {
        //Check amount of items under Resources sub-header
        reviseElementAmount(idResourcesGroup, amount);
    }

    private void checkLinksTopNavigationMenu(List<String> links) {
//        Check the links from list displayed in top navigation menu
        reviseElementsExist(idTopMenu, links);
        delay(3);
    }


    private void openURL(String url) {
        //Open some link. In context of test case is the "http://www.wiley.com/WileyCDA/"
        driver.get(url);
        System.out.println("Open url : " + url);
    }

}
