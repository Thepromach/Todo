import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LocalTodoList implements TodoList{

	private String filename;
	private ArrayList<Todo> list;
	public LocalTodoList(String filename)
	{
		this.filename = filename;
		this.list = new ArrayList<Todo>();
		
		Path p = Paths.get(filename);
		if(Files.exists(p))
		{
			readFile();
		}
	}
	
	
	@Override
	public void addTodo(Todo t) {
		list.add(t);
		writeToFile();
		
	}
	
	@Override
	public void deleteTodo(int index) {
		list.remove(list.get(index));
		
		writeToFile();
		
	}

	@Override
	public void updateTodo(Todo t, int index) {
		list.set(index, t);
		
		writeToFile();
	}

	@Override
	public Todo getTodo(int index) {
		return list.get(index);
	}

	@Override
	public Todo[] getArray() {
		return list.toArray(new Todo[list.size()]);
	}
	
	@Override
	public Object[][] getTodoTable()
	{
		Object[][] o = new Object[list.size()][3];
		
		for(int i = 0; i < list.size(); i++)
		{
			o[i][0] = list.get(i).getName();
			o[i][1] = list.get(i).getDescription();
			o[i][2] = list.get(i).getPriority();
		}
		return o;
	}
	
	
	private void readFile()
	{

		try {
			@SuppressWarnings("resource")
			DataInputStream is = new DataInputStream(new FileInputStream(filename));
			int size = is.readInt();
			
			for(int i = 0; i < size; i++)
			{
				Todo t = new Todo();
				t.setPriority(is.readInt());
				
				int name_size = is.readInt();
				String name = new String();
				for(int j = 0; j < name_size; j++)
				{
					name += (char)is.readByte();
				}
				t.setName(name);
				
				int description_size = is.readInt();
				String description = new String();
				for(int j = 0; j < description_size; j++)
				{
					description += (char)is.readByte();
				}
				t.setDescription(description);
				
				list.add(t);
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void writeToFile()
	{
		try {
			@SuppressWarnings("resource")
			DataOutputStream os = new DataOutputStream(new FileOutputStream(filename));
			os.writeInt(list.size());
			
			for(Todo t : list)
			{
				os.writeInt(t.getPriority());
				
				os.writeInt(t.getName().length());
				os.writeBytes(t.getName());

				os.writeInt(t.getDescription().length());
				os.writeBytes(t.getDescription());				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public int getSize() {
		
		return list.size();
	}
}

