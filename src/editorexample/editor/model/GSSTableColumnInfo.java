package editorexample.editor.model;

import java.util.HashMap;
import java.util.Map;

public class GSSTableColumnInfo {

	private int lineNumber;
	private Map<String, GSSTableItemInfo> gssTableItemMap = new HashMap<String, GSSTableItemInfo>();

	public void putItem(String ItemName, GSSTableItemInfo itemInfo) {
		gssTableItemMap.put(ItemName, itemInfo);
	}

	public GSSTableItemInfo getItem(String ItemName) {
		return gssTableItemMap.get(ItemName);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String toString() {
		StringBuilder lineStrBuilder = new StringBuilder("\t\t<column");
		appendProperty(lineStrBuilder, "name");
		appendProperty(lineStrBuilder, "type");
		appendProperty(lineStrBuilder, "length");
		appendProperty(lineStrBuilder, "notNull");
		appendProperty(lineStrBuilder, "comment");
		lineStrBuilder.append("/>\n");
		String lineStr = lineStrBuilder.toString();
		if (lineStr.equals("\t\t<column/>")) {
			lineStr = "";
		}
		return lineStr;
	}

	private void appendProperty(StringBuilder lineStrBuilder, String property) {
		GSSTableItemInfo itemInfo = gssTableItemMap.get(property);
		if (itemInfo != null && !"".equals(itemInfo.getValue())) {
			String propertyValue = itemInfo.getValue();
			lineStrBuilder.append(" " + property + "=\"");
			lineStrBuilder.append(propertyValue + "\"");
		}

	}
}
