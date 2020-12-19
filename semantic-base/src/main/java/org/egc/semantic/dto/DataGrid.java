package org.egc.semantic.dto;

import java.util.Collection;

/**
 * 组成datagrid数据格式，包括total和rows
 *
 * @author Houzw
 * @date 2016年4月6日 下午3:00:55
 */
public class DataGrid<T> {
    private int total;
    private Collection<T> rows;

    public DataGrid() {

    }

    public DataGrid(int total, Collection<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Collection<T> rows) {
        this.rows = rows;
    }
}
