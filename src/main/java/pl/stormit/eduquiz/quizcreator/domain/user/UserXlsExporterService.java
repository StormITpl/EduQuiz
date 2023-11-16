package pl.stormit.eduquiz.quizcreator.domain.user;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserXlsExporterService {

    public byte[] exportUsersToXLS(List<User> users) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");

            createHeader(sheet);

            int rowNum = 1;
            for (User user : users) {
                createRow(sheet, rowNum++, user);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return outputStream.toByteArray();
        }
    }

    private void createHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Nickname", "Email", "Status", "Role", "Created At"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private void createRow(Sheet sheet, int rowNum, User user) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(user.getId().toString());
        row.createCell(1).setCellValue(user.getNickname());
        row.createCell(2).setCellValue(user.getEmail());
        setCellStringValue(row.createCell(3), user.getStatus());
        setCellStringValue(row.createCell(4), user.getRole());
        setCellDateValue(row.createCell(5), user.getCreatedAt());
    }

    private void setCellStringValue(Cell cell, Object value) {
        if (value != null) {
            cell.setCellValue(value.toString());
        } else {
            cell.setCellValue("N/A");
        }
    }

    private void setCellDateValue(Cell cell, Instant dateTime) {
        if (dateTime != null) {
            cell.setCellValue(dateTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_DATE_TIME));
        } else {
            cell.setCellValue("N/A");
        }
    }
}
