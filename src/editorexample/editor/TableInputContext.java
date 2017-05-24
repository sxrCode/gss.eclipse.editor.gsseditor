package editorexample.editor;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;

import editorexample.model.GSSTableColumnInfo;
import editorexample.model.GSSTableInfo;
import editorexample.parser.GSSTableXMLParser;

public class TableInputContext {

	private IDocument document;
	private FileEditorInput editorInput;
	private IDocumentProvider documentProvider;
	private GSSTableXMLParser tableXMLParser;
	private GSSTableInfo gssTableInfo;
	private List<TableInputContextListener> listeners;

	public TableInputContext(FileEditorInput input, IDocumentProvider documentProvider) {
		this.documentProvider = documentProvider;
		this.editorInput = input;
		tableXMLParser = new GSSTableXMLParser();
		listeners = new LinkedList<TableInputContextListener>();
		init();

	}

	private void init() {
		try {
			this.documentProvider.connect(editorInput);
			this.document = documentProvider.getDocument(editorInput);
			TableDocumentListener documentListener = new TableDocumentListener();
			document.addDocumentListener(documentListener);
			documentProvider.addElementStateListener(documentListener);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public void refreshAndFire() {
		refresh();
		fireModelChangeEvent();
	}

	public void refresh() {
		try {
			System.out.println("\n\nfilename: " + editorInput.getName());
			gssTableInfo = tableXMLParser.parseTableXML(document);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void fireModelChangeEvent() {
		for (TableInputContextListener listener : listeners) {
			listener.listenModelChanged(gssTableInfo);
		}
	}

	public void addTableInputContextListener(TableInputContextListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public GSSTableInfo getGssTableInfo() {
		return gssTableInfo;
	}

	public void modifyDocumentByModel(GSSTableColumnInfo model) {
		int lineNum = model.getLineNumber();
		try {
			int length = document.getLineLength(lineNum);
			int offset = document.getLineOffset(lineNum);
			System.out.println("length: " + length + "; offset: " + offset);
			document.replace(offset, length, model.toString());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void saveDocument(IProgressMonitor monitor) throws CoreException {
		documentProvider.saveDocument(monitor, editorInput, document, true);
	}

	private class TableDocumentListener implements IDocumentListener, IElementStateListener {

		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {
			if (event.getDocument().equals(document)) {
				System.out.println(editorInput.getName() + " AboutToBeChanged!");
			}
		}

		@Override
		public void documentChanged(DocumentEvent event) {
			if (event.getDocument().equals(document)) {
				refreshAndFire();
			}
		}

		@Override
		public void elementDirtyStateChanged(Object element, boolean isDirty) {
			if (element != null && element.equals(editorInput)) {
				System.out.println(editorInput.getName() + " DirtyStateChanged!");
			}
		}

		@Override
		public void elementContentAboutToBeReplaced(Object element) {

		}

		@Override
		public void elementContentReplaced(Object element) {

		}

		@Override
		public void elementDeleted(Object element) {

		}

		@Override
		public void elementMoved(Object originalElement, Object movedElement) {

		}

	}

}
