package org.joinfaces.richfaces.example.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.joinfaces.autoconfigure.rewrite.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	DataSource dataSource;
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public List<User> findByName(String name) {
		List<User> result =new ArrayList<User>();
		//String sql = "SELECT * FROM users WHERE name=:name";
		
		
	
		/*
		 * List<User> result = jdbcTemplate.query("SELECT * FROM users WHERE name= ?",
		 * new UserMapper(), name);
		 */
		try {
			Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM users where id="+name);
        ResultSetMetaData rsmd = results.getMetaData();
       // int numberCols = rsmd.getColumnCount();
			/*
			 * for (int i=1; i<=numberCols; i++) { //print Column Names
			 * System.out.print(rsmd.getColumnLabel(i)+"\t\t"); }
			 * 
			 * System.out.println("\n-------------------------------------------------");
			 */

        while(results.next())
        {
            int id = results.getInt(1);
            String name1 = results.getString(2);
            String email = results.getString(3);
           // System.out.println(id + "\t\t" + name1 + "\t\t" + email);
            User user=new User();
            user.setId(id+"");
            user.setName(name1);
            user.setEmail(email);
            result.add(user);
        }
        results.close();
        stmt.close();
		}catch(SQLException sqlExcept) {
			System.out.println("sql exception"+sqlExcept);
		}
        return result;
        
	}

	@Override
	public List<User> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT * FROM users";
		
        List<User> result = namedParameterJdbcTemplate.query(sql, params, new UserMapper());
        
        return result;
        
	}

	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id")+"");
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	}

}