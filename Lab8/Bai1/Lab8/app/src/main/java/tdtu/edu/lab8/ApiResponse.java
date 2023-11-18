package tdtu.edu.lab8;

import java.util.List;

public class ApiResponse {
    private boolean status;
    private List<Student> data;
    private String message;

    public ApiResponse(boolean status, List<Student> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Student> getData() {
        return data;
    }

    public void setData(List<Student> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
