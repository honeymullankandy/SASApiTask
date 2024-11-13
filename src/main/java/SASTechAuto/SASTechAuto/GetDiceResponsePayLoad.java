package SASTechAuto.SASTechAuto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetDiceResponsePayLoad {
    private String status;
    private List<DataItem> data;

    // Getter and Setter for 'status'
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter and Setter for 'data'
    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }
}