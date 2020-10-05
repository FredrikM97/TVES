package autopark;

public class StatusWrapper <T>{
	public static final String OK = "OK";
	public static final String NO_INIT = "Variable missing initialization";
	public static final String UNEXPECTED_STATE = "Field is in an unexpected state";
	public static final String NOT_POSSIBLE = "This method cannot be executed";
	
	private T content = null;
	private String status = OK;
	private String message = "";
	
	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public StatusWrapper(T content, String status, String info) {
		this.content = content;
		this.status = status;
		this.message = info;
	}

	public StatusWrapper(T content, String status) {
		this.content = content;
		this.status = status;
	}

	public StatusWrapper(T content) {
		this.content = content;
	}
	
	public StatusWrapper(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString(){
		return "Content: "+this.getContent().toString()+" \tStatus: "+this.getStatus()+" \tmessage: "+this.getMessage();
	}
}
