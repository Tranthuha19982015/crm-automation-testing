package com.hatester.crm.pages;

import com.hatester.commons.BasePage;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class CustomerPage extends BasePage {
    //Customer List Page
    private By buttonNewCustomer = By.xpath("//a[normalize-space()='New Customer' and contains(@href,'clients/client')]");

    private By headerCustomersSummary = By.xpath("//span[normalize-space()='Customers Summary']");

    private By labelTotalCustomers = By.xpath("//span[normalize-space()='Total Customers']/preceding-sibling::span");
    private By labelActiveCustomers = By.xpath("//span[normalize-space()='Active Customers']/preceding-sibling::span");
    private By labelInactiveCustomers = By.xpath("//span[normalize-space()='Inactive Customers']/preceding-sibling::span");
    private By labelActiveContacts = By.xpath("//span[normalize-space()='Active Contacts']/preceding-sibling::span");
    private By labelInactiveContacts = By.xpath("//span[normalize-space()='Inactive Contacts']/preceding-sibling::span");
    private By labelContactsLoggedInToday = By.xpath("//span[@data-title='Contacts Logged In Today']/preceding-sibling::span");

    private By inputSearchCustomers = By.xpath("//div[@id='clients_filter']/descendant::input[@type='search']");

    private By firstRowItemCompanyName(String companyName) {
        By xpathCompanyName = By.xpath("//table[@id='clients']/descendant::a[text()='" + companyName + "']");
        return xpathCompanyName;
    }

    //Add New Customer Page
    //Customer Details tab
    private By tabCustomerDetails = By.xpath("//a[@aria-controls='contact_info']");

    //input
    private By inputCompany = By.xpath("//input[@id='company']");
    private By errorCompanyRequired = By.xpath("//p[@id='company-error']");

    private By inputVatNumber = By.xpath("//input[@id='vat']");
    private By inputPhone = By.xpath("//input[@id='phonenumber']");
    private By inputWebsite = By.xpath("//input[@id='website']");

    //group
    private By dropdownGroups = By.xpath("//button[contains(@data-id,'groups_in')]");
    private By inputSearchGroup = By.xpath("(//button[contains(@data-id,'groups_in')]/following-sibling::div)//input[@type='search']");

    private By valueGroup(String groupName) {
        By xpathValueGroup = By.xpath("(//button[contains(@data-id,'groups_in')]/following-sibling::div)//a[normalize-space()='" + groupName + "']");
        return xpathValueGroup;
    }

    //currency
    private By dropdownCurrency = By.xpath("//button[@data-id='default_currency']");
    private By inputSearchCurrency = By.xpath("(//button[@data-id='default_currency']/following-sibling::div)//input[@type='search']");

    private By valueCurrency(String currency) {
        By xpathValueCurrency = By.xpath("(//button[@data-id='default_currency']/following-sibling::div)//a[contains(normalize-space(),'" + currency + "')]");
        return xpathValueCurrency;
    }

    //language
    private By dropdownDefaultLanguage = By.xpath("//button[@data-id='default_language']");

    private By valueLanguage(String language) {
        By xpathValueLanguage = By.xpath("(//button[@data-id='default_language']/following-sibling::div)//a[normalize-space()='" + language + "']");
        return xpathValueLanguage;
    }

    //input
    private By inputAddress = By.xpath("//textarea[@id='address']");
    private By inputCity = By.xpath("//input[@id='city']");
    private By inputState = By.xpath("//input[@id='state']");
    private By inputZipCode = By.xpath("//input[@id='zip']");

    //country
    private By dropdownCountry = By.xpath("//button[@data-id='country']");
    private By inputSearchCountry = By.xpath("(//button[@data-id='country']/following-sibling::div)//input[@type='search']");

    private By valueCountry(String country) {
        By xpathValueCountry = By.xpath("(//button[@data-id='country']/following-sibling::div)//a[normalize-space()='" + country + "']");
        return xpathValueCountry;
    }

    //button
    private By buttonSaveAndCreatContact = By.xpath("//div[@id='profile-save-section']//button[normalize-space()='Save and create contact']");
    private By buttonSave = By.xpath("//div[@id='profile-save-section']//button[normalize-space()='Save']");

    //Processing methods
    public void verifyHeaderCustomersSummaryIsDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(headerCustomersSummary, 5, 500), "Header customers summary is not displayed.");
    }

    public void clickButtonNewCustomer() {
        WebUI.clickElement(buttonNewCustomer);
    }

    public void verifyCustomerDetailsTabIsActive() {
        Assert.assertTrue(WebUI.checkElementExist(tabCustomerDetails, 5, 500), "Customer Details tab is not active.");
    }

    public void fillData(CustomerDTO customerDTO) {
        WebUI.setText(inputCompany, customerDTO.getCompany());
        WebUI.setText(inputVatNumber, customerDTO.getVatNumber());
        WebUI.setText(inputPhone, customerDTO.getPhone());
        WebUI.setText(inputWebsite, customerDTO.getWebsite());

        WebUI.clickElement(dropdownGroups);
        WebUI.setText(inputSearchGroup, customerDTO.getGroups());
        WebUI.clickElement(valueGroup(customerDTO.getGroups()));
        WebUI.clickElement(dropdownGroups);

        WebUI.clickElement(dropdownCurrency);
        WebUI.setText(inputSearchCurrency, customerDTO.getCurrency());
        WebUI.clickElement(valueCurrency(customerDTO.getCurrency()));

        WebUI.clickElement(dropdownDefaultLanguage);
        WebUI.clickElement(valueLanguage(customerDTO.getDefaultLanguage()));

        WebUI.setText(inputAddress, customerDTO.getAddress());
        WebUI.setText(inputCity, customerDTO.getCity());
        WebUI.setText(inputState, customerDTO.getState());
        WebUI.setText(inputZipCode, customerDTO.getZipCode());

        WebUI.clickElement(dropdownCountry);
        WebUI.setText(inputSearchCountry, customerDTO.getCountry());
        WebUI.clickElement(valueCountry(customerDTO.getCountry()));
    }

    public void clickButtonSave() {
        WebUI.clickElement(buttonSave);
    }

    public void searchCustomer(String company) {
        WebUI.waitForPageLoaded();
        WebUI.clickElement(inputSearchCustomers);
        WebUI.setText(inputSearchCustomers, company);
    }

    public void verifyCustomerIsAddedSuccessfully(String company) {
        Assert.assertTrue(WebUI.checkElementExist(firstRowItemCompanyName(company), 10, 500),
                "Customer is not added successfully.");
    }
}