package editorexample.editor.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import editorexample.editor.model.GSSTableColumnInfo;
import editorexample.editor.model.GSSTableInfo;
import editorexample.editor.model.GSSTableItemInfo;

public class GSSTableXMLParser {

	public GSSTableInfo parseTableXML(IDocument xmlDocument) throws BadLocationException {
		GSSTableInfo tableInfo = new GSSTableInfo();
		for (int i = 0; i < xmlDocument.getNumberOfLines(); i++) {
			IRegion region = xmlDocument.getLineInformation(i);
			String lineStr = xmlDocument.get(region.getOffset(), region.getLength());
			if (lineStr.indexOf("<column ") != -1) {
				GSSTableColumnInfo columnInfo = extractColumnInfo(lineStr);
				columnInfo.setLineNumber(i);
				tableInfo.addGSSTableColumn(columnInfo);
			} else if (lineStr.indexOf("<TableInfo") != -1) {
				Pattern pattern = Pattern.compile("((name|comment)=\"([^\"]*))");
				Matcher matcher = pattern.matcher(lineStr);
				while (matcher.find()) {
					if (matcher.group(2).equals("name")) {
						String tablename = matcher.group(3);
						tableInfo.setTableName(tablename);
					} else if (matcher.group(2).equals("comment")) {
						String nameComment = matcher.group(3);
						tableInfo.setNameComment(nameComment);
					}

				}
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
