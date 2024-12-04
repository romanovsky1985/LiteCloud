package my.litecloud.dto;

import java.time.LocalDateTime;

public class FileReadDTO {
    private Long id;
    private String text;
    private Integer size;
    private Boolean shared;
    private LocalDateTime upDateTime;



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
        return String.format("Up: %4d/%02d/%02d   Kb: %d",
                upDateTime.getYear(), upDateTime.getMonthValue(), upDateTime.getDayOfMonth(),
                size / 1024 + 1);
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

    public LocalDateTime getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(LocalDateTime upDateTime) {
        this.upDateTime = upDateTime;
    }
}
