package com.hatester.crm.testcontext;

import com.hatester.crm.models.CustomerDTO;

public class TestContext {
    //ThreadLocal giữ 1 object đơn lẻ (CustomerDTO)
    private static final ThreadLocal<CustomerDTO> customerHolder = new ThreadLocal<>();

    public static CustomerDTO getCustomer() {
        return customerHolder.get();
    }

    public static void setCustomer(CustomerDTO customer) {
        customerHolder.set(customer);
    }

    public static void clear() {
        customerHolder.remove();
    }
}
