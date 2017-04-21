package editorexample.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.ui.forms.editor.FormEditor;

public class TestEditor extends FormEditor {

	@Override
	protected void addPages() {
		
		IContentProvider contentProvider = new IContentProvider() {
		};
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

}
