
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class Window extends JFrame implements TableModelListener {

	private static final long serialVersionUID = 1L;
	
	private TodoList tList;
	private JTable jt;
	private JPanel jp;
	private JScrollPane jsp;

	public Window(TodoList list)
	{
		this.setTitle("Todo");
		this.setSize(1280, 720);
		
		if(list != null)
			this.tList = list;
		else
			this.tList = new LocalTodoList("local.todo");
			
		
		jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
		
		String[] names = {"Name", "Description", "Priority"};
		
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(names);
		
		for(Object[] o : this.tList.getTodoTable())
		{
			model.addRow(o);
		}
		int size = this.tList.getSize();
		
		Object[] row = new Object[size];
		for(int i = 0; i < row.length; i++)
		{
			row[i] = "Remove";
		}
		model.addColumn("Remove", row);
		
		
		jt = new JTable(model);
		jt.getColumn("Remove").setCellRenderer(new ButtonRenderer());
		jt.getColumn("Remove").setCellEditor(new ButtonCellEditor(this.tList));
		
		
		jt.getModel().addTableModelListener(this);
		
		JButton button = new JButton("add Todo");
		button.setPreferredSize(new Dimension(100, 100));
		button.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                tList.addTodo(new Todo());
                DefaultTableModel t = (DefaultTableModel) jt.getModel();
                t.addRow(new Object[]{"", "", 0, "Remove"});
            }
		});
		
		jsp = new JScrollPane(jt);
		jp.add(jsp);
		jp.add(button);
		
		this.add(jp);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public void tableChanged(TableModelEvent e) {

		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        //String columnName = model.getColumnName(column);
        if(column >= 0 && column < 3)
        {
        	Object data = model.getValueAt(row, column);
        	
	        Todo t = tList.getTodo(row);
	        
	        switch(column)
	        {
	        	case 0:
	        		t.setName(data.toString());
	        		break;
	        	case 1:
	        		t.setDescription(data.toString());
	        		break;
	        	case 2:
	        		t.setPriority(Integer.parseInt((String) data));
	        		break;
	        }
	        
	        tList.updateTodo(t, row);
        }
    }
	
	public void run() {
		
		this.setVisible(true);
		
	}
	private class ButtonRenderer extends JButton implements TableCellRenderer {

		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ButtonRenderer() {
		    setOpaque(true);
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    if (isSelected) {
		      setForeground(table.getSelectionForeground());
		      setBackground(table.getSelectionBackground());
		    } else {
		      setForeground(table.getForeground());
		      setBackground(UIManager.getColor("Button.background"));
		    }
		    setText((value == null) ? "" : value.toString());
		    return this;
		  }
	}
	 private static class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = 1L;
	        private JButton editor;
	        private Object value;
	        private int row;
	        private JTable table;

	        public ButtonCellEditor(TodoList list) {
	            editor = new JButton();
	            editor.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    if (table != null) {
	                        fireEditingStopped();
	                        TableModel model = table.getModel();
	                        if (model instanceof DefaultTableModel) {
	                            ((DefaultTableModel) model).removeRow(row);
	                            list.deleteTodo(row);
	                        }
	                    }
	                }
	            });
	        }

	        @Override
	        public boolean isCellEditable(EventObject e) {
	            return true;
	        }

	        @Override
	        public Object getCellEditorValue() {
	            return value;
	        }

	        @Override
	        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	            this.table = table;
	            this.row = row;
	            this.value = value;
	            if (isSelected) {
	                editor.setForeground(table.getSelectionForeground());
	                editor.setBackground(table.getSelectionBackground());
	            } else {
	                editor.setForeground(table.getForeground());
	                editor.setBackground(UIManager.getColor("Button.background"));
	            }
	            return editor;
	        }
	    }
}




