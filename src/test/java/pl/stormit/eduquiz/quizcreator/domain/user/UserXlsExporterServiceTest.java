package pl.stormit.eduquiz.quizcreator.domain.user;

import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.stormit.eduquiz.quizcreator.domain.user.Role.ROLE_USER;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class UserXlsExporterServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserXlsExporterService userXlsExporterService;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void SetUp() {
        User firstUser = new User(null, "user1", "user1@example.com", "FirstUser1!", Status.VERIFIED, ROLE_USER, Instant.now());
        User secondUser = new User(null, "user2", "user2@example.com", "SecondUser2!", Status.UNVERIFIED, ROLE_USER, Instant.now());
        User thirdUser = new User(null, "user3", "user3@example.com", "ThirdUser3!", Status.UNVERIFIED, ROLE_USER, Instant.now());

        userRepository.saveAll(List.of(firstUser, secondUser, thirdUser))
                .forEach(user -> userMapper.mapUserEntityToUserDto(user));
    }

    @Test
    void exportUsersToXlsShouldCreateValidExcelFile() throws IOException {
        // Given
        List<User> users = userRepository.findAll();

        // When
        byte[] excelFile = userXlsExporterService.exportUsersToXLS(users);
        Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelFile));
        Sheet sheet = workbook.getSheet("Users");
        Row headerRow = sheet.getRow(0);
        Row userRow = sheet.getRow(1);

        // Then
        assertEquals("ID", headerRow.getCell(0).getStringCellValue());
        assertEquals("Nickname", headerRow.getCell(1).getStringCellValue());
        assertEquals("Email", headerRow.getCell(2).getStringCellValue());
        assertEquals("Status", headerRow.getCell(3).getStringCellValue());
        assertEquals("Role", headerRow.getCell(4).getStringCellValue());
        assertEquals("Created At", headerRow.getCell(5).getStringCellValue());

        assertEquals(users.get(0).getId().toString(), userRow.getCell(0).getStringCellValue());
        assertEquals(users.get(0).getNickname(), userRow.getCell(1).getStringCellValue());
        assertEquals(users.get(0).getEmail(), userRow.getCell(2).getStringCellValue());
        assertEquals(users.get(0).getStatus().toString(), userRow.getCell(3).getStringCellValue());
        assertEquals(users.get(0).getRole().toString(), userRow.getCell(4).getStringCellValue());
        assertEquals(users.get(0).getCreatedAt().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_DATE_TIME),
                userRow.getCell(5).getStringCellValue());
    }
}
