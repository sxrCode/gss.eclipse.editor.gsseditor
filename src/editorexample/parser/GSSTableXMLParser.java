package editorexample.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import editorexample.model.GSSTableColumnInfo;
import editorexample.model.GSSTableInfo;
import editorexample.model.GSSTableItemInfo;

public class GSSTableXMLParser {

	public GSSTableInfo parseTableXML(IDocument xmlDocument) throws BadLocationException {
		GSSTableInfo tableInfo = new GSSTableInfo();
		for (int i = 0; i < xmlDocument.getNumberOfLines(); i++) {
			IRegion region = xmlDocument.getLineInformation(i);
			String lineStr = xmlDocument.get(region.getOffset(), region.getLength());
			System.out.println("the " + i + " line String is : " + lineStr);
			if (lineStr.startsWith("<column ")) {
				GSSTableColumnInfo columnInfo = extractColumnInfo(lineStr);
				columnInfo.setLineNumber(i);
				tableInfo.addGSSTableColumn(columnInfo);
			}
		}
		return tableInfo;
	}

	private GSSTableColumnInfo extractColumnInfo(String lineStr) {
		GSSTableColumnInfo columnInfo = new GSSTableColumnInfo();
		Pattern pattern = Pattern.compile("(name|type|length|comment|notNull)=\"([^\"]*)");
		Matcher matcher = pattern.matcher(lineStr);
		while (matcher.find()) {
			GSSTableItemInfo gssTableItemInfo = new GSSTableItemInfo();
			String name = matcher.group(1);
			gssTableItemInfo.setValue(matcher.group(2));
			gssTableItemInfo.setOffset(matcher.start(2));
			gssTableItemInfo.setLength(matcher.end(2) - matcher.start(2));
			columnInfo.putItem(name, gssTableItemInfo);
		}

		return columnInfo;
	}
}
