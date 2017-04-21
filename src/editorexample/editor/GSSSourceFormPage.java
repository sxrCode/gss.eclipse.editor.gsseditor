package editorexample.editor;

import org.eclipse.jface.text.IDocument;
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
		TableWrapLayout layout = createBodyLayout();
		body.setLayout(layout);
		Composite left = toolkit.createComposite(body);
		left.setLayout(createTableWrapLayout());
		left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		IFormPart sourcePart = new GSSSourceFormPart(sourceDocument, body, toolkit, body.getStyle());
		managedForm.addPart(sourcePart);
	}

	private TableWrapLayout createBodyLayout() {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = 12;
		layout.bottomMargin = 12;
		layout.leftMargin = 6;
		layout.rightMargin = 6;

		layout.horizontalSpacing = 20;
		layout.verticalSpacing = 17;

		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 1;
		return layout;
	}

	private TableWrapLayout createTableWrapLayout() {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = 0;
		layout.bottomMargin = 0;
		layout.leftMargin = 0;
		layout.rightMargin = 0;

		layout.horizontalSpacing = 20;
		layout.verticalSpacing = 17;

		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 1;

		return layout;
	}

	public void setSourceDocument(IDocument document) {
		sourceDocument = document;
	}
}
