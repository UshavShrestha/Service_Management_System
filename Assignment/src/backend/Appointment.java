package backend;

import java.time.LocalDate;

public class Appointment {
    private int code_booking;
    private int code_cus;
    private String service_name;
    private LocalDate booking_date;
    private int code_ser;
    private int code_staff;
    private String customerName;
    private String staffName;
    private String phone_no;
    private String status;
    private String customerPhone;
    private String customerAddress;

    public Appointment(int code_booking, int code_cus, String staffName, String service_name, LocalDate booking_date, String customerName, String status, int code_staff, int code_ser, String phone_no) {
        this.code_booking = code_booking;
        this.code_cus = code_cus;
        this.service_name = service_name;
        this.booking_date = booking_date;
        this.customerName = customerName;
        this.staffName = staffName;
        this.status = status;
        this.code_ser = code_ser;
        this.code_staff = code_staff;
        this.phone_no = phone_no;

    }
    
    public Appointment(int code_booking, int code_cus, String customerAddress, String customerPhone, String service_name, LocalDate booking_date, String customerName, String status, int code_staff, int code_ser) {
        this.code_booking = code_booking;
        this.code_cus = code_cus;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.service_name = service_name;
        this.booking_date = booking_date;
        this.customerName = customerName;
        this.status = status;
        this.code_ser = code_ser;
        this.code_staff = code_staff;
    }


	public int getCode_booking() {
		return code_booking;
	}

	public void setCode_booking(int code_booking) {
		this.code_booking = code_booking;
	}

	public int getCode_cus() {
		return code_cus;
	}

	public void setCode_cus(int code_cus) {
		this.code_cus = code_cus;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public LocalDate getBooking_date() {
		return booking_date;
	}

	public void setBooking_date(LocalDate booking_date) {
		this.booking_date = booking_date;
	}

	public int getCode_ser() {
		return code_ser;
	}

	public void setCode_ser(int code_ser) {
		this.code_ser = code_ser;
	}

	public int getCode_staff() {
		return code_staff;
	}

	public void setCode_staff(int code_staff) {
		this.code_staff = code_staff;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	@Override
	public String toString() {
		return "Appointment [code_booking=" + code_booking + ", code_cus=" + code_cus + ", service_name=" + service_name
				+ ", booking_date=" + booking_date + ", code_ser=" + code_ser + ", code_staff=" + code_staff
				+ ", customerName=" + customerName + ", staffName=" + staffName + ", phone_no=" + phone_no + ", status="
				+ status + ", customerPhone=" + customerPhone + ", customerAddress=" + customerAddress + "]";
	}
}
