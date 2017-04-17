
public class Main {

	public static void main(String[] args){
		
		Settings s = new Settings();
		TodoList t = s.loadSettings();
		
		if(args.length > 0)
		{
			CommandParser c = new CommandParser(t);
			c.Parse(args);
		}
		else
		{
			Window w = new Window(t);
			w.run();
		}
	}
}
