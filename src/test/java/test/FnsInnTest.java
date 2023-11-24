package test;

import data.LoginInfo;
import data.RequestData;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static page.FNSInn.*;

public class FnsInnTest {
    private WebDriver driver;
    private LoginInfo log = new LoginInfo();
    private RequestData data = new RequestData();

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "artifacts/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--window-size=1920,1200", "--ignore-certificate-errors"); //включение headless режима
        driver = new ChromeDriver(options);

        //driver = new ChromeDriver();
        driver.get(log.getUrl());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); // добавление неявного ожидания
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    // Happy Path Tests
    @Test
    void validRequest() {

        loginIn(driver, log.getUserName(), log.getPassword()); // залогиниться
        openScrollerFNS(driver); // открыть скроллер
        buttonCreate(driver); //создание документа

        enterINN(driver, data.getValidInn()); // ввести ИНН
        enterLastName(driver, data.getSurname()); // ввести Фамилию
        enterFirstName(driver, data.getName()); // ввести Имя
        enterDulNumber(driver, data.getNumberDUL()); // ввести ДУЛ

        buttonSave(driver); // нажать сохранить
        sendDocument(driver); // отправить документ
        refreshPage(driver); // обновить страницу

        String result = driver.findElement(By.xpath("//div[@class='v-label v-widget v-has-width']")).getText();
        String expected = "Запрос проверки ИНН ФЛ - Запрос проверки ИНН ФЛ(Отправлен)";

        assertEquals(expected, result.trim());
    }

    // Sad Path Tests
    @Test
    void shouldShowNoticeWhenSaveAllFieldEmpty() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenSaveInnFieldEmpty() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterLastName(driver, data.getSurname()); // ввести Фамилию
        enterFirstName(driver, data.getName()); // ввести Имя
        enterDulNumber(driver, data.getNumberDUL()); // ввести ДУЛ
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenSaveSurnameFieldEmpty() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, data.getValidInn()); // ввести ИНН
        enterFirstName(driver, data.getName()); // ввести Имя
        enterDulNumber(driver, data.getNumberDUL()); // ввести ДУЛ
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenSaveNameFieldEmpty() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, data.getValidInn()); // ввести ИНН
        enterLastName(driver, data.getSurname()); // ввести Фамилию
        enterDulNumber(driver, data.getNumberDUL()); // ввести ДУЛ
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenSaveNumberDULFieldEmpty() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, data.getValidInn());
        enterLastName(driver, data.getSurname());
        enterFirstName(driver, data.getName());
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    //Проверка на формат поля ИНН
    @Test
    void shouldShowNoticeWhenInnContainsSpace() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, " ");
        enterLastName(driver, data.getSurname());
        enterFirstName(driver, data.getName());
        enterDulNumber(driver, data.getNumberDUL());
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenInnContains0() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, "0");
        enterLastName(driver, data.getSurname());
        enterFirstName(driver, data.getName());
        enterDulNumber(driver, data.getNumberDUL());
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenInnContains11digits() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, "52020500455");
        enterLastName(driver, data.getSurname());
        enterFirstName(driver, data.getName());
        enterDulNumber(driver, data.getNumberDUL());
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenInnContainsWords() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, "qwerty");
        enterLastName(driver, data.getSurname());
        enterFirstName(driver, data.getName());
        enterDulNumber(driver, data.getNumberDUL());
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    //Проверка на формат поля Код подразделения

    @Test
    void shouldShowNoticeWhenDulDeptCodeContains0() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, data.getValidInn());
        enterLastName(driver, data.getSurname());
        enterFirstName(driver, data.getName());
        enterDulNumber(driver, data.getNumberDUL());
        enterDulDeptCode(driver, "0");
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenDulDeptCodeContains5digits() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, data.getValidInn());
        enterLastName(driver, data.getSurname());
        enterFirstName(driver, data.getName());
        enterDulNumber(driver, data.getNumberDUL());
        enterDulDeptCode(driver, "000-00");
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenDulDeptCodeContainsWords() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, data.getValidInn());
        enterLastName(driver, data.getSurname());
        enterFirstName(driver, data.getName());
        enterDulNumber(driver, data.getNumberDUL());
        enterDulDeptCode(driver, "qwerty");
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

    @Test
    void shouldShowNoticeWhenDulDeptCodeContainsSpace() {
        loginIn(driver, log.getUserName(), log.getPassword());
        openScrollerFNS(driver);

        buttonCreate(driver);
        enterINN(driver, data.getValidInn());
        enterLastName(driver, data.getSurname());
        enterFirstName(driver, data.getName());
        enterDulNumber(driver, data.getNumberDUL());
        enterDulDeptCode(driver, " ");
        buttonSave(driver);

        WebElement result = driver.findElement(By.xpath("//*[contains(text(), 'Внимание')]"));

        assertEquals(true, result.isDisplayed());
    }

}
