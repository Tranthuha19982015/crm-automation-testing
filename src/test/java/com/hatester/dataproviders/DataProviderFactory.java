package com.hatester.dataproviders;

import com.hatester.crm.mappers.CustomerMapper;
import com.hatester.crm.mappers.LoginMapper;
import com.hatester.crm.mappers.ProjectMapper;
import com.hatester.crm.mappers.TaskMapper;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.models.LoginDTO;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.crm.models.TaskDTO;
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

    @DataProvider(name = "addCustomerData", parallel = false)
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
    //TCs: (cus, add)
    @DataProvider(name = "projectBaseData")
    public Object[][] projectBaseData() {
        ExcelHelper excel = new ExcelHelper();

        int startRow = 1;
        int endRow = 1;

        Object[][] customerMap = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_CUSTOMERS, startRow, endRow);
        Object[][] projectMap = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_PROJECTS, startRow, endRow);

        int totalCustomerRows = customerMap.length;

        Object[][] finalData = new Object[totalCustomerRows][2];

        for (int i = 0; i < totalCustomerRows; i++) {
            Map<String, String> customerRow = (Map<String, String>) customerMap[i][0];
            Map<String, String> projectRow = (Map<String, String>) projectMap[i][0];

            CustomerDTO customerDTO = CustomerMapper.customerMapper(customerRow);
            ProjectDTO projectDTO = ProjectMapper.projectMapper(projectRow);

            finalData[i][0] = customerDTO;
            finalData[i][1] = projectDTO;
        }
        return finalData;
    }

    //Cấu trúc data trả về: Object[][] = {{ CustomerDTO, addProjectDTO, editProjectDTO }}
    //TCs 1: (cus1, add1, edit1)
    //TCs 2: (cus1, add2, edit2)
    @DataProvider(name = "editProjectData", parallel = false)
    public Object[][] editProject() {
        ExcelHelper excel = new ExcelHelper();
        Object[][] customerMap = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_CUSTOMERS, 1, 1);
        Object[][] projectMap = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_PROJECTS, 1, 4);

        int totalRow = projectMap.length;
        int totalTestcase = totalRow / 2;

        Object[][] finalData = new Object[totalTestcase][3];

        int indexTCs = 0;

        for (int i = 0; i < totalRow; i += 2) {
            Map<String, String> customerRow = (Map<String, String>) customerMap[0][0];
            Map<String, String> addMap = (Map<String, String>) projectMap[i][0];
            Map<String, String> editMap = (Map<String, String>) projectMap[i + 1][0];

            CustomerDTO customerDTO = CustomerMapper.customerMapper(customerRow);
            ProjectDTO addProject = ProjectMapper.projectMapper(addMap);
            ProjectDTO editProject = ProjectMapper.projectMapper(editMap);

            finalData[indexTCs][0] = customerDTO;
            finalData[indexTCs][1] = addProject;
            finalData[indexTCs][2] = editProject;

            indexTCs++;
        }
        return finalData;
    }


    //-------------------------------TASKS DATA------------------------------------------
    @DataProvider(name = "taskBaseData")
    public Object[][] taskBaseData() {
        ExcelHelper excel = new ExcelHelper();

        int startRow = 1;
        int endRow = 1;

        Object[][] customerData = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_CUSTOMERS, startRow, endRow);
        Object[][] projectData = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_PROJECTS, startRow, endRow);
        Object[][] taskData = excel.getDataMap(getExcelDataFilePath() + EXCEL_FILE_NAME, EXCEL_SHEET_TASKS, startRow, endRow);

        int totalRow = (endRow - startRow) + 1;

        Object[][] finalData = new Object[totalRow][3];
        for (int i = 0; i < totalRow; i++) {
            Map<String, String> customerRow = (Map<String, String>) customerData[i][0];
            Map<String, String> projectRow = (Map<String, String>) projectData[i][0];
            Map<String, String> taskRow = (Map<String, String>) taskData[i][0];

            CustomerDTO customerDTO = CustomerMapper.customerMapper(customerRow);
            ProjectDTO projectDTO = ProjectMapper.projectMapper(projectRow);
            TaskDTO taskDTO = TaskMapper.taskMapper(taskRow);

            finalData[i][0] = customerDTO;
            finalData[i][1] = projectDTO;
            finalData[i][2] = taskDTO;
        }
        return finalData;
    }
}