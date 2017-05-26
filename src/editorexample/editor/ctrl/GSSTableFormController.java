package editorexample.editor.ctrl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import editorexample.editor.model.GSSTableColumnInfo;
import editorexample.editor.model.GSSTableItemInfo;
import editorexample.editor.model.InputContextManager;
import editorexample.editor.model.InputContextManagerListener;
import editorexample.editor.model.TableInputContext;
import editorexample.editor.view.GSSTableFormPage;
import editorexample.editor.view.IGSSTableColumnProvider;
import editorexample.editor.view.IGSSTableFormPageListener;
import editorexample.editor.view.IGSSTableFormProvider;

public class GSSTableFormController {

	private GSSTableFormPage gssTableFormPage;
	private InputContextManager contextManager;

	public GSSTableFormController(GSSTableFormPage gssTableFormPage, InputContextManager contextManager) {
		this.contextManager = contextManager;
		this.gssTableFormPage = gssTableFormPage;
	}

	public void manage() {
		List<Object> tables = new LinkedList<>(contextManager.getTableInputContexts());
		gssTableFormPage.setLabelProvider(new GSSTableColumnLabelProvider());

		setTableFormProvider();
		setColumnProvider();
		setFormPageListener();
		gssTableFormPage.setTables(tables);
		addInputContextManagerListener();
		gssTableFormPage.setTables(tables);
	}

	private void setFormPageListener() {
		gssTableFormPage.setFormPageListener(new IGSSTableFormPageListener() {

			@Override
			public void changeColumnInfo(Object inputContext, Object element, String property, Object value) {
				GSSTableColumnInfo columnInfo = (GSSTableColumnInfo) element;
				GSSTableItemInfo itemInfo = new GSSTableItemInfo();
				itemInfo.setValue((String) value);
				columnInfo.putItem(property, itemInfo);
				System.out.println(columnInfo.getItem(property));
				TableInputContext tableInputContext = (TableInputContext) inputContext;
				tableInputContext.modifyDocumentByModel(columnInfo);
			}
		});
	}

	private void addInputContextManagerListener() {
		contextManager.addInputContextManagerListener(new InputContextManagerListener() {

			@Override
			public void listenerManagerChange(InputContextManager inputContextManager) {
				List<Object> tables = new ArrayList<>(inputContextManager.getTableInputContexts());
				gssTableFormPage.setTables(tables);
			}

			@Override
			public void listeneTableChange(TableInputContext inputContext) {
				gssTableFormPage.changeTable(inputContext);
			}
		});
	}

	private void setColumnProvider() {
		gssTableFormPage.setColumnProvider(new IGSSTableColumnProvider() {

			@Override
			public Object getValue(Object element, String property) {
				if (element instanceof GSSTableColumnInfo) {
					GSSTableColumnInfo columnInfo = (GSSTableColumnInfo) element;
					GSSTableItemInfo itemInfo = columnInfo.getItem(property);
					if (itemInfo != null) {
						System.out.println(itemInfo.getValue());
						return itemInfo.getValue();
					}
				}
				return "";
			}

			@Override
			public boolean canModify(Object element, String property) {
				return true;
			}
		});
	}

	private void setTableFormProvider() {
		gssTableFormPage.setTableProvider(new IGSSTableFormProvider() {
			private TableInputContext tableInputContext;

			private boolean translateFrom(Object tableInfo) {
				if (tableInfo instanceof TableInputContext) {
					tableInputContext = (TableInputContext) tableInfo;
					return true;
				}
				return false;
			}

			@Override
			public String getTableName(Object tableInfo) {
				if (translateFrom(tableInfo)) {
					return tableInputContext.getGssTableInfo().getTableName();
				}
				return "";
			}

			@Override
			public Object[] getTableContent(Object tableInfo) {
				if (translateFrom(tableInfo)) {
					return tableInputContext.getGssTableInfo().getColumnInfos().toArray();
				}
				return new LinkedList<>().toArray();
			}

			@Override
			public String getNameComment(Object tableInfo) {
				if (translateFrom(tableInfo)) {
					return tableInputContext.getGssTableInfo().getNameComment();
				}
				return "";
			}
		});
	}

}
