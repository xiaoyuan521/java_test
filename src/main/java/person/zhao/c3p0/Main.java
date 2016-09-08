package person.zhao.c3p0;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Main {

    private static ComboPooledDataSource cpds = null;
    static {
        setupConnection();
    }

    public static void setupConnection() {
        // connection
        cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        cpds.setJdbcUrl("jdbc:mysql://localhost/test");
        cpds.setUser("root");
        cpds.setPassword("");

        // config
        cpds.setMaxPoolSize(3);
    }

    public static Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    /**
     * 测试方法1
     *
     * 最大连接池数为3个的时候
     * 第4个getConnection的时候非发生阻塞
     *
     * 当前面3个连接被释放后，
     * 才能继续取得连接，而且取得的连接是之前使用过的。
     *
     * @throws SQLException
     * @throws PropertyVetoException
     * @throws InterruptedException
     */
    public void p() throws SQLException, PropertyVetoException, InterruptedException {

        // The DataSource cpds is now a fully configured and usable pooled
        // DataSource

        final Connection c = getConnection();
        System.out.println(c);
        final Connection c2 = getConnection();
        System.out.println(c2);
        final Connection c3 = getConnection();
        System.out.println(c3);
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    c3.close();
                    c2.close();
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        t.join();

        Connection c4 = getConnection();
        System.out.println(c4);
        Connection c5 = getConnection();
        System.out.println(c5);
        Connection c6 = getConnection();
        System.out.println(c6);

        System.out.println("ddddddd");

    }

    /**
     * RuntimeExeption同样能被捕获
     */
    public void p2() {
        throw new RuntimeException("error !!!");
    }

    /**
     * 测试2
     *
     * conn.setAutoCommit(false)来开启事务
     * 证明可以
     *
     * @throws Exception
     */
    public void p3() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("update user set first=\"222\" where id='1' ");
            stmt.execute();
            int a = 1;
            if (a == 1) {
                // throw new RuntimeException("sql error , run time !");
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Main().p();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
