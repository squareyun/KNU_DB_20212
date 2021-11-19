package db;

public class dbInfo {
	
	private String driverName = "oracle.jdbc.driver.OracleDriver";
	private String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
	private String dbID = "userchat";
	private String dbPassword = "1234";
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDbURL() {
		return dbURL;
	}
	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}
	public String getDbID() {
		return dbID;
	}
	public void setDbID(String dbID) {
		this.dbID = dbID;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	
	
}
