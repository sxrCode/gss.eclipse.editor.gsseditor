package editorexample.editor.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
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

import editorexample.editor.GSSDBEditor;

public class GSSTableFormPage extends FormPage {

	private FormToolkit toolkit;
	private IManagedForm managedForm;
	private Composite content;

	private Map<String, TableViewer> tableviewerMap = new HashMap<String, TableViewer>();
	private Map<TableViewer, Object> inputMap = new HashMap<TableViewer, Object>();
	private List<Object> tables;

	private IGSSTableFormProvider tableProvider;
	private ITableLabelProvider labelProvider;
	private IGSSTableFormPageListener formPageListener;
	private IGSSTableColumnProvider columnProvider;

	public GSSTableFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
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
		content = toolkit.createComposite(body);
		content.setLayout(createBodyLayout(true, 1));
		content.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		tableviewerMap.clear();
		inputMap.clear();
		fillContent();
	}

	private void fillContent() {
		for (Object tableInfo : tables) {
			Section tableSection = toolkit.createSection(content, Section.DESCRIPTION);

			String tableName = tableProvider.getTableName(tableInfo);
			String nameComment = tableProvider.getNameComment(tableInfo);
			if (nameComment != null && !"".equals(nameComment)) {
				tableName += "(" + nameComment + ")";
			}
			tableSection.setText(tableName);
			tableSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
			tableSection.setLayout(createBodyLayout(true, 1));
			TableViewer tableViewer = new TableViewer(tableSection,
					SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

			configTableViewer(tableViewer);
			tableViewer.setInput(tableProvider.getTableContent(tableInfo));

			tableviewerMap.put(tableName, tableViewer);
			inputMap.put(tableViewer, tableInfo);
			tableSection.setClient(tableViewer.getControl());
		}
	}

	private void configTableViewer(TableViewer tableViewer) {
		tableViewer.setColumnProperties(new String[] { "name", "type", "length", "comment", "notNull" });
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(labelProvider);

		tableViewer.setCellModifier(new ICellModifier() {

			Object selectedObj;

			@Override
			public void modify(Object element, String property, Object value) {
				System.out.println("modify!");
				if (element instanceof Item) {
					element = ((Item) element).getData();
				}
				formPageListener.changeColumnInfo(inputMap.get(tableViewer), element, property, value);
				GSSDBEditor editor = (GSSDBEditor) getEditor();
				editor.updateDirtyState(true);
				if (selectedObj != element) {
					System.out.println("selectedObj != element");
				}
			}

			@Override
			public Object getValue(Object element, String property) {
				System.out.println("getValue");
				selectedObj = element;
				return columnProvider.getValue(element, property);
			}

			@Override
			public boolean canModify(Object element, String property) {
				return columnProvider.canModify(element, property);
			}
		});

		Table table = tableViewer.getTable();
		setTableColumn(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		CellEditor[] cellEditors = new TextCellEditor[5];
		for (int i = 0; i < cellEditors.length; i++) {
			CellEditor cellEditor = new TextCellEditor(table);
			cellEditors[i] = cellEditor;
		}
		tableViewer.setCellEditors(cellEditors);
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

	public void changeTable(Object tableInfo) {
		TableViewer tableViewer = tableviewerMap.get(tableProvider.getTableName(tableInfo));
		tableViewer.setInput(tableProvider.getTableContent(tableInfo));
		tableViewer.refresh();
	}

	public void setTables(List<Object> tables) {
		this.tables = tables;
		updateView();
	}

	private void updateView() {
		System.out.println("updateView!");
		if (managedForm != null) {
			refreshTableList();
			managedForm.reflow(true);
		}
	}

	public void setTableProvider(IGSSTableFormProvider tableProvider) {
		this.tableProvider = tableProvider;
	}

	public void setLabelProvider(ITableLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public void setFormPageListener(IGSSTableFormPageListener formPageListener) {
		this.formPageListener = formPageListener;
	}

	public void setColumnProvider(IGSSTableColumnProvider columnProvider) {
		this.columnProvider = columnProvider;
	}

}
