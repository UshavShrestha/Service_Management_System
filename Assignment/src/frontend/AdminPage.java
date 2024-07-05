package frontend;

import java.time.LocalDate;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import backend.DatabaseControl;
import backend.Service;
import backend.Staff;
import backend.Appointment;
import backend.Customer;

public class AdminPage extends Application{
	DatabaseControl database = new DatabaseControl();
	Register reg = new Register();
	GridPane gridPane = new GridPane();
	
	private boolean validateFields(Object... fields) {
	    for (Object field : fields) {
	        if (field instanceof TextField) {
	            TextField textField = (TextField) field;
	            if (textField.getText().isEmpty()) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Error");
	                alert.setHeaderText(null);
	                alert.setContentText("Please fill in all fields.");
	                alert.showAndWait();
	                return false;
	            }
	        } else if (field instanceof ComboBox) {
	            ComboBox<?> comboBox = (ComboBox<?>) field;
	            if (comboBox.getValue() == null || comboBox.getValue().toString().isEmpty()) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Error");
	                alert.setHeaderText(null);
	                alert.setContentText("Please select an option from all dropdowns.");
	                alert.showAndWait();
	                return false;
	            }
	        }
	    }
	    return true;
	}
	@Override
	public void start(Stage adminStage) throws Exception {
		homepage();
	    
	    String buttonStyle = "-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 8px 16px; " +
                "-fx-border-radius: 5px; " +
                "-fx-cursor: hand;";
	    
	    VBox sideMenu = new VBox();
        sideMenu.setPrefWidth(200); 
        sideMenu.setSpacing(10);
        sideMenu.setPadding(new Insets(10));
        sideMenu.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        
        ImageView imageView = new ImageView(new Image("file:logo.png"));
        imageView.setFitHeight( 100);
        imageView.setFitWidth( 100);
        imageView.setStyle(buttonStyle);
        imageView.setOnMouseClicked(e -> {
            System.out.println("Image clicked!");
            gridPane.getChildren().clear();
            homepage();
        });
		
        Button appointmentButton = new Button("Appointments");
        appointmentButton.setStyle(buttonStyle);
        appointmentButton.setOnMouseEntered(e -> appointmentButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        appointmentButton.setOnMouseExited(e -> appointmentButton.setStyle(buttonStyle));
        appointmentButton.setOnAction(e->{
        	gridPane.getChildren().clear();
        	showAppointments();
        });

        Button customerlistButton = new Button("Customer List");
        customerlistButton.setOnAction(e -> {
        	gridPane.getChildren().clear();
        	showCustomerList();
        	});
        customerlistButton.setStyle(buttonStyle);
        customerlistButton.setOnMouseEntered(e -> customerlistButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        customerlistButton.setOnMouseExited(e -> customerlistButton.setStyle(buttonStyle));

        Button stafflistButton = new Button("Staff List");
        stafflistButton.setOnAction(e->{
        	gridPane.getChildren().clear();
        	showstafflist();
     
        });
        stafflistButton.setStyle(buttonStyle);
        stafflistButton.setOnMouseEntered(e -> stafflistButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        stafflistButton.setOnMouseExited(e -> stafflistButton.setStyle(buttonStyle));
        
        Button servicelistButton = new Button("Service List");
        servicelistButton.setOnAction(e->{
        	gridPane.getChildren().clear();
        	showServices();
        
        });
        servicelistButton.setStyle(buttonStyle);
        servicelistButton.setOnMouseEntered(e -> servicelistButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        servicelistButton.setOnMouseExited(e -> servicelistButton.setStyle(buttonStyle));
        
        Button serviceaddButton = new Button("Add New Service");
        serviceaddButton.setOnAction(e->{
        	gridPane.getChildren().clear();
        	addservice();
        
        });
        serviceaddButton.setStyle(buttonStyle);
        serviceaddButton.setOnMouseEntered(e -> serviceaddButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        serviceaddButton.setOnMouseExited(e -> serviceaddButton.setStyle(buttonStyle));
                
        appointmentButton.setPrefSize(180, 40);
        customerlistButton.setPrefSize(180, 40);
        stafflistButton.setPrefSize(180, 40);
        servicelistButton.setPrefSize(180, 40);
        serviceaddButton.setPrefSize(180, 40);

        sideMenu.getChildren().addAll(imageView, appointmentButton, customerlistButton, stafflistButton, servicelistButton, serviceaddButton);
        
        VBox bottomContainer = new VBox();
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.setAlignment(Pos.BOTTOM_LEFT);
        bottomContainer.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e->{
        	adminStage.close();
        	reg.loginpage();
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> adminStage.close());
        
        logoutButton.setPrefSize(180, 40);
        exitButton.setPrefSize(180, 40);

        bottomContainer.getChildren().addAll(logoutButton, exitButton);
	    
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(bottomContainer);
        borderPane.setLeft(sideMenu);
        borderPane.setCenter(gridPane);
    
		Scene scene = new Scene(borderPane,1000,600);
	    adminStage.setTitle("Admin Page");
	    adminStage.setScene(scene); 
	    Image icon = new Image("file:logo.png");
	    adminStage.getIcons().add(icon);
		
		adminStage.show();
	}
	
	public void homepage() {
		gridPane.setMinSize(400, 200);   
	    gridPane.setPadding(new Insets(10, 10, 10, 10));
	    gridPane.setVgap(2);
	    gridPane.setHgap(2);
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
		gridPane.setBackground(background);
		
		Label welcomeLabel = new Label("Welcome to Service Management System ");
		Label userLabel = new Label("Admin");
	    welcomeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20)); 
	    userLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
	    userLabel.setStyle("-fx-text-fill: red;");
	    GridPane.setConstraints(welcomeLabel, 0, 0); 
	    GridPane.setConstraints(userLabel, 1, 0);  

	    gridPane.getChildren().addAll(welcomeLabel,userLabel);
	    
	}	
	public void addservice() {	
	    gridPane.setMinSize(400, 200);   
	    gridPane.setPadding(new Insets(10, 10, 10, 10));
	    gridPane.setVgap(10);
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    Label nameLabel = new Label("Service Name:");
	    TextField nameField = new TextField();

	    Label costLabel = new Label("Cost:");
	    TextField costField = new TextField();

	    Label durationLabel = new Label("Duration:");
	    TextField durationField = new TextField();

	    Label descriptionLabel = new Label("Description:");
	    TextArea descriptionArea = new TextArea();
	    
	    Button addButton = new Button("Add");
	    addButton.setOnAction(e -> {
	    	if (validateFields(nameField, costField, durationField, descriptionArea)) {
            	
                String serviceNname = nameField.getText();
                String cost = costField.getText();
                String duration = durationField.getText();
                String description = descriptionArea.getText();
                
                DatabaseControl.insertService(serviceNname, description, cost, duration);
				nameField.clear();
				costField.clear();
				durationField.clear();
				descriptionArea.clear();
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Successful");
				alert.setHeaderText(null);
				alert.setContentText("You have successfully added new service!");
				alert.showAndWait();
	    }});
	    addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");

	    gridPane.add(nameLabel, 0, 0);
	    gridPane.add(nameField, 1, 0);
	    gridPane.add(costLabel, 0, 1);
	    gridPane.add(costField, 1, 1);
	    gridPane.add(durationLabel, 0, 2);
	    gridPane.add(durationField, 1, 2);
	    gridPane.add(descriptionLabel, 0, 3);
	    gridPane.add(descriptionArea, 1, 3);
	    gridPane.add(addButton, 1, 4);

	    GridPane.setColumnSpan(descriptionArea, 2);

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
		gridPane.setBackground(background);
		
		nameField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            costField.requestFocus();
	        }
	    });
	    
	    costField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            durationField.requestFocus();
	        }
	    });
	    durationField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            descriptionArea.requestFocus();
	        }
	    });
	}
	
	public void showstafflist() {
	    gridPane.setMinSize(400, 200);
	    gridPane.setPadding(new Insets(10));
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);

	    TableView<Staff> tableView = new TableView<>();
	    tableView.setMinWidth(400);
	    tableView.setMinHeight(200);

	    TableColumn<Staff, Integer> staffCodeColumn = new TableColumn<>("Staff Code");
	    staffCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code_staff"));
	    staffCodeColumn.setPrefWidth(100);

	    TableColumn<Staff, String> nameColumn = new TableColumn<>("Name");
	    nameColumn.setCellValueFactory(new PropertyValueFactory<>("full_name"));
	    nameColumn.setPrefWidth(150);

	    TableColumn<Staff, String> phoneColumn = new TableColumn<>("Phone");
	    phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone_no"));
	    phoneColumn.setPrefWidth(100);

	    TableColumn<Staff, String> emailColumn = new TableColumn<>("Email");
	    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
	    emailColumn.setPrefWidth(200);

	    TableColumn<Staff, String> serviceColumn = new TableColumn<>("Role");
	    serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service_name"));
	    serviceColumn.setPrefWidth(150);

	    tableView.getColumns().addAll(staffCodeColumn, nameColumn, phoneColumn, emailColumn, serviceColumn);
	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	    List<Staff> staffList = database.retrieveStaff();
	    ObservableList<Staff> staffData = FXCollections.observableArrayList(staffList);
	    tableView.setItems(staffData);

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setContent(tableView);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	    gridPane.getChildren().add(scrollPane);
	}

	public void showAppointments() {
	    gridPane.setMinSize(400, 200);
	    gridPane.setPadding(new Insets(10));
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);

	    TableView<Appointment> appointmentTableView = new TableView<>();
	    appointmentTableView.setMinWidth(800);
	    appointmentTableView.setMinHeight(400);

	    TableColumn<Appointment, Integer> bookingCodeColumn = new TableColumn<>("Booking Code");
	    bookingCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code_booking"));
	    bookingCodeColumn.setPrefWidth(90);

	    TableColumn<Appointment, String> serviceNameColumn = new TableColumn<>("Service Name");
	    serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("service_name"));
	    serviceNameColumn.setPrefWidth(150);

	    TableColumn<Appointment, String> staffNameColumn = new TableColumn<>("Staff Name");
	    staffNameColumn.setCellValueFactory(new PropertyValueFactory<>("staffName"));
	    staffNameColumn.setPrefWidth(150);

	    TableColumn<Appointment, LocalDate> bookingDateColumn = new TableColumn<>("Booking Date");
	    bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("booking_date"));
	    bookingDateColumn.setPrefWidth(150);

	    TableColumn<Appointment, String> statusColumn = new TableColumn<>("Status");
	    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
	    statusColumn.setPrefWidth(100);

	    TableColumn<Appointment, String> customerNameColumn = new TableColumn<>("Customer Name");
	    customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
	    customerNameColumn.setPrefWidth(150);

	    appointmentTableView.getColumns().addAll(bookingCodeColumn, customerNameColumn, serviceNameColumn, staffNameColumn, bookingDateColumn, statusColumn);

	    List<Appointment> appointmentsList = database.appointmentListForAdmin();
		ObservableList<Appointment> appointmentsData = FXCollections.observableArrayList(appointmentsList);
		appointmentTableView.setItems(appointmentsData);

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setContent(appointmentTableView);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	    gridPane.add(scrollPane, 0, 0);
	    Button rejectButton = new Button("Reject");
	    rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-border-radius: 5px; -fx-cursor: hand;");
	    rejectButton.setOnMouseEntered(event -> rejectButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-border-radius: 5px; -fx-cursor: hand;"));
	    rejectButton.setOnMouseExited(event -> rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-border-radius: 5px; -fx-cursor: hand;"));
	    rejectButton.setOnAction(event -> {
	        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
	        if (selectedAppointment != null) {
	            boolean success = database.rejectAppointment(selectedAppointment.getCode_booking());
	            if (success) {
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Rejection Success");
	                alert.setHeaderText(null);
	                alert.setContentText("Appointment rejected successfully.");
	                alert.showAndWait();
	            } else {
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Rejection Failed");
	                alert.setHeaderText(null);
	                alert.setContentText("Failed to reject appointment.");
	                alert.showAndWait();
	            }
	            appointmentTableView.refresh();
	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Selection Error");
	            alert.setHeaderText(null);
	            alert.setContentText("Please select an appointment.");
	            alert.showAndWait();
	        }
            appointmentTableView.refresh();
	    });
	    
	    ComboBox<String> staffComboBox = new ComboBox<>();
	    staffComboBox.setPromptText("Select Staff");
        staffComboBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-color: black;");

	    appointmentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            String selectedStaffRole = newSelection.getService_name();
	            List<Staff> staffWithSameRole = database.getStaffByRole(selectedStaffRole);
	            staffComboBox.getItems().clear();
	            for (Staff staff : staffWithSameRole) {
	                staffComboBox.getItems().add(staff.getFull_name());
	            }
	        }
	    });

	    Button appointButton = new Button("Appoint");
	    appointButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-border-radius: 5px; -fx-cursor: hand;");
	    appointButton.setOnMouseEntered(event -> appointButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-border-radius: 5px; -fx-cursor: hand;"));
	    appointButton.setOnMouseExited(event -> appointButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-border-radius: 5px; -fx-cursor: hand;"));
	    appointButton.setOnAction(event -> {
	        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
	        String selectedStaff = staffComboBox.getValue();
	        if (selectedAppointment != null && selectedStaff != null) {
	            boolean success = database.appointStaffForMember(selectedAppointment.getCode_booking(), selectedStaff);
	            if (success) {
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Appointment Success");
	                alert.setHeaderText(null);
	                alert.setContentText("Staff appointed successfully.");
	                alert.showAndWait();
	            } else {
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Appointment Failed");
	                alert.setHeaderText(null);
	                alert.setContentText("Failed to appoint staff.");
	                alert.showAndWait();
	            }
	            appointmentTableView.refresh();

	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Selection Error");
	            alert.setHeaderText(null);
	            alert.setContentText("Please select an appointment and a staff member.");
	            alert.showAndWait();
	        }
	    });
	    HBox buttonBox = new HBox(10);
	    buttonBox.setAlignment(Pos.CENTER);
	    buttonBox.getChildren().addAll(staffComboBox, appointButton, rejectButton);
	    gridPane.add(buttonBox, 0, 1);
	}

	public void showCustomerList() {
	    gridPane.setMinSize(400, 200);
	    gridPane.setPadding(new Insets(10));
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);

	    TableView<Customer> customerTableView = new TableView<>();
	    customerTableView.setMinWidth(600);
	    customerTableView.setMinHeight(400);

	    TableColumn<Customer, Integer> codeColumn = new TableColumn<>("Customer Code");
	    TableColumn<Customer, String> nameColumn = new TableColumn<>("Full Name");
	    TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
	    TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone No");
	    TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");

	    codeColumn.setPrefWidth(100);
	    nameColumn.setPrefWidth(150);
	    addressColumn.setPrefWidth(150);
	    phoneColumn.setPrefWidth(100);
	    emailColumn.setPrefWidth(200);

	    customerTableView.getColumns().addAll(codeColumn, nameColumn, addressColumn, phoneColumn, emailColumn);

	    ObservableList<Customer> customerData = FXCollections.observableArrayList();

	    customerData.addAll(database.retrieveCustomers());

	    codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty().asObject());
	    nameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
	    addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
	    phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNoProperty());
	    emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

	    customerTableView.setItems(customerData);

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setContent(customerTableView);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	    gridPane.getChildren().add(scrollPane);
	}

	public void showServices() {
	    gridPane.setPadding(new Insets(10));
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);

	    TableView<Service> tableView = new TableView<>(); 

	    TableColumn<Service, Integer> serviceCodeColumn = new TableColumn<>("Service Code");
	    serviceCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code_ser"));
	    serviceCodeColumn.setPrefWidth(100);

	    TableColumn<Service, String> nameColumn = new TableColumn<>("Service Name");
	    nameColumn.setCellValueFactory(new PropertyValueFactory<>("service_name"));
	    nameColumn.setPrefWidth(150); 

	    TableColumn<Service, String> descriptionColumn = new TableColumn<>("Description");
	    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
	    descriptionColumn.setPrefWidth(250);

	    TableColumn<Service, String> costColumn = new TableColumn<>("Cost");
	    costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
	    costColumn.setPrefWidth(100);

	    TableColumn<Service, String> durationColumn = new TableColumn<>("Duration");
	    durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
	    durationColumn.setPrefWidth(100);

	    tableView.getColumns().addAll(serviceCodeColumn, nameColumn, descriptionColumn, costColumn, durationColumn);

	    List<Service> serviceList = database.showServices(); 
	    ObservableList<Service> serviceData = FXCollections.observableArrayList(serviceList);

	    tableView.setItems(serviceData);

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setContent(tableView);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	    gridPane.getChildren().add(scrollPane);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
