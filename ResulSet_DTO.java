package in.pennant.jdbc.awt;
/**
 * 
 * @author hemanthraju.v
 * @version 1.0.0
 * @company PENNANT TECHNOLOGIES PVT LTD
 * 
 */
public class ResulSet_DTO {
	private String columnName;
	private int colNo;
	private String dataType;
	private String constrainttype;
	private String constraintname;
	public ResulSet_DTO(){}
	
	protected String getColumnName() {
		return columnName;
	}
	protected void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	protected int getColNo() {
		return colNo;
	}
	protected void setColNo(int colNo) {
		this.colNo = colNo;
	}
	protected String getDataType() {
		return dataType;
	}
	protected void setDataType(String dataType) {
		this.dataType = dataType;
	}
	protected String getConstrainttype() {
		return constrainttype;
	}
	protected void setConstrainttype(String constrainttype) {
		this.constrainttype = constrainttype;
	}
	protected String getConstraintname() {
		return constraintname;
	}
	protected void setConstraintname(String constraintname) {
		this.constraintname = constraintname;
	}
	
	public String toString(int i) {
		return String.format("%-2d\t%-20s\t\t%-10d\t\t%-15s\t\t%-20s\t\t%-20s",i,columnName, colNo, dataType, constrainttype, constraintname);
	}
}
