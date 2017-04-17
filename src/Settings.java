import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {

	
	
	public Settings()
	{
		
	}
	
	
	public TodoList loadSettings()
	{
		String target = "";
		String user = "";
		String password = "";
		
		Path p = Paths.get("settings.ini");
		
		if(Files.exists(p))
		{
			try {
				@SuppressWarnings("resource")
				BufferedReader in = new BufferedReader(new FileReader("settings.ini"));
				String line;
				while((line = in.readLine()) != null)
				{
					if(line.startsWith("#"))continue;
					String[] values = line.split("=");
					
					if(values[0].toUpperCase().matches("TARGET"))
					{
						
						target = values[1];
						
					}
					else if(values[0].toUpperCase().matches("USER"))
					{
						user = values[1];
					}
					else if(values[0].toUpperCase().matches("PASSWORD"))
					{
						password = values[1];
					}
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(target != null)
		{
			if(target.startsWith("jdbc:"))
			{
				return new SQLTodoList(target, user, password);
			}
			else
			{
				return new LocalTodoList(target);
			}
		}
		
		
		return null;
	}
}
