package com.soto;

import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.util.Arrays;

public class HBaseFilterTest {
    @Test
    public void createTable() {
        HBaseUtil.createTable("FileTable", new String[]{"fileInfo", "saveInfo"});
    }
    @Test
    public void addFileDetails() {
        HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "name", "file1.txt");
        HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "type", "txt");
        HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "size", "1024");
        HBaseUtil.putRow("FileTable", "rowkey1", "saveInfo", "creator", "wang");
        HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "name", "file2.txt");
        HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "type", "txt");
        HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "size", "1024");
        HBaseUtil.putRow("FileTable", "rowkey2", "saveInfo", "creator", "wang");
        HBaseUtil.putRow("FileTable", "rowkey3", "fileInfo", "name", "file2.txt");
        HBaseUtil.putRow("FileTable", "rowkey3", "fileInfo", "type", "txt");
        HBaseUtil.putRow("FileTable", "rowkey3", "fileInfo", "size", "1024");
        HBaseUtil.putRow("FileTable", "rowkey3", "saveInfo", "creator", "wang");
    }

    @Test
    public void rowFilterTest() {
        Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("rowkey2")));
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(filter));
        ResultScanner scanner = HBaseUtil
                .getScanner("FileTable", "rowkey1", "rowkey3", filterList);

        if (scanner != null) {
            scanner.forEach(result -> {
                System.out.println("rowkey=" + Bytes.toString(result.getRow()));
                System.out.println("fileName="+Bytes.toString(result.getValue(Bytes.toBytes("fileInfo"),Bytes.toBytes("name"))));
            });
            scanner.close();

        }
    }

    @Test
    public void prefixFilterTest() {
        Filter filter = new PrefixFilter(Bytes.toBytes("rowkey2"));
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(filter));


        ResultScanner scanner = HBaseUtil
                .getScanner("FileTable", "rowkey1", "rowkey3", filterList);

        if (scanner != null) {
            scanner.forEach(result -> {
                System.out.println("rowkey=" + Bytes.toString(result.getRow()));
                System.out.println("fileName="+Bytes.toString(result.getValue(Bytes.toBytes("fileInfo"),Bytes.toBytes("name"))));
            });
            scanner.close();

        }
    }

    @Test
    public  void keyOnlyFilterTest() {
        Filter filter = new KeyOnlyFilter(true);
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(filter));


        ResultScanner scanner = HBaseUtil
                .getScanner("FileTable", "rowkey1", "rowkey3", filterList);

        if (scanner != null) {
            scanner.forEach(result -> {
                System.out.println("rowkey=" + Bytes.toString(result.getRow()));
                System.out.println("fileName="+Bytes.toString(result.getValue(Bytes.toBytes("fileInfo"),Bytes.toBytes("name"))));
            });
            scanner.close();
        }
    }

    @Test
    public void columnPrefixFilterTest() {
        Filter filter = new ColumnPrefixFilter(Bytes.toBytes("name"));


        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(filter));


        ResultScanner scanner = HBaseUtil
                .getScanner("FileTable", "rowkey1", "rowkey3", filterList);

        if (scanner != null) {
            scanner.forEach(result -> {
                System.out.println("rowkey=" + Bytes
                        .toString(result.getRow()));
                System.out.println("fileName="+Bytes
                        .toString(result.getValue(Bytes.toBytes("fileInfo"),
                                Bytes.toBytes("name"))));
                System.out.println("fileType="+Bytes
                        .toString(result.getValue(Bytes.toBytes("fileInfo"),
                                Bytes.toBytes("type"))));
            });
            scanner.close();
        }
    }


}
