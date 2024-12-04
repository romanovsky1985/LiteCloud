package my.litecloud.dto;

public class FileReadDTO {
    private Long id;
    private String text;
    private Integer size;
    private Boolean shared;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSize() {
        return size;
    }

    public String getInfo() {
        return String.format("Up: 2024/12/12   Kb: %d", size / 1024 + 1);
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }
}
