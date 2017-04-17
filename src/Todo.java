
public class Todo {
	
	//TODO: Make sure that name and description length are okay
	
	
	private String name;
	private String description;
	private int priority;
	
	public Todo()
	{
		this.priority = 0;
		this.name = "";
		this.description = "";
	}
	
	public Todo(String name, String description, int priority)
	{
		this.priority = priority;
		this.name = name;
		this.description = description;
	}
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
