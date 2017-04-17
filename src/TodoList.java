
public interface TodoList {

	public void addTodo(Todo t);
	public void deleteTodo(int index);
	public void updateTodo(Todo t, int index);
	public Todo getTodo(int index);
	public Todo[] getArray();
	public int getSize();
	public Object[][] getTodoTable();
	
}
