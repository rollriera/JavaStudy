package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {

    public void executeUpdate(User user, String sql, PreparedStatementSetter psseter) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try{
            con =  ConnectionManager.getConnection();
            ps = con.prepareStatement(sql);
            psseter.setter(ps);

            ps.executeUpdate();
        } finally {
            if(ps != null) ps.close();
            if(con != null) con.close();
        }
    }

    public Object executeQuery(String sql, PreparedStatementSetter psseter, RowMapper rowMapper) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(sql);
            psseter.setter(ps);

            rs = ps.executeQuery();

            User obj = null;
            if(rs.next()){
                return rowMapper.map(rs);
            }
            return obj;
        }finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        }
    }
}
