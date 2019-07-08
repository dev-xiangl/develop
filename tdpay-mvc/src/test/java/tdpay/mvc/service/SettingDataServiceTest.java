package tdpay.mvc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.transaction.annotation.Transactional;

import jp.isols.common.utils.ExcelUtils;

@Transactional
public class SettingDataServiceTest {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(SettingDataServiceTest.class);

    @InjectMocks
    private SettingDataService service;

	@BeforeEach
    public void beforeEach() {
		service = new SettingDataService();
    }

    /**
     * ObjectList取得機能のテスト
     *
     */
    @Test
    public void getObjectListTest() {
        Path path = Paths.get(System.getProperty("java.io.tmpdir"), "CompanyTest.xls");
        Workbook workbook = ExcelUtils.getWorkbook(path);
        Sheet sheet = ExcelUtils.getSheet(workbook);

        { /* null */
            List<Object> objectList = service.getObjectList(null, null);
            assertNotNull(objectList);
            assertTrue(objectList.isEmpty());
        }
        { /* 正常値 */
            List<Object> objectList = service.getObjectList(workbook, sheet);
            assertNotNull(objectList);
        }
    }

}
