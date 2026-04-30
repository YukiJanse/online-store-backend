package se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.Address;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
public class ShippingInfo {
    private String name;
    private Address address;

    private ShippingInfo(String name, Address address) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can not be blank");
        }

        if (address == null) {
            throw new IllegalArgumentException("Address can not be null");
        }

        this.name = name;
        this.address = address;
    }

    public static ShippingInfo of(String name, Address address) {
        return new ShippingInfo(name, address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ShippingInfo shippingInfo)) return false;

        return name.equals(shippingInfo.getName())
                && address.equals(shippingInfo.getAddress());
    }

    @Override
    public String toString() {
        return "name: " + name + ", address: " + address;
    }
}
