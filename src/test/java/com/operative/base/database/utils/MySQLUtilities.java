/**
 * 
 */
package com.operative.base.database.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.operative.aos.configs.AutoGlobalConstants;

/**
 * @author upratap
 *
 */
public class MySQLUtilities {
	
	private Connection conn;
	private Statement statement;
	private ResultSet resultSet;

	public ResultSet executeQuery(String sQuerry) throws SQLException {

		conn = getMySqlConnection();
		statement = conn.createStatement();
		resultSet = statement.executeQuery(sQuerry);

		return resultSet;

	}

	/**
	 * dynamic DB Name
	 */
	public ResultSet executeQuery(String sQuerry, String DBName) throws SQLException {
		conn = getMySqlConnection(DBName);
		statement = conn.createStatement();
		resultSet = statement.executeQuery(sQuerry);

		return resultSet;
	}

	public void insertQuery(String sQuerry) throws SQLException {
		conn = getMySqlConnection();
		statement = conn.createStatement();
		statement.executeUpdate(sQuerry);

	}

	public ResultSet executeQueryWithOutConn(String sQuerry) throws Exception {
		resultSet = statement.executeQuery(sQuerry);

		return resultSet;
	}

	private Connection getMySqlConnection() throws SQLException {

		final String mysqlDriver = "com.mysql.cj.jdbc.Driver";
		final String url = AutoGlobalConstants.dbhost;

		final String userName = AutoGlobalConstants.dbuserId;
		final String password = AutoGlobalConstants.dbpwd;
		final String dbName = AutoGlobalConstants.dbname;

		try {
			Class.forName(mysqlDriver).newInstance();
		} catch (final InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Objects.isNull(conn)) {
			conn = DriverManager.getConnection(url + dbName, userName, password);
		}
		System.out.println("Connection to the database : " + dbName + " established \n");

		return conn;
	}

	/**
	 * parametrized DB Connection
	 */
	private Connection getMySqlConnection(String DBName) throws SQLException {

		final String mysqlDriver = "com.mysql.cj.jdbc.Driver";
		final String url = AutoGlobalConstants.dbhost;
		final String userName = AutoGlobalConstants.dbuserId;
		final String password = AutoGlobalConstants.dbpwd;
		final String dbName = DBName;

		try {
			Class.forName(mysqlDriver).newInstance();
		} catch (final InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Objects.isNull(conn)) {
			conn = DriverManager.getConnection(url + dbName, userName, password);
		}
		System.out.println("Connection to the database : " + dbName + " established \n");

		return conn;
	}

	public int getRowCountForQuery(ResultSet queryResultSet) throws SQLException {
		int rowCount = 0;
		queryResultSet.next();

		queryResultSet.last();

		rowCount = queryResultSet.getRow();

		queryResultSet.beforeFirst();

		return rowCount;
	}

	public int getColumnCountForQuery(ResultSet resultSet) throws SQLException {
		int columnCount = 0;
		final ResultSetMetaData rsmd = resultSet.getMetaData();

		columnCount = rsmd.getColumnCount();

		return columnCount;
	}

	public void updateQuery(String Query) throws SQLException {
		conn = getMySqlConnection();
		statement = conn.createStatement();
		statement.executeUpdate(Query);

	}

	public int executeUpdate(String dbhost, String dbuserid, String dbpwd, String dbname, String sQuerry)
			throws SQLException {

		conn = getMySqlConnection(dbhost, dbuserid, dbpwd, dbname);
		statement = conn.createStatement();
		return statement.executeUpdate(sQuerry);
	}

	public String[][] executeQueryAndGetResultsWithoutConn(String sQuerry) throws Exception {
		int j, k, p = 1;
		resultSet = statement.executeQuery(sQuerry);
		k = getColumnCountForQuery(resultSet);
		j = getRowCountForQuery(resultSet);

		final String[][] array = new String[j][k];

		while (resultSet.next()) {
			for (int q = 1; q <= k; q++) {
				array[p - 1][q - 1] = resultSet.getString(q);
			}
			p++;
			if (p > j) {
				break;
			}

		}
		return array;
	}

	public String[][] executeQueryAndGetResults(String sQuerry, boolean bConnClose) throws SQLException {
		int j, k, p = 1;
		resultSet = executeQuery(sQuerry);

		k = getColumnCountForQuery(resultSet);
		j = getRowCountForQuery(resultSet);

		final String[][] array = new String[j][k];

		while (resultSet.next()) {
			for (int q = 1; q <= k; q++) {
				array[p - 1][q - 1] = resultSet.getString(q);
			}
			p++;
			if (p > j) {
				break;
			}

		}
		if (bConnClose) {
			conn.close();
		}
		return array;
	}

	public String[][] executeQueryAndGetResults(String sQuerry, boolean bConnClose, String DBName) throws SQLException {
		int j, k, p = 1;
		resultSet = executeQuery(sQuerry, DBName);

		k = getColumnCountForQuery(resultSet);
		j = getRowCountForQuery(resultSet);

		final String[][] array = new String[j][k];

		while (resultSet.next()) {
			for (int q = 1; q <= k; q++) {
				array[p - 1][q - 1] = resultSet.getString(q);
			}
			p++;
			if (p > j) {
				break;
			}

		}
		if (bConnClose) {
			conn.close();
		}
		return array;
	}

	public enum PREFNODETYPES {
		Internationalization
	}

	public enum PREFTYPES {
		DateFormat, TimeFormat, Currency
	}

	// Execute callable stored Procedure
	public void executeProcedure(String procedure) throws SQLException {
		final Connection con = getMySqlConnection();
		final CallableStatement stm = con.prepareCall(procedure);
		stm.execute();

	}

	public ResultSet executeQueryAndGetResults(String sQuerry) throws SQLException {

		resultSet = executeQuery(sQuerry);

		return resultSet;
	}

	public ResultSet executeQuery(String dbhost, String dbuserid, String dbpwd, String dbname, String sQuerry)
			throws SQLException {

		conn = getMySqlConnection(dbhost, dbuserid, dbpwd, dbname);
		statement = conn.createStatement();
		resultSet = statement.executeQuery(sQuerry);
		return resultSet;
	}

	private Connection getMySqlConnection(String dbhost, String dbuserid, String dbpwd, String dbname)
			throws SQLException {

		final String mysqlDriver = "com.mysql.cj.jdbc.Driver";
		final String url = dbhost;

		final String userName = dbuserid;
		final String password = dbpwd;
		final String dbName = dbname;

		try {
			Class.forName(mysqlDriver).newInstance();
		} catch (final InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Objects.isNull(conn)) {
			conn = DriverManager.getConnection(url + dbName, userName, password);
		}
		System.out.println("Connection to the database : " + dbName + " established \n");

		return conn;
	}

	public ResultSet executeQueryAndGetResults(String dbhost, String dbuserid, String dbpwd, String dbname,
			String sQuerry) throws SQLException {

		resultSet = executeQuery(dbhost, dbuserid, dbpwd, dbname, sQuerry);

		return resultSet;
	}

}
