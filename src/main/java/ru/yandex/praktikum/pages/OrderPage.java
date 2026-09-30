package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поле «Имя»
    private final By nameInput =
            By.xpath("//input[@placeholder='* Имя']");

    // Поле «Фамилия»
    private final By surnameInput =
            By.xpath("//input[@placeholder='* Фамилия']");

    // Поле «Адрес»
    private final By addressInput =
            By.xpath(
                    "//input[@placeholder="
                            + "'* Адрес: куда привезти заказ']"
            );

    // Поле «Станция метро»
    private final By metroInput =
            By.xpath(
                    "//input[@placeholder='* Станция метро']"
            );

    // Поле «Телефон»
    private final By phoneInput =
            By.xpath(
                    "//input[@placeholder="
                            + "'* Телефон: на него позвонит курьер']"
            );

    // Кнопка «Далее»
    private final By nextButton =
            By.xpath("//button[normalize-space()='Далее']");

    // Поле «Когда привезти самокат»
    private final By deliveryDateInput =
            By.xpath(
                    "//input[@placeholder="
                            + "'* Когда привезти самокат']"
            );

    // Выпадающий список «Срок аренды»
    private final By rentalPeriodDropdown =
            By.className("Dropdown-placeholder");

    // Поле «Комментарий для курьера»
    private final By commentInput =
            By.xpath(
                    "//input[@placeholder="
                            + "'Комментарий для курьера']"
            );

    // Кнопка «Заказать» в нижней части формы
    private final By orderButton =
            By.xpath(
                    "//div[contains(@class, 'Order_Buttons')]"
                            + "//button[normalize-space()='Заказать']"
            );

    // Кнопка подтверждения заказа «Да»
    private final By confirmOrderButton =
            By.xpath(
                    "//div[contains(@class, 'Order_Modal')]"
                            + "//button[normalize-space()='Да']"
            );

    // Сообщение об успешном оформлении заказа
    private final By orderSuccessMessage =
            By.xpath(
                    "//div[contains(@class, 'Order_ModalHeader')"
                            + " and contains(., 'Заказ оформлен')]"
            );

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void fillCustomerInformation(
            String name,
            String surname,
            String address,
            String metroStation,
            String phone
    ) {
        setInputValue(nameInput, name);
        setInputValue(surnameInput, surname);
        setInputValue(addressInput, address);
        selectMetroStation(metroStation);
        setInputValue(phoneInput, phone);

        wait.until(
                ExpectedConditions.elementToBeClickable(nextButton)
        ).click();
    }

    public void fillRentalInformation(
            String deliveryDate,
            String rentalPeriod,
            String colour,
            String comment
    ) {
        WebElement dateInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        deliveryDateInput
                )
        );

        dateInput.clear();
        dateInput.sendKeys(deliveryDate);
        dateInput.sendKeys(Keys.ENTER);

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        rentalPeriodDropdown
                )
        ).click();

        By rentalPeriodOption = By.xpath(
                "//div[contains(@class, 'Dropdown-option')"
                        + " and normalize-space()='"
                        + rentalPeriod
                        + "']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        rentalPeriodOption
                )
        ).click();

        By colourCheckbox = By.id(colour);

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        colourCheckbox
                )
        ).click();

        setInputValue(commentInput, comment);
    }

    public void submitOrder() {
        wait.until(
                ExpectedConditions.elementToBeClickable(orderButton)
        ).click();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        confirmOrderButton
                )
        ).click();
    }

    public boolean isOrderSuccessMessageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        orderSuccessMessage
                )
        ).isDisplayed();
    }

    private void selectMetroStation(String metroStation) {
        WebElement metroField = wait.until(
                ExpectedConditions.elementToBeClickable(metroInput)
        );

        metroField.click();
        metroField.sendKeys(metroStation);

        By stationOption = By.xpath(
                "//div[contains(@class, 'Order_Text')"
                        + " and normalize-space()='"
                        + metroStation
                        + "']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        stationOption
                )
        ).click();
    }

    private void setInputValue(By locator, String value) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        locator
                )
        );

        input.clear();
        input.sendKeys(value);
    }
}
