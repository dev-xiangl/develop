package tdpay.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import jp.isols.common.utils.ExcelUtils;

/**
 *
 */
@Service
public class SettingDataService {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(SettingDataService.class);

    public List<Object> getObjectList(Workbook workbook, Sheet sheet) {
        List<Object> objectList = new ArrayList<Object>();
        List<Cell> cellList = getCellList(sheet);

        for( Cell cell : cellList ){
            Object object = ExcelUtils.getCellValue(workbook, cell);
		    if( object == null ) break;
            objectList.add(object);
            logger.debug("object = {}", object);
        }
        return objectList;
    }

    private List<Cell> getCellList(Sheet sheet) {
        Integer colIndex = 1;
        Integer rowIndex = 1;
        List<Cell> cellList = new ArrayList<Cell>();

        while (true) {
            Cell cell = ExcelUtils.getCell(sheet, rowIndex, colIndex);
            if (cell == null) break;

            cellList.add(cell);
            rowIndex++;
        }

        return cellList;
    }

}
