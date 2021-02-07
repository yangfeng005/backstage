package com.backstage.common.utils.excel;


import com.backstage.common.utils.reflect.ClassUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

/**
 * 导出报表excel
 *
 * @author yangfeng
 */
public class ReportExportUtil {


    /**
     * 导出报表到excel
     * 中文标题与标题对应的字段一一对应
     *
     * @param reportTitle 报表头
     * @param reportDate  报表日期
     * @param fileName    文件名
     * @param dataset     内容集合
     * @param clazz       数据类型
     */
    public static void exportReportToExcel(String reportTitle, String reportDate, String fileName,
                                           List<Map<String, Object>> dataset, Class clazz,
                                           HttpServletResponse response) throws Exception {
        /*初始化excel模板*/
        Workbook workbook = null;
        try {
            /*初始化excel模板*/
            workbook = generateExcelReport(reportTitle, reportDate, dataset, clazz);
            workbook.write(generateResponseExcel(fileName, response));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成excel报表
     *
     * @param reportTitle
     * @param reportDate
     * @param dataset
     * @param clazz
     * @return
     * @throws Exception
     */
    public static Workbook generateExcelReport(String reportTitle, String reportDate,
                                               List<Map<String, Object>> dataset, Class clazz) throws Exception {
        List<String> titlesCN = null;//中文标题数组
        List<String> titleFields = null;//标题对应的字段数组
        List<Integer> headerWidths = null;//表头宽度
        List<String> mergeColumns = null;//合并单元格的列
        List<String> wrapper = null;//自动换行的列
        Field[] allFields = ClassUtil.getAllFields(clazz.newInstance());//得到所有定义字段
        List<Field> fields = new ArrayList<>();
        // 得到所有标注注解field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                fields.add(field);
            }
        }
        //字段排序
        if (!CollectionUtils.isEmpty(fields)) {
            Collections.sort(fields, new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return o1.getAnnotation(ExcelVOAttribute.class).sort() - o2.getAnnotation(ExcelVOAttribute.class).sort();
                }
            });
            titlesCN = new ArrayList<>();
            titleFields = new ArrayList<>();
            mergeColumns = new ArrayList<>();
            headerWidths = new ArrayList<>();
            wrapper = new ArrayList<>();
            for (Field f : fields) {
                ExcelVOAttribute annotation = f.getAnnotation(ExcelVOAttribute.class);
                if (null != annotation) {
                    titlesCN.add(annotation.name());
                    titleFields.add(f.getName());
                    headerWidths.add(annotation.width());
                    if (annotation.isMergeColumn()) {
                        mergeColumns.add(f.getName());
                    }
                    if (annotation.isWrapper()) {
                        wrapper.add(f.getName());
                    }
                }
            }
        }
        if (titleFields.size() == 0) {
            return null;
        }

        /*初始化excel模板*/
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = null;
        try {
            sheet = workbook.createSheet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int reportBegin = 0;
        //报表标题
        if (!StringUtils.isEmpty(reportTitle)) {
            Row rowReportTitle = sheet.createRow(reportBegin);
            rowReportTitle.setHeight((short) 425);
            for (int i = 0; i < titlesCN.size(); i++) {
                Cell titleCell = rowReportTitle.createCell(i, Cell.CELL_TYPE_STRING);
                setReportStyle(workbook, titleCell, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
                        HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index, (short) 16, "宋体", "reportHead");
            }
            CellRangeAddress titleRegion = new CellRangeAddress(0, 0, 0, titlesCN.size() - 1);
            sheet.addMergedRegion(titleRegion);
            Cell titleCell = rowReportTitle.getCell(0);
            titleCell.setCellValue(reportTitle);
            reportBegin++;
        }
        //报表日期行
        if (!StringUtils.isEmpty(reportDate)) {
            Row rowReportDate = sheet.createRow(reportBegin);
            rowReportDate.setHeight((short) 425);
            for (int i = 0; i < titlesCN.size(); i++) {
                Cell dateCell = rowReportDate.createCell(i, Cell.CELL_TYPE_STRING);
                setReportStyle(workbook, dateCell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER,
                        HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index, (short) 12, "宋体", "reportHead");
            }
            CellRangeAddress dateRegion = new CellRangeAddress(1, 1, 0, titlesCN.size() - 1);
            sheet.addMergedRegion(dateRegion);
            Cell dateCell = rowReportDate.getCell(0);
            dateCell.setCellValue(reportDate);
            reportBegin++;
        }
        /*初始化head，填值标题行*/
        Row row0 = sheet.createRow(reportBegin);
        row0.setHeight((short) 625);
        for (int i = 0; i < titlesCN.size(); i++) {
            /*创建单元格，指定类型*/
            Cell cell_1 = row0.createCell(i, Cell.CELL_TYPE_STRING);
            cell_1.setCellValue(titlesCN.get(i));
            sheet.setColumnWidth(i, headerWidths.get(i).intValue());//设置列宽
            setReportStyle(workbook, cell_1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
                    HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index, (short) 12, "宋体", "title");
        }

        List<PoiModel> poiModels = new ArrayList<PoiModel>();
        Iterator<Map<String, Object>> iterator = dataset.iterator();
        Map<Integer, String> colorMap = new HashMap<>();
        int colorIndex = 0;
        int index = reportBegin + 1;
        while (iterator.hasNext()) {
            Row row = sheet.createRow(index);
            row.setHeight((short) 425);
            // 取得当前这行的map，该map中以key，value的形式存着这一行值
            Map<String, Object> map = iterator.next();
            // 循环列数，给当前行塞值
            for (int i = 0; i < titleFields.size(); i++) {
                Object value = map.get(titleFields.get(i)) != null ? map.get(titleFields.get(i)) : "";
                Object old = "";
                if (!CollectionUtils.isEmpty(mergeColumns)) {
                    // old存的是上一行同一位置的单元的值
                    if (index > reportBegin + 1) {
                        old = poiModels.get(i) == null ? "" : poiModels.get(i).getContent();
                    }

                    CellRangeAddress cra = null;
                    // 循环需要合并的列
                    for (int j = 0; j < mergeColumns.size(); j++) {
                        PoiModel poiModel = null;
                        //第一行数据直接放入poiModel
                        if (index == reportBegin + 1) {
                            poiModel = new PoiModel();
                            poiModel.setOldContent(value);
                            poiModel.setContent(value);
                            poiModel.setRowIndex(reportBegin + 1);
                            poiModel.setCellIndex(i);
                            poiModels.add(poiModel);
                            old = value;
                            break;
                        }
                        //第二行数据取出上一行统一位置数据进行比对
                        poiModel = poiModels.get(i);

                        int rowStartIndex = poiModel.getRowIndex();
                        int rowEndIndex = index - 1;
                        int cellIndex = poiModel.getCellIndex();
                        Object content = poiModel.getContent();
                        Object preOldContent = poiModels.get(0).getOldContent();
                        Object preValue = map.get(titleFields.get(0));
                        Boolean isHeaderEquals = mergeColumns.get(j).equals(titleFields.get(i));

                        //第一列合并单元格
                        if (i == 0 && isHeaderEquals && !content.equals(value)) {
                            if (rowStartIndex != rowEndIndex) {
                                cra = new CellRangeAddress(rowStartIndex, rowEndIndex, cellIndex, cellIndex);
                                sheet.addMergedRegion(cra);
                                setCellNullValue(sheet, rowStartIndex + 1, rowEndIndex, cellIndex);
                            }
                            // 重新记录该列的内容为当前内容，行标记改为当前行标记
                            poiModel.setContent(value);
                            poiModel.setRowIndex(index);
                            poiModel.setCellIndex(i);
                            colorMap.put(++colorIndex, rowStartIndex + "-" + rowEndIndex);
                            //第二，三......列合并单元格
                        } else if (i > 0 && isHeaderEquals) {
                            //和第一列做比较判断是否要合并
                            if (!content.equals(value) || (content.equals(value) && !preOldContent.equals(preValue))) {
                                if (rowStartIndex != rowEndIndex) {
                                    cra = new CellRangeAddress(rowStartIndex, rowEndIndex, cellIndex, cellIndex);
                                    sheet.addMergedRegion(cra);
                                    setCellNullValue(sheet, rowStartIndex + 1, rowEndIndex, cellIndex);
                                }
                                poiModels.get(i).setContent(value);
                                poiModels.get(i).setRowIndex(index);
                                poiModels.get(i).setCellIndex(i);
                            }
                        }
                        //最后一行合并单元格
                        if (isHeaderEquals && (index - reportBegin) == dataset.size()) {
                            //第一列
                            if (i == 0) {
                                if (content.equals(value)) {
                                    cra = new CellRangeAddress(rowStartIndex, index, cellIndex, cellIndex);
                                    sheet.addMergedRegion(cra);
                                    setCellNullValue(sheet, rowStartIndex + 1, index - 1, cellIndex);
                                }
                                colorMap.put(++colorIndex, rowStartIndex + "-" + index);
                                //第二，三......列
                            } else if (i > 0) {
                                if (content.equals(value) && preOldContent.equals(preValue)) {
                                    cra = new CellRangeAddress(rowStartIndex, index, cellIndex, cellIndex);
                                    sheet.addMergedRegion(cra);
                                    setCellNullValue(sheet, rowStartIndex + 1, index - 1, cellIndex);
                                }
                            }
                        }
                    }
                }
                //最后一行，并且列是需要合并的单元格，则只创建单元格不设置值，防止合并单元格求和错误
                if ((index - reportBegin) == dataset.size() && (!CollectionUtils.isEmpty(mergeColumns)
                        && mergeColumns.contains(titleFields.get(i)))) {
                    row.createCell(i);
                } else {
                    Cell cell = row.createCell(i);
                    if (!StringUtils.isEmpty(value)) {
                        if (value instanceof Integer) {
                            Integer result = ((Integer) value).intValue();
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellValue(result);
                        } else if (value instanceof Double) {
                            Double result = ((Double) value).doubleValue();
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellValue(result);
                        } else {
                            String result = (String) value;
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            cell.setCellValue(result);
                        }
                    }
                }
                // 在每一个单元格处理完成后，把这个单元格内容设置为old内容
                if (!CollectionUtils.isEmpty(mergeColumns)) {
                    poiModels.get(i).setOldContent(old);
                }
                //自动计算行高
                ExcelRowAutoHeightUtil.calcAndSetRowHeigt(row);
            }
            index++;
        }
        //改变数据行的背景色、加边框
        changeColor(workbook, sheet, colorMap, reportBegin, wrapper, titleFields);
        return workbook;
    }

    /**
     * 单元格值置空，防止合并单元格求和错误
     *
     * @param sheet
     * @param rowStartIndex
     * @param rowEndIndex
     * @param cellIndex
     */
    public static void setCellNullValue(Sheet sheet, int rowStartIndex, int rowEndIndex, int cellIndex) {
        while (rowStartIndex <= rowEndIndex) {
            Row row = sheet.getRow(rowStartIndex);
            Cell cell = row.getCell(cellIndex);
            if (cell != null) {
                cell.setCellValue("");
            }
            rowStartIndex++;
        }
    }

    /**
     * 设置报表样式
     *
     * @param workbook
     * @param cell
     * @param zyjz
     * @param sxjz
     * @param bw
     * @param color
     * @param fh
     * @param fontName
     */
    public static void setReportStyle(Workbook workbook, Cell cell, short zyjz, short sxjz, short bw, short color, short fh, String fontName, String flag) {
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle.setAlignment(zyjz);//左右居中
        cellStyle.setVerticalAlignment(sxjz);//上下居中
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
        if ("reportHead".equals(flag)) {
            cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(156, 195, 230)));//设置背景色
        } else if ("title".equals(flag)) {
            cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(91, 155, 213)));
        }
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);//填充模式
        Font font = workbook.createFont();
        font.setBoldweight(bw);
        font.setColor(color);
        font.setFontHeightInPoints(fh);
        font.setFontName(fontName);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 改变表格行背景色
     *
     * @param workbook
     * @param sheet
     * @param colorMap
     */
    public static void changeColor(Workbook workbook, Sheet sheet, Map<Integer, String> colorMap, int reportBegin, List<String> wrapper, List<String> titleFields) {
        if (colorMap == null) {
            return;
        }

        XSSFCellStyle cellStyle = (XSSFCellStyle) createCellStyle(workbook);
        cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(156, 195, 230)));
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //隔行换色
        List<Integer> existMerge = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : colorMap.entrySet()) {
            if (entry.getKey() % 2 == 1) {
                String[] rows = entry.getValue().split("-");
                for (int i = Integer.parseInt(rows[0]); i <= Integer.parseInt(rows[1]); i++) {
                    existMerge.add(i);
                    setCellStyle(sheet, i, cellStyle, wrapper, titleFields);
                }
            }
        }
        //没有换色的需要设置边框和文字居中
        CellStyle noColorStyle = createCellStyle(workbook);
        int totalRows = sheet.getLastRowNum();
        for (int i = reportBegin + 1; i <= totalRows; i++) {
            if (existMerge.contains(i)) {
                continue;
            }
            setCellStyle(sheet, i, noColorStyle, wrapper, titleFields);
        }
    }

    public static CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
        return cellStyle;
    }

    public static void setCellStyle(Sheet sheet, int i, CellStyle cellStyle, List<String> wrapper, List<String> titleFields) {
        Row row = sheet.getRow(i);
        int totalCells = row.getPhysicalNumberOfCells();
        for (int j = 0; j < totalCells; j++) {
            if (!CollectionUtils.isEmpty(wrapper) && wrapper.contains(titleFields.get(j))) {
                cellStyle.setWrapText(true);
            }
            row.getCell(j).setCellStyle(cellStyle);
        }
    }

    /**
     * @param fileName 要生成的文件名
     * @return
     * @throws IOException
     */
    public static ServletOutputStream generateResponseExcel(String fileName, HttpServletResponse response) throws
            IOException {
        fileName = fileName == null || "".equals(fileName) ? "excel" : URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
        return response.getOutputStream();
    }

}