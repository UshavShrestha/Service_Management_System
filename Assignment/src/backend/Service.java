package backend;

public class Service {
	private int code_ser;
	private String service_name;
	private String description;
	private String cost;
	private String duration;
	public Service() {
		this.code_ser =0;
		this.service_name="";
		this.description="";
		this.cost="";
		this.duration="";
	}
	
	public Service(int code_ser, String service_name, String description, String cost, String duration) {
		super();
		this.code_ser = code_ser;
		this.service_name = service_name;
		this.description = description;
		this.cost = cost;
		this.duration = duration;
	}

	public int getCode_ser() {
		return code_ser;
	}

	public void setCode_ser(int code_ser) {
		this.code_ser = code_ser;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Service [code_ser=" + code_ser + ", service_name=" + service_name + ", description=" + description
				+ ", cost=" + cost + ", duration=" + duration + "]";
	}
}
