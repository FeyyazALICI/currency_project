package com.currency.bb.components.bussiness.service.excel;


import java.text.SimpleDateFormat;
import java.util.Date;

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
public class FrankfurtherExcelService {
    
    public ResponseEntity<byte[]> exportAllDataToExcel(FrankfurterApiDTO data) throws Exception {
        // Create an Excel workbook
        Workbook workbook = new XSSFWorkbook();

        // preparing sheet name
        Sheet sheet = workbook.createSheet(   "data"   );

        // Create header row
        Row headerRow = sheet.createRow(0);
        int i = 0;
        headerRow.createCell(i++).setCellValue("currency");

        headerRow.createCell(i++).setCellValue("value");

        // adding base currency:
        // Fill data
        int rowNum = 1;
        for (String currency : data.getRates().keySet()) {
            Row row = sheet.createRow(rowNum++);
            int j = 0;
            row.createCell(j++).setCellValue(currency);

            row.createCell(j++).setCellValue(data.getRates().get(currency));
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
