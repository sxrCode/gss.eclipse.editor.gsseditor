package editorexample.editor;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import editorexample.model.GSSTableColumnInfo;
import editorexample.model.GSSTableInfo;
import editorexample.model.GSSTableItemInfo;

public class GSSTableFormPage extends FormPage implements InputContextManagerListener {

	private InputContextManager contextManager;
	private List<GSSTableViewer> gssTableViewers;
	private FormToolkit toolkit;
	private IManagedForm managedForm;

	public GSSTableFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
		gssTableViewers = new LinkedList<GSSTableViewer>();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		this.managedForm = managedForm;
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		this.toolkit = toolkit;
		toolkit.decorateFormHeading(form.getForm());
		form.setText("数据库表");
		refreshTableList();
	}

	private void refreshTableList() {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(createBodyLayout(true, 1));

		for (Control control : body.getChildren()) {
			control.dispose();
		}

		Composite content = toolkit.createComposite(body);
		content.setLayout(createBodyLayout(true, 1));
		content.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		List<TableInputContext> tableInputContexts = contextManager.getTableInputContexts();
		for (TableInputContext tableInputContext : tableInputContexts) {
			Section tableSection = toolkit.createSection(content, Section.DESCRIPTION);
			
			GSSTableInfo tableInfo = tableInputContext.getGssTableInfo();
			String sectionTitle = tableInfo.getTableName();
			if (tableInfo.getNameComment() != null && !"".equals(tableInfo.getNameComment())) {
				sectionTitle += "(" + tableInfo.getNameComment() + ")";
			}
			tableSection.setText(sectionTitle);
			
			tableSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
			tableSection.setLayout(createBodyLayout(true, 1));

			GSSTableViewer gssTableViewer = createTableViewer(tableInputContext, tableSection);
			tableSection.setClient(gssTableViewer.getControl());
		}
	}

	private GSSTableViewer createTableViewer(TableInputContext tableInputContext, Section tableSection) {
		GSSTableViewer gssTableViewer = new GSSTableViewer(tableSection,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		configTableViewer(gssTableViewer);
		tableInputContext.addTableInputContextListener(gssTableViewer);
		gssTableViewer.setInputContext(tableInputContext);
		gssTableViewer.setInput(tableInputContext.getGssTableInfo().getColumnInfos().toArray());
		gssTableViewers.add(gssTableViewer);
		return gssTableViewer;
	}

	private void configTableViewer(GSSTableViewer gssTableViewer) {
		gssTableViewer.setColumnProperties(new String[] { "name", "type", "length", "comment", "notNull" });
		gssTableViewer.setContentProvider(new ArrayContentProvider());
		gssTableViewer.setLabelProvider(new ITableLabelProvider() {

			@Override
			public void removeListener(ILabelProviderListener listener) {
			}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			@Override
			public void dispose() {

			}

			@Override
			public void addListener(ILabelProviderListener listener) {

			}

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

			@Override
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			private String getColumnValue(String name, GSSTableColumnInfo columnInfo) {
				GSSTableItemInfo itemInfo = columnInfo.getItem(name);
				if (itemInfo != null) {
					return itemInfo.getValue();
				}
				return "";

			}
		});

		gssTableViewer.setCellModifier(new ICellModifier() {

			@Override
			public void modify(Object element, String property, Object value) {
				if (element instanceof Item) {
					element = ((Item) element).getData();
				}
				GSSTableColumnInfo columnInfo = (GSSTableColumnInfo) element;
				GSSTableItemInfo itemInfo = new GSSTableItemInfo();
				itemInfo.setValue((String) value);
				columnInfo.putItem(property, itemInfo);
				gssTableViewer.changeModel(columnInfo);

				GSSDBEditor editor = (GSSDBEditor) getEditor();

				editor.updateDirtyState(true);
			}

			@Override
			public Object getValue(Object element, String property) {
				if (element instanceof GSSTableColumnInfo) {
					GSSTableColumnInfo columnInfo = (GSSTableColumnInfo) element;
					GSSTableItemInfo itemInfo = columnInfo.getItem(property);
					if (itemInfo != null) {
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

		Table table = gssTableViewer.getTable();
		setTableColumn(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		CellEditor[] cellEditors = new TextCellEditor[5];
		for (int i = 0; i < cellEditors.length; i++) {
			CellEditor cellEditor = new TextCellEditor(table);
			cellEditors[i] = cellEditor;
		}
		gssTableViewer.setCellEditors(cellEditors);
		table.setLayout(createTableViewLayout());
		table.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
	}

	private TableLayout createTableViewLayout() {
		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(150)); // 列名
		tableLayout.addColumnData(new ColumnWeightData(100)); // 类型
		tableLayout.addColumnData(new ColumnWeightData(100)); // 长度
		tableLayout.addColumnData(new ColumnWeightData(300)); // 注释
		tableLayout.addColumnData(new ColumnWeightData(100)); // 是否为空
		return tableLayout;
	}

	private void setTableColumn(Table table) {
		addTableColumn(table, "列名", SWT.LEFT);
		addTableColumn(table, "类型", SWT.LEFT);
		addTableColumn(table, "长度", SWT.LEFT);
		addTableColumn(table, "注释", SWT.LEFT);
		addTableColumn(table, "是否为空", SWT.LEFT);
	}

	private void addTableColumn(Table table, String columnName, int styles) {
		TableColumn column = new TableColumn(table, styles);
		column.setText(columnName);
	}

	public void setContextManager(InputContextManager contextManager) {
		this.contextManager = contextManager;
	}

	private TableWrapLayout createBodyLayout(boolean makeColumnsEqualWidth, int numColumns) {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = 0;
		layout.bottomMargin = 0;
		layout.leftMargin = 6;
		layout.rightMargin = 6;

		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	@Override
	public void listenerManagerChange(InputContextManager inputContextManager) {
		System.out.println("listenerManagerChange!");
		this.contextManager = inputContextManager;
		if (managedForm.getForm() != null) {
			refreshTableList();
			managedForm.reflow(true);
		}
	}

}
