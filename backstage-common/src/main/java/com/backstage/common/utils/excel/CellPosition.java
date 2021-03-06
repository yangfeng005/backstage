package com.backstage.common.utils.excel;

/**
 * excel 单元格
 *
 * Created by yangfeng
 */
public class CellPosition {
    private int row;
    private int column;

    public CellPosition() {
    }

    public CellPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
