package com.hatester.drivers.strategy;

import com.hatester.config.FrameworkConfig;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public abstract class AbstractBrowserStrategy implements BrowserStrategy {
    protected boolean isHeadless() {
        return FrameworkConfig.isHeadless();
    }

    private Dimension getWindowDimension() {
        String[] size = FrameworkConfig.getWindowSize().split(",");
        int width = Integer.parseInt(size[0]);
        int height = Integer.parseInt(size[1]);
        return new Dimension(width, height);
    }

    protected void configureWindow(WebDriver driver) {
        boolean headless = isHeadless();
        LogUtils.info("Headless: " + headless);

        if (headless) {
            driver.manage().window().setSize(getWindowDimension());
        } else {
            driver.manage().window().maximize();
        }
    }
}
