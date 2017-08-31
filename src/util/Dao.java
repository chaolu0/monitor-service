package util;
import Bean.UserBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Dao {

    private JdbcUtil jdbcUtil;
    private Connection connection;

    public Dao() {
        jdbcUtil = JdbcUtil.getInstance();
    }

    public List<UserBean> query(String sql) {
        List<UserBean> stUsers = new ArrayList<>();
        try {
            connection = jdbcUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = null;
            if (sql.contains("select") || sql.contains("SELECT")) {
                rs = ps.executeQuery();
                while (rs != null && rs.next()) {
                    UserBean stuser = new UserBean();
                    stuser.setId(rs.getInt(1));
                    stuser.setUsername(rs.getString(2));
                    stuser.setPassword(rs.getString(3));
                    stuser.setType(rs.getInt(4));
                    stUsers.add(stuser);
                }
            } else {
                ps.executeUpdate();
            }
            jdbcUtil.closeConnection(rs, ps, connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stUsers;
    }

    public UserBean find(UserBean user) {
        if (user != null) {
            if (user.getUsername() != null && user.getPassword() != null) {
                if (!"".equals(user.getUsername()) && !"".equals(user.getPassword())) {
                    List<UserBean> u = query("SELECT * FROM users WHERE"
                            + " username = '" + user.getUsername()
                            + "'AND password = '" + user.getPassword() + "'");
                    if (u != null && u.size() != 0) {
                        return u.get(0);
                    }
                }
            }
        }
        return null;
    }
}
