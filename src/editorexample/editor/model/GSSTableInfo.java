package editorexample.editor.model;

import java.util.LinkedList;
import java.util.List;

public class GSSTableInfo {

	private String tableName;
	private String nameComment;
	private List<GSSTableColumnInfo> columnInfos = new LinkedList<GSSTableColumnInfo>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void addGSSTableColumn(GSSTableColumnInfo columnInfo) {
		columnInfos.add(columnInfo);
	}

	public List<GSSTableColumnInfo> getColumnInfos() {
		return columnInfos;
	}

	public String getNameComment() {
		return nameComment;
	}

	public void setNameComment(String nameComment) {
		this.nameComment = nameComment;
	}

}
