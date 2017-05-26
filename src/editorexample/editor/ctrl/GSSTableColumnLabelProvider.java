package editorexample.editor.ctrl;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import editorexample.editor.model.GSSTableColumnInfo;
import editorexample.editor.model.GSSTableItemInfo;

public class GSSTableColumnLabelProvider implements ITableLabelProvider {

	@Override
	public String getColumnText(Object element, int columnIndex) {
		String text = "";
		if (element instanceof GSSTableColumnInfo) {
			GSSTableColumnInfo columnInfo = (GSSTableColumnInfo) element;
			switch (columnIndex) {
			case 0:
				text = getColumnValue("name", columnInfo);
				break;
			case 1:
				text = getColumnValue("type", columnInfo);
				break;
			case 2:
				text = getColumnValue("length", columnInfo);
				break;
			case 3:
				text = getColumnValue("comment", columnInfo);
				break;
			case 4:
				text = getColumnValue("notNull", columnInfo);
				break;

			default:
				text = "";
				break;
			}
		}
		return text;
	}

	private String getColumnValue(String name, GSSTableColumnInfo columnInfo) {
		GSSTableItemInfo itemInfo = columnInfo.getItem(name);
		if (itemInfo != null) {
			return itemInfo.getValue();
		}
		return "";

	}

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

}
