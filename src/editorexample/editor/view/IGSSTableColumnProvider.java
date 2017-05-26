package editorexample.editor.view;

public interface IGSSTableColumnProvider {

	public Object getValue(Object element, String property);

	public boolean canModify(Object element, String property);
}
