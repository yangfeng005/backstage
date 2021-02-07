package com.backstage.common.utils.excel;

import com.backstage.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ExcelUtil工具类实现Excel导入导出功能
 *
 * @author yangfeng
 * <p>
 * 导出时传入list<T>,即可实现导出为一个excel,其中每个对象Ｔ为Excel中的一条记录.导出为97-2003版本
 * 导入时读取excel,得到的结果是一个list<T>.T是自己定义的对象.导入支持97-2003和2007版本
 * 需要导出的实体对象只需简单配置注解就能实现灵活导出,通过注解您可以方便实现下面功能:
 * 1.实体属性配置了注解就能导出到excel中,每个属性都对应一列.
 * 2.列名称可以通过注解配置.
 * 3.导出到哪一列可以通过注解配置.
 * 4.鼠标移动到该列时提示信息可以通过注解配置.
 * 5.用注解设置只能下拉选择不能随意填写功能.
 * 6.用注解设置是否只导出标题而不导出内容,这在导出内容作为模板以供用户填写时比较实用.
 */
public class ExcelUtil<T> {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private Class<T> clazz;

    private Map<Integer, Map<Integer, CellPosition>> cellValuePositionMap;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 导入excel
     *
     * @param sheetIndex     sheet序号
     * @param fis            文件流 如：new FileInputStream(new File("D:\\test.xlsx"))
     * @param headRowNumbers 表格头行数
     * @return T类型的实体列表
     */
    public List<T> importExcel(Integer sheetIndex, InputStream fis, Integer headRowNumbers) {
        return importExcel(sheetIndex, fis, headRowNumbers, true);
    }

    /**
     * 导入excel
     *
     * @param sheetIndex     sheet序号
     * @param fis            文件流 如：new FileInputStream(new File("D:\\test.xlsx"))
     * @param headRowNumbers 表格头行数
     * @param hasDefineOrder T中是否定义了列的顺序
     * @return T类型的实体列表
     */
    public List<T> importExcel(Integer sheetIndex, InputStream fis, Integer headRowNumbers, boolean hasDefineOrder) {
        List<T> list = new ArrayList<>();
        try {
            Sheet sheet = getSheet(sheetIndex, fis);
            if (sheet == null) {
                logger.error("Can't find sheet:" + sheetIndex);
                return list;
            }
            initCellPosition(sheet);
            int rows = sheet.getLastRowNum();// 得到数据的行数

            if (rows > 0) {// 有数据时才处理
                Map<Integer, Field> fieldsMap;
                if (hasDefineOrder) {
                    fieldsMap = buildFieldOrder(null);
                } else {
                    fieldsMap = buildFieldOrder(sheet, headRowNumbers);
                }
                for (int i = headRowNumbers; i <= rows; i++) {// 从第2行开始取数据,默认第一行是表头.
                    T entity = buildEntity(sheet, i, fieldsMap);
                    if (entity != null) {
                        list.add(entity);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }


    /**
     * 导入日出、日落时刻表excel
     *
     * @return T类型的实体列表
     */
    public List importSunTimeExcel(InputStream fis, Integer sheetIndex, Integer headRowNumbers) throws Exception {
        List list = new ArrayList<>();
        HSSFWorkbook book = new HSSFWorkbook(fis);
        for (int i = 0; i < book.getNumberOfSheets(); i++) {
            if (i == sheetIndex) {
                getSunTimeList(book.getSheetAt(i), list, headRowNumbers);
            }
        }
        return list;
    }

    /**
     * 读取日出、日落时刻表数据
     *
     * @param sheet
     * @param list
     * @param headRowNumbers
     * @return
     */
    public List getSunTimeList(Sheet sheet, List list, Integer headRowNumbers) throws Exception {
        initCellPosition(sheet);
        int rows = sheet.getLastRowNum();// 得到数据的行数

        if (rows > 0) {// 有数据时才处理
            Map<Integer, Field> fieldsMap = buildField(sheet, headRowNumbers);
            for (int i = headRowNumbers; i <= rows; i++) {// 从第2行开始取数据,默认第一行是表头.
                T entity = buildEntity(sheet, i, fieldsMap);
                if (entity != null) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    /**
     * 导入冬春、夏秋航班计划excel
     *
     * @return T类型的实体列表
     */
    public List importFlightPlanExcel(InputStream fis, Integer sheetIndex, Integer headRowNumbers) throws Exception {
        List list = new ArrayList<>();
        HSSFWorkbook book = new HSSFWorkbook(fis);
        for (int i = 0; i < book.getNumberOfSheets(); i++) {
            if (i == sheetIndex) {
                getFlightPlanList(book.getSheetAt(i), list, headRowNumbers);
            }
        }
        return list;
    }


    /**
     * 读取冬春、夏秋航班计划表数据
     *
     * @param sheet
     * @param list
     * @param headRowNumbers
     * @return
     */
    public List getFlightPlanList(Sheet sheet, List list, Integer headRowNumbers) throws Exception {
        initCellPosition(sheet);
        int rows = sheet.getLastRowNum();// 得到数据的行数

        if (rows > 0) {// 有数据时才处理
            Map<Integer, Field> fieldsMap = buildFieldOrder(sheet, headRowNumbers);
            for (int i = headRowNumbers; i <= rows; i++) {// 从第2行开始取数据,默认第一行是表头.
                T entity = buildEntity(sheet, i, fieldsMap);
                if (entity != null) {
                    list.add(entity);
                }
            }
        }
        return list;
    }


    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @param sheetSize 每个sheet中数据的行数,此数值必须小于65536
     * @param output    java输出流
     */
    public boolean exportExcel(List<T> list, String sheetName, int sheetSize, OutputStream output) {

        Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
        List<Field> fields = new ArrayList<>();
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                fields.add(field);
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象

        // excel2003中每个sheet中最多有65536行,为避免产生错误所以加这个逻辑.
        if (sheetSize > 65536 || sheetSize < 1) {
            sheetSize = 65536;
        }
        double sheetNo = Math.ceil((double) list.size() / sheetSize);// 取出一共有多少个sheet.
        for (int index = 0; index <= sheetNo; index++) {
            HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
            workbook.setSheetName(index, sheetName + index);// 设置工作表的名称.
            HSSFRow row;
            HSSFCell cell;// 产生单元格

            row = sheet.createRow(0);// 产生一行
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
                int col = getExcelCol(attr.column());// 获得列号
                cell = row.createCell(col);// 创建列
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
                cell.setCellValue(attr.name());// 写入列名

                // 如果设置了提示信息则鼠标放上去提示.
                if (!"".equals(attr.prompt().trim())) {
                    setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);// 这里默认设了2-101列提示.
                }
                // 如果设置了combo属性则本列只能选择不能输入
                if (attr.combo().length > 0) {
                    setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);// 这里默认设了2-101列只能选择不能输入.
                }
            }

            int startNo = index * sheetSize;
            int endNo = Math.min(startNo + sheetSize, list.size());
            // 写入各条记录,每条记录对应excel表中的一行
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                T vo = list.get(i); // 得到导出对象.
                for (int j = 0; j < fields.size(); j++) {
                    Field field = fields.get(j);// 获得field.
                    field.setAccessible(true);// 设置实体类私有属性可访问
                    ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
                    try {
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport()) {
                            cell = row.createCell(getExcelCol(attr.column()));// 创建cell
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
                        }
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }

        }
        try {
            output.flush();
            workbook.write(output);
            output.close();
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
     * <p>
     * 算法说明：
     * 1. Excel前26列 A-Z 对应的顺序减A字符的ASCII编码刚好对应0,1,2,3.。
     * 2. 超过26列对应的字母为AA，AB，AC...刚好相当于26进制的转换
     *
     * @param col excel中列的编号
     */
    public static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow((double) 26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 设置单元格上提示
     *
     * @param sheet         要设置的sheet.
     * @param promptTitle   标题
     * @param promptContent 内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow,
                                          int endRow, int firstCol, int endCol) {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow,
                                              int firstCol, int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }

    private Sheet getSheet(Integer sheetIndex, InputStream fis) {
        try {
            Workbook book = WorkbookFactory.create(fis);
            return book.getSheetAt(sheetIndex);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 对合并的单元格进行单元格值读取位置的初始化，根据该位置，可以读取到单元格的值
     */
    private void initCellPosition(Sheet sheet) {
        this.cellValuePositionMap = new HashMap<>();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstRow = ca.getFirstRow();
            int firstColumn = ca.getFirstColumn();
            int lastRow = ca.getLastRow();
            initCellPosition_i(firstRow, firstColumn, lastRow);
        }
    }

    private void initCellPosition_i(int firstRow, int firstColumn, int lastRow) {
        for (int rowPos = firstRow; rowPos <= lastRow; ++rowPos) {
            Map<Integer, CellPosition> rowCellPostions = cellValuePositionMap.get(rowPos);
            if (rowCellPostions == null) {
                rowCellPostions = new HashMap<>();
                cellValuePositionMap.put(rowPos, rowCellPostions);
            }
            rowCellPostions.put(firstColumn, new CellPosition(firstRow, firstColumn));
        }
    }

    private int getExcelCol(String columnName, List<String> columnList) {
        int pos = 0;
        for (; pos < columnList.size(); ++pos) {
            if (columnList.get(pos).equals(columnName)) {
                break;
            }
        }
        if (pos == columnList.size()) {
            logger.error("Can't find column name in excel file, column:" + columnName);
            pos = -1;
        }
        return pos;
    }

    private Map<Integer, Field> buildFieldOrder(List<String> columnList) {
        Field[] allFields = clazz.getDeclaredFields();// 得到类的所有field.
        Map<Integer, Field> fieldsMap = new HashMap<>();// 定义一个map用于存放列的序号和field.
        for (Field field : allFields) {
            // 将有注解的field存放到map中.
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
                int pos;
                if (columnList == null || columnList.isEmpty()) {
                    pos = getExcelCol(attr.column());
                } else {
                    pos = getExcelCol(attr.name().trim(), columnList);
                }
                if (pos != -1) {
                    field.setAccessible(true);// 设置类的私有字段属性可访问.
                    fieldsMap.put(pos, field);
                }
            }
        }
        return fieldsMap;
    }

    private Map<Integer, Field> buildField(Sheet sheet, Integer headerNum) {
        Row rowMonth = sheet.getRow(headerNum - 2);//获取列头-日期、月份
        Row rowSun = sheet.getRow(headerNum - 1);//获取列头-日出日落行
        List<String> columnList = new ArrayList<>();
        Map<Integer, String> monthMap = new HashMap<>();
        int columnMonthCount = rowMonth.getLastCellNum();
        int columnSunCount = rowSun.getLastCellNum();
        //获取日出日落时刻表第二行(日期+月份)，转为key->value
        for (int monthPos = 0; monthPos < columnMonthCount; ++monthPos) {
            Cell cell = rowMonth.getCell(monthPos);
            monthMap.put(monthPos, cell.getStringCellValue().trim());
        }
        //获取日出日落时刻表第三行(日出+日落)，转为key->value
        Map<Integer, String> sunMap = new HashMap<>();
        for (int sunPos = 0; sunPos < columnSunCount; ++sunPos) {
            Cell cell = rowSun.getCell(sunPos);
            sunMap.put(sunPos, cell.getStringCellValue().trim());
        }
        //Iterator遍历，组装第二行和第三行，形成新的表头，日期+1月日出+1月日落+2月日出+2月日落+3月日出+3月日落......
        Iterator<Map.Entry<Integer, String>> it = monthMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            if (entry.getKey() == 0) {
                columnList.add(entry.getValue());
            } else {
                if (StringUtils.isNotEmpty(entry.getValue())) {
                    columnList.add(entry.getValue() + sunMap.get(entry.getKey()));
                } else {
                    columnList.add(monthMap.get(entry.getKey() - 1) + sunMap.get(entry.getKey()));
                }
            }
        }
        return buildFieldOrder(columnList);
    }

    private Map<Integer, Field> buildFieldOrder(Sheet sheet, Integer headerNum) {
        Row row = sheet.getRow(headerNum - 1);//获取列头
        List<String> columnList = new ArrayList<>();
        int columnCount = row.getLastCellNum();
        for (int pos = 0; pos < columnCount; ++pos) {
            Cell cell = row.getCell(pos);
            if (cell != null) {
                String cellValue = cell.getStringCellValue().trim();
                if (StringUtils.isEmpty(cellValue)) {
                    continue;
                }
                columnList.add(cellValue);
            }
        }
        return buildFieldOrder(columnList);
    }

    private T buildEntity(Sheet sheet, int rowNum, Map<Integer, Field> fieldsMap) throws Exception {
        Row row = sheet.getRow(rowNum);// 得到一行中的所有单元格对象.
        if (null == row) {
            return null;
        }
        T entity = null;
        Iterator iter = fieldsMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry map_entity = (Map.Entry) iter.next();
            Integer key = (Integer) map_entity.getKey();

            Cell c = getCell(sheet, rowNum, key); // 单元格中的内容.
            entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
            Field field = (Field) map_entity.getValue();// 从map中得到对应列的field.
            ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
            // 取得类型,并根据对象类型设置值.
            Class<?> fieldType = field.getType();
            if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                field.set(entity, getCellValueForInteger(c));
            } else if (String.class == fieldType) {
                field.set(entity, getCellValueForString(c, attr.dateFormat()));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                field.set(entity, getCellValueForLong(c));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                field.set(entity, getCellValueForFloat(c));
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                field.set(entity, getCellValueForShort(c));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                field.set(entity, getCellValueForDouble(c));
            } else if (Character.TYPE == fieldType) {
                if ((c != null)) {
                    field.set(entity, getCellValueForString(c, null));
                }
            }
            //表格中值的长度校验
            if (attr.valueLength() != -1) {
                Object value = field.get(entity);
                if (StringUtils.isNotEmpty(String.valueOf(value)) && String.valueOf(value).length() > attr.valueLength()) {
                    logger.error("Key field " + field.getName() + " length more, row num: " + row.getRowNum());
                    String errorMsg = "表格《" + sheet.getSheetName() + "》第" + (row.getRowNum() + 1) + "行第" + (key + 1) + "列列名《"
                            + (StringUtils.isNotEmpty(attr.name()) ? attr.name() : field.getName()) + "》：值长度超出限制";
                    throw new BaseException(-1, errorMsg);
                }
            }
            //关键字段为空，则不生成此记录的实体
            if (attr.keyAttr()) {
                Object value = field.get(entity);
                if (value == null || StringUtils.isEmpty(String.valueOf(value))) {
                    logger.error("Key field " + field.getName() + " is empty, row num: " + row.getRowNum());
                    String errorMsg = "表格《" + sheet.getSheetName() + "》第" + (row.getRowNum() + 1) + "行第" + (key + 1) + "列列名《"
                            + (StringUtils.isNotEmpty(attr.name()) ? attr.name() : field.getName()) + "》：不能为空";
                    throw new BaseException(-1, errorMsg);
                }
            }
        }
        return entity;
    }

    /**
     * 获取单元格
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private Cell getCell(Sheet sheet, int row, int column) {
        Map<Integer, CellPosition> rowCellPositions = cellValuePositionMap.get(row);
        CellPosition position = null;
        if (rowCellPositions != null) {
            position = rowCellPositions.get(column);
        }
        if (position != null) {
            row = position.getRow();
            column = position.getColumn();
        }
        Row rowRecord = sheet.getRow(row);
        try {
            if (null != rowRecord) {
                return rowRecord.getCell(column);
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 去单元格值
     *
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {
        return getCellValue(cell, null);
    }

    /**
     * 按照类型去单元格值
     *
     * @param cell
     * @param dateFormat
     * @return
     */
    private String getCellValue(Cell cell, String dateFormat) {
        String value = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    value = null;
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = getNumericCellValue(cell, dateFormat);
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cell.setCellType(1);
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = null;
            }
        }
        if ("".equals(value))
            value = null;
        return value;
    }

    /**
     * 单元格数字转换
     *
     * @param cell
     * @param dateFormat
     * @return
     */
    private String getNumericCellValue(Cell cell, String dateFormat) {
        String result;
        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            // 单元格设置成常规
            if ("General".equals(temp)) {
                format.applyPattern("#.#########");
            }
            result = format.format(value);
        }
        return result;
    }

    /**
     * 单元格double值转换
     *
     * @param cell
     * @return
     */
    private Double getCellValueForDouble(Cell cell) {
        String cellValue = getCellValue(cell);
        if (cellValue == null) {
            return null;
        } else {
            if (cellValue.indexOf(",") >= 0) {
                cellValue = cellValue.replaceAll(",", "");
            }
            return Double.parseDouble(cellValue);
        }
    }

    /**
     * 单元格float值转换
     *
     * @param cell
     * @return
     */
    private Float getCellValueForFloat(Cell cell) {
        String cellValue = getCellValue(cell);
        if (cellValue == null)
            return null;
        else {
            if (cellValue.indexOf(",") >= 0) {
                cellValue = cellValue.replaceAll(",", "");
            }
            return Float.parseFloat(cellValue);
        }
    }

    /**
     * 单元格String值转换
     *
     * @param cell
     * @return
     */
    private String getCellValueForString(Cell cell, String dateFormat) {
        return getCellValue(cell, dateFormat);
    }

    /**
     * 单元格Int值转换
     *
     * @param cell
     * @return
     */
    private Integer getCellValueForInteger(Cell cell) {
        String cellValue = getCellValue(cell);
        if (cellValue == null)
            return null;
        else {
            if (cellValue.indexOf(",") >= 0) {
                cellValue = cellValue.replaceAll(",", "");
            }
            return Integer.parseInt(cellValue);
        }
    }

    /**
     * 单元格short值转换
     *
     * @param cell
     * @return
     */
    private Short getCellValueForShort(Cell cell) {
        String cellValue = getCellValue(cell);
        if (cellValue == null)
            return null;
        else {
            if (cellValue.indexOf(",") >= 0) {
                cellValue = cellValue.replaceAll(",", "");
            }
            return Short.parseShort(cellValue);
        }
    }

    /**
     * 单元格long值转换
     *
     * @param cell
     * @return
     */
    private Long getCellValueForLong(Cell cell) {
        String cellValue = getCellValue(cell);
        if (cellValue == null)
            return null;
        else {
            if (cellValue.indexOf(",") >= 0) {
                cellValue = cellValue.replaceAll(",", "");
            }
            return Long.parseLong(cellValue);
        }
    }

}