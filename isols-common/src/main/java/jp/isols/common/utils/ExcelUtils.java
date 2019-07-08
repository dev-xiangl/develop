package jp.isols.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ConditionalFormatting;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * Excelユーティリティクラス
 */
public class ExcelUtils {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(ExcelUtils.class);

    private ExcelUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * ワークブックを取得する。
     *
     * @param path ファイルのパス
     */
    public static Workbook getWorkbook(final Path path) {
        try {
            return WorkbookFactory.create(new FileInputStream(path.toAbsolutePath().toString()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * シートを取得する。
     *
     * @param workbook ワークブック
     * @note 0番目のシートを取得する。
     */
    public static Sheet getSheet(final Workbook workbook) {
        return getSheet(workbook, 0);
    }

    /**
     * シートを取得する。
     *
     * @param workbook ワークブック
     * @param sheetIndex シートのインデックス
     * @note 指定したインデックスのシートを取得する。
     */
    public static Sheet getSheet(final Workbook workbook, final int sheetIndex) {
        try {
            return workbook.getSheetAt(sheetIndex);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * シートを取得する。
     *
     * @param workbook ワークブック
     * @param sheetName シート名
     * @note 指定したシート名のシートを取得する。
     */
    public static Sheet getSheet(final Workbook workbook, final String sheetName) {
        try {
            return workbook.getSheet(sheetName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * シートを生成する。
     *
     * @param workbook ワークブック
     * @param templateSheetName テンプレートシート名
     * @param sheetName シート名
     * @return 生成したシート
     * @note 指定したテンプレート名のシートをコピーして、指定した名称のシートを生成する。
     *       テンプレートシートは削除する。
     */
    public static Sheet createSheet(final Workbook workbook, final String templateSheetName, final String sheetName) {
        final List<Sheet> sheetList = createSheets(workbook, templateSheetName, true, sheetName);
        return CollectionUtils.isEmpty(sheetList) ? null : sheetList.get(0);
    }

    /**
     * シートを生成する。
     *
     * @param workbook ワークブック
     * @param templateSheetName テンプレートシート名
     * @param sheetNames シート名配列
     * @return 生成したシートのリスト
     * @note 指定したテンプレート名のシートをコピーして、指定した名称分のシートを生成する。
     *       テンプレートシートは削除する。
     */
    public static List<Sheet> createSheets(final Workbook workbook, final String templateSheetName, final String... sheetNames) {
        return createSheets(workbook, templateSheetName, true, sheetNames);
    }

    /**
     * シートを生成する。
     *
     * @param workbook ワークブック
     * @param templateSheetName テンプレートシート名
     * @param templateDelete テンプレートシートを削除(true -> 削除する, false -> 削除しない)
     * @param sheetNames シート名配列
     * @return 生成したシートのリスト
     * @note 指定したテンプレート名のシートをコピーして、指定した名称分のシートを生成する。
     */
    private static List<Sheet> createSheets(final Workbook workbook, final String templateSheetName, final boolean templateDelete, final String... sheetNames) {
        if (workbook == null) {
            logger.error("workbook is null.");
            return null;
        }

        if (StringUtils.isEmpty(templateSheetName)) {
            logger.error("templateSheetName is null.");
            return null;
        }

        final Sheet templateSheet = getSheet(workbook, templateSheetName);
        if (templateSheet == null) {
            logger.error("templateSheet is null.");
            return null;
        }

        if (sheetNames == null || sheetNames.length < 1) {
            logger.error("sheetNames is empty.");
            return null;
        }

        List<Sheet> sheetList = new LinkedList<>();
        final PrintSetup templatePs = templateSheet.getPrintSetup();
        for (int i = 0; i < sheetNames.length; i++) {
            final String sheetName = sheetNames[i];
            if (StringUtils.isBlank(sheetName)) {
                continue;
            }

            final Sheet sheet = workbook.cloneSheet(workbook.getSheetIndex(templateSheetName));
            workbook.setSheetName(workbook.getSheetIndex(sheet), sheetName);

            // 印刷設定
            final PrintSetup ps = sheet.getPrintSetup();
            ps.setCopies(templatePs.getCopies());
            ps.setDraft(templatePs.getDraft());
            ps.setFitHeight(templatePs.getFitHeight());
            ps.setFitWidth(templatePs.getFitWidth());
            ps.setFooterMargin(templatePs.getFooterMargin());
            ps.setHeaderMargin(templatePs.getHeaderMargin());
            ps.setHResolution(templatePs.getHResolution());
            ps.setLandscape(templatePs.getLandscape());
            ps.setLeftToRight(templatePs.getLeftToRight());
            ps.setNoColor(templatePs.getNoColor());
            ps.setNoOrientation(templatePs.getNoOrientation());
            ps.setNotes(templatePs.getNotes());
            ps.setPaperSize(templatePs.getPaperSize());
            ps.setScale(templatePs.getScale());
            ps.setUsePage(templatePs.getUsePage());
            ps.setValidSettings(templatePs.getValidSettings());
            ps.setVResolution(templatePs.getVResolution());

            sheetList.add(sheet);
        }

        if (templateDelete == true) {
            removeSheet(workbook, templateSheetName);
        }

        return sheetList;
    }

    /**
     * シートをコピーする。
     *
     * @param workbook ワークブック
     * @param fromSheetName コピー元シート名
     * @param toSheetName コピー先シート名
     * @return 生成したシート
     */
    public static Sheet copySheet(final Workbook workbook, final String fromSheetName, final String toSheetName) {
        final List<Sheet> sheetList = createSheets(workbook, fromSheetName, false, toSheetName);
        return CollectionUtils.isEmpty(sheetList) ? null : sheetList.get(0);
    }

    /**
     * シートをコピーする。
     *
     * @param workbook ワークブック
     * @param fromSheetName コピー元シート名
     * @param toSheetName コピー先シート名配列
     * @return 生成したシートのリスト
     */
    public static List<Sheet> copySheets(final Workbook workbook, final String fromSheetName, final String... toSheetNames) {
        return createSheets(workbook, fromSheetName, false, toSheetNames);
    }

    /**
     * シートを削除する。
     *
     * @param workbook ワークブック
     * @param sheetName シート名
     * @note 指定したインデックスのシートを削除する。
     */
    public static void removeSheet(final Workbook workbook, final int sheetIndex) {
        workbook.removeSheetAt(sheetIndex);
    }

    /**
     * シートを削除する。
     *
     * @param workbook ワークブック
     * @param sheetName シート名
     * @note 指定したシート名のシートを削除する。
     */
    public static void removeSheet(final Workbook workbook, final String sheetName) {
        workbook.removeSheetAt(workbook.getSheetIndex(sheetName));
    }

    /**
     * 行を取得する。
     *
     * @param sheet シート
     * @param rowIndex 行インデックス
     * @param firstCol 開始列インデックス
     * @param lastCol 終了列インデックス
     * @return Rowオブジェクト
     * @note 行が存在しない場合は、前の行を指定列まで書式をコピーして生成する。
     */
    public static Row getRow(final Sheet sheet, final int rowIndex, final int firstCol, final int lastCol) {
        if (sheet == null) {
            logger.error("sheet is null.");
            return null;
        }

        if (rowIndex < 0) {
            logger.error("rowIndex({}) is invalid.", rowIndex);
            return null;
        }

        if (firstCol < 0 || lastCol < 0) {
            logger.error("firstCol({}) / lastCol({}) is invalid.", firstCol, lastCol);
            return null;
        }

        if (firstCol > lastCol) {
            logger.error("firstCol({}) / lastCol({}) is invalid.", firstCol, lastCol);
            return null;
        }

        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            final Row srcRow = sheet.getRow(rowIndex - 1);
            if (srcRow == null) {
                logger.error("srcRow(rowIndex={}) is invalid.", rowIndex - 1);
                return null;
            }
            row = sheet.createRow(rowIndex);

            row.setHeight(srcRow.getHeight());

            for (int i = firstCol; i <= lastCol; i++) {
                final Cell srcCell = srcRow.getCell(i);
                final Cell cell = row.createCell(i);

                cell.setCellStyle(srcCell.getCellStyle());
                cell.setCellType(srcCell.getCellType());
            }
        } else {
            //logger.debug("CellNum: " + row.getFirstCellNum() + " -> " + row.getLastCellNum());
            for (int i = row.getLastCellNum(); i >= row.getFirstCellNum(); i--) {
                if (i < 0) {
                    continue;
                }

                final Cell cell = row.getCell(i);
                if (cell == null) {
                    continue;
                }

                if (i < firstCol || i > lastCol) {
                    row.removeCell(cell);
                }
            }
        }

        return row;
    }

    /**
     * セルを取得する。
     *
     * @param sheet シート
     * @param rowIndex 行インデックス
     * @param colIndex 列インデックス
     * @return Cellオブジェクト
     */
    public static Cell getCell(final Sheet sheet, final int rowIndex, final int colIndex) {
        if (sheet == null) {
            logger.error("sheet is null.");
            return null;
        }

        if (rowIndex < 0) {
            logger.error("rowIndex({}) is invalid.", rowIndex);
            return null;
        }

        if (colIndex < 0) {
            logger.error("colIndex({}) is invalid.", colIndex);
            return null;
        }

        final Row row = sheet.getRow(rowIndex);
        return row == null ? null : row.getCell(colIndex);
    }

    /**
     * セルの値を取得する。
     *
     * @param workbook ワークブック
     * @param cell セル
     * @return セルの値
     */
    public static Object getCellValue(final Workbook workbook, final Cell cell) {
        if (workbook == null || cell == null) {
            return null;
        }

        final String cellCoordinate = "(" + cell.getRowIndex() + "," + cell.getColumnIndex() + ")";
        Object result;
        final CellType cellType = cell.getCellType();

        if (cellType == CellType.BLANK) {
            result = null;
        } else if (cellType == CellType.BOOLEAN) {
            result = cell.getBooleanCellValue();
        } else if (cellType == CellType.ERROR) {
            String errorResult;
            try {
                byte errorCode = cell.getErrorCellValue();
                FormulaError formulaError = FormulaError.forInt(errorCode);
                errorResult = formulaError.getString();
            } catch (RuntimeException e) {
                logger.debug("Getting error code for {} failed!: {}", cellCoordinate, e.getMessage());
                if (cell instanceof XSSFCell) {
                    errorResult = ((XSSFCell)cell).getErrorCellString();
                } else {
                    logger.error("Couldn't handle unexpected error scenario in cell: " + cellCoordinate, e);
                    throw e;
                }
            }
            result = errorResult;
        } else if (cellType == CellType.FORMULA) {
            result = getFormulaCellValue(workbook, cell);
        } else if (cellType == CellType.NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                result = cell.getDateCellValue();
            } else {
                result = cell.getNumericCellValue();
            }
        } else if (cellType == CellType.STRING) {
            /* xls -> HSSFRichTextString, xlsx -> XSSFRichTextString */
            result = cell.getRichStringCellValue();
        } else {
            throw new IllegalStateException("Unknown cell type: " + cell.getCellType());
        }

        logger.debug("cell{} resolved to value: {}", cellCoordinate, result);

        return result;
    }

    /**
     * セル(数式)の値を取得する。
     *
     * @param workbook ワークブック
     * @param cell セル
     * @return セルの値
     */
    private static Object getFormulaCellValue(final Workbook workbook, final Cell cell) {
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            logger.error("Failed to fetch cached/precalculated formula value of cell: " + cell, e);
        }

        try {
            logger.info("cell({},{}) is a formula. Attempting to evaluate: {}",
                new Object[] {
                    cell.getRowIndex(),
                    cell.getColumnIndex(), cell.getCellFormula()
                });

            final FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            final Cell evaluatedCell = evaluator.evaluateInCell(cell);
            return getCellValue(workbook, evaluatedCell);
        } catch (RuntimeException e) {
            logger.warn("Exception occurred while evaluating formula at position ({},{}): {}",
                new Object[] {
                    cell.getRowIndex(),
                    cell.getColumnIndex(),
                    e.getMessage()
                });

            if (e instanceof FormulaParseException) {
                logger.error("Parse exception occurred while evaluating cell formula: " + cell, e);
            } else if (e instanceof IllegalArgumentException) {
                logger.error("Illegal formula argument occurred while evaluating cell formula: " + cell, e);
            } else {
                logger.error("Unexpected exception occurred while evaluating cell formula: " + cell, e);
            }
        }

        return cell.getCellFormula();
    }

    /**
     * シートの条件付き書式をセットする。
     *
     * @param sheet シート
     */
    public static void conditionalFormat(final Sheet sheet) {
        conditionalFormat(sheet, 0);
    }

    /**
     * シートの条件付き書式をセットする。
     *
     * @param sheet シート
     * @param rowIndexFrom 開始行インデックス
     */
    public static void conditionalFormat(final Sheet sheet, final int rowIndexFrom) {
        final SheetConditionalFormatting formatting = sheet.getSheetConditionalFormatting();
        for (int i = 0; i < formatting.getNumConditionalFormattings(); i++) {
            final ConditionalFormatting format = formatting.getConditionalFormattingAt(i);
            final CellRangeAddress[] ranges = format.getFormattingRanges();
            if (rowIndexFrom <= ranges[0].getFirstRow()) {
                for (int j = 0; j < ranges.length; j++) {
                    ranges[j].setLastRow(sheet.getLastRowNum());
                }
                format.setFormattingRanges(ranges);
            }
        }
    }

    /**
     * ワークブックからバイト配列を取得する。
     *
     * @param workbook ワークブック
     * @return バイト配列
     */
    public static byte[] getBytes(final Workbook workbook) {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Excelファイルを指定パスに出力する。
     *
     * @param workbook ワークブック
     * @param path ファイルのパス
     */
    public static void output(final Workbook workbook, final Path path) {
        try (BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(getBytes(workbook)));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path.toAbsolutePath().toString()))) {
            byte[] buff = new byte[4096];
            int len = 0;
            while ((len = bis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            bos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
