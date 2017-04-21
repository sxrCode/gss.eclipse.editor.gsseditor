package editorexample.model;

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
}
