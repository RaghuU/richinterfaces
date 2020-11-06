package org.joinfaces.richfaces.example.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class RichFacesDao {
	
	private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
}

	//Sql injection
	public List<String> atriskdogs(String productId) {
		String sql = "select name from Products where productId = '" + productId + "'";
		List valuesList = new ArrayList();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List extractData(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String name = rs.getString("name");
					valuesList.add(name);
				}
				return valuesList;
			}
		}

		);
		return valuesList;
	}
    
}
	
