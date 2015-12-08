package com.example.componentexamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.Renderer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

@Theme("componentexamples")
public class ComponentexamplesUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8287644174586839906L;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ComponentexamplesUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
		});
		
		
		
		
		layout.addComponent(button);
		
		Label label = addLable();
		layout.addComponent(label);
		
		Panel panel=addTextWrapingPanel();
		layout.addComponent(panel);
		
		VerticalLayout styledLableLayout = addStyledLable();
		layout.addComponent(styledLableLayout);
		
		layout.addComponent(new Label("<h1>Data Binding Example</h1>",ContentMode.HTML));
		
		VerticalLayout 	addDataBinding = addDataBindingLayout();
		layout.addComponent(addDataBinding);
		
		VerticalLayout 	addLink = addLinks();
		layout.addComponent(addLink);
		
		VerticalLayout addTextAreas = addTextAreaExamples();
		layout.addComponent(addTextAreas);
		
		VerticalLayout addDateTime = addDateTimeExamples();
		layout.addComponent(addDateTime);
		
		VerticalLayout addComboSelect = addCheckBoxComboBoxExamples();
		layout.addComponent(addComboSelect);
		
		VerticalLayout addTablesAndTree = addTablesAndTreeExample();
		layout.addComponent(addTablesAndTree);
		
		VerticalLayout addGrid = addGridExample();
		layout.addComponent(addGrid);
		
		VerticalLayout addUpload = addUploadExample();
		layout.addComponent(addUpload);
		
	}
	
public VerticalLayout addUploadExample(){
		
		VerticalLayout layout=new VerticalLayout();
		
		
		
		PDFUploader receiver = new PDFUploader();
		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("Upload PDF", receiver);
		upload.setButtonCaption("Start Upload");
		upload.addSucceededListener(receiver);
		
		layout.addComponent(upload);
			
		return layout;
		
}
	
public VerticalLayout addGridExample(){
		
		VerticalLayout layout=new VerticalLayout();
		
		
		// Have some data
		Collection<Person> people = new ArrayList<Person>();
		
		for(int i=0;i<20;i++){
		people.add(new Person("Nicolaus Copernicus", 1543));
		people.add(new Person("Galileo Galilei", 1564));
		people.add(new Person("Johannes Kepler", 1571));
		}
		// Have a container of some type to contain the data
		BeanItemContainer<Person> container =
		new BeanItemContainer<Person>(Person.class, people);
		
		// Generate button caption column
				GeneratedPropertyContainer gpc =
				new GeneratedPropertyContainer(container);
				gpc.addGeneratedProperty("delete", new PropertyValueGenerator<String>() {
					@Override
					public String getValue(Item item, Object itemId,
					Object propertyId) {
					return "Delete"; // The caption
					}
					@Override
					public Class<String> getType() {
					return String.class;
					}
				});
				
				
		
		// Create a grid bound to the container
		Grid grid = new Grid(container);
		grid.setColumnOrder("name", "born");
		
		
		grid.addSelectionListener(listener -> {
			
			// Get selection from the selection model
			Object selected = ((SingleSelectionModel)
			grid.getSelectionModel()).getSelectedRow();
			if (selected != null)
			Notification.show("Selected " +
			grid.getContainerDataSource().getItem(selected)
			.getItemProperty("name"));
			else
			Notification.show("Nothing selected");
			
		});
		
		// Pre-select an item
		SingleSelectionModel selection =
		(SingleSelectionModel) grid.getSelectionModel();
		selection.select( // Select 3rd item
		grid.getContainerDataSource().getIdByIndex(2));
		
		
		Grid.Column bornCol = grid.getColumn("born");
		bornCol.setRenderer(new NumberRenderer("born in %d AD"));
		
		HeaderRow mainHeader = grid.getDefaultHeaderRow();
		mainHeader.getCell("name").setText("Inventor Name");
		mainHeader.getCell("born").setText("Born In");
		
		
		// Render a button that deletes the data row (item)
		//grid.getColumn("delete").setRenderer(new ButtonRenderer(e -> // Java 8
		//	grid.getContainerDataSource().removeItem(e.getItemId())));
		
		layout.addComponent(grid);
		
		return layout;
		
}
	
	
public VerticalLayout addTablesAndTreeExample(){
		
		VerticalLayout layout=new VerticalLayout();
		
		Table table = new Table("The Brightest Stars");
		// Define two columns for the built-in container
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Mag", Float.class, null);
		// Add a row the hard way
		Object newItemId = table.addItem();
		Item row1 = table.getItem(newItemId);
		row1.getItemProperty("Name").setValue("Sirius");
		row1.getItemProperty("Mag").setValue(-1.46f);
		// Add a few other rows using shorthand addItem()
		table.addItem(new Object[]{"Canopus", -0.72f}, 2);
		table.addItem(new Object[]{"Arcturus", -0.04f}, 3);
		table.addItem(new Object[]{"Alpha Centauri", -0.01f}, 4);
		// Show exactly the currently contained rows (items)
		table.setPageLength(table.size());
		
		layout.addComponent(table);
		
		
		// Create a table and add a style to allow setting the row height in theme.
		final Table table1 = new Table();
		table1.addStyleName("components-inside");
		/* Define the names and data types of columns.
		* The "default value" parameter is meaningless here. */
		table1.addContainerProperty("Sum", Label.class, null);
		table1.addContainerProperty("Is Transferred", CheckBox.class, null);
		table1.addContainerProperty("Comments", TextField.class, null);
		table1.addContainerProperty("Details", Button.class, null);
		/* Add a few items in the table. */
		for (int i=0; i<100; i++) {
		// Create the fields for the current table row
		Label sumField = new Label(String.format(
		"Sum is <b>$%04.2f</b><br/><i>(VAT incl.)</i>",
		new Object[] {new Double(Math.random()*1000)}),
		ContentMode.HTML);
		CheckBox transferredField = new CheckBox("is transferred");
		// Multiline text field. This required modifying the
		// height of the table row.
		TextField commentsField = new TextField();
		// The Table item identifier for the row.
		Integer itemId = new Integer(i);
		// Create a button and handle its click. A Button does not
		// know the item it is contained in, so we have to store the
		// item ID as user-defined data.
		Button detailsField = new Button("show details");
		detailsField.setData(itemId);
		detailsField.addClickListener(new Button.ClickListener() {
		public void buttonClick(ClickEvent event) {
		// Get the item identifier from the user-defined data.
		Integer iid = (Integer)event.getButton().getData();
		Notification.show("Link " +
		iid.intValue() + " clicked.");
		}
		});
		detailsField.addStyleName("link");
		// Create the table row.
		table1.addItem(new Object[] {sumField, transferredField,
		commentsField, detailsField},
		itemId);
		}
		// Show just three rows because they are so high.
		table1.setPageLength(3);
		
		layout.addComponent(table1);
		
		Table table2 = new Table("Table with Cell Styles");
		table2.addStyleName("checkerboard");
		// Add some columns in the table. In this example, the property
		// IDs of the container are integers so we can determine the
		// column number easily.
		table2.addContainerProperty("0", String.class, null, "", null, null);
		for (int i=0; i<8; i++)
		table2.addContainerProperty(""+(i+1), String.class, null,
		String.valueOf((char) (65+i)), null, null);
		// Add some items in the table.
		table2.addItem(new Object[]{
		"1", "R", "N", "B", "Q", "K", "B", "N", "R"}, new Integer(0));
		table2.addItem(new Object[]{
		"2", "P", "P", "P", "P", "P", "P", "P", "P"}, new Integer(1));
		for (int i=2; i<6; i++)
		table2.addItem(new Object[]{String.valueOf(i+1),
		"", "", "", "", "", "", "", ""}, new Integer(i));
		table2.addItem(new Object[]{
		"7", "P", "P", "P", "P", "P", "P", "P", "P"}, new Integer(6));
		table2.addItem(new Object[]{
		"8", "R", "N", "B", "Q", "K", "B", "N", "R"}, new Integer(7));
		table2.setPageLength(8);
		// Set cell style generator
		table2.setCellStyleGenerator(new Table.CellStyleGenerator() {
			@Override
			public String getStyle(Table source, Object itemId, Object propertyId) {
				// Row style setting, not relevant in this example.
				if (propertyId == null)
				return "green"; // Will not actually be visible
				int row = ((Integer)itemId).intValue();
				int col = Integer.parseInt((String)propertyId);
				// The first column.
				if (col == 0)
				return "rowheader";
				// Other cells.
				if ((row+col)%2 == 0)
				return "black";
				else
				return "white";
			}
		});
		
		layout.addComponent(table2);
		
		
		final Object[][] planets = new Object[][]{
			new Object[]{"Mercury"},
			new Object[]{"Venus"},
			new Object[]{"Earth", "The Moon"},
			new Object[]{"Mars", "Phobos", "Deimos"},
			new Object[]{"Jupiter", "Io", "Europa", "Ganymedes",
			"Callisto"},
			new Object[]{"Saturn", "Titan", "Tethys", "Dione",
			"Rhea", "Iapetus"},
			new Object[]{"Uranus", "Miranda", "Ariel", "Umbriel",
			"Titania", "Oberon"},
			new Object[]{"Neptune", "Triton", "Proteus", "Nereid",
			"Larissa"}};
			Tree tree = new Tree("The Planets and Major Moons");
			/* Add planets as root items in the tree. */
			for (int i=0; i<planets.length; i++) {
			String planet = (String) (planets[i][0]);
			tree.addItem(planet);
			if (planets[i].length == 1) {
			// The planet has no moons so make it a leaf.
			tree.setChildrenAllowed(planet, false);
			} else {
			// Add children (moons) under the planets.
			for (int j=1; j<planets[i].length; j++) {
			String moon = (String) planets[i][j];
			// Add the item as a regular item.
			tree.addItem(moon);
			// Set it to be a child.
			tree.setParent(moon, planet);
			// Make the moons look like leaves.
			tree.setChildrenAllowed(moon, false);
			}
			// Expand the subtree.
			tree.expandItemsRecursively(planet);
			}
			}
			layout.addComponent(tree);
			
			
			TreeTable ttable = new TreeTable("Item Table Tree");
			ttable.addContainerProperty("Item ID", String.class, null);
			ttable.addContainerProperty("Item Name", String.class, null);
			ttable.addContainerProperty("Item Price", Double.class, null);
			ttable.addContainerProperty("Item UPS", String.class, null);
			ttable.addContainerProperty("Item Qty", Integer.class, null);
			
			// Create the tree nodes and set the hierarchy
			ttable.addItem(new Object[]{"I001", null, null, null, null}, 0);
			ttable.addItem(new Object[]{"I001","555",5.0D, "G001", 42}, 1);
			ttable.addItem(new Object[]{"I001","555L",10.0D, "G002", 56}, 2);
			ttable.addItem(new Object[]{"I001","555XL",15.0D, "G003", 68}, 3);
			ttable.addItem(new Object[]{"I001","555XXL",20.0D, "G004", 72}, 4);
			ttable.setParent(1, 0);
			ttable.setParent(2, 0);
			ttable.setParent(3, 0);
			ttable.setParent(4, 0);
			
			ttable.addItem(new Object[]{"I002", null, null, null, null}, 5);
			ttable.addItem(new Object[]{"I001","Marlbro Regular",11.0D, "G005", 40}, 6);
			ttable.addItem(new Object[]{"I001","Marlbro Light",15.0D, "G006", 38}, 7);
			ttable.addItem(new Object[]{"I001","Marlbro White",14.0D, "G007", 59}, 8);
			ttable.addItem(new Object[]{"I001","Marlbro Clove",18.0D, "G008", 32}, 9);
			ttable.setParent(6, 5);
			ttable.setParent(7, 5);
			ttable.setParent(8, 5);
			ttable.setParent(9, 5);
			
			// Expand the tree
			for (Object itemId: ttable.getContainerDataSource()
			.getItemIds()) {
			ttable.setCollapsed(itemId, false);
			// As we're at it, also disallow children from
			// the current leaves
			if (! ttable.hasChildren(itemId))
			ttable.setChildrenAllowed(itemId, false);
			}
			
			layout.addComponent(ttable);
			
		return layout;
	}
	
	public Label addLable(){
		
		Label label = new Label("Labeling can be dangerous");
		return label;
	}
	
	
	public Panel addTextWrapingPanel(){
		// A container with a defined width.
		Panel panel = new Panel("Panel Containing a Label");
		panel.setWidth("300px");
		panel.setContent(
		new Label("This is a Label inside a Panel. There is " +
		"enough text in the label to make the text " +
		"wrap when it exceeds the width of the panel."));
		
		return panel;
	}
	
	public VerticalLayout addStyledLable(){
		
		VerticalLayout vLayout=new VerticalLayout();
		Label textLabel = new Label(
				"Text where formatting characters, such as \\n, " +
				"and HTML, such as <b>here</b>, are quoted.",
				ContentMode.TEXT);
				Label preLabel = new Label(
				"Preformatted text is shown in an HTML <pre> tag.\n" +
				"Formatting such as\n" +
				" * newlines\n" +
				" * whitespace\n" +
				"and such are preserved. HTML tags, \n"+
				"such as <b>bold</b>, are quoted.",
				ContentMode.PREFORMATTED);
				Label htmlLabel = new Label(
				"In HTML mode, all HTML formatting tags, such as \n" +
				"<ul>"+
				" <li><b>bold</b></li>"+
				" <li>itemized lists</li>"+
				" <li>etc.</li>"+
				"</ul> "+
				"are preserved. <a href='https://www.google.com'> Link to Google </a>",
				ContentMode.HTML);
				
				vLayout.addComponent(textLabel);
				vLayout.addComponent(preLabel);
				vLayout.addComponent(htmlLabel);
				return vLayout;
	}
	
	public VerticalLayout addDataBindingLayout(){
		
				VerticalLayout vLayout=new VerticalLayout();
				// Some property
				ObjectProperty<String> property =
				new ObjectProperty<String>("some value");
				// Label that is bound to the property
				TextField label = new TextField();
				

				TextField editor = new TextField();
				editor.setPropertyDataSource(label);
				editor.setImmediate(true);
				
				vLayout.addComponent(label);
				vLayout.addComponent(editor);
				return vLayout;
	}
	
	public VerticalLayout addLinks(){
		
		VerticalLayout layout=new VerticalLayout();
		
		Link link = new Link("To appease both literal and visual",
		new ExternalResource("http://vaadin.com/"));
		
		link.setTargetName("_blank");
		link.setTargetBorder(Link.TARGET_BORDER_MINIMAL);
		link.setTargetHeight(300);
		link.setTargetWidth(400);
		
		layout.addComponent(link);
		
		return layout;
	}
	
public VerticalLayout addTextAreaExamples(){
		
		VerticalLayout layout=new VerticalLayout();
		
		// Create the area
		TextArea area = new TextArea("Big Area");
		// Put some content in it
		area.setValue("A row\n"+
		"Another row\n"+
		"Yet another row");
		
		PasswordField tf = new PasswordField("Keep it secret");
		
		layout.addComponent(tf);
		
		layout.addComponent(area);
		
		// Create a rich text area
		final RichTextArea rtarea = new RichTextArea();
		rtarea.setCaption("My Rich Text Area");
		// Set initial content as HTML
		rtarea.setValue("<h1>Hello</h1>\n" +
		"<p>This rich text area contains some text.</p>");
		
		layout.addComponent(rtarea);
		
		return layout;
	}

public VerticalLayout addDateTimeExamples(){
	
	VerticalLayout layout=new VerticalLayout();
	
	// Create a DateField with the default style
	DateField date = new DateField();
	// Set the date and time to present
	date.setValue(new Date());
	
	// Display only year, month, and day in ISO format
	date.setDateFormat("yyyy-MM-dd");
	
	// Create a DateField with the default style
		DateField time = new DateField();
		// Set the date and time to present
		time.setValue(new Date());
		
		// Display only year, month, and day in ISO format
		time.setDateFormat("hh:mm");
		
		time.setResolution(Resolution.MINUTE);
	
	layout.addComponent(date);
	
	layout.addComponent(time);
	
	return layout;
}

public VerticalLayout addCheckBoxComboBoxExamples(){
	
	VerticalLayout layout=new VerticalLayout();
	
	CheckBox checkbox1 = new CheckBox("Box with no Check");
	CheckBox checkbox2 = new CheckBox("Box with a Check");
	checkbox2.setValue(true);
	checkbox1.addValueChangeListener(event -> // Java 8
	checkbox2.setValue(! checkbox1.getValue()));
	checkbox2.addValueChangeListener(event -> // Java 8
	checkbox1.setValue(! checkbox2.getValue()));
	
	ComboBox cb = new ComboBox("Select Planet");
	cb.addItems("Mercury", "Venus", "Earth", "Mars");
	cb.setFilteringMode(FilteringMode.CONTAINS);
	
	// Create the selection component
	ListSelect select = new ListSelect("The List");
	// Add some items (here by the item ID as the caption)
	select.addItems("Mercury", "Venus", "Earth", "Mars");
	select.setNullSelectionAllowed(false);
	// Show 5 items and a scrollbar if there are more
	select.setRows(5);
	
	// A single-select radio button group
	OptionGroup single = new OptionGroup("Single Selection");
	single.addItems("Single", "Sola", "Yksi");
	// A multi-select check box group
	OptionGroup multi = new OptionGroup("Multiple Selection");
	multi.setMultiSelect(true);
	multi.addItems("Many", "Muchos", "Monta");
	
	// Have an option group with some items
	OptionGroup group = new OptionGroup("My Disabled Group");
	group.addItems("One", "Two", "Three");
	// Disable one item by its item ID
	group.setItemEnabled("Two", false);
	
	TwinColSelect select1 = new TwinColSelect("Select Targets");
	// Put some items in the select
	select1.addItems("Mercury", "Venus", "Earth", "Mars",
	"Jupiter", "Saturn", "Uranus", "Neptune");
	// Few items, so we can set rows to match item count
	select1.setRows(select1.size());
	// Preselect a few items by creating a set
	select1.setValue(new HashSet<String>(
	Arrays.asList("Venus", "Earth", "Mars")));
	// Handle value changes
	select1.addValueChangeListener(event -> // Java 8
	layout.addComponent(new Label("Selected: " +
	event.getProperty().getValue())));
	
	
	layout.addComponent(checkbox1);
	layout.addComponent(checkbox2);
	layout.addComponent(cb);
	layout.addComponent(select);
	layout.addComponent(single);
	layout.addComponent(multi);
	layout.addComponent(group);
	layout.addComponent(select1);
	return layout;
}
	
	
	
	

}