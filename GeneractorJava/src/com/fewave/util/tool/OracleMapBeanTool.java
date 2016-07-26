package com.fewave.util.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fewave.util.base.OracleManage;
import com.fewave.util.common.FileUitl;
import com.fewave.util.common.StringUtil;
import com.fewave.util.mapping.Table;
import com.fewave.util.mapping.Variable;



public class OracleMapBeanTool {
	
	private String ID = "1";

	private static Map<String, String> dataTypeMapping = new HashMap<String, String>();

	static {
		dataTypeMapping.put("int", "int");
		dataTypeMapping.put("tinyint", "int");
		dataTypeMapping.put("smallint", "int");
		dataTypeMapping.put("mediumint", "int");
		dataTypeMapping.put("integer", "int");
		dataTypeMapping.put("bigint", "long");
		dataTypeMapping.put("real", "float");
		dataTypeMapping.put("bit", "boolean");
		dataTypeMapping.put("double", "double");
		dataTypeMapping.put("decimal", "java.math.BigDecimal");
		dataTypeMapping.put("NUMBER", "java.math.BigDecimal");
		dataTypeMapping.put("char", "java.lang.String");
		dataTypeMapping.put("VARCHAR2", "java.lang.String");
		dataTypeMapping.put("date", "java.util.Date");
		dataTypeMapping.put("time", "java.util.Time");
		dataTypeMapping.put("year", "java.util.Date");
		dataTypeMapping.put("timestamp", "java.util.Timestamp");
		dataTypeMapping.put("datetime", "java.util.Date");
		dataTypeMapping.put("blob", "byte[]");
		dataTypeMapping.put("binary", "byte[]");
		dataTypeMapping.put("tinytext", "java.lang.String");
		dataTypeMapping.put("text", "java.lang.String");
	}
	
	public static void main(String[] args) {
		OracleManage manager = new OracleManage();
		List<Table> tables = manager.getTable();
		OracleMapBeanTool tool = new OracleMapBeanTool();
		for (Table table : tables) {
			Map java = tool.getJava(table);
		}
	}

	/**
	 * 创建java实体字符串
	 * @param table
	 * @return
	 */
	public Map<String, Object> getJava(Table table) {
		List<Variable> tableInfo = table.getTableInfo();
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		String sqlString = createJava(tableInfo, table);
		System.out.println("-------输出VO--------");
		FileUitl.createFile("C:\\Users\\fewave\\Desktop\\11.java", sqlString);
		String hibernateStr = getHibernateStr(table,"com.test.util","testVO");
		System.out.println("-------输出hibernate--------");
		FileUitl.createFile("C:\\Users\\fewave\\Desktop\\11.xml", hibernateStr);
		return sqlMap;
	}
	
	/**
	 * 创建JAVA实体字符串
	 * @param tableInfo
	 * @param table
	 * @return
	 */
	private String createJava(List<Variable> tableInfo, Table table){
		StringBuilder sb = new StringBuilder();
		//包名
		String packageString = createPackage(table.getEntityPackage());
		sb.append(packageString);
		//创建导入包
		String importString = createImport();
		sb.append(importString);
		//类型
		String classString = createClass(table.getTableName());
		sb.append(classString);
		//变量
		String variablesString = createVariables(tableInfo);
		sb.append(variablesString);
		//get和set
		String setAndGetString = createSetAndGet(tableInfo);
		sb.append(setAndGetString);
		//尾部
		String tailString = createTail();
		sb.append(tailString);
		
		return sb.toString();
	}

	/**
	 * 创建包信息
	 * @param packagePath
	 * @return
	 */
	private String createPackage(String packagePath) {
		StringBuilder sb = new StringBuilder();
		sb.append("package " + packagePath + ";\r\n");
		return sb.toString();
	}

	/**
	 * 创建导入信息
	 * @return
	 */
	private String createImport() {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n");
		return sb.toString();
	}

	/**
	 * 创建类名
	 * @param tableName
	 * @return
	 */
	private String createClass(String tableName) {
		StringBuilder sb = new StringBuilder();
		String[] temps = tableName.split("_");
		tableName = "";
		for (int i = 0; i < temps.length; i++) {
			//第一个字符大写
			
			tableName += StringUtil.strFirstToUpper(temps[i].toLowerCase());
		}
		sb.append("public class " + tableName + "{\r\n");
		return sb.toString();
	}

	/**
	 * 创建变量
	 * @param tableInfo
	 * @return
	 */
	private String createVariables(List<Variable> tableInfo) {
		StringBuilder sb = new StringBuilder();
		for (Variable variable : tableInfo) {
			sb.append("\tprivate " + dataTypeMapping.get(variable.getType()) + " " + StringUtil.exchangeStr(variable.getPropertyName(),1) + ";");
			if (!StringUtil.isEmpty(variable.getComment())){
				sb.append("//" + variable.getComment());
			}
			sb.append("\r\n");
		}
		sb.append("\r\n");
		return sb.toString();
	}

	/**
	 * 创建Get和Set
	 * @param tableInfo
	 * @return
	 */
	private String createSetAndGet(List<Variable> tableInfo) {
		StringBuilder sb = new StringBuilder();
		for (Variable variable : tableInfo) {
			sb.append("\tpublic " + dataTypeMapping.get(variable.getType()) + " get" + StringUtil.exchangeStr(variable.getPropertyName(),0) + "(){\r\n");
			sb.append("\t\treturn this." + StringUtil.exchangeStr(variable.getPropertyName(),1) + ";\r\n");
			sb.append("\t}\r\n\r\n");

			sb.append("\tpublic void set" + StringUtil.exchangeStr(variable.getPropertyName(),0) + "(" + dataTypeMapping.get(variable.getType()) + " "
					+ variable.getPropertyName() + "){\r\n");
			sb.append("\t\tthis." + StringUtil.exchangeStr(variable.getPropertyName(),1) + " = " + StringUtil.exchangeStr(variable.getPropertyName(),1) + ";\r\n");
			sb.append("\t}\r\n\r\n");
		}
		return sb.toString();
	}

	/**
	 * 创建class 尾部
	 * @return
	 */
	private String createTail() {
		return "}";
	}
	
	/**
	 * 获取hibernate映射文件
	 * @param table
	 * @param packageName
	 * @param tableName
	 * @return
	 */
	private String getHibernateStr(Table table, String packageName, String tableName){
		StringBuilder sb = new StringBuilder();
		sb.append(getXmlHeader());
		sb.append(getXmlProperty(table, packageName, tableName));
		return sb.toString();
	}
	
	/**
	 * 获取hibernate文件头
	 * @return
	 */
	private String getXmlHeader(){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>\r\n");
		sb.append("<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\r\n");
		sb.append("\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\r\n");
		sb.append("<hibernate-mapping>\r\n");
		return sb.toString();
	}
	
	/**
	 * 获取hibernate字段属性
	 * @param table
	 * @param packageName
	 * @param tableName
	 * @return
	 */
	private String getXmlProperty(Table table, String packageName, String tableName){
		StringBuilder sb = new StringBuilder();
		sb.append("\t<class name=\"" + packageName + "." + tableName + "\" table=\"");
		sb.append(table.getTableName() + "\">\r\n");
		
		List<Variable> tableInfo = table.getTableInfo();
		for (Variable variable : tableInfo) {
			if (ID.equals(variable.getColumnId())){
				sb.append("\t\t<id name=\"" + StringUtil.exchangeStr(variable.getPropertyName(),1));
				sb.append("\" type=\"" + dataTypeMapping.get(variable.getType()) + "\">\r\n");
				sb.append("\t\t\t<column name=\"" + variable.getPropertyName());
				sb.append("\" length=\"" + variable.getTypeLength() + "\" />\r\n");
				sb.append("\t\t\t<generator class=\"assigned\" />\r\n");
				sb.append("\t\t</id>\r\n");
			}else {
				
				String notNull = "";
				if ("N".equals(variable.getNullAble())){
					notNull = "true";
				}else {
					notNull = "false";
				}
				sb.append("\t\t<property name=\"" + StringUtil.exchangeStr(variable.getPropertyName(),1));
				sb.append("\" type=\"" + dataTypeMapping.get(variable.getType()) + "\">\r\n");
				sb.append("\t\t\t<column name=\"" + variable.getPropertyName());
				sb.append("\" length=\"" + variable.getTypeLength());
				sb.append("\" not-null=\"" + notNull);
				sb.append("\"></column>\r\n");
				sb.append("\t\t</property>\r\n");
			}
		}
		sb.append("\t</class>\r\n");
		sb.append("</hibernate-mapping>");
		return sb.toString();
	}

}
