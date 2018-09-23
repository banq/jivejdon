package com.jdon.jivejdon.auth.jaas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * the default implements of RolesProvider using JDBC,maybe in the future we
 * will use Hibernate or others utility if you want to deploy jivejdon on
 * tomcat, you should compile this package(com.jdon.jivejdon.auth.jaas)
 * independently,then put the jar to the lib of tomcat.
 * 
 * @author oojdon
 * 
 */
public class DefaultRolesProvider implements RolesProvider {

	private static String AUTH_SQL = "select r.name from role as r,user as u,users_roles as ur where u.name=? and password=? and  ur.userId=u.userId and ur.roleId=r.roleId";
	//private static String AUTH_SQL = "SELECT name FROM role where roleid=(SELECT roleid FROM users_roles WHERE userid = ( SELECT  userId FROM user  WHERE name=? and password=?) )";

	@Override
	public List<String> provideRoles(String username, String password) {
		List<String> roles = new ArrayList<String>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String datasource = PropertiesUtil.getProperty("JAAS_DATASOURCE");
			con = this.getConnection(datasource);
			ps = con.prepareStatement(AUTH_SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				roles.add(name);
			}
			this.release(rs, ps, con);
		} catch (NamingException ne) {
			throw new RuntimeException(ne);
		} catch (SQLException sqlEx) {
			throw new RuntimeException(sqlEx);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			this.release(rs, ps, con);
		}
		return roles;
	}

	private Connection getConnection(String datasource) throws NamingException, SQLException {
		InitialContext ic = new InitialContext();
		DataSource dataSource = (DataSource) ic.lookup(datasource);
		Connection con = dataSource.getConnection();
		return con;
	}

	private void release(ResultSet rs, PreparedStatement ps, Connection con) {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
		}
	}

}
