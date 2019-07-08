package jp.isols.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class ExcelUtilsTest {

    @BeforeEach
    public void beforeEach() {
    }

    private Path getResourceFile(final String path) {
        URI resourceUri = null;
        try {
            resourceUri = getClass().getClassLoader().getResource(path).toURI();
            return Paths.get(resourceUri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    private String getResourceFilePath(final String path) {
        return getResourceFile(path).toString();
    }

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = ImageUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void getWorkbookTest() throws Exception {
        assertNull(ExcelUtils.getWorkbook(null));
        assertNull(ExcelUtils.getWorkbook(getResourceFile("testFile-A.txt")));

        assertNotNull(ExcelUtils.getWorkbook(getResourceFile("testExcel/empty.xls")));
        assertNotNull(ExcelUtils.getWorkbook(getResourceFile("testExcel/empty.xlsx")));
        assertNotNull(ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls")));
        assertNotNull(ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx")));
    }

    @Test
    public void getSheetTest() throws Exception {
        // null
        assertNull(ExcelUtils.getSheet(null));

        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook);

            assertNotNull(sheet);
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook);

            assertNotNull(sheet);
        }

        { /* インデックス(正常) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertNotNull(sheet);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, -1);

            assertNull(sheet);
        }

        { /* インデックス(異常プラス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 9999);

            assertNull(sheet);
        }

        { /* シート名(正常) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet1");

            assertNotNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "");

            assertNull(sheet);
        }

        { /* シート名(存在しない) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet9999");

            assertNull(sheet);
        }

        { /* インデックス(正常) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertNotNull(sheet);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, -1);

            assertNull(sheet);
        }

        { /* インデックス(異常プラス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 9999);

            assertNull(sheet);
        }

        { /* シート名(正常) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet1");

            assertNotNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "");

            assertNull(sheet);
        }

        { /* シート名(存在しない) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet9999");

            assertNull(sheet);
        }
    }

    @Test
    public void createSheetTest() throws Exception {
        { /* null */
            final Sheet sheet = ExcelUtils.createSheet(null, null, null);

            assertNull(sheet);
        }

        { /* null */
            final Sheet sheet = ExcelUtils.createSheet(null, "templateSheetName", "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = workbook.getNumberOfSheets();
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // テンプレートシートは存在しない。
            assertNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = workbook.getNumberOfSheets();
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // テンプレートシートは存在しない。
            assertNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum);
        }
    }

    @Test
    public void copySheetTest() throws Exception {
        { /* null */
            final Sheet sheet = ExcelUtils.copySheet(null, null, null);

            assertNull(sheet);
        }

        { /* null */
            final Sheet sheet = ExcelUtils.copySheet(null, "templateSheetName", "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = workbook.getNumberOfSheets();
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // コピー元は存在する。
            assertNotNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum + 1);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = workbook.getNumberOfSheets();
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // コピー元は存在する。
            assertNotNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum + 1);
        }
    }

    @Test
    public void removeSheetTest_シートインデックス() throws Exception {
        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = workbook.getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, 0);

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
        }

        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-multi.xls"));
            final int sheetNum = workbook.getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, 0);

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
            assertNotNull(ExcelUtils.getSheet(workbook, "Sheet2"));
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = workbook.getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, 0);

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-multi.xlsx"));
            final int sheetNum = workbook.getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, 0);

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
            assertNotNull(ExcelUtils.getSheet(workbook, "Sheet2"));
        }
    }

    @Test
    public void removeSheetTest_シート名() throws Exception {
        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = workbook.getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, "Sheet1");

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
        }

        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-multi.xls"));
            final int sheetNum = workbook.getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, "Sheet1");

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
            assertNotNull(ExcelUtils.getSheet(workbook, "Sheet2"));
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = workbook.getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, "Sheet1");

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-multi.xlsx"));
            final int sheetNum = workbook.getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, "Sheet1");

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
            assertNotNull(ExcelUtils.getSheet(workbook, "Sheet2"));
        }
    }

    @Test
    public void removeSheetTest_異常() throws Exception {
        { /* null */
            assertThrows(Exception.class, () -> {
                ExcelUtils.removeSheet(null, null);
            });
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            assertThrows(Exception.class, () -> {
                ExcelUtils.removeSheet(workbook, null);
            });
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            assertThrows(Exception.class, () -> {
                ExcelUtils.removeSheet(workbook, -1);
            });
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            assertThrows(Exception.class, () -> {
                ExcelUtils.removeSheet(workbook, "");
            });
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            assertThrows(Exception.class, () -> {
                ExcelUtils.removeSheet(workbook, null);
            });
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            assertThrows(Exception.class, () -> {
                ExcelUtils.removeSheet(workbook, -1);
            });
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            assertThrows(Exception.class, () -> {
                ExcelUtils.removeSheet(workbook, "");
            });
        }
    }

    @Test
    public void getRowTest_行0() throws Exception {
        { /* null */
            final Row row = ExcelUtils.getRow(null, 0, 0, 0);

            assertNull(row);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 0);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 1);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 1, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 1);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 0);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 1);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 1, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 1);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }
    }

    @Test
    public void getRowTest_行1() throws Exception {
        { /* null */
            final Row row = ExcelUtils.getRow(null, 1, 0, 0);

            assertNull(row);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 1, 0, 0);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 1);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 1);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 1, 1, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 1);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 1);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 1, 0, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 1);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 1, 0, 0);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 1);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 1);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 1, 1, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 1);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 1);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 1, 0, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 1);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }
    }

    @Test
    public void getRowTest_マイナス() throws Exception {
        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, 0, 0);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, -1, 0);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, -1);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, -1, 0);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, -1, -1);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, 0, -1);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, -1, -1);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, 0, 0);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, -1, 0);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, -1);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, -1, 0);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, -1, -1);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, 0, -1);

            assertNull(row);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, -1, -1);

            assertNull(row);
        }
    }

    @Test
    public void getRowTest_範囲() throws Exception {
        { /* 範囲が異常値 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 9999, 0, 1);

            assertNull(row);
        }

        { /* 範囲が逆転 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 1, 0);

            assertNull(row);
        }

        { /* 範囲外 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 99);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* 範囲外 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 99, 999);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 0);
            assertEquals(row.getFirstCellNum(), -1);
            assertEquals(row.getLastCellNum(), -1);
        }

        { /* 範囲が異常値 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 9999, 0, 1);

            assertNull(row);
        }

        { /* 範囲が逆転 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 1, 0);

            assertNull(row);
        }

        { /* 範囲外 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 99);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* 範囲外 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 99, 999);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 0);
            assertEquals(row.getFirstCellNum(), -1);
            assertEquals(row.getLastCellNum(), -1);
        }
    }

    @Test
    public void getCellTest() throws Exception {
        { /* null */
            final Cell cell = ExcelUtils.getCell(null, 0, 0);

            assertNull(cell);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, -1, 0);

            assertNull(cell);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, -1);

            assertNull(cell);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, -1, -1);

            assertNull(cell);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);

            assertNotNull(cell);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, -1, 0);

            assertNull(cell);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, -1);

            assertNull(cell);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, -1, -1);

            assertNull(cell);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);

            assertNotNull(cell);
        }
    }

    @Test
    public void getCellValueTest() throws Exception {
        { /* null */
            final Object value = ExcelUtils.getCellValue(null, null);

            assertNull(value);
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Object value = ExcelUtils.getCellValue(workbook, null);

            assertNull(value);
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(null, cell);

            assertNull(value);
        }

        { /* 通常(標準セル - 文字列) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof HSSFRichTextString);
            assertEquals(((HSSFRichTextString)value).toString(), "testA1");
        }

        { /* 通常(標準セル - 数値) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 1, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Double);
            assertEquals(value, 1.0);
        }

        { /* 通常(数値セル - 数値) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 2, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Double);
            assertEquals(value, 3.0);
        }

        { /* 通常(空白セル) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 3, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNull(value);
        }

        { /* 通常(標準セル - 空白3文字) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 4, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof HSSFRichTextString);
            assertEquals(((HSSFRichTextString)value).toString(), "   ");
        }

        { /* 通常(日付セル) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 5, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Date);
            assertEquals(value, DateTimeUtils.parseToDate("2019" + DateTimeUtils.DATE_SPLIT + "01" + DateTimeUtils.DATE_SPLIT + "01", DateTimeUtils.DATE_FORMAT));
        }

        { /* 通常(真偽) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 6, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Boolean);
            assertEquals(value, Boolean.TRUE);
        }

        { /* 通常(エラー) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 7, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof String);
            assertEquals(value, "#DIV/0!");
        }

        { /* 通常(数式 - 数値) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 8, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Double);
            assertEquals(value, 3.0);
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Object value = ExcelUtils.getCellValue(workbook, null);

            assertNull(value);
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(null, cell);

            assertNull(value);
        }

        { /* 通常(標準セル - 文字列) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals(((XSSFRichTextString)value).toString(), "testA1");
        }

        { /* 通常(標準セル - 数値) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 1, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Double);
            assertEquals(value, 1.0);
        }

        { /* 通常(数値セル - 数値) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 2, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Double);
            assertEquals(value, 3.0);
        }

        { /* 通常(空白セル) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 3, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNull(value);
        }

        { /* 通常(標準セル - 空白3文字) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 4, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals(((XSSFRichTextString)value).toString(), "   ");
        }

        { /* 通常(日付セル) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 5, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Date);
            assertEquals(value, DateTimeUtils.parseToDate("2019" + DateTimeUtils.DATE_SPLIT + "01" + DateTimeUtils.DATE_SPLIT + "01", DateTimeUtils.DATE_FORMAT));
        }

        { /* 通常(真偽) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 6, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Boolean);
            assertEquals(value, Boolean.TRUE);
        }

        { /* 通常(エラー) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 7, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof String);
            assertEquals(value, "#DIV/0!");
        }

        { /* 通常(数式 - 数値) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 8, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Double);
            assertEquals(value, 3.0);
        }
    }
}
