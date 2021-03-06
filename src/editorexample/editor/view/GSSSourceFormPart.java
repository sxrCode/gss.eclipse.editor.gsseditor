package editorexample.editor.view;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class GSSSourceFormPart extends SectionPart {

	private SourceViewer sourceViewer;

	public GSSSourceFormPart(IDocument document, Composite parent, FormToolkit toolkit) {
		super(parent, toolkit, SWT.FULL_SELECTION);
		createClient(parent, toolkit, document);
	}

	private void createClient(Composite parent, FormToolkit toolkit, IDocument document) {

		Section section = getSection();
		section.setText("source section");
		section.setLayout(createClearTableWrapLayout(false, 1));
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		section.setBackground(new Color(section.getDisplay(), new RGB(0, 225, 0)));

		Composite wrap = toolkit.createComposite(section);
		wrap.setBackground(new Color(wrap.getDisplay(), new RGB(225, 0, 0)));
		wrap.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		wrap.setLayout(createSectionClientTableWrapLayout(false, 1));
		section.setClient(wrap);

		int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION;
		sourceViewer = new SourceViewer(wrap, new VerticalRuler(12), styles);
		sourceViewer.getControl().setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		sourceViewer.setDocument(document);
	}

	private TableWrapLayout createClearTableWrapLayout(boolean makeColumnsEqualWidth, int numColumns) {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = 2;
		layout.bottomMargin = 2;
		layout.leftMargin = 2;
		layout.rightMargin = 2;

		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	private TableWrapLayout createSectionClientTableWrapLayout(boolean makeColumnsEqualWidth, int numColumns) {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = 5;
		layout.bottomMargin = 5;
		layout.leftMargin = 2;
		layout.rightMargin = 2;

		layout.horizontalSpacing = 5;
		layout.verticalSpacing = 5;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}
	

}
