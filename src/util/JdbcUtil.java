package util;

import java.sql.*;
public class JdbcUtil {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String dbName = "monitor";
    private static final String passWord = "";
    private static final String userName = "root";
    private static final String url = "jdbc:mysql://localhost:3306/"
            + dbName + "?useUnicode=true&characterEncoding=UTF-8";

    private static JdbcUtil jdbcUtil = null;

    public static JdbcUtil getInstance() {
        if (jdbcUtil == null) {
            // 给类加锁 防止线程并发
            synchronized (JdbcUtil.class) {
                if (jdbcUtil == null) {
                    jdbcUtil = new JdbcUtil();
                }
            }
        }
        return jdbcUtil;
    }

    private JdbcUtil(){}

    // 通过静态代码块注册数据库驱动，保证注册只执行一次
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, userName, passWord);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    // 关闭连接
    public void closeConnection(ResultSet rs, Statement statement, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
