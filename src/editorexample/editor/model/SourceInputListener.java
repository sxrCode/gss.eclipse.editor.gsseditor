package editorexample.editor.model;

import java.util.List;

public interface SourceInputListener {

	public void addTableInputContext(TableInputContext inputContext);

	public void changeContexts(List<TableInputContext> tableInputContexts);
}
