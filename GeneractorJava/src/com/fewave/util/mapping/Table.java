package com.fewave.util.mapping;

import java.util.List;

/**
 * 数据库表实体
 * @author fewave
 *
 */
public class Table {
	private String entityPackage;
	private String tableName;
	private List<Variable> tableInfo;

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Variable> getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(List<Variable> tableInfo) {
		this.tableInfo = tableInfo;
	}

}
