package editorexample.editor;

import org.eclipse.core.internal.resources.File;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IElementStateListener;

public class MyEditorListener implements IDocumentListener, IElementStateListener {

	private GSSDBEditor myEditor;

	public MyEditorListener(GSSDBEditor editor) {
		myEditor = editor;
	}

	@Override
	public void elementDirtyStateChanged(Object element, boolean isDirty) {
		System.out.println("elementDirtyStateChanged");
		myEditor.updateDirtyState(isDirty);
		System.out.println("element string: " + element.getClass().getName());
		if (element instanceof IEditorInput) {
			System.out.println("element is IEditorInput!\n");
		}
	}

	@Override
	public void elementContentAboutToBeReplaced(Object element) {
		System.out.println("elementContentAboutToBeReplaced");

	}

	@Override
	public void elementContentReplaced(Object element) {
		System.out.println("elementContentReplaced");

	}

	@Override
	public void elementDeleted(Object element) {
		System.out.println("elementDeleted");

	}

	@Override
	public void elementMoved(Object originalElement, Object movedElement) {
		System.out.println("elementMoved");

	}

	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {
		System.out.println("documentAboutToBeChanged");

	}

	@Override
	public void documentChanged(DocumentEvent event) {
		System.out.println("documentChanged");
	}

}
