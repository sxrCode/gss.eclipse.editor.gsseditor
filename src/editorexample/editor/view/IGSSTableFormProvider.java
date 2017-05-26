package editorexample.editor.view;

public interface IGSSTableFormProvider {

	public String getTableName(Object tableInfo);

	public String getNameComment(Object tableInfo);

	public Object[] getTableContent(Object tableInfo);
}
