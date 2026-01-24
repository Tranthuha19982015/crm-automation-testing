package com.hatester.dataproviders;

import com.hatester.crm.mappers.CustomerMapper;
import com.hatester.crm.mappers.LoginMapper;
import com.hatester.crm.mappers.ProjectMapper;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.models.LoginDTO;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.helpers.ExcelHelper;
import org.testng.annotations.DataProvider;

import java.util.Map;

import static com.hatester.config.FrameworkConfig.*;
import static com.hatester.constants.ExcelConstant.*;

public class DataProviderFactory {

    //-------------------------------LOGIN DATA------------------------------------------

    private Object[][] getLoginData(int startRow, int endRow) {
        ExcelHelper excel = new ExcelHelper();

        //Sau khi đọc Excel thì kết quả trả ra excelDataMap có n dòng, 1 cột
        //Mỗi dòng (1 ô) là 1 Map
        Object[][] excelDataMap = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_LOGIN, startRow, endRow);

        Object[][] finalData = new Object[excelDataMap.length][1];

        for (int i = 0; i < excelDataMap.length; i++) {
            Map<String, String> map = (Map<String, String>) excelDataMap[i][0];

            //Gán đối tượng LoginDTO sau khi map dữ liệu từ MAP sang DTO
            LoginDTO loginDTO = LoginMapper.loginMapper(map);

            //Gán đối tượng LoginDTO vào mảng Object[][] để trả về cho DataProvider
            //Tương ứng: Mỗi Testcase thứ i nhận 1 tham số là loginDTO
            finalData[i][0] = loginDTO;
        }
        return finalData;
    }

    @DataProvider(name = "loginSuccessData")
    public Object[][] loginSuccess() {
        return getLoginData(1, 1);
    }

    @DataProvider(name = "invalidLoginEmailData")
    public Object[][] invalidLoginEmail() {
        return getLoginData(2, 2);
    }

    @DataProvider(name = "invalidLoginPasswordData")
    public Object[][] invalidLoginPassword() {
        return getLoginData(3, 3);
    }


    //-------------------------------CUSTOMERS DATA------------------------------------------

    @DataProvider(name = "addCustomerData")
    public Object[][] addCustomer() {
        ExcelHelper excel = new ExcelHelper();
        Object[][] excelDataMap = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_CUSTOMERS, 1, 2);

        Object[][] finalData = new Object[excelDataMap.length][1];

        for (int i = 0; i < excelDataMap.length; i++) {
            Map<String, String> map = (Map<String, String>) excelDataMap[i][0];
            CustomerDTO customerDTO = CustomerMapper.customerMapper(map);
            finalData[i][0] = customerDTO;
        }
        return finalData;
    }

    //-------------------------------PROJECTS DATA------------------------------------------

    @DataProvider(name = "addProjectData")
    public Object[][] addProject() {
        ExcelHelper excel = new ExcelHelper();
        Object[][] excelDataMap = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_PROJECTS, 1, 1);

        Object[][] finalData = new Object[excelDataMap.length][1];
        for (int i = 0; i < excelDataMap.length; i++) {
            Map<String, String> map = (Map<String, String>) excelDataMap[i][0];
            ProjectDTO projectDTO = ProjectMapper.projectMapper(map);
            finalData[i][0] = projectDTO;
        }
        return finalData;
    }
}