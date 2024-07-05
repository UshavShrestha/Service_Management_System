package backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import javafx.scene.control.ComboBox;

public class DatabaseControl {
	final String DRIVER="com.mysql.cj.jdbc.Driver";
	final static String DBNAME="service_management";
	final static String USERNAME="root";
	final static String PASSWORD="Devil_B0y";
	final static int PORT = 3306;
	final static String HOST = "localhost";
	final static String URL="jdbc:mysql://"+HOST+":"+PORT+"/"+DBNAME;
	
	public void populateRoleComboBox(ComboBox<String> comboBox) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT service_name FROM services");

            comboBox.getItems().clear();

            while (resultSet.next()) {
                String role = resultSet.getString("service_name");
                comboBox.getItems().add(role);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public void registercustomer(String full_name, String address, String phone_no, String email, String password) throws IOException {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO customers (full_name, address, phone_no, email, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
           
            statement.setString(1, full_name);
            statement.setString(2, address);
            statement.setString(3, phone_no);
            statement.setString(4, email);
            statement.setString(5, password);
          
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public void registerStaff(String name, String email, String password, String phone, String serviceName) {
	    String insertStaffSql = "INSERT INTO staffs (full_name, role, phone_no, email, password) VALUES (?, ?, ?, ?, ?)";
	    String getServiceCodeSql = "SELECT code_ser FROM services WHERE service_name = ?";

	    try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        PreparedStatement getServiceCodeStatement = connection.prepareStatement(getServiceCodeSql);
	        getServiceCodeStatement.setString(1, serviceName);
	        ResultSet resultSet = getServiceCodeStatement.executeQuery();

	        int serviceCode = -1; 
	        if (resultSet.next()) {
	            serviceCode = resultSet.getInt("code_ser");
	        }

	        PreparedStatement insertStaffStatement = connection.prepareStatement(insertStaffSql);
	        insertStaffStatement.setString(1, name);
	        insertStaffStatement.setInt(2, serviceCode); 
	        insertStaffStatement.setString(3, phone);
	        insertStaffStatement.setString(4, email);
	        insertStaffStatement.setString(5, password);

	        int rowsInserted = insertStaffStatement.executeUpdate();
	        return; 	    
	        } catch (SQLException e) {
	        e.printStackTrace();
	        return;
	        }
	}
	
	public static void insertService(String serviceName, String description, String cost, String duration) {
        String sqlQuery = "INSERT INTO services (service_name, description, cost, duration) VALUES (?, ?, ?, ?)";

        try {
        	Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            
            preparedStatement.setString(1, serviceName);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, cost);
            preparedStatement.setString(4, duration);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Service inserted successfully.");
            } else {
                System.out.println("Failed to insert service.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	 public ArrayList<Customer> retrieveCustomers() {
	        String query = "SELECT code_cus, full_name, address, phone_no, email, password FROM customers";
	        ArrayList<Customer> customers = new ArrayList<>();
	        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	             Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {

	            while (resultSet.next()) {
	                int code = resultSet.getInt("code_cus");
	                String fullName = resultSet.getString("full_name");
	                String address = resultSet.getString("address");
	                String phoneNo = resultSet.getString("phone_no");
	                String email = resultSet.getString("email");
	                String password = resultSet.getString("password");

	                Customer customer = new Customer(code, fullName, address, phoneNo, email, password);
	                customers.add(customer);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return customers;
	    }
	 
	 public List<Staff> retrieveStaff() {
		    List<Staff> staffList = new ArrayList<>();
		    String query = "SELECT s.code_staff, s.full_name, s.role, s.phone_no, s.email, sr.service_name " +
		                   "FROM staffs s " +
		                   "JOIN services sr ON s.role = sr.code_ser";

		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement preparedStatement = connection.prepareStatement(query);
		         ResultSet resultSet = preparedStatement.executeQuery()) {

		        while (resultSet.next()) {
		            int code_staff = resultSet.getInt("code_staff");
		            String full_name = resultSet.getString("full_name");
		            int role = resultSet.getInt("role");
		            String phone_no = resultSet.getString("phone_no");
		            String email = resultSet.getString("email");
		            String serviceName = resultSet.getString("service_name");

		            Staff staff = new Staff(code_staff, full_name, role, phone_no, email, serviceName,null);
		            staffList.add(staff);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return staffList;
		}
	 
	 public Service serviceInfo(String serviceName) {
		    String query = "SELECT code_ser, service_name, description, cost, duration FROM services WHERE service_name = ?";
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

		        preparedStatement.setString(1, serviceName);
		        ResultSet resultSet = preparedStatement.executeQuery();

		        if (resultSet.next()) {
		            int codeSer = resultSet.getInt("code_ser");
		            String description = resultSet.getString("description");
		            String cost = resultSet.getString("cost");
		            String duration = resultSet.getString("duration");

		            return new Service(codeSer, serviceName, description, cost, duration);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; 
		}
	 
	 public String authenticateUser(String email, String password) {
		    try {
		        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		        
		        String staffSql = "SELECT * FROM staffs WHERE email = ? AND password = ?";
		        PreparedStatement staffStatement = conn.prepareStatement(staffSql);
		        staffStatement.setString(1, email);
		        staffStatement.setString(2, password);
		        ResultSet staffResultSet = staffStatement.executeQuery();

		        if (staffResultSet.next()) {
		            conn.close();
		            return "staff";
		        }		        
		        String customerSql = "SELECT * FROM customers WHERE email = ? AND password = ?";
		        PreparedStatement customerStatement = conn.prepareStatement(customerSql);
		        customerStatement.setString(1, email);
		        customerStatement.setString(2, password);
		        ResultSet customerResultSet = customerStatement.executeQuery();

		        if (customerResultSet.next()) {
		            conn.close();
		            return "customer";
		        }

		        conn.close();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }    
		    return null;
		}

	 public List<Service> showServices() {
	        List<Service> serviceList = new ArrayList<>();
	        String query = "SELECT code_ser, service_name, description, cost, duration FROM services";

	        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(query);
	             ResultSet resultSet = preparedStatement.executeQuery()) {

	            while (resultSet.next()) {
	                int codeSer = resultSet.getInt("code_ser");
	                String serviceName = resultSet.getString("service_name");
	                String description = resultSet.getString("description");
	                String cost = resultSet.getString("cost");
	                String duration = resultSet.getString("duration");

	                Service service = new Service(codeSer, serviceName, description, cost, duration);
	                serviceList.add(service);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return serviceList;
	    }
	 public Customer validateCustomerLogin(String email, String password) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        Customer customer = null;

	        try {
	            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	            String query = "SELECT * FROM customers WHERE email = ? AND password = ?";
	            stmt = conn.prepareStatement(query);
	            stmt.setString(1, email);
	            stmt.setString(2, password);
	            rs = stmt.executeQuery();

	            if (rs.next()) {
	                int codeCus = rs.getInt("code_cus");
	                String fullName = rs.getString("full_name");
	                String address = rs.getString("address");
	                String phoneNo = rs.getString("phone_no");
	                String emailFromDB = rs.getString("email");

	                customer = new Customer(codeCus, fullName, address, phoneNo, emailFromDB, password);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return customer;
	    }
	 
	 public Customer getCustomerByEmail(String email) {
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    Customer customer = null;

		    try {
		        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		        String query = "SELECT * FROM customers WHERE email = ?";
		        stmt = conn.prepareStatement(query);
		        stmt.setString(1, email);
		        rs = stmt.executeQuery();

		        if (rs.next()) {
		            int code_cus = rs.getInt("code_cus");
		            String fullName = rs.getString("full_name");
		            String address = rs.getString("address");
		            String phoneNo = rs.getString("phone_no");
		            String password = rs.getString("password");

		            customer = new Customer(code_cus, fullName, address, phoneNo, email, password);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) conn.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return customer;
		}

	 public Staff getStaffByEmail(String email) {
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    Staff staff = null;

		    try {
		        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		        String query = "SELECT * FROM staffs WHERE email = ?";
		        stmt = conn.prepareStatement(query);
		        stmt.setString(1, email);
		        rs = stmt.executeQuery();

		        if (rs.next()) {
		            int code_staff = rs.getInt("code_staff");
		            String fullName = rs.getString("full_name");
		            int role = rs.getInt("role");
		            String phoneNo = rs.getString("phone_no");
		            String password = rs.getString("password");
		            String service_name = null;

		            int serviceCode = rs.getInt("role");
		            String serviceQuery = "SELECT service_name FROM services WHERE code_ser = ?";
		            try (PreparedStatement serviceStmt = conn.prepareStatement(serviceQuery)) {
		                serviceStmt.setInt(1, serviceCode);
		                try (ResultSet serviceRs = serviceStmt.executeQuery()) {
		                    if (serviceRs.next()) {
		                        service_name = serviceRs.getString("service_name");
		                    }
		                }
		            }
		            staff = new Staff(code_staff, fullName, role, phoneNo, email, service_name, password);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) conn.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return staff;
		}
	 
	 public Staff validateStaffLogin(String email, String password) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        Staff staff = null;

	        try {
	            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	            String query = "SELECT * FROM staffs WHERE email = ? AND password = ?";
	            stmt = conn.prepareStatement(query);
	            stmt.setString(1, email);
	            stmt.setString(2, password);
	            rs = stmt.executeQuery();

	            if (rs.next()) {
	                int codeStaff = rs.getInt("code_staff");
	                String fullName = rs.getString("full_name");
	                int role = rs.getInt("role");
	                String phoneNo = rs.getString("phone_no");
	                String emailFromDB = rs.getString("email");
	                String serviceName = rs.getString("service_name");
	                staff = new Staff(codeStaff, fullName, role, phoneNo, emailFromDB, serviceName, password );
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return staff;
	    }
	 
	 public void bookAppointment(String serviceName, LocalDate appointmentDate, Customer customer) {
		    try {
		        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		        String getServiceCodeSql = "SELECT code_ser FROM services WHERE service_name = ?";
		        PreparedStatement getServiceCodeStatement = connection.prepareStatement(getServiceCodeSql);
		        getServiceCodeStatement.setString(1, serviceName);
		        ResultSet resultSet = getServiceCodeStatement.executeQuery();

		        int serviceCode = -1;
		        if (resultSet.next()) {
		            serviceCode = resultSet.getInt("code_ser");
		        }
		        String insertAppointmentSql = "INSERT INTO service_booking_appointments (code_cus, code_ser, booking_date, status) VALUES (?, ?, ?, ?)";
		        PreparedStatement insertAppointmentStatement = connection.prepareStatement(insertAppointmentSql);
		        insertAppointmentStatement.setInt(1, customer.getCode()); 
		        insertAppointmentStatement.setInt(2, serviceCode);
		        insertAppointmentStatement.setDate(3, Date.valueOf(appointmentDate));
		        insertAppointmentStatement.setString(4, "Pending");

		        insertAppointmentStatement.executeUpdate();

		        resultSet.close();
		        getServiceCodeStatement.close();
		        insertAppointmentStatement.close();
		        connection.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	 
	 public boolean updateCustomer(Customer customer) {
		    String sql = "UPDATE customers SET full_name = ?, address = ?, phone_no = ?, email = ?, password = ? WHERE code_cus = ?";
		    
		    try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement statement = conn.prepareStatement(sql)) {
		        statement.setString(1, customer.getFullName());
		        statement.setString(2, customer.getAddress());
		        statement.setString(3, customer.getPhoneNo());
		        statement.setString(4, customer.getEmail());
		        statement.setString(5, customer.getPassword());
		        statement.setInt(6, customer.getCode());

		        int rowsUpdated = statement.executeUpdate();
		        return rowsUpdated > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
	 
	 public List<Appointment> appointmentListForAdmin() {
		    List<Appointment> appointments = new ArrayList<>();

		    String query = "SELECT a.code_booking, s.service_name, st.full_name AS staff_name, a.booking_date, a.status, c.full_name AS customer_name " +
	                   "FROM service_booking_appointments a " +
	                   "JOIN customers c ON a.code_cus = c.code_cus " +
	                   "JOIN services s ON a.code_ser = s.code_ser " +
	                   "LEFT JOIN staffs st ON a.code_staff = st.code_staff " +
	                   "WHERE a.status = 'Pending'";

		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement statement = connection.prepareStatement(query);
		         ResultSet resultSet = statement.executeQuery()) {

		        while (resultSet.next()) {
		            int code_booking = resultSet.getInt("code_booking");
		            String service_name = resultSet.getString("service_name");
		            String staffName = resultSet.getString("staff_name");
		            LocalDate booking_date = resultSet.getDate("booking_date").toLocalDate();
		            String status = resultSet.getString("status");
		            String customerName = resultSet.getString("customer_name");

		            Appointment appointment = new Appointment(code_booking, 0, staffName, service_name, booking_date, customerName, status, 0, 0, null);
		            appointments.add(appointment);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace(); 
		    }

		    return appointments;
		}

	 public List<Appointment> getHistoryForCustomer(Customer loggedInCustomer) {
		    List<Appointment> appointments = new ArrayList<>();

		    String query = "SELECT a.code_booking, " +
		                    "COALESCE(s.service_name, 'Unknown') AS service_name, " +
		                    "a.booking_date, " +
		                    "COALESCE(st.full_name, 'Unknown') AS staff_name, " +
		                    "COALESCE(st.phone_no, 'Unknown') AS staff_phone, " +
		                    "a.status " +
		                "FROM service_booking_appointments a " +
		                "LEFT JOIN services s ON a.code_ser = s.code_ser " +
		                "LEFT JOIN staffs st ON a.code_staff = st.code_staff " +
		                "WHERE a.code_cus = ?";

		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setInt(1, loggedInCustomer.getCode());
		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            while (resultSet.next()) {
		                int code_booking = resultSet.getInt("code_booking");
		                String service_name = resultSet.getString("service_name");
		                String staffName = resultSet.getString("staff_name");
		                LocalDate booking_date = resultSet.getDate("booking_date").toLocalDate();
		                String staffPhone = resultSet.getString("staff_phone");
		                String status = resultSet.getString("status");

		                Appointment appointment = new Appointment(code_booking, loggedInCustomer.getCode(), staffName, service_name, booking_date, loggedInCustomer.getFullName(), status, 0, 0, staffPhone); // Assuming code_staff and code_ser are not needed in this context
		                appointments.add(appointment);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return appointments;
		}
	 
	 public void setBookingDate(Appointment appointment, LocalDate newDate) {
		    String query = "UPDATE service_booking_appointments SET booking_date = ?, status = 'Pending' WHERE code_booking = ?";	        
	        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setDate(1, java.sql.Date.valueOf(newDate));
	            preparedStatement.setInt(2, appointment.getCode_booking());
	            
	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                appointment.setBooking_date(newDate);
	            } else {
	                System.out.println("Failed to update booking date for appointment: " + appointment.getCode_booking());
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public void cancelAppointment(Appointment appointment) {
	        String query = "UPDATE service_booking_appointments SET status = 'Cancelled' WHERE code_booking = ?";
	        
	        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1, appointment.getCode_booking());
	            
	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                appointment.setStatus("Cancelled");
	            } else {
	                System.out.println("Failed to cancel appointment: " + appointment.getCode_booking());
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public List<Appointment> getcurrentAppointmentsForCustomer(Customer loggedInCustomer) {
		    List<Appointment> appointments = new ArrayList<>();

		    String query = "SELECT a.code_booking, " +
	                "COALESCE(s.service_name, 'Unknown') AS service_name, " +
	                "a.booking_date, " +
	                "COALESCE(st.full_name, 'Unknown') AS staff_name, " +
	                "COALESCE(st.phone_no, 'Unknown') AS staff_phone, " +
	                "a.status " +
	              "FROM service_booking_appointments a " +
	              "LEFT JOIN services s ON a.code_ser = s.code_ser " +
	              "LEFT JOIN staffs st ON a.code_staff = st.code_staff " +
	              "WHERE a.code_cus = ? " +
	              "AND a.status IN ('Pending', 'Reschedule Requested', 'Accepted')";
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setInt(1, loggedInCustomer.getCode());
		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            while (resultSet.next()) {
		                int code_booking = resultSet.getInt("code_booking");
		                String service_name = resultSet.getString("service_name");
		                String staffName = resultSet.getString("staff_name");
		                LocalDate booking_date = resultSet.getDate("booking_date").toLocalDate();
		                String staffPhone = resultSet.getString("staff_phone"); 
		                String status = resultSet.getString("status");

		                Appointment appointment = new Appointment(code_booking, loggedInCustomer.getCode(), staffName, service_name, booking_date, loggedInCustomer.getFullName(), status, 0, 0, staffPhone); // Assuming code_staff and code_ser are not needed in this context
		                appointments.add(appointment);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return appointments;
		}

	 public List<Staff> getStaffByRole(String role) {
		    List<Staff> staffList = new ArrayList<>();

		    String query = "SELECT st.*, st.full_name " +
	                   "FROM staffs st " +
	                   "JOIN services s ON st.role = s.code_ser " +
	                   "WHERE s.service_name = ?";
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setString(1, role);
		        try (ResultSet resultSet = statement.executeQuery()) {
		            while (resultSet.next()) {
		                int codeStaff = resultSet.getInt("code_staff");
		                String fullName = resultSet.getString("full_name");

		                Staff staff = new Staff(codeStaff, fullName, 0, null, null, null, null);
		                staffList.add(staff);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace(); 
		    }

		    return staffList;
		}
	 
	 public boolean rejectAppointment(int bookingCode) {
		    String query = "UPDATE service_booking_appointments SET status = 'Rejected' WHERE code_booking = ?";		    
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement statement = connection.prepareStatement(query)) { 
		        statement.setInt(1, bookingCode);
		        int rowsUpdated = statement.executeUpdate();
		        return rowsUpdated > 0;
		    } catch (SQLException e) {
		        e.printStackTrace(); 
		        return false;
		    }
		}

	 public boolean appointStaffForMember(int bookingCode, String staffName) {
		    String staffQuery = "SELECT code_staff FROM staffs WHERE full_name = ?";
		    String appointmentQuery = "UPDATE service_booking_appointments SET code_staff = ? WHERE code_booking = ?";
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement staffStatement = connection.prepareStatement(staffQuery);
		         PreparedStatement appointmentStatement = connection.prepareStatement(appointmentQuery)) {
		        staffStatement.setString(1, staffName);
		        try (ResultSet resultSet = staffStatement.executeQuery()) {
		            int codeStaff = 0;
		            if (resultSet.next()) {
		                codeStaff = resultSet.getInt("code_staff");
		            } else {
		                return false;
		            }
		            appointmentStatement.setInt(1, codeStaff);
		            appointmentStatement.setInt(2, bookingCode);

		            int rowsUpdated = appointmentStatement.executeUpdate();
		            return rowsUpdated > 0;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace(); 
		        return false;
		    }
		}
	 
	 public List<Appointment> getHistoryForStaff(Staff loggedInStaff) {
		    List<Appointment> appointments = new ArrayList<>();

		    String query = "SELECT app.code_booking, c.full_name AS customer_name, c.address AS customer_address, c.phone_no AS customer_phone, app.booking_date, app.status " +
		                   "FROM service_booking_appointments app " +
		                   "JOIN customers c ON app.code_cus = c.code_cus " +
		                   "WHERE app.code_staff = ?";
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setInt(1, loggedInStaff.getCode_staff());
		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            while (resultSet.next()) {
		                int code_booking = resultSet.getInt("code_booking");
		                String customerName = resultSet.getString("customer_name");
		                String customerAddress = resultSet.getString("customer_address");
		                String customerPhone = resultSet.getString("customer_phone");
		                LocalDate booking_date = resultSet.getDate("booking_date").toLocalDate();
		                String status = resultSet.getString("status");

		                Appointment appointment = new Appointment(code_booking, loggedInStaff.getCode_staff(), customerAddress, customerPhone, null, booking_date, customerName, status, 0, 0);
		                appointments.add(appointment);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return appointments;
		}

	 public List<Appointment> getOrdersForStaff(Staff loggedInStaff) {
		    List<Appointment> appointments = new ArrayList<>();

		    String query = "SELECT app.code_booking, c.full_name AS customer_name, c.address AS customer_address, c.phone_no AS customer_phone, app.booking_date, app.status " +
		               "FROM service_booking_appointments app " +
		               "JOIN customers c ON app.code_cus = c.code_cus " +
		               "WHERE app.code_staff = ? " +
		               "AND app.status IN ('Pending', 'Reschedule Requested', 'Accepted')";
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setInt(1, loggedInStaff.getCode_staff());
		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            while (resultSet.next()) {
		                int code_booking = resultSet.getInt("code_booking");
		                String customerName = resultSet.getString("customer_name");
		                String customerAddress = resultSet.getString("customer_address");
		                String customerPhone = resultSet.getString("customer_phone");
		                LocalDate booking_date = resultSet.getDate("booking_date").toLocalDate();
		                String status = resultSet.getString("status");

		                Appointment appointment = new Appointment(code_booking, loggedInStaff.getCode_staff(), customerAddress, customerPhone, null, booking_date, customerName, status, 0, 0);
		                appointments.add(appointment);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return appointments;
		}
	 
	 public void completeAppointment(Appointment appointment) {
		    String query = "UPDATE service_booking_appointments SET status = 'Completed' WHERE code_booking = ?";
		    
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setInt(1, appointment.getCode_booking());
		        
		        int rowsAffected = preparedStatement.executeUpdate();
		        if (rowsAffected > 0) {
		            appointment.setStatus("Completed");
		        } else {
		            System.out.println("Failed to complete appointment: " + appointment.getCode_booking());
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

		public void requestReschedule(Appointment appointment) {
		    String query = "UPDATE service_booking_appointments SET status = 'Reschedule Requested' WHERE code_booking = ?";
		    
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setInt(1, appointment.getCode_booking());
		        
		        int rowsAffected = preparedStatement.executeUpdate();
		        if (rowsAffected > 0) {
		            appointment.setStatus("Reschedule Requested");
		        } else {
		            System.out.println("Failed to request reschedule for appointment: " + appointment.getCode_booking());
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

		public void acceptAppointment(Appointment appointment) {
		    String query = "UPDATE service_booking_appointments SET status = 'Accepted' WHERE code_booking = ?";
		    
		    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setInt(1, appointment.getCode_booking());
		        
		        int rowsAffected = preparedStatement.executeUpdate();
		        if (rowsAffected > 0) {
		            appointment.setStatus("Accepted");
		        } else {
		            System.out.println("Failed to accept appointment: " + appointment.getCode_booking());
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
}