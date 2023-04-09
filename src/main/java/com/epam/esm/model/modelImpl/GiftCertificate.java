package com.epam.esm.model.modelImpl;

import com.epam.esm.model.Entity;
import com.epam.esm.util.CreateValidationGroup;
import com.epam.esm.util.UpdateValidationGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificate implements Entity {

    private final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private Integer id;

    @NotNull(message = "Name is missing", groups = CreateValidationGroup.class)
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters",
            groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private String name;

    @NotNull(message = "Description is missing", groups = CreateValidationGroup.class)
    @Size(min = 2, max = 30, message = "Description should be between 2 and 30 characters",
            groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private String description;

    @NotNull(message = "Price is missing", groups = CreateValidationGroup.class)
    @Min(value = 0, message = "Price should be greater than 0",
            groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private Double price;

    @NotNull(message = "Duration is missing", groups = CreateValidationGroup.class)
    @Min(value = 0, message = "Duration should be greater than 0",
            groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private Integer duration;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime createDate;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime lastUpdateDate;

    @Valid
    private List<Tag> tags = new ArrayList<>();

    public GiftCertificate() {

    }

    public GiftCertificate(Integer id, String name, String description, Double price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public GiftCertificate(Integer id, String name, String description, Double price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags.toString() +
                '}';
    }
}
