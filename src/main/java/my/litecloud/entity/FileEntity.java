package my.litecloud.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String name;

    private Integer size;

    private Boolean shared;

    private Boolean editable;

    @Column(name = "up_datetime")
    private LocalDateTime upDateTime;

    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity owner;



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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public LocalDateTime getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(LocalDateTime upDateTime) {
        this.upDateTime = upDateTime;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }
}
