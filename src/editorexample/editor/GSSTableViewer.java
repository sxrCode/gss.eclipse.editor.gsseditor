package editorexample.editor;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

import editorexample.model.GSSTableColumnInfo;
import editorexample.model.GSSTableInfo;

public class GSSTableViewer extends TableViewer implements TableInputContextListener {

	public GSSTableViewer(Composite parent, int style) {
		super(parent, style);
	}

	private TableInputContext inputContext;

	@Override
	public void listenModelChanged(GSSTableInfo model) {
		setInput(model.getColumnInfos().toArray());
		System.out.println("ChangedModel name: " + model.getTableName());
	}

	public void setInputContext(TableInputContext inputContext) {
		this.inputContext = inputContext;
	}

	public void changeModel(GSSTableColumnInfo model) {
		inputContext.modifyDocumentByModel(model);
	}

}
