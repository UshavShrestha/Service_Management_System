package backend;

public class Staff {
    private int code_staff;
    private String full_name;
    private int role;
    private String phone_no;
    private String email;
    private String service_name; 
    private String password;

    public Staff(int code_staff, String full_name, int role, String phone_no, String email, String service_name, String password) {
        this.code_staff = code_staff;
        this.full_name = full_name;
        this.role = role;
        this.phone_no = phone_no;
        this.email = email;
        this.service_name = service_name;
        this.setPassword(password);
    }

    public int getCode_staff() {
        return code_staff;
    }

    public void setCode_staff(int code_staff) {
        this.code_staff = code_staff;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Staff [code_staff=" + code_staff + ", full_name=" + full_name + ", role=" + role + ", phone_no="
				+ phone_no + ", email=" + email + ", service_name=" + service_name + ", password=" + password + "]";
	}
}
