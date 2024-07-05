package backend;

import javafx.beans.property.*;

public class Customer {
    private final IntegerProperty code;
    private final StringProperty fullName;
    private final StringProperty address;
    private final StringProperty phoneNo;
    private final StringProperty email;
    private final StringProperty password;

    public Customer(int code, String fullName, String address, String phoneNo, String email, String password) {
        this.code = new SimpleIntegerProperty(code);
        this.fullName = new SimpleStringProperty(fullName);
        this.address = new SimpleStringProperty(address);
        this.phoneNo = new SimpleStringProperty(phoneNo);
        this.email = new SimpleStringProperty(email);
		this.password = new SimpleStringProperty(password);
    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty phoneNoProperty() {
        return phoneNo;
    }

    public StringProperty emailProperty() {
        return email;
    }
    
    public StringProperty passwordProperty() {
        return password;
    }

    // Getters
    public int getCode() {
        return code.get();
    }

    public String getFullName() {
        return fullName.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPhoneNo() {
        return phoneNo.get();
    }

    public String getEmail() {
        return email.get();
    }
    
    public String getPassword() {
        return password.get();
    }

    public void setCode(int code) {
        this.code.set(code);
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo.set(phoneNo);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public String toString() {
        return "Customer [code=" + code + ", fullName=" + fullName + ", address=" + address + ", phoneNo=" + phoneNo
                + ", email=" + email +", password=" + password + "]";
    }
}
