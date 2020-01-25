package okon;

public class Message {
    private String description;
    private String result;

    public Message(String description, String result) {
        this.description = description;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
