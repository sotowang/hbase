package com.soto;

import org.junit.Test;

public class HBaseUtilTest {
    @Test
    public void createTable() {
        HBaseUtil.createTable("FileTable", new String[]{"fileInfo", "saveInfo"});
    }
}
