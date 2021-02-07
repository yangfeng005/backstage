package com.backstage.common.utils.excel;

/**
 * Created by yangfeng on 2019/7/20.
 */
public class PoiModel {

    private Object content;

    private Object oldContent;

    private int rowIndex;

    private int cellIndex;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getOldContent() {
        return oldContent;
    }

    public void setOldContent(Object oldContent) {
        this.oldContent = oldContent;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getCellIndex() {
        return cellIndex;
    }

    public void setCellIndex(int cellIndex) {
        this.cellIndex = cellIndex;
    }
}
