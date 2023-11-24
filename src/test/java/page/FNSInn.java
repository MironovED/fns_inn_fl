package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FNSInn {
    private static By fieldLogin = By.id("LOGIN-USERNAME");
    private static By fieldPassword = By.id("LOGIN-PASSWORD");
    private static By buttonOK = By.id("BTN-OK");
    private static By moduleFNS = By.className("FontAwesome");
    private static By windowInnDoc = By.className("v-tree-node-caption");
    private static By buttonCreate = By.cssSelector("div .v-slot .vn-s-button");
    private static By fieldINN = By.id("PERSON_INN");
    private static By fieldLastName = By.id("LAST_NAME");
    private static By fieldFirstName = By.id("FIRST_NAME");
    private static By fieldDulNumber = By.id("DUL_NUMBER");
    private static By buttonSave = By.cssSelector(".v-slot-vn-action-button");
    private static By buttonNew = By.cssSelector(".v-slot-vn-action-button");
    public static By buttonSend = By.cssSelector(".vn-popup-button");
    public static By fieldDulDeptCode = By.id("DUL_DEPT_CODE");

    public static void loginIn(WebDriver driver, String userName, String password) {
        driver.findElement(fieldLogin).sendKeys(userName);
        driver.findElement(fieldPassword).sendKeys(password);
        driver.findElement(buttonOK).click();
    }

    public static void openScrollerFNS(WebDriver driver) {
        driver.findElement(moduleFNS).click();
        List<WebElement> el1 = driver.findElements(windowInnDoc); //херовый поиск элементов
        el1.get(0).click();
    }

    public static void buttonCreate(WebDriver driver) {
        List<WebElement> el2 = driver.findElements(buttonCreate);  //херовый поиск элемента
        el2.get(1).click();
    }

    public static void enterINN(WebDriver driver, String INN) {
        driver.findElement(fieldINN).sendKeys(INN);
    }

    public static void enterLastName(WebDriver driver, String lastName) {
        driver.findElement(fieldLastName).sendKeys(lastName);
    }

    public static void enterFirstName(WebDriver driver, String firstName) {
        driver.findElement(fieldFirstName).sendKeys(firstName);
    }

    public static void enterDulNumber(WebDriver driver, String numDUL) {
        driver.findElement(fieldDulNumber).sendKeys(numDUL);
    }

    public static void enterDulDeptCode(WebDriver driver, String DulDeptCode) {
        driver.findElement(fieldDulDeptCode).sendKeys(DulDeptCode);
    }

    public static void buttonSave(WebDriver driver) {
        List<WebElement> el3 = driver.findElements(buttonSave);
        el3.get(2).click();
    }

    public static void sendDocument(WebDriver driver) {
        List<WebElement> el3 = driver.findElements(buttonNew);

        el3.get(0).click();
        driver.findElement(buttonSend).click();
    }

    public static void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

}
