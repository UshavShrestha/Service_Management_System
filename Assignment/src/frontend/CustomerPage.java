package frontend;

import java.time.LocalDate;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
import backend.Appointment;
import backend.Customer;
import backend.DatabaseControl;
import backend.Service;

public class CustomerPage extends Application{
	DatabaseControl database = new DatabaseControl();
	Register reg = new Register();
	GridPane gridPane = new GridPane();
	Customer loggedInCustomer;

    public CustomerPage(Customer loggedInCustomer) {
        this.loggedInCustomer = loggedInCustomer;
    }
	@Override
	public void start(Stage cusstage) throws Exception {
	    homepage();
	    if (loggedInCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Logged in customer is null. Please log in again.");
            alert.showAndWait();
            cusstage.close();
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
		
        Button appointmentButton = new Button("Make a appointment");
        appointmentButton.setOnAction(e->{
        	gridPane.getChildren().clear();
        	appointments();
        });
        appointmentButton.setStyle(buttonStyle);
        appointmentButton.setOnMouseEntered(e -> appointmentButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        appointmentButton.setOnMouseExited(e -> appointmentButton.setStyle(buttonStyle));
         
        Button historyButton = new Button("Appointment History");
        historyButton.setOnAction(e -> {
        	gridPane.getChildren().clear();
            displayHistory(loggedInCustomer);

        	});
        historyButton.setStyle(buttonStyle);
        historyButton.setOnMouseEntered(e -> historyButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        historyButton.setOnMouseExited(e -> historyButton.setStyle(buttonStyle));
        
        Button currentappointmentButton = new Button("Current Appointments");
        currentappointmentButton.setOnAction(e -> {
        	gridPane.getChildren().clear();
            displayAppointments(loggedInCustomer);

        	});
        currentappointmentButton.setStyle(buttonStyle);
        currentappointmentButton.setOnMouseEntered(e -> currentappointmentButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        currentappointmentButton.setOnMouseExited(e -> currentappointmentButton.setStyle(buttonStyle));

        Button profileButton = new Button("Profile");
        profileButton.setOnAction(e->{
        	gridPane.getChildren().clear();
        	profile();
        });
        profileButton.setStyle(buttonStyle);
        profileButton.setOnMouseEntered(e -> profileButton.setStyle(buttonStyle + "-fx-background-color: lightblue;"));
        profileButton.setOnMouseExited(e -> profileButton.setStyle(buttonStyle));
                
        appointmentButton.setPrefSize(180, 40);
        historyButton.setPrefSize(180, 40);
        currentappointmentButton.setPrefSize(180, 40);
        profileButton.setPrefSize(180, 40);

        sideMenu.getChildren().addAll(imageView, appointmentButton,currentappointmentButton, historyButton, profileButton);
        
        VBox bottomContainer = new VBox();
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.setAlignment(Pos.BOTTOM_LEFT);
        bottomContainer.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e->{
        	cusstage.close();
        	reg.loginpage();
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> cusstage.close());

        logoutButton.setPrefSize(180, 40);
        exitButton.setPrefSize(180, 40);

        bottomContainer.getChildren().addAll(logoutButton, exitButton);
	    
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(bottomContainer);
        borderPane.setLeft(sideMenu);
        borderPane.setCenter(gridPane);

	    
	    
		Scene scene = new Scene(borderPane,1000,600);
	    cusstage.setTitle("Customer Page");
	    cusstage.setScene(scene); 
	    Image icon = new Image("file:logo.png");
	    cusstage.getIcons().add(icon);
		
		cusstage.show();
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
		Label userLabel = new Label(loggedInCustomer.getFullName());
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
	    gridPane.setVgap(10);
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
	    Label address = new Label("Address");       

		  
	    TextField nameField = new TextField();            
	    TextField emailField = new TextField();            
	    PasswordField passwordField = new PasswordField();
	    TextField phoneField = new TextField();            
	    TextField addressField = new TextField();
	    

	    if (loggedInCustomer != null) {
            nameField.setText(loggedInCustomer.getFullName());
            emailField.setText(loggedInCustomer.getEmail());
            passwordField.setText(loggedInCustomer.getPassword());
            phoneField.setText(loggedInCustomer.getPhoneNo());
            addressField.setText(loggedInCustomer.getAddress());
        }
    
	    Button updateButton = new Button("Update");
        GridPane.setHalignment(updateButton, HPos.CENTER);
	    updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
	    updateButton.setOnAction(event -> {
	        String newName = nameField.getText();
	        String newEmail = emailField.getText();
	        String newPassword = passwordField.getText();
	        String newPhone = phoneField.getText();
	        String newAddress = addressField.getText();

	        if (newName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty() || newPhone.isEmpty() || newAddress.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setHeaderText(null);
	            alert.setContentText("Please fill in all fields.");
	            alert.showAndWait();
	            return;
	        }

	        Customer updatedCustomer = new Customer(loggedInCustomer.getCode(), newName, newAddress, newPhone, newEmail, newPassword);
	        boolean success = database.updateCustomer(updatedCustomer);

	        if (success) {
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Success");
	            alert.setHeaderText(null);
	            alert.setContentText("Profile updated successfully.");
	            alert.showAndWait();
	            loggedInCustomer = updatedCustomer;
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setHeaderText(null);
	            alert.setContentText("Failed to update profile.");
	            alert.showAndWait();
	        }
	    });

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
	            addressField.requestFocus();
	        }
	    });
	    addressField.setOnKeyPressed(event -> {
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
	    gridPane.add(address, 0, 5);       
	    gridPane.add(addressField, 1, 5);
	    gridPane.add(updateButton, 1, 7);

        
	}
	public void appointments() {
		gridPane.setMinSize(400, 200);   
	    gridPane.setPadding(new Insets(10, 10, 10, 10));
	    gridPane.setVgap(5);
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);
	    
		Label service = new Label("Select the service you require");       
	    Label date = new Label("Date for the service");        
	    Label costLabel = new Label("Cost:");
	    Label durationLabel = new Label("Duration:");
	    Label descriptionLabel = new Label("Description:");

	    TextField costTextField = new TextField();
	    TextField durationTextField = new TextField();
	    TextArea descriptionTextArea = new TextArea();

	    costTextField.setEditable(false);
	    durationTextField.setEditable(false);
	    descriptionTextArea.setEditable(false);

	    descriptionTextArea.setPrefColumnCount(20);
	    descriptionTextArea.setPrefRowCount(5);
	    
	    ComboBox<String> servicelist = new ComboBox<>();  
	    database.populateRoleComboBox(servicelist);
	    servicelist.setPromptText("Select required service");
	    servicelist.setOnAction(event -> {
	        String selectedService = servicelist.getSelectionModel().getSelectedItem();
	        if (selectedService != null) {
	            Service service1 = database.serviceInfo(selectedService);
	            if (service1 != null) {
	                String cost = service1.getCost();
	                String duration = service1.getDuration();
	                String description = service1.getDescription();
	                
	                costTextField.setText(cost);
	                durationTextField.setText(duration);
	                descriptionTextArea.setText(description);
	            }
	        }
	    });

	    DatePicker datePicker = new DatePicker();
	    datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); 
                }
            }
        });
	    
	    Button bookButton = new Button("Book");
	    
	    bookButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        GridPane.setHalignment(bookButton, HPos.CENTER);
        bookButton.setOnAction(event -> {
            String selectedService = servicelist.getSelectionModel().getSelectedItem();
            LocalDate selectedDate = datePicker.getValue();

            if (selectedService == null || selectedDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a service and date.");
                alert.showAndWait();
                return;
            }

            Service service1 = database.serviceInfo(selectedService);
            if (service1 == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Service details not found.");
                alert.showAndWait();
                return;
            }

            database.bookAppointment(selectedService, selectedDate, loggedInCustomer);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Appointment booked successfully.");
            alert.showAndWait();
        });

	    
	    servicelist.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            datePicker.requestFocus();
	        }
	    });	    
	    datePicker.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	        	bookButton.requestFocus();
	        }
	    });

	    gridPane.add(service,0, 1);
	    gridPane.add(servicelist,1,1);
	    gridPane.add(date, 0, 2); 
	    gridPane.add(datePicker, 1, 2);     
	    gridPane.add(bookButton, 1, 6);

	    gridPane.add(costLabel, 2, 0);
	    gridPane.add(costTextField, 3, 0);
	    gridPane.add(durationLabel, 2, 1);
	    gridPane.add(durationTextField, 3, 1);
	    gridPane.add(descriptionLabel, 2, 2);
	    gridPane.add(descriptionTextArea, 3, 2);
	}
	
	public void displayHistory(Customer loggedInCustomer) {
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

	    TableColumn<Appointment, String> serviceNameCol = new TableColumn<>("Service Name");
	    serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("service_name"));
	    serviceNameCol.setPrefWidth(150);

	    TableColumn<Appointment, String> staffNameCol = new TableColumn<>("Staff Name");
	    staffNameCol.setCellValueFactory(new PropertyValueFactory<>("staffName"));
	    staffNameCol.setPrefWidth(160);

	    TableColumn<Appointment, String> staffPhoneCol = new TableColumn<>("Phone No.");
	    staffPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone_no")); 
	    staffPhoneCol.setPrefWidth(100); 

	    TableColumn<Appointment, LocalDate> dateCol = new TableColumn<>("Date");
	    dateCol.setCellValueFactory(new PropertyValueFactory<>("booking_date"));
	    dateCol.setPrefWidth(150);

	    TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
	    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
	    statusCol.setPrefWidth(100);

	    List<Appointment> currentapp = database.getHistoryForCustomer(loggedInCustomer);
	    ObservableList<Appointment> appointments = FXCollections.observableArrayList(currentapp);

	    table.setItems(appointments);
	    table.getColumns().addAll(codeBookingCol, serviceNameCol, staffNameCol, staffPhoneCol, dateCol, statusCol);

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
	    controls.getChildren().addAll(searchLabel ,datePicker);

	    VBox hbox = new VBox(10);
	    hbox.setAlignment(Pos.CENTER);
	    hbox.getChildren().addAll(scrollPane, controls);

	    gridPane.getChildren().add(hbox);
	}

	public void displayAppointments(Customer loggedInCustomer) {
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

	    TableColumn<Appointment, String> serviceNameCol = new TableColumn<>("Service Name");
	    serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("service_name"));
	    serviceNameCol.setPrefWidth(150);

	    TableColumn<Appointment, String> staffNameCol = new TableColumn<>("Staff Name");
	    staffNameCol.setCellValueFactory(new PropertyValueFactory<>("staffName"));
	    staffNameCol.setPrefWidth(160);

	    TableColumn<Appointment, String> staffPhoneCol = new TableColumn<>("Phone No.");
	    staffPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone_no")); 
	    staffPhoneCol.setPrefWidth(100); 

	    TableColumn<Appointment, LocalDate> dateCol = new TableColumn<>("Date");
	    dateCol.setCellValueFactory(new PropertyValueFactory<>("booking_date"));
	    dateCol.setPrefWidth(150);

	    TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
	    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
	    statusCol.setPrefWidth(100);

	    List<Appointment> currentapp = database.getcurrentAppointmentsForCustomer(loggedInCustomer);
	    ObservableList<Appointment> appointments = FXCollections.observableArrayList(currentapp);

	    table.setItems(appointments);
	    table.getColumns().addAll(codeBookingCol, serviceNameCol, staffNameCol, staffPhoneCol, dateCol, statusCol);

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setMaxHeight(300);
	    scrollPane.setContent(table);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	    
	    DatePicker datePicker = new DatePicker();
	    datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); 
                }
            }
        });
	    
	    Button cancelButton = new Button("Cancel appointment");
	    cancelButton.setOnAction(event -> {
	        Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
	        if (selectedAppointment != null) {
	            DatabaseControl databaseControl = new DatabaseControl();
	            databaseControl.cancelAppointment(selectedAppointment);
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Success");
	            alert.setHeaderText(null);
	            alert.setContentText("Appointment successfully cancelled.");
	            alert.showAndWait();
	            appointments.remove(selectedAppointment);
	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Warning");
	            alert.setHeaderText(null);
	            alert.setContentText("Please select an appointment to cancel.");
	            alert.showAndWait();
	        }
	    });
	    
	    Button rescheduleButton = new Button("Reschedule");
	    rescheduleButton.setOnAction(event -> {
	        Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
	        if (selectedAppointment != null) {
	            LocalDate newDate = datePicker.getValue();
	            if (newDate != null) {
	                DatabaseControl databaseControl = new DatabaseControl();
	                databaseControl.setBookingDate(selectedAppointment, newDate);
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Success");
	                alert.setHeaderText(null);
	                alert.setContentText("Appointment successfully rescheduled.");
	                alert.showAndWait();
	                table.refresh();
	            } else {
	                Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setTitle("Warning");
	                alert.setHeaderText(null);
	                alert.setContentText("Please select a date for rescheduling.");
	                alert.showAndWait();
	            }
	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Warning");
	            alert.setHeaderText(null);
	            alert.setContentText("Please select an appointment to reschedule.");
	            alert.showAndWait();
	        }
	        table.refresh();
	    });

	    rescheduleButton.setStyle("-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 8px 16px; " +
                "-fx-border-radius: 5px; " +
                "-fx-cursor: hand;");
	    cancelButton.setStyle("-fx-background-color: red; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 8px 16px; " +
                "-fx-border-radius: 5px; " +
                "-fx-cursor: hand;");
	    
	    HBox controls = new HBox(10);
	    controls.setAlignment(Pos.CENTER);
	    controls.getChildren().addAll(datePicker,rescheduleButton,cancelButton );

	    VBox hbox = new VBox(10);
	    hbox.setAlignment(Pos.CENTER);
	    hbox.getChildren().addAll(scrollPane, controls);

	    gridPane.getChildren().add(hbox);
	}


	
	public static void main(String[] args) {
		launch(args);
	}
}
