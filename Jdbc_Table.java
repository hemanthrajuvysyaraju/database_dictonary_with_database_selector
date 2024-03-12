package in.pennant.jdbc.awt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import in.pennant.jdbc.util.JdbcUtilities;

/**
 * 
 * @author hemanthraju.v
 * @version 1.0.0
 * @company PENNANT TECHNOLOGIES PVT LTD
 */

public class Jdbc_Table {
	public static final ArrayList<String> TABLENAME = new ArrayList<>();
	public static final ArrayList<String> COLUMNNAME = new ArrayList<>();
	public static final ArrayList<ResulSet_DTO> aldt = new ArrayList<>();
	public static final Connection con=JdbcUtilities.getConnection();
	public static final ArrayList<String> databases = new ArrayList<>();
	private static final String PROCEDURE_TABLE_INFO="SELECT\r\n"
			+ "    column_name,\r\n"
			+ "    ordinal_position,\r\n"
			+ "    data_type || COALESCE('(' || character_maximum_length || ')', '') AS data_type_with_length,\r\n"
			+ "    CASE\r\n"
			+ "        WHEN constraint_type = 'PRIMARY KEY' THEN 'Primary Key'\r\n"
			+ "        WHEN constraint_type = 'FOREIGN KEY' THEN 'Foreign Key'\r\n"
			+ "        ELSE ''\r\n"
			+ "    END AS constraint_type,\r\n"
			+ "    constraint_name\r\n"
			+ "FROM\r\n"
			+ "    information_schema.columns col\r\n"
			+ "LEFT JOIN (\r\n"
			+ "    SELECT\r\n"
			+ "        con.conname AS constraint_name,\r\n"
			+ "        con.conrelid AS table_id,\r\n"
			+ "        unnest(con.conkey) AS column_id,\r\n"
			+ "        CASE\r\n"
			+ "            WHEN con.contype = 'p' THEN 'PRIMARY KEY'\r\n"
			+ "            WHEN con.contype = 'f' THEN 'FOREIGN KEY'\r\n"
			+ "            ELSE ''\r\n"
			+ "        END AS constraint_type\r\n"
			+ "    FROM\r\n"
			+ "        pg_constraint con\r\n"
			+ ") cons ON col.ordinal_position = cons.column_id AND col.table_name::regclass = cons.table_id\r\n"
			+ "WHERE\r\n"
			+ "    table_name = ?";
	private static DatabaseMetaData tnames = null;
	public static void setmeta() {
		try {
			JdbcUtilities.setDatabasename(Data_Dictonary.SELECTED_DATA_BASE);
			tnames = JdbcUtilities.getConnection().getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<String> getDataBases()
	{
//		databases.clear();
		Connection con=JdbcUtilities.getConnection();
		try {
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT datname FROM pg_database");
			while(rs.next())
			{
				databases.add(rs.getString(1));
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return databases;
	}
	public static ArrayList<String> getTableNames() {
		TABLENAME.clear();
		try (ResultSet rs = tnames.getTables(null, null, "%", new String[] { "TABLE" })) {
			while (rs.next()) {
				TABLENAME.add(rs.getString("TABLE_NAME"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return TABLENAME;
	}

	public static ArrayList<String> getColumnNames() {
		COLUMNNAME.clear();
		try (ResultSet rs = tnames.getColumns(null, null, Data_Dictonary.SELECTED_TABLE, null)) {
			boolean is_columns_exists = false;
			while (rs.next()) {
				COLUMNNAME.add(rs.getString("COLUMN_NAME"));
				is_columns_exists = true;
			}
			if (!is_columns_exists) {
				COLUMNNAME.add("NO COLUMNS AVAILABLE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return COLUMNNAME;
	}
	public static HashMap<String,ResulSet_DTO> getColumnDetailsByHashMap()
	{
		HashMap<String,ResulSet_DTO> hm=new HashMap<>();
		for(ResulSet_DTO al:aldt)
		{
			hm.put(al.getColumnName(), al);
		}
		return hm;
		
	}
	public static ArrayList<ResulSet_DTO> getColumnDetails() {
		aldt.clear();
//		ResultSet rs = null;
//		PreparedStatement psmt = null;
		try {
			PreparedStatement psmt = con.prepareStatement(PROCEDURE_TABLE_INFO);			
			psmt.setString(1, Data_Dictonary.SELECTED_TABLE);
			ResultSet rs = psmt.executeQuery();
			ResulSet_DTO rsdt=null;
			while(rs.next())
			{
				rsdt=new ResulSet_DTO();
				rsdt.setColumnName(rs.getString(1));
				rsdt.setColNo(rs.getInt(2));
				rsdt.setDataType(rs.getString(3));
				rsdt.setConstrainttype(rs.getString(4));
				rsdt.setConstraintname(rs.getString(5));
				aldt.add(rsdt);
			}
			JdbcUtilities.closeConnections(null, rs, psmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		aldt.forEach(System.out::println);
		return aldt;
	}

	public static void main(String[] args) {
	}

}