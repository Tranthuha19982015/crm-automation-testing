package com.hatester.crm.testcontext;

import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.models.ProjectDTO;

public class TestContext {
    //ThreadLocal giữ 1 object đơn lẻ (CustomerDTO)
    private static final ThreadLocal<CustomerDTO> customer = new ThreadLocal<>();
    private static final ThreadLocal<ProjectDTO> project = new ThreadLocal<>();

    /// Customer
    public static CustomerDTO getCustomer() {
        return customer.get();
    }

    public static void setCustomer(CustomerDTO customer) {
        TestContext.customer.set(customer);
    }

    /// Project
    public static ProjectDTO getProject() {
        return project.get();
    }

    public static void setProject(ProjectDTO project) {
        TestContext.project.set(project);
    }

    public static void clear() {
        customer.remove();
        project.remove();
    }
}
