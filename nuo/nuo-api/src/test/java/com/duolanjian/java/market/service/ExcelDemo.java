package com.duolanjian.java.market.service;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

public class ExcelDemo {

    @Test
    public void readAndWrite() throws Exception{

        File file = new File("/Users/chenlisong/Downloads/alldata.xlsx");//表格存储的位置
        File header = new File("/Users/chenlisong/Downloads/header2.xlsx");
        String unitPath = new String("/Users/chenlisong/Downloads/excels/");

        Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            File unitFile = new File(unitPath + (int) Double.parseDouble(row.getCell(3).toString()) + ".xlsx");
            copyFile(header, unitFile);

            Workbook unitWorkbook = new XSSFWorkbook(new FileInputStream(unitFile));

            Sheet unitSheet = unitWorkbook.getSheetAt(0);
            Row unitRow = unitSheet.createRow(1);
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                Cell unitCell = unitRow.createCell(j);
                //unitCell = row.getCell(j);
                copyCell(row.getCell(j), unitCell);
            }

            FileOutputStream outputStream = new FileOutputStream(unitFile);
            unitWorkbook.write(outputStream);
            outputStream.close();
        }

        /*for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if(row == null) {
                continue;
            }

            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if(cell == null) {
                    continue;
                }
                System.out.println(cell);
            }

        }*/
    }

    private static void copyFile(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    public static void copyCell(Cell srcCell, Cell distCell) {
        if(srcCell == null){
            return;
        }

        // 不同数据类型处理
        int srcCellType = srcCell.getCellType();
        distCell.setCellType(srcCellType);
        if (srcCellType == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
                distCell.setCellValue(srcCell.getDateCellValue());
            } else {
                distCell.setCellValue(srcCell.getNumericCellValue());
            }
        } else if (srcCellType == Cell.CELL_TYPE_STRING) {
            distCell.setCellValue(srcCell.getRichStringCellValue());
        } else if (srcCellType == Cell.CELL_TYPE_BLANK) {
            // nothing21
        } else if (srcCellType == Cell.CELL_TYPE_BOOLEAN) {
            distCell.setCellValue(srcCell.getBooleanCellValue());
        } else if (srcCellType == Cell.CELL_TYPE_ERROR) {
            distCell.setCellErrorValue(srcCell.getErrorCellValue());
        } else if (srcCellType == Cell.CELL_TYPE_FORMULA) {
            distCell.setCellFormula(srcCell.getCellFormula());
        } else { // nothing29
        }
    }



}
