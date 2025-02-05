package ru.netology.selenide;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void sendingFormTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Встреча успешно забронирована на " + generateDate(3, "dd.MM.yyyy")));
    }

    @Test
    public void сityFieldEmptyTest() {
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    public void dateFieldEmptyTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Неверно введена дата"));
    }

    @Test
    public void nameFieldEmptyTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    public void incorrectNameTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Ivan Petrov");
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void phoneFieldEmptyTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    public void incorrectPhoneTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+7900-000-00-00");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void uncheckedCheckboxTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id = 'agreement'].input_invalid .checkbox__text").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    public void enterCityTwoLettersTest() {
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item").findBy(text("Москва")).click();
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(7, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Встреча успешно забронирована на " + generateDate(7, "dd.MM.yyyy")));
    }

    @Test
    public void enterDateFromCalendarTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] .icon-button").click();
        if (!generateDate(0, "M").equals(generateDate(7, "M"))) {
            $$(".calendar__arrow").last().click();
        }
        $$(".calendar__day").findBy(text(generateDate(7, "d"))).click();
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Встреча успешно забронирована на " + generateDate(7, "dd.MM.yyyy")));
    }
}
