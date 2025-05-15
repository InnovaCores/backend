package pe.edu.upc.managewise.backend.members.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
@Embeddable
public record StreetAddress(String streetAddress) {
    public StreetAddress() {
        this(null);
    }
    public StreetAddress {
        if (streetAddress == null || streetAddress.isBlank()) {
            throw new IllegalArgumentException("streetAddress cannot be null or blank");
        }
    }
}