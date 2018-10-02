package com.soto;

import org.apache.hadoop.hbase.client.Connection;
import org.junit.Test;

public class HBaseConnTest {

    @Test
    public void getConnectTest() {
        Connection conn = HBaseConn.getHBaseConn();
        System.out.println(conn.isClosed());
        HBaseConn.closeConn();
        System.out.println(conn.isClosed());

    }
}
