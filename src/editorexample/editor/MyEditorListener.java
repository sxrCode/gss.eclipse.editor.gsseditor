package editorexample.editor;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IElementStateListener;

public class MyEditorListener implements IElementStateListener, IDocumentListener {

	private IEditorInput input;
	private IDocument document;
	private String filename;

	public MyEditorListener(IEditorInput editorInput, IDocument document) {
		input = editorInput;
		this.document = document;
		filename = input.getName();
	}

	@Override
	public void elementDirtyStateChanged(Object element, boolean isDirty) {
		if (element != null && element.equals(input)) {
			System.out.println(filename + " elementDirtyStateChanged");
		}

	}

	@Override
	public void elementContentAboutToBeReplaced(Object element) {
		if (element != null && element.equals(input)) {
			System.out.println(filename + " elementContentAboutToBeReplaced");
		}

	}

	@Override
	public void elementContentReplaced(Object element) {
		System.out.println("elementContentReplaced");
		if (element != null && element.equals(input)) {
			System.out.println(filename + " elementContentReplaced");
		}

	}

	@Override
	public void elementDeleted(Object element) {
		if (element != null && element.equals(input)) {
			System.out.println(filename + " elementDeleted");
		}

	}

	@Override
	public void elementMoved(Object originalElement, Object movedElement) {
		if (originalElement != null && originalElement.equals(input)) {
			System.out.println(filename + " elementMoved");
		}

	}

	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {
		if (event.getDocument().equals(document)) {
			System.out.println(filename + " documentAboutToBeChanged");
		}
	}

	@Override
	public void documentChanged(DocumentEvent event) {
		if (event.getDocument().equals(document)) {
			System.out.println(filename + " documentChanged");
		}
	}

}
