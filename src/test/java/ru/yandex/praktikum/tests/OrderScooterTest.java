package ru.yandex.praktikum.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.driver.DriverFactory;
import ru.yandex.praktikum.pages.HomePage;
import ru.yandex.praktikum.pages.OrderPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderScooterTest {

    private WebDriver driver;
    private HomePage homePage;
    private OrderPage orderPage;

    private final HomePage.OrderButtonPosition buttonPosition;
    private final String name;
    private final String surname;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String rentalPeriod;
    private final String colour;
    private final String comment;

    public OrderScooterTest(
            HomePage.OrderButtonPosition buttonPosition,
            String name,
            String surname,
            String address,
            String metroStation,
            String phone,
            String rentalPeriod,
            String colour,
            String comment
    ) {
        this.buttonPosition = buttonPosition;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentalPeriod = rentalPeriod;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters(
            name = "Заказ через кнопку {0}, пользователь {1} {2}"
    )
    public static Object[][] getOrderData() {
        return new Object[][]{
                {
                        HomePage.OrderButtonPosition.TOP,
                        "Егор",
                        "Гиль",
                        "улица Тверская, дом 10",
                        "Сокольники",
                        "+79991234567",
                        "сутки",
                        "black",
                        "Позвоните перед доставкой"
                },
                {
                        HomePage.OrderButtonPosition.BOTTOM,
                        "Ольга",
                        "Иванова",
                        "улица Арбат, дом 15",
                        "Черкизовская",
                        "+79997654321",
                        "двое суток",
                        "grey",
                        "Оставьте у подъезда"
                }
        };
    }

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver();

        homePage = new HomePage(driver);
        orderPage = new OrderPage(driver);

        homePage.open();
        homePage.acceptCookiesIfPresent();
    }

    @Test
    public void orderShouldBeCreatedSuccessfully() {
        homePage.clickOrderButton(buttonPosition);

        orderPage.fillCustomerInformation(
                name,
                surname,
                address,
                metroStation,
                phone
        );

        String deliveryDate = LocalDate.now()
                .plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        orderPage.fillRentalInformation(
                deliveryDate,
                rentalPeriod,
                colour,
                comment
        );

        orderPage.submitOrder();

        assertTrue(
                "Сообщение об успешном оформлении заказа не появилось",
                orderPage.isOrderSuccessMessageDisplayed()
        );
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
