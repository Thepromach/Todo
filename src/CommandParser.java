
public class CommandParser {

	private TodoList tList;
	
	public CommandParser(TodoList list)
	{
		tList = list;
	}
	
	public void Parse(String[] args)
	{
		for(int i = 0; i < args.length; i++)
		{
			switch(args[i])
			{
				case "-h":
				case "-?":
				case "/?":
					System.out.println("Commands:");
					System.out.println("-o [filename/datasbase]");
					System.out.println("-a [name] [destriction] [priority] ");
					System.out.println("-d [name/index]");
					System.out.println("-p [name/index] [priority]");
					System.out.println("\nMore info on command write command and ?. Example: '-a ?'\n");
					
					
					break;
				case "-o":
					i++;
					String location = args[i];
					if(location.equals("?"))
					{
						System.out.println("-o [filename/database]");
						System.out.println(" filename can be absolute or relative. Example: '-o C:\\mytodolist.todo'");
						System.out.println(" database should be jdbc:mysql://servername:port/database. Example: '-o jdbc:mysql://localhost:3306/test'\n");
					}
					else if(location.toLowerCase().startsWith("jdbc:"))
					{
						this.tList = new SQLTodoList(location, "admin", "password");
					}
					else{
						this.tList = new LocalTodoList(location);
					}
					break;
				case "-a":
					i++;
					if(i < args.length && args[i].equals("?"))
					{
						System.out.println("-a [name] [destriction] [priority]");
						System.out.println(" name and destriction can't have spaces");
						System.out.println(" priority needs to be integer");
						System.out.println(" example: -a homework 30.4 1\n");
					}
					else if(this.tList != null)
					{
						
						int priority = 0;
						String name = null;
						String destriction = ""; 
						
						if(i < args.length && !args[i].startsWith("-"))
						{
							name = args[i];
							i++;
						}
						if(i < args.length && !args[i].startsWith("-"))
						{
							destriction = args[i];
							i++;
						}
						if(i < args.length && args[i].matches("^-?\\d+$"))
						{
							priority = Integer.parseInt(args[i]);
							
						}
						
						if(name != null)
						{
							tList.addTodo(new Todo(name, destriction, priority));
						}
						else{
							System.out.println("Name can't start with -");
						}
					}
					else
					{
						System.out.println("There is no connection to any todo file or database");
					}
					break;
				case "-d":
					i++;
					if(i < args.length && args[i].equals("?"))
					{
						System.out.println("-d [index]");
						System.out.println(" index must be integer");
						System.out.println(" example: -d 1\n");
					}
					else if(i < args.length && args[i].matches("^-?\\d+$"))
					{
						int index = Integer.parseInt(args[i]);
						if(tList != null)
						{
							tList.deleteTodo(index);
						}
					}
					break;
				case "-p":
					if(i+1 < args.length && args[i+1].equals("?"))
					{
						System.out.println("-p");
						System.out.println(" prints all todos");
					}
					else if(tList != null)
					{
						Todo[] t = tList.getArray();
						
						for(int j = 0; j < t.length; j++)
						{
							System.out.println(j + ": " +  t[j].getName() + ", " + t[j].getDescription());
						}
					}
					break;
				default:
					System.out.println("'"+ args[i] + "' is not recognized as command. To see all command write '-h'");
					break;
			}
		}
	}
}
