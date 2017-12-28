package org.chaoyue.test.export.excl;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.chaoyue.test.util.JsonUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static final String PROJECT_NAME = "projectName";
    public static final String LAYOUT_NAME ="layoutName";
  //  private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static  <T> List<T> readExcel(MultipartFile file, Map<String, String> map, Class<T> clazz) {
        if (!isExcel(file.getOriginalFilename())) {
            throw new RuntimeException("文件名不是excel格式");
        }
        if (isExcel2007(file)) {
            return readExcel2007(file, map, clazz);
        } else {
            return null;
        }
    }

    /**
     * 依据内容判断是否为excel2007及以上
     */
    public static boolean isExcel2007(MultipartFile file) {
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    file.getInputStream());
            if (POIXMLDocument.hasOOXMLHeader(bis)) {
             //   logger.info("Excel版本为excel2007及以上");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    //    logger.info("Excel版本为excel2007及以下");
        return false;
    }

    /**
     * 依据后缀名判断读取的是否为Excel文件
     *
     * @param filePath
     * @return
     */
    public static boolean isExcel(String filePath) {
        if (filePath.matches("^.+\\.(?i)(xls)$")
                || filePath.matches("^.+\\.(?i)(xlsx)$")) {
            return true;
        }
        return false;
    }

    /***
     * 读取2007-2013格式
     * @param file 文件流
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> readExcel2007(MultipartFile file, Map<String, String> map, Class<T> clazz){
        List<T> valueList = new ArrayList<>();
        try (InputStream in = file.getInputStream()) {
            XSSFWorkbook xwb = new XSSFWorkbook(in);   // 构造 XSSFWorkbook 对象，strPath 传入文件路径
            XSSFSheet sheet = xwb.getSheetAt(0);            // 读取第一章表格内容
            String projectName =  getValue(sheet.getRow(0).getCell(1));
            String layoutName = getValue(sheet.getRow(0).getCell(5));
            if (StringUtils.isEmpty(projectName) || !projectName.equals(map.get(PROJECT_NAME))){
               // throw new JcRsvServiceException(JcRsvServiceCodeEnum.BAD_REQUEST,"导入"+projectName+"项目不存在,请确认后重新导入");
            }
            if (StringUtils.isEmpty(projectName) || !layoutName.equals(map.get(LAYOUT_NAME))){
               // throw new JcRsvServiceException(JcRsvServiceCodeEnum.BAD_REQUEST,"导入"+layoutName+"户型不存在,请确认后重新导入");
            }
            // 循环输出表格中的第一行内容   表头
            Map<Integer, String> keys = new HashMap<>();
            // 定义 row、cell
            XSSFRow row = sheet.getRow(1);
            if (row == null) {
              //  throw new JcRsvServiceException(JcRsvServiceCodeEnum.BAD_REQUEST,"表头信息异常");
            }
            //处理表头
            for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
                // 通过 row.getCell(j).toString() 获取单元格内容，
                if (row.getCell(j) != null) {
                    String key = row.getCell(j).toString();

                    if (!row.getCell(j).toString().isEmpty()) {
                        if (map.containsKey(key)) {
                            keys.put(j, map.get(key));
                        } else {
                            keys.put(j, key);
                        }

                    }
                } else {
                    keys.put(j, "unknown-" + j + "-column");
                }
            }

            // 循环输出表格中的从第二行开始内容
            for (int i = sheet.getFirstRowNum() + 2; i <= sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if (row == null) continue;
                boolean isValidRow = false;
                Map<String, Object> val = new HashMap<>();
                for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
                    XSSFCell cell = row.getCell(j);
                    if (cell == null) continue;
                    String cellValue = getValue(cell);
                    if (cellValue != null && cellValue.trim().length() <= 0) {
                        cellValue = null;
                    }
                    if (StringUtils.isNotEmpty(cellValue)){
                        val.put(keys.get(j), cellValue.trim());
                    }else{
                        val.put(keys.get(j), cellValue);
                    }
                    if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
                        isValidRow = true;
                    }
                }
                // 第I行所有的列数据读取完毕，放入valuelist
                if (isValidRow) {
                    JsonUtil.toObject(JsonUtil.toString(val), clazz);

                    valueList.add(JsonUtil.toObject(JsonUtil.toString(val), clazz));
                }
            }
        } catch (Exception e) {
            if (e instanceof JcRsvServiceException){
                /*logger.error("readExcel2007 Exception:",e);
                throw new JcRsvServiceException(JcRsvServiceCodeEnum.BAD_REQUEST,e.getMessage());*/
            }
            /*logger.error("readExcel2007 Exception:",e);
            throw new JcRsvServiceException(JcRsvServiceCodeEnum.BAD_REQUEST,"读取Excel信息异常");*/
        }
        return valueList;
    }


    public static String getValue(XSSFCell cell){
        String cellValue = "";
        if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:     // 文本
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:    // 数字、日期
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:    // 布尔型
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK: // 空白
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_ERROR: // 错误
                    cellValue = "错误#";
                    break;
                case Cell.CELL_TYPE_FORMULA:    // 公式
                    // 得到对应单元格的字符串
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                    break;
                default:
                    cellValue = "";
            }
        } else {
            cellValue = cell.toString();
        }
        return cellValue;
    }
    /**
     * 读取老版本格式
     */
    @SuppressWarnings("rawtypes")
    public static void readExcelXls(MultipartFile file) throws IOException {

    }
}
