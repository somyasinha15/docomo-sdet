package com.core;

import org.openqa.selenium.WebDriver;

public class DriverManager<T> extends WebDriverController<T> {

    public WebDriver driverThread;

    public DriverManager() {
        this.driverThread = super.getDriver();
    }

}
