package editorexample.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;

import editorexample.editor.ctrl.GSSTableFormController;
import editorexample.editor.model.InputContextManager;
import editorexample.editor.model.SourceInputContext;
import editorexample.editor.view.GSSSourceFormPage;
import editorexample.editor.view.GSSTableFormPage;

public class GSSDBEditor extends FormEditor {

	private GSSSourceFormPage sourceFormPage;

	private GSSTableFormPage tableFormPage;

	private InputContextManager manager;

	private IDocumentProvider documentProvider;

	private boolean dirtyState = false;

	private IDocument gssdbDocument;

	private GSSTableFormController controllerDelegate;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		System.out.println("create editor!");
		documentProvider = new TextFileDocumentProvider();

		try {
			documentProvider.connect(input);
			gssdbDocument = documentProvider.getDocument(input);

			SourceInputContext sourceInputContext = new SourceInputContext(gssdbDocument, input, this);
			manager = new InputContextManager();
			sourceInputContext.addSourceInputListener(manager);
			sourceInputContext.refresh();

		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		setPartName(input.getName());
		MyEditorListener myEditorListener = new MyEditorListener(input, gssdbDocument);
		gssdbDocument.addDocumentListener(myEditorListener);
		documentProvider.addElementStateListener(myEditorListener);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		System.out.println("GSSDBEditor doSave!");
		try {
			manager.saveAll(monitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		updateDirtyState(false);
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public boolean isDirty() {
		return dirtyState;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {

	}

	public void updateDirtyState(boolean isDirty) {
		dirtyState = isDirty;
		editorDirtyStateChanged();
	}

	@Override
	protected void addPages() {
		try {
			addPage(createSourcePage());
			addPage(createTableFormPage());
			controllerDelegate = new GSSTableFormController(tableFormPage, manager);
			controllerDelegate.manage();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	private GSSSourceFormPage createSourcePage() {
		sourceFormPage = new GSSSourceFormPage(this, "gssdb_source_page", "source");
		sourceFormPage.setSourceDocument(gssdbDocument);
		return sourceFormPage;
	}

	private GSSTableFormPage createTableFormPage() {
		tableFormPage = new GSSTableFormPage(this, "gssdb_atble_page", "table");
		return tableFormPage;
	}

	public IDocumentProvider getDocumentProvider() {
		return documentProvider;
	}

	public InputContextManager getManager() {
		return manager;
	}

}
