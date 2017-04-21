package editorexample.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class GSSSourceFormPart extends SectionPart {

	private SourceViewer sourceViewer;

	/*
	 * public GSSSourceFormPart(Composite parent, FormToolkit toolkit, int
	 * style) { super(parent, toolkit, style); }
	 */

	public GSSSourceFormPart(IDocument document, Composite parent, FormToolkit toolkit, int style) {
		super(parent, toolkit, style);
		createClient(parent, toolkit, document);
	}

	private void createClient(Composite parent, FormToolkit toolkit, IDocument document) {
		
		int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
		Section section = getSection();//toolkit.createSection(parent, Section.TITLE_BAR);
		Composite wrap = toolkit.createComposite(section);
		sourceViewer = new SourceViewer(wrap, new VerticalRuler(12), styles);
		sourceViewer.setDocument(document);
	}

}
