package com.currency.bb.components.bussiness.service.excel;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.currency.bb.components.bussiness.dto.FrankfurterApiDTO;

// Excel imports
import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@Service
public class FrankfurtherDerivedExcelService {
    
    public ResponseEntity<byte[]> exportAllDataToExcel(ArrayList<FrankfurterApiDTO> data) throws Exception {
        // Create an Excel workbook
        Workbook workbook = new XSSFWorkbook();

        // preparing sheet name
        Sheet sheet = workbook.createSheet(   "data"   );

        // Create header row
        Row headerRow = sheet.createRow(0);
        int i = 0;
        headerRow.createCell(i++).setCellValue("base");
        headerRow.createCell(i++).setCellValue("target");
        headerRow.createCell(i++).setCellValue("value");
        headerRow.createCell(i++).setCellValue("date");

        // adding base currency:
        // Fill data
        int rowNum = 1;
        for (FrankfurterApiDTO e : data) {
            Row row = sheet.createRow(rowNum++);
            int j = 0;
            row.createCell(j++).setCellValue(e.getBase());
            // target -------------------------------------------------
            Set<String> keys = e.getRates().keySet();
            String firstKey = keys.iterator().next();  // get the first element
            row.createCell(j++).setCellValue(firstKey);
            // target -------------------------------------------------
            row.createCell(j++).setCellValue(e.getRates().get(firstKey));
            row.createCell(j++).setCellValue(e.getDate());
        }
          {
  }

        // Write to byte array output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
        // preparing file name
        Date currenDate = new Date(System.currentTimeMillis());
        String sdfPattern = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(sdfPattern);
        String dateString = sdf.format(currenDate);
        StringBuilder stb = new StringBuilder();
        stb.append("AllData");
        stb.append("_");
        stb.append(dateString);
        stb.append(".xlsx");
        String fileName = stb.toString();
        
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(outputStream.size());

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }
}
