package frontend;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import backend.Customer;
import backend.DatabaseControl;
import backend.Staff;

public class Register extends Application{
	DatabaseControl database = new DatabaseControl();
	
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
	
	private boolean isValidEmail(String email) {
        return email.contains("@") && email.endsWith(".com");
    }
	@Override
	public void start(Stage firststage) {
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        gridPane.setBackground(background);
        
        ComboBox<String> position = new ComboBox<>();
        position.getItems().addAll("Customer", "Staff");
        position.setPromptText("Select your position for registration");
        position.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-color: black;");
        
        StackPane stackPane = new StackPane();

        ImageView logo = new ImageView(new Image("file:logo.png"));
        logo.setFitWidth(200);
        logo.setFitHeight(200);
        stackPane.getChildren().addAll(position, logo);
        StackPane.setAlignment(logo, Pos.TOP_CENTER);

        Button continuebutton = new Button("Continue");
        continuebutton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");

        continuebutton.setOnAction(e -> {
            if (position.getValue() != null) {
                if (position.getValue().equals("Customer")) {
                    firststage.close();
                    cusregisterpage();
                } else if (position.getValue().equals("Staff")) {
                    firststage.close();
                    staffregisterpage();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select your position.");
                alert.showAndWait();
            }
        });

        Button loginbutton = new Button("Click here to login");
        loginbutton.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");

        loginbutton.setOnAction(e -> {
            firststage.close();
            loginpage();
        });
        gridPane.add(stackPane, 1, 0);
        gridPane.add(position, 1, 1);
        gridPane.add(continuebutton, 1, 2);
        gridPane.add(loginbutton, 1, 4);
        GridPane.setHalignment(position, HPos.CENTER);
        GridPane.setHalignment(continuebutton, HPos.CENTER);
        GridPane.setHalignment(loginbutton, HPos.CENTER);

        Scene scene = new Scene(gridPane, 500, 500);
        firststage.setTitle("Registration Selection");
        firststage.setScene(scene);
        Image icon = new Image("file:logo.png");
        firststage.getIcons().add(icon);

        firststage.show();
    }
	public void cusregisterpage() {
		Stage regstage = new Stage();
		Text register = new Text("Register for Customer");
        register.setStyle("-fx-font-size: 25;");
     
        TextField nameField = new TextField();      
	    nameField.setPromptText("Enter full name");
	    TextField emailField = new TextField();    
	    emailField.setPromptText("Enter email");
	    PasswordField passwordField = new PasswordField();
	    passwordField.setPromptText("Enter password");
	    TextField phoneField = new TextField(); 
	    phoneField.setPromptText("Enter phone number");
	    TextField addressField = new TextField();
	    addressField.setPromptText("Enter address");
	    
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
	    
	    Button registerbutton = new Button("Register"); 
	    registerbutton.setStyle("-fx-background-color: #4CAF50;  -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
	    registerbutton.setOnAction(e -> {
            if (validateFields(nameField, emailField, passwordField, phoneField, addressField)) {
            	
                String full_name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String phone_no = phoneField.getText();
                String address = addressField.getText();
                if (!isValidEmail(email)) {
                	Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Invaild email");
	                alert.setHeaderText(null);
	                alert.setContentText("Please use valid email");
	                alert.showAndWait();
                }
                else {
                try {
					database.registercustomer(full_name, address, phone_no, email, password);
					nameField.clear();
	                emailField.clear();
	                passwordField.clear();
	                phoneField.clear();
	                addressField.clear();
	                
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Registration Successful");
	                alert.setHeaderText(null);
	                alert.setContentText("You have been successfully registered!");
	                alert.showAndWait();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                }
            }
            
        });
	    GridPane.setHalignment(registerbutton, HPos.CENTER);
	    
	    Button loginbutton = new Button("Click here to login");  
	    loginbutton.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
	    loginbutton.setOnAction(e -> {
	    	regstage.close();
	    	loginpage();
        });
	    GridPane.setHalignment(loginbutton, HPos.CENTER);
	    
	    addressField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            registerbutton.requestFocus();
	        }
	    });
	      
	    GridPane gridPane = new GridPane();
	    gridPane.setMinSize(400, 200);   
	    gridPane.setPadding(new Insets(10, 10, 10, 10));
	    gridPane.setVgap(5);
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	       
	    gridPane.add(register, 0,0);
	    gridPane.add(nameField,0,1);
	    gridPane.add(emailField, 0, 2); 
	    gridPane.add(passwordField, 0, 3);
	    gridPane.add(phoneField, 0, 4);
	    gridPane.add(addressField, 0, 5); 
	    gridPane.add(registerbutton, 0, 6); 
	    gridPane.add(loginbutton, 0, 7);  
	  
	    
	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);

	    Scene scene = new Scene(gridPane,500,500);     
	    regstage.setTitle("Register Form");
	    regstage.setScene(scene); 
	    Image icon = new Image("file:logo.png");
	    regstage.getIcons().add(icon);

	    regstage.show(); 	
	}
	public void staffregisterpage() {
		Stage regstage = new Stage();
		Text register = new Text("Register for Staff");
        register.setStyle("-fx-font-size: 25;");
		  
        TextField nameField = new TextField();      
	    nameField.setPromptText("Enter full name");
	    TextField emailField = new TextField();    
	    emailField.setPromptText("Enter email");
	    PasswordField passwordField = new PasswordField();
	    passwordField.setPromptText("Enter password");
	    TextField phoneField = new TextField(); 
	    phoneField.setPromptText("Enter phone number");
	    ComboBox<String> roleselection = new ComboBox<>();  
	    database.populateRoleComboBox(roleselection);
	    roleselection.setPromptText("Select your role");
	    GridPane.setHalignment(roleselection, HPos.CENTER);
	    roleselection.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-color: black;");
	    
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
	            roleselection.requestFocus();
	        }
	    });
	    
	    Button registerbutton = new Button("Register"); 
	    registerbutton.setStyle("-fx-background-color: #4CAF50;  -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
	    registerbutton.setOnAction(e -> {
            if (validateFields(nameField, emailField, passwordField, phoneField, roleselection)) {
            	String full_name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String phone_no = phoneField.getText();
                String serviceName = roleselection.getValue();
                if (!isValidEmail(email)) {
                	Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Invaild email");
	                alert.setHeaderText(null);
	                alert.setContentText("Please use valid email");
	                alert.showAndWait();
                }
                else {
                database.registerStaff( full_name, email,password, phone_no, serviceName);
				nameField.clear();
				emailField.clear();
				passwordField.clear();
				phoneField.clear();
           
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Registration Successful");
				alert.setHeaderText(null);
				alert.setContentText("You have been successfully registered!");
				alert.showAndWait();
                System.out.println("Done");
                
            }}
            
        });
	    GridPane.setHalignment(registerbutton, HPos.CENTER);
	  
	    Button loginbutton = new Button("Click here to login");  
	    loginbutton.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
	    loginbutton.setOnAction(e -> {
	    	regstage.close();
	    	loginpage();
        });
	    GridPane.setHalignment(loginbutton, HPos.CENTER);
   
	    roleselection.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            registerbutton.requestFocus();
	        }
	    });
	      
	    GridPane gridPane = new GridPane();
	    gridPane.setMinSize(400, 200);   
	    gridPane.setPadding(new Insets(10, 10, 10, 10));
	    gridPane.setVgap(5);
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	       
	    gridPane.add(register, 0,0);
	    gridPane.add(nameField,0,1);
	    gridPane.add(emailField, 0, 2); 
	    gridPane.add(passwordField, 0, 3);
	    gridPane.add(phoneField, 0, 4);
	    gridPane.add(roleselection,0, 5); 
	    gridPane.add(registerbutton, 0, 6); 
	    gridPane.add(loginbutton, 0, 7);  
	      
	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);

	    Scene scene = new Scene(gridPane,500,500);     
	    regstage.setTitle("Register Form");
	    regstage.setScene(scene); 
	    Image icon = new Image("file:logo.png");
	    regstage.getIcons().add(icon);

	    regstage.show(); 	
	}
	public void loginpage(){
		Stage logstage = new Stage();
		
		Text login = new Text("Login");
        login.setStyle("-fx-font-size: 20;");
		  
	    TextField emailField = new TextField();
	    emailField.setPromptText("Enter email");
	    PasswordField passwordField = new PasswordField();
	    passwordField.setPromptText("Enter password");
	    Button loginbutton = new Button("Login"); 
	    loginbutton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
	    loginbutton.setOnAction(e -> {
	    	
            if (validateFields(emailField, passwordField)) {
            	String email = emailField.getText();
                String password = passwordField.getText();
                
                String role = database.authenticateUser(email, password);
            	if (emailField.getText().equals("admin@gmail.com") && passwordField.getText().equals("admin")) {
            		Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome, Admin!");
                    alert.showAndWait();
                    logstage.close();

                    AdminPage adminPage = new AdminPage();
                    try {
                        adminPage.start(new Stage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if(role != null) {
                	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome to Service Management System!");
                    alert.showAndWait();
                    logstage.close();
                    if (role.equals("staff")) {
                    	
                    	Staff staff = database.getStaffByEmail(email);
                    	StaffPage staffPage = new StaffPage(staff);
                        try {
                            staffPage.start(new Stage());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else if (role.equals("customer")) {
                    		Customer customer = database.getCustomerByEmail(email);    
                            CustomerPage customerPage = new CustomerPage(customer);
                            try {
                                customerPage.start(new Stage());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid email or password.");
                    alert.showAndWait();
                }
        }});
	    GridPane.setHalignment(loginbutton, HPos.CENTER);
	    
	    Button regbutton = new Button("Click here to register");  
	    regbutton.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
	    GridPane.setHalignment(regbutton, HPos.CENTER);
	    GridPane.setHalignment(login, HPos.CENTER);

	    regbutton.setOnAction(e -> {
	    	logstage.close();
	    	try {
				start(logstage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        });
	    emailField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            passwordField.requestFocus();
	        }
	    });
	    passwordField.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            loginbutton.requestFocus();
	        }
	    });
	      
	    GridPane gridPane = new GridPane();    
	    gridPane.setMinSize(400, 200); 	       
	    gridPane.setPadding(new Insets(10, 10, 10, 10)); 	      
	    gridPane.setVgap(5); 
	    gridPane.setHgap(5);       	      
	    gridPane.setAlignment(Pos.CENTER); 
	    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);
	    gridPane.setBackground(background);
	       
	    gridPane.add(login, 0, 0);
	    gridPane.add(emailField, 0, 2); 
	    gridPane.add(passwordField, 0, 3);
	    gridPane.add(loginbutton, 0, 6); 
	    gridPane.add(regbutton, 0, 7);  
	    
	      
	    Scene scene = new Scene(gridPane,500,500);     
	    logstage.setTitle("Login Form");
	    logstage.setScene(scene); 
	    Image icon = new Image("file:logo.png");
	    logstage.getIcons().add(icon);

	    logstage.show(); 	
	}
	public static void main(String[] args) {
		launch(args);
	}

}
