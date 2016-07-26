package com.fewave.util.mapping;

/**
 * ±í×Ö¶ÎÊµÌå
 * @author fewave
 *
 */
public class Variable {
	private String propertyName;
	private String type;
	private String typeLength;
	private String comment;
	private String columnId;
	private String nullAble;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getNullAble() {
		return nullAble;
	}

	public void setNullAble(String nullAble) {
		this.nullAble = nullAble;
	}

	public String getTypeLength() {
		return typeLength;
	}

	public void setTypeLength(String typeLength) {
		this.typeLength = typeLength;
	}

}
