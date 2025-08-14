package com.briskpe.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelUtils {

    private static final Logger logger = Logger.getLogger(ExcelUtils.class.getName());
    private static final String EXCEL_PATH = "src/test/resources/BriskPeTestDataWeb.xlsx";

    /**
     * Reads the value of a single cell.
     */
    public static String readCell(String sheetName, int rowNum, int colNum) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet '" + sheetName + "' not found");
            Row row = sheet.getRow(rowNum);
            if (row == null) return "";
            Cell cell = row.getCell(colNum);
            return getCellValue(cell);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read from Excel", e);
            throw new RuntimeException("❌ Failed to read from Excel", e);
        }
    }

    /**
     * Writes a string value to a specified cell.
     */
    public static void writeCell(String sheetName, int rowNum, int colNum, String value) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet '" + sheetName + "' not found");
            Row row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);
            Cell cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);

            cell.setCellValue(value);

            try (FileOutputStream fos = new FileOutputStream(EXCEL_PATH)) {
                workbook.write(fos);
            }
            logger.info("Data written to Excel: " + value);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to write to Excel", e);
            throw new RuntimeException("❌ Failed to write to Excel", e);
        }
    }

    /**
     * Reads an entire row as a Map where keys are column headers and values are corresponding cell values.
     */
    public static Map<String, String> readRowAsMap(String sheetName, int rowNum) {
        Map<String, String> data = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet '" + sheetName + "' not found");
            Row header = sheet.getRow(0);
            Row row = sheet.getRow(rowNum);
            if (row == null) return data;

            for (int i = 0; i < header.getLastCellNum(); i++) {
                String key = getCellValue(header.getCell(i));
                String value = getCellValue(row.getCell(i));
                data.put(key, value);
            }

            return data;

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read row as map from Excel", e);
            throw new RuntimeException("❌ Failed to read row as map", e);
        }
    }

    /**
     * Fetches value by looking up a key in one column and returning the related value from another column.
     */
    public static String getValueByKey(String sheetName, String keyColumnName, String keyValue, String valueColumnName) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet '" + sheetName + "' not found");

            DataFormatter formatter = new DataFormatter();

            // Find column indexes from header row
            Row headerRow = sheet.getRow(0);
            int keyColIndex = -1;
            int valueColIndex = -1;

            for (Cell cell : headerRow) {
                String columnName = formatter.formatCellValue(cell).trim();
                if (columnName.equalsIgnoreCase(keyColumnName)) {
                    keyColIndex = cell.getColumnIndex();
                }
                if (columnName.equalsIgnoreCase(valueColumnName)) {
                    valueColIndex = cell.getColumnIndex();
                }
            }

            if (keyColIndex == -1 || valueColIndex == -1) {
                throw new RuntimeException("Column not found: " + keyColumnName + " or " + valueColumnName);
            }

            // Search rows for the matching key
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String currentKey = formatter.formatCellValue(row.getCell(keyColIndex)).trim();
                    if (currentKey.equalsIgnoreCase(keyValue)) {
                        return formatter.formatCellValue(row.getCell(valueColIndex)).trim();
                    }
                }
            }

            return null; // key not found

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read value by key from Excel", e);
            throw new RuntimeException("❌ Failed to read by key", e);
        }
    }

    /**
     * Retrieves the cell value as a String, handling different cell types.
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    /**
     * Generates a random invalid mobile number and writes it to Excel.
     */
    public static void generateAndSaveInvalidMobileNumber(String sheetName, int row, int col) {
        String invalidMobile = generateInvalidMobileNumber();
        writeCell(sheetName, row, col, invalidMobile);
    }

    /**
     * Generates an invalid mobile number starting with digits 0-4.
     */
    private static String generateInvalidMobileNumber() {
        Random rand = new Random();
        int firstDigit = rand.nextInt(5);
        StringBuilder number = new StringBuilder(String.valueOf(firstDigit));
        for (int i = 1; i < 10; i++) {
            number.append(rand.nextInt(10));
        }
        return number.toString();
    }
}
