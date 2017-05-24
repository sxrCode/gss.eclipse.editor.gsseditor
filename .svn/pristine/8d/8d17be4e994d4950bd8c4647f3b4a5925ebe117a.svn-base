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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;

public class SourceInputContext {

	private IDocument document;
	private FileEditorInput editorInput;
	private GSSDBEditor editor;
	private IDocumentProvider documentProvider;
	private List<SourceInputListener> listeners;

	public SourceInputContext(IDocument document, IEditorInput editorInput, GSSDBEditor editor) {
		listeners = new LinkedList<SourceInputListener>();
		this.editor = editor;
		this.documentProvider = this.editor.getDocumentProvider();
		this.editorInput = (FileEditorInput) editorInput;
		try {
			documentProvider.connect(editorInput);
			this.document = documentProvider.getDocument(editorInput);
			init();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		SourceDocumentListener listener = new SourceDocumentListener();
		documentProvider.addElementStateListener(listener);
		document.addDocumentListener(listener);
	}

	public void refresh() {
		if (editorInput instanceof FileEditorInput) {
			FileEditorInput fileInput = (FileEditorInput) editorInput;
			IFile file = fileInput.getFile();
			IContainer container = file.getParent();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(file.getContents()));
				List<TableInputContext> tableInputContexts = new LinkedList<TableInputContext>();
				int lineNum = document.getNumberOfLines();

				for (int i = 0; i < lineNum; i++) {
					try {
						int offset = document.getLineOffset(i);
						int length = document.getLineLength(i);
						String filename = document.get(offset, length);
						System.out.println("parseDocument filename: " + filename);
						IResource resource = container.findMember(filename.replace('\n', ' ').trim());
						if (resource != null && resource instanceof IFile) {
							IFile xmlFile = (IFile) resource;
							IEditorInput editorInput = new FileEditorInput(xmlFile);

							TableInputContext tableInputContext = new TableInputContext((FileEditorInput) editorInput,
									documentProvider);
							tableInputContext.refresh();
							tableInputContexts.add(tableInputContext);
						}

					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
				for (SourceInputListener listener : listeners) {
					listener.changeContexts(tableInputContexts);
				}

			} catch (CoreException e) {
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

	public void addSourceInputListener(SourceInputListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	private class SourceDocumentListener implements IDocumentListener, IElementStateListener {

		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {
			if (document.equals(event.getDocument())) {

			}

		}

		@Override
		public void documentChanged(DocumentEvent event) {
			if (document.equals(event.getDocument())) {
				refresh();
			}

		}

		@Override
		public void elementDirtyStateChanged(Object element, boolean isDirty) {
			if (element != null && element.equals(editorInput)) {

			}

		}

		@Override
		public void elementContentAboutToBeReplaced(Object element) {
			if (element != null && element.equals(editorInput)) {

			}

		}

		@Override
		public void elementContentReplaced(Object element) {
			if (element != null && element.equals(editorInput)) {

			}

		}

		@Override
		public void elementDeleted(Object element) {
			if (element != null && element.equals(editorInput)) {

			}

		}

		@Override
		public void elementMoved(Object originalElement, Object movedElement) {
			if (originalElement != null && originalElement.equals(editorInput)) {

			}
		}

	}

}
