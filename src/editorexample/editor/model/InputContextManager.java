package editorexample.editor.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class InputContextManager implements SourceInputListener {

	private List<TableInputContext> tableInputContexts;
	private List<InputContextManagerListener> listeners;

	public InputContextManager() {
		tableInputContexts = new LinkedList<TableInputContext>();
		listeners = new LinkedList<InputContextManagerListener>();
	}

	@Override
	public void addTableInputContext(TableInputContext inputContext) {
		tableInputContexts.add(inputContext);
		inputContext.addTableInputContextListener(new TableInputContextListener() {

			@Override
			public void listenModelChanged(GSSTableInfo model) {
				for (InputContextManagerListener listener : listeners) {
					listener.listeneTableChange(inputContext);
				}
			}
		});
	}

	public void addInputContextManagerListener(InputContextManagerListener listener) {
		if (!listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public List<TableInputContext> getTableInputContexts() {
		return tableInputContexts;
	}

	@Override
	public void changeContexts(List<TableInputContext> tableInputContexts) {
		this.tableInputContexts = tableInputContexts;
		for (InputContextManagerListener listener : listeners) {
			listener.listenerManagerChange(this);
		}
	}

	public void saveAll(IProgressMonitor monitor) throws CoreException {
		for (TableInputContext inputContext : tableInputContexts) {
			inputContext.saveDocument(monitor);
		}
	}

}
