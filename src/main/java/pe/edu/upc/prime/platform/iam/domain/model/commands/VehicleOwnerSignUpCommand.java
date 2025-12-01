package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.LocationInformation;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.CardType;

import java.time.LocalDate;

/**
 * Command to sign up a new vehicle owner in the system.
 * This command encapsulates all the necessary information required to create a Location, User, UserAccount, Membership, and Payment.
 *
 * @param name the first name of the user
 * @param lastName the last name of the user
 * @param dni the national identification number of the user
 * @param phoneNumber the phone number of the user
 * @param username the desired username for the user account
 * @param email the email address of the user
 * @param password the password for the user account
 * @param locationInformation the location information of the user
 * @param membershipDescription the membership description for the user
 * @param started the start date of the membership
 * @param over the end date of the membership
 * @param cardNumber the credit/debit card number for payment
 * @param cardType the type of the card
 * @param month the expiration month of the card
 * @param year the expiration year of the card
 * @param cvv the CVV code of the card
 */
public record VehicleOwnerSignUpCommand(String name,
                                        String lastName,
                                        String dni,
                                        String phoneNumber,
                                        String username,
                                        String email,
                                        String password,
                                        LocationInformation locationInformation,
                                        MembershipDescription membershipDescription,
                                        LocalDate started,
                                        LocalDate over,
                                        String cardNumber,
                                        CardType cardType,
                                        Integer month,
                                        Integer year,
                                        Integer cvv) {
}
