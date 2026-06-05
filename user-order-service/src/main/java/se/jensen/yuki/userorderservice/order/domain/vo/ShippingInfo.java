package se.jensen.yuki.userorderservice.order.domain.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.jensen.yuki.userorderservice.user.domain.vo.Address;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
public class ShippingInfo {
    private String firstName;
    private String lastName;
    @Embedded
    private Address address;

    private ShippingInfo(String firstName, String lastName, Address address) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name can not be blank");
        }

        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name can not be blank");
        }

        if (address == null) {
            throw new IllegalArgumentException("Address can not be null");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public static ShippingInfo of(String firstName, String lastName, Address address) {
        return new ShippingInfo(firstName, lastName, address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ShippingInfo shippingInfo)) return false;

        return firstName.equals(shippingInfo.getFirstName())
                && lastName.equals(shippingInfo.getLastName())
                && address.equals(shippingInfo.getAddress());
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + ", lastName: " + lastName + ", address: " + address;
    }
}
