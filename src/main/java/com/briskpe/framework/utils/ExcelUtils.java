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
     *
     * @param sheetName sheet name
     * @param rowNum    zero-based row index
     * @param colNum    zero-based column index
     * @return cell value as String
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
     *
     * @param sheetName sheet name
     * @param rowNum    zero-based row index
     * @param colNum    zero-based column index
     * @param value     string value to write
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

            // Write changes back to the Excel file
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
     * Reads an entire row as a Map where keys are column headers and values are the corresponding cell values.
     *
     * @param sheetName sheet name
     * @param rowNum    zero-based row index (excluding header row which is 0)
     * @return map of header->value pairs
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
     * Retrieves the cell value as a String handling different cell types.
     *
     * @param cell Excel cell
     * @return string representation of the cell value
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        // Using DataFormatter for better formatting support
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    /**
     * Generates a random invalid mobile number and writes it to the specified location in the Excel sheet.
     *
     * @param sheetName Excel sheet name
     * @param row       zero-based row index
     * @param col       zero-based column index
     */
    public static void generateAndSaveInvalidMobileNumber(String sheetName, int row, int col) {
        String invalidMobile = generateInvalidMobileNumber();
        writeCell(sheetName, row, col, invalidMobile);
    }

    /**
     * Generates an invalid mobile number starting with digits 0-4 (assumed invalid in domain),
     * followed by 9 random digits.
     *
     * @return invalid mobile number string of length 10
     */
    private static String generateInvalidMobileNumber() {
        Random rand = new Random();
        int firstDigit = rand.nextInt(5); // digits 0 to 4 - presumably invalid start digit
        StringBuilder number = new StringBuilder(String.valueOf(firstDigit));
        for (int i = 1; i < 10; i++) {
            number.append(rand.nextInt(10));
        }
        return number.toString();
    }
}
