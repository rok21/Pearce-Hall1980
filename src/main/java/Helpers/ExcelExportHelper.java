package Helpers;

import Controllers.FilePickerController;
import ViewModels.ReportViewModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Rokas on 22/12/2015.
 */
public class ExcelExportHelper {
    public static void exportSimulation(FilePickerController filePickerController, ReportViewModel reportVM){
        String path = filePickerController.pickExcelExportPath();
        if(path == null)
            return;

        try {
            File file = new File(path);
            FileOutputStream stream = new FileOutputStream(file);
            ExportToStream(reportVM, stream);
        }catch (Exception ex){

        }
    }

    private static void ExportToStream(ReportViewModel reportVM, OutputStream outputStream) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for(int rowId=0;rowId<reportVM.getNumberOfRows();rowId++) {
            Row excelRow = sheet.createRow(rowId);
            for (int colId = 0; colId < reportVM.getColumnCount(); colId++) {
                excelRow.createCell(colId).setCellValue(reportVM.getCell(rowId, colId));
            }
        }
        workbook.write(outputStream);
    }
}