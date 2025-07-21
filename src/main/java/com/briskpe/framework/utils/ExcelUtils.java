package com.briskpe.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExcelUtils {

    private static final String EXCEL_PATH = "src/test/resources/BriskPeTestDataWeb.xlsx";

    // Read a single cell
    public static String readCell(String sheetName, int rowNum, int colNum) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            return getCellValue(cell);

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to read from Excel", e);
        }
    }

    // Write a single cell
    public static void writeCell(String sheetName, int rowNum, int colNum, String value) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);
            Cell cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);

            cell.setCellValue(value);

            try (FileOutputStream fos = new FileOutputStream(EXCEL_PATH)) {
                workbook.write(fos);
                System.out.println("✅ Data written to Excel: " + value);
            }

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to write to Excel", e);
        }
    }

    // Optional: Read row as Map<String, String> if headers exist
    public static Map<String, String> readRowAsMap(String sheetName, int rowNum) {
        Map<String, String> data = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row header = sheet.getRow(0);
            Row row = sheet.getRow(rowNum);

            for (int i = 0; i < header.getLastCellNum(); i++) {
                String key = getCellValue(header.getCell(i));
                String value = getCellValue(row.getCell(i));
                data.put(key, value);
            }

            return data;

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to read row as map", e);
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    // ExcelUtils.java (already exists, add this method)
    public static void generateAndSaveInvalidMobileNumber(String sheetName, int row, int col) {
        String invalidMobile = generateInvalidMobileNumber();
        writeCell(sheetName, row, col, invalidMobile);
    }

    private static String generateInvalidMobileNumber() {
        Random rand = new Random();
        int firstDigit = rand.nextInt(5); // 0 to 4 (invalid start)
        StringBuilder number = new StringBuilder(String.valueOf(firstDigit));
        for (int i = 1; i < 10; i++) {
            number.append(rand.nextInt(10));
        }
        return number.toString();
    }

}
