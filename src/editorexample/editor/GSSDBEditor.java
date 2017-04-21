package editorexample.editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

import editorexample.model.GSSTableInfo;

public class GSSDBEditor extends FormEditor {

	private GSSSourceFormPage sourceFormPage;

	private IDocumentProvider documentProvider;

	private boolean dirtyState = false;

	private List<IDocument> xmlTableDocuments = new LinkedList<IDocument>();

	private List<IFile> xmlTableFiles = new LinkedList<IFile>();

	private IDocument gssdbDocument;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		System.out.println("create editor!");
		MyEditorListener myEditorListener = new MyEditorListener(this);
		documentProvider = new TextFileDocumentProvider();
		documentProvider.addElementStateListener(myEditorListener);
		try {
			documentProvider.connect(input);
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
		gssdbDocument = documentProvider.getDocument(input);
		// content = gssdbDocument.get();
		gssdbDocument.addDocumentListener(myEditorListener);
		setPartName(input.getName());
		if (input instanceof FileEditorInput) {
			FileEditorInput fileInput = (FileEditorInput) input;
			IFile file = fileInput.getFile();
			IContainer container = file.getParent();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(file.getContents()));
				String lineTxt = null;

				while ((lineTxt = reader.readLine()) != null) {
					IResource resource = container.findMember(lineTxt);
					if (resource != null && resource instanceof IFile) {
						IFile xmlFile = (IFile) resource;
						documentProvider.connect(xmlFile);
						IDocument xmlDocument = documentProvider.getDocument(xmlFile);
						xmlTableFiles.add(xmlFile);
						xmlTableDocuments.add(xmlDocument);
						xmlDocument.addDocumentListener(myEditorListener);
					}

				}
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		dirtyState = false;
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
		if (dirtyState) {
			setPartName("*" + getEditorInput().getName());
		} else {
			setPartName(getEditorInput().getName());
		}
	}

	@Override
	protected void addPages() {
		try {
			addPage(createSourcePage());
		} catch (PartInitException e) {
			e.printStackTrace();
		}

	}

	private GSSSourceFormPage createSourcePage() {
		sourceFormPage = new GSSSourceFormPage(this, "gssdb_source_page", "source");
		sourceFormPage.setSourceDocument(gssdbDocument);
		return sourceFormPage;
	}

	public void setTableInfos(List<GSSTableInfo> tableInfos) {

	}

	public void setNewTableInfo(GSSTableInfo tableInfo) {

	}

	public void responseSourceChange() {

	}
}
