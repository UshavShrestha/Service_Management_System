package frontend;

import backend.Staff;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

import java.time.LocalDate;
import java.util.List;

import backend.Appointment;
import backend.DatabaseControl;

public class StaffPage extends Application{
	DatabaseControl database = new DatabaseControl();
	Register reg = new Register();
	GridPane gridPane = new GridPane();
    Staff loggedInStaff;
    
    public StaffPage(Staff loggedInStaff) {
        this.loggedInStaff = loggedInStaff;
    }
	@Override
	public void start(Stage staffStage) throws Exception {
		homepage();
        if (loggedInStaff == null) {
            System.out.println("Login failed. Please check your credentials.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Logged in staff is null. Please log in again.");
            alert.showAndWait();
            staffStage.close();
            reg.loginpage();
            return; 
        }
    
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
		
        Button orderButton = new Button("Ongoing Orders");
        orderButton.setOnAction(e -> {
        	gridPane.getChildren().clear();
        	displayOrder(loggedInStaff);
        	});
        orderButton.setStyle(buttonStyle);
        orderButton.setOnMouseEntered(e -> orderButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        orderButton.setOnMouseExited(e -> orderButton.setStyle(buttonStyle));
        
        Button historyButton = new Button("Order History");
        historyButton.setOnAction(e -> {
        	gridPane.getChildren().clear();
        	displayHistory(loggedInStaff);
        	});
        historyButton.setStyle(buttonStyle);
        historyButton.setOnMouseEntered(e -> historyButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        historyButton.setOnMouseExited(e -> historyButton.setStyle(buttonStyle));

        Button profileButton = new Button("Profile");
        profileButton.setOnAction(e->{
        	gridPane.getChildren().clear();
        	profile();
        });
        profileButton.setStyle(buttonStyle);
        profileButton.setOnMouseEntered(e -> profileButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        profileButton.setOnMouseExited(e -> profileButton.setStyle(buttonStyle));
                
        orderButton.setPrefSize(180, 40);
        profileButton.setPrefSize(180, 40);
        historyButton.setPrefSize(180, 40);


        sideMenu.getChildren().addAll(imageView, orderButton, historyButton, profileButton);
        
        VBox bottomContainer = new VBox();
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.setAlignment(Pos.BOTTOM_LEFT);
        bottomContainer.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e->{
        	staffStage.close();
        	reg.loginpage();
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> staffStage.close());

        logoutButton.setPrefSize(180, 40);
        exitButton.setPrefSize(180, 40);

        bottomContainer.getChildren().addAll(logoutButton, exitButton);
	    
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(bottomContainer);
        borderPane.setLeft(sideMenu);
        borderPane.setCenter(gridPane);

	    
	    
		Scene scene = new Scene(borderPane,1000,600);
	    staffStage.setTitle("Staff Page");
	    staffStage.setScene(scene); 
	    Image icon = new Image("file:logo.png");
	    staffStage.getIcons().add(icon);
		
		staffStage.show();
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
		Label userLabel = new Label(loggedInStaff.getFull_name());
	    welcomeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20)); 
	    userLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
	    userLabel.setStyle("-fx-text-fill: red;");
	    GridPane.setConstraints(welcomeLabel, 0, 0); 
	    GridPane.setConstraints(userLabel, 1, 0);  

	    gridPane.getChildren().addAll(welcomeLabel,userLabel);
	    
	    Label serviceLabel = new Label("Where you can book any service you require!");
	    serviceLabel.setFont(Font.font("Arial",  15)); 

	    GridPane.setConstraints(serviceLabel, 0, 1); 
	    gridPane.getChildren().add(serviceLabel);
	}
	
	public void profile() {
		gridPane.setMinSize(400, 200);   
	    gridPane.setPadding(new Insets(10, 10, 10, 10));
	    gridPane.setVgap(5);
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);
	    
		Label name = new Label("Name");       
	    Label email = new Label("Email");       
	    Label password = new Label("Password"); 
	    Label phone = new Label("Phone no.");       
	    Label role = new Label("Role");       

		  
	    TextField nameField = new TextField();            
	    TextField emailField = new TextField();            
	    PasswordField passwordField = new PasswordField();
	    TextField phoneField = new TextField();            
	    ComboBox<String> roleField = new ComboBox<>();   
	    database.populateRoleComboBox(roleField);
	    Staff staff = database.getStaffByEmail(loggedInStaff.getEmail()); 

	    nameField.setText(staff.getFull_name());
	    emailField.setText(staff.getEmail());
	    passwordField.setText(staff.getPassword());
	    phoneField.setText(staff.getPhone_no());
	    roleField.setValue(staff.getService_name()); 
	    
	    Button updateButton = new Button("Update");
	    
	    updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");

	    
	    nameField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            emailField.requestFocus();
	        }
	    });
	    
	    emailField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            passwordField.requestFocus();
	        }
	    });
	    passwordField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            phoneField.requestFocus();
	        }
	    });
	    phoneField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            roleField.requestFocus();
	        }
	    });
	    roleField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            updateButton.requestFocus();
	        }
	    });

	    gridPane.add(name,0, 1);
	    gridPane.add(nameField,1,1);
	    gridPane.add(email, 0, 2); 
	    gridPane.add(emailField, 1, 2); 
	    gridPane.add(password, 0, 3);       
	    gridPane.add(passwordField, 1, 3);
	    gridPane.add(phone, 0, 4);
	    gridPane.add(phoneField, 1, 4);
	    gridPane.add(role, 0, 5);       
	    gridPane.add(roleField, 1, 5);
	    gridPane.add(updateButton, 1, 6);
	}
	
	public void displayOrder(Staff loggedInStaff) {
	    gridPane.setMinSize(400, 200);
	    gridPane.setPadding(new Insets(10));
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);

	    TableView<Appointment> table = new TableView<>();
	    table.setMinWidth(400);
	    table.setMinHeight(100);

	    TableColumn<Appointment, Integer> codeBookingCol = new TableColumn<>("Booking Code");
	    codeBookingCol.setCellValueFactory(new PropertyValueFactory<>("code_booking"));
	    codeBookingCol.setPrefWidth(100);

	    TableColumn<Appointment, String> customerNameCol = new TableColumn<>("Customer Name");
	    customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
	    customerNameCol.setPrefWidth(150);

	    TableColumn<Appointment, String> customerPhoneCol = new TableColumn<>("Phone No.");
	    customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
	    customerPhoneCol.setPrefWidth(100);

	    TableColumn<Appointment, String> customerAddressCol = new TableColumn<>("Customer Address");
	    customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
	    customerAddressCol.setPrefWidth(200);

	    TableColumn<Appointment, LocalDate> dateCol = new TableColumn<>("Date");
	    dateCol.setCellValueFactory(new PropertyValueFactory<>("booking_date"));
	    dateCol.setPrefWidth(70);

	    TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
	    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
	    statusCol.setPrefWidth(100);

	    List<Appointment> currentAppointments = database.getOrdersForStaff(loggedInStaff);
	    ObservableList<Appointment> appointments = FXCollections.observableArrayList(currentAppointments);

	    table.setItems(appointments);
	    table.getColumns().addAll(codeBookingCol, customerNameCol, customerPhoneCol, customerAddressCol, dateCol, statusCol);

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setMaxHeight(300);
	    scrollPane.setContent(table);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	    
	    String buttonStyle = "-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 8px 16px; " +
                "-fx-border-radius: 5px; " +
                "-fx-cursor: hand;";

	    Button completeButton = new Button("Complete");
	    completeButton.setStyle(buttonStyle);
	    completeButton.setOnAction(e -> {
	        Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
	        if (selectedAppointment != null) {
	            database.completeAppointment(selectedAppointment);
	        }
	        table.refresh();
	    });

	    Button requestRescheduleButton = new Button("Request Reschedule");
	    requestRescheduleButton.setStyle(buttonStyle);
	    requestRescheduleButton.setOnAction(e -> {
	        Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
	        if (selectedAppointment != null) {
	            database.requestReschedule(selectedAppointment);
	        }
	        table.refresh();
	    });

	    Button acceptButton = new Button("Accept");
	    acceptButton.setStyle(buttonStyle);
	    acceptButton.setOnAction(e -> {
	        Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
	        if (selectedAppointment != null) {
	            database.acceptAppointment(selectedAppointment);
	        }
	        table.refresh();

	    });


	    HBox controls = new HBox(10);
	    controls.setAlignment(Pos.CENTER);
	    controls.getChildren().addAll(acceptButton, requestRescheduleButton, completeButton);

	    VBox hbox = new VBox(10);
	    hbox.setAlignment(Pos.CENTER);
	    hbox.getChildren().addAll(scrollPane, controls);

	    gridPane.getChildren().add(hbox);
	}
	
	
	public void displayHistory(Staff loggedInStaff) {
	    gridPane.setMinSize(400, 200);
	    gridPane.setPadding(new Insets(10));
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);

	    TableView<Appointment> table = new TableView<>();
	    table.setMinWidth(400);
	    table.setMinHeight(100);

	    TableColumn<Appointment, Integer> codeBookingCol = new TableColumn<>("Booking Code");
	    codeBookingCol.setCellValueFactory(new PropertyValueFactory<>("code_booking"));
	    codeBookingCol.setPrefWidth(100);

	    TableColumn<Appointment, String> customerNameCol = new TableColumn<>("Customer Name");
	    customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
	    customerNameCol.setPrefWidth(150);

	    TableColumn<Appointment, String> customerPhoneCol = new TableColumn<>("Phone No.");
	    customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
	    customerPhoneCol.setPrefWidth(100);

	    TableColumn<Appointment, String> customerAddressCol = new TableColumn<>("Customer Address");
	    customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
	    customerAddressCol.setPrefWidth(200);

	    TableColumn<Appointment, LocalDate> dateCol = new TableColumn<>("Date");
	    dateCol.setCellValueFactory(new PropertyValueFactory<>("booking_date"));
	    dateCol.setPrefWidth(70);

	    TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
	    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
	    statusCol.setPrefWidth(100);

	    List<Appointment> currentAppointments = database.getHistoryForStaff(loggedInStaff);
	    ObservableList<Appointment> appointments = FXCollections.observableArrayList(currentAppointments);

	    table.setItems(appointments);
	    table.getColumns().addAll(codeBookingCol, customerNameCol, customerPhoneCol, customerAddressCol, dateCol, statusCol);

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setMaxHeight(300);
	    scrollPane.setContent(table);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

	    DatePicker datePicker = new DatePicker();
	    datePicker.setOnAction(e -> {
	        LocalDate selectedDate = datePicker.getValue();
	        if (selectedDate != null) {
	            ObservableList<Appointment> filteredAppointments = appointments.filtered(a -> a.getBooking_date().equals(selectedDate));
	            table.setItems(filteredAppointments);
	        } else {
	            table.setItems(appointments);
	        }
	    });
	    Label searchLabel = new Label("Select the date you want to search");

	    HBox controls = new HBox(10);
	    controls.setAlignment(Pos.CENTER);
	    controls.getChildren().addAll(searchLabel, datePicker);

	    VBox hbox = new VBox(10);
	    hbox.setAlignment(Pos.CENTER);
	    hbox.getChildren().addAll(scrollPane, controls);

	    gridPane.getChildren().add(hbox);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
