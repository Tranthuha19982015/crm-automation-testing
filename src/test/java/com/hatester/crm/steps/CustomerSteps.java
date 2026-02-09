package com.hatester.crm.steps;

import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.pages.CustomerPage;

public class CustomerSteps {
    private final CustomerPage customerPage;

    public CustomerSteps(CustomerPage customerPage) {
        this.customerPage = customerPage;
    }

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        customerPage.verifyCustomersPageDisplayed();
        customerPage.clickNewCustomerButton();
        customerPage.verifyCustomerDetailsTabIsActive();

        CustomerDTO cusDTO = customerPage.fillCustomerFormAndSave(customerDTO);
        String customerName = cusDTO.getCompany();

        // UI redirect sang detail → recover về list
        customerPage.goToCustomersFromMenu();

        customerPage.searchCustomerByName(customerName);
        customerPage.verifyCustomerDisplayedInList(customerName);

        return cusDTO;
    }
}
