package org.example.controllers;

import org.example.services.ExcelService;

import java.io.IOException;
import java.util.List;

public class DashboardController {
    private ExcelService excelService;

    public DashboardController() {
        this.excelService = new ExcelService();
    }

    public List<List<String>> importData(String filePath) {
        try {
            return excelService.importExcel(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void exportData(String filePath, List<List<String>> data) {
        try {
            excelService.exportExcel(filePath, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
