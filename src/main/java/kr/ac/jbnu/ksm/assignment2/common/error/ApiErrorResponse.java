package kr.ac.jbnu.ksm.assignment2.common.error;

import java.util.Map;

public class ApiErrorResponse {

    private String timestamp; // ISO-8601
    private String path;
    private int status;
    private String code;
    private String message;
    private Map<String, Object> details; // 선택

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(String timestamp, String path, int status, String code, String message, Map<String, Object> details) {
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Map<String, Object> getDetails() { return details; }
    public void setDetails(Map<String, Object> details) { this.details = details; }
}
