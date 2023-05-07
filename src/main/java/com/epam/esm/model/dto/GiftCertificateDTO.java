package com.epam.esm.model.dto;

import com.epam.esm.controller.util.CreateValidationGroup;
import com.epam.esm.controller.util.UpdateValidationGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO>
        implements DTO, Comparator<GiftCertificateDTO> {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private Long id;

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
    private Set<TagDTO> tagDTOs = new HashSet<>();

    public GiftCertificateDTO() {

    }

    public GiftCertificateDTO(Long id, String name, String description, Double price, Integer duration,
                              LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public GiftCertificateDTO(Long id, String name, String description, Double price, Integer duration,
                              LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<TagDTO> tagDTOs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagDTOs = tagDTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<TagDTO> getTags() {
        return tagDTOs;
    }

    public void setTags(Set<TagDTO> tagDTOs) {
        this.tagDTOs = tagDTOs;
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
                ", tags=" + tagDTOs.toString() +
                '}';
    }

    @Override
    public int compare(GiftCertificateDTO o1, GiftCertificateDTO o2) {
        LocalDateTime date1 = o1.getLastUpdateDate();
        LocalDateTime date2 = o2.getLastUpdateDate();
        return date1.compareTo(date2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftCertificateDTO that = (GiftCertificateDTO) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(price, that.price)) return false;
        return Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }
}
