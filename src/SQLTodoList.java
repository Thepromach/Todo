import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLTodoList implements TodoList{

	private ArrayList<Integer> index_list;
	private Connection conn;
	
	private PreparedStatement insert;
	private PreparedStatement select;
	private PreparedStatement update;
	private PreparedStatement delete;
	private PreparedStatement selectAll;
	private PreparedStatement getId;
	
	public SQLTodoList(String server, String username, String password)
	{
	    try {
	    	//System.out.println(server + ", '" + username + "', '" + password + "'");
	    	conn = DriverManager.getConnection(server, username, password);
			insert = conn.prepareStatement("INSERT INTO Todo (name, description, priority) VALUES (?,?,?);");
			select = conn.prepareStatement("SELECT * FROM Todo WHERE id=?;");
		    update = conn.prepareStatement("UPDATE Todo SET name=?, description=?, priority=? WHERE id=?;");
		    delete = conn.prepareStatement("DELETE FROM Todo WHERE id=?;");
		    selectAll = conn.prepareStatement("SELECT * FROM Todo;");
		    getId = conn.prepareStatement("SELECT LAST_INSERT_ID()");
		    
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//Prepares the insert query to add new client
	    
	    
	    index_list = new ArrayList<Integer>();
	    
	    //jdbc:mysql://localhost:3306/
	    //"jdbc:" + this.dbms + "://" + this.serverName + ":" + this.portNumber + "/"
	}
	
	@Override
	public void addTodo(Todo t) {
		
		try
		{
			insert.setString(1, t.getName());
			insert.setString(2, t.getDescription());
			insert.setInt(3, t.getPriority());
						
			insert.execute();
			
			
			ResultSet result = getId.executeQuery();
			result.next();
			int r = result.getInt("LAST_INSERT_ID()");
			this.index_list.add(r);
			
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
	}

	@Override
	public void deleteTodo(int index) {
		
		int id = this.index_list.get(index);
		try
		{
			delete.setInt(1, id);		
			delete.execute();
			
			this.index_list.remove(index);
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
	}

	@Override
	public void updateTodo(Todo t, int index) {
		int id = this.index_list.get(index);
		
		
		try
		{
			update.setString(1, t.getName());
			update.setString(2, t.getDescription());
			update.setInt(3, t.getPriority());
			update.setInt(4, id);
						
			update.execute();
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
	}

	@Override
	public Todo getTodo(int index) {
		int id = this.index_list.get(index);
		
		try {
			select.setInt(1, id);
			ResultSet result = select.executeQuery();
			if(result.next()){
				Todo t = new Todo(result.getString("name"), result.getString("description"), result.getInt("priority"));
				return t;
			}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Todo[] getArray() {
		ArrayList<Todo> list = new ArrayList<Todo>();
		
		try {
			ResultSet result = selectAll.executeQuery();
			
			while(result.next())
			{
				this.index_list.add(result.getInt("id"));
				Todo t = new Todo(result.getString("name"), result.getString("description"), result.getInt("priority"));
				list.add(t);
			}
			
			return list.toArray(new Todo[list.size()]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int getSize() {
		return this.index_list.size();
	}

	@Override
	public Object[][] getTodoTable() {
		
		Todo[] tt = this.getArray();
		Object[][] o = new Object[this.index_list.size()][3];
		
		for(int i = 0; i < tt.length; i++)
		{
			o[i][0] = tt[i].getName();
			o[i][1] = tt[i].getDescription();
			o[i][2] = tt[i].getPriority();
		}
		
		return o;
	}

}
