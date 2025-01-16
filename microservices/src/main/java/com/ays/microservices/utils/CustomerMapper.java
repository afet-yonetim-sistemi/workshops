package com.ays.microservices.utils;

import com.ays.microservices.dtos.AddressResponse;
import com.ays.microservices.dtos.CustomerResponse;
import com.ays.microservices.dtos.PaymentMethodResponse;
import com.ays.microservices.model.Address;
import com.ays.microservices.model.Customer;
import com.ays.microservices.model.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = CustomerResponse.from(customer);
        // Problem: Exposing all data without filtering
        return response;
    }

    public AddressResponse toAddressResponse(Address address) {
        AddressResponse response = new AddressResponse();
        response.setId(address.getId());                // Exposing internal ID
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setZipCode(address.getZipCode());     // Potential PII data
        response.setCountry(address.getCountry());
        response.setIsDefault(address.getIsDefault());
        response.setAddressType(address.getAddressType());
        return response;
    }

    public PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod) {
        PaymentMethodResponse response = new PaymentMethodResponse();
        response.setId(paymentMethod.getId());         // Exposing internal ID
        response.setCardNumber(paymentMethod.getCardNumber()); // Sensitive data
        response.setCardHolder(paymentMethod.getCardHolder()); // PII data
        response.setExpiryMonth(paymentMethod.getExpiryMonth()); // Sensitive data
        response.setExpiryYear(paymentMethod.getExpiryYear());   // Sensitive data
        response.setCardType(determineCardType(paymentMethod.getCardNumber())); // Processing sensitive data
        response.setIsDefault(paymentMethod.getIsDefault());
        return response;
    }

    // Problem: Card type determination logic in DTO mapper
    private String determineCardType(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            return "VISA";
        } else if (cardNumber.startsWith("5")) {
            return "MASTERCARD";
        }
        return "UNKNOWN";
    }
}