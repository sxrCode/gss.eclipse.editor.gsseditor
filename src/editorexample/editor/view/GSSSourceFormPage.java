package editorexample.editor.view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class GSSSourceFormPage extends FormPage {

	private IDocument sourceDocument;

	public GSSSourceFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		toolkit.decorateFormHeading(form.getForm());
		form.setText("源码页");
		fillBody(managedForm, toolkit);
	}

	private void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		TableWrapLayout layout = createBodyLayout(false, 1);
		body.setLayout(layout);
		body.setBackground(new Color(body.getDisplay(), new RGB(251, 189, 8)));
		
		Composite left = toolkit.createComposite(body);
		left.setLayout(createBodyLayout(false, 1));
		left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		left.setBackground(new Color(left.getDisplay(), new RGB(0, 0, 225)));

		IFormPart sourcePart = new GSSSourceFormPart(sourceDocument, left, toolkit);
		managedForm.addPart(sourcePart);
	}

	private TableWrapLayout createBodyLayout(boolean makeColumnsEqualWidth, int numColumns) {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = 12;
		layout.bottomMargin = 12;
		layout.leftMargin = 6;
		layout.rightMargin = 6;

		layout.horizontalSpacing = 20;
		layout.verticalSpacing = 17;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	public void setSourceDocument(IDocument document) {
		sourceDocument = document;
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		System.out.println("GSSSourceFormPage dosave!");
	}
}
