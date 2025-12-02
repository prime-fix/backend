package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.LocationInformation;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.CardType;

import java.time.LocalDate;

/**
 * Command to sign up a new auto repair in the system.
 * This command encapsulates all the necessary information required to create a Location, AutoRepair, UserAccount, User, Membership, and Payment.
 *
 * @param name the first name of the auto repair consultant
 * @param lastName the last name of the auto  consultant
 * @param dni the national identification number of the auto repair consultant
 * @param phoneNumber the phone number of the auto repair consultant
 * @param username the desired username for the auto repair account consultant
 * @param email the email address of the auto repair consultant
 * @param password the password for the auto repair account consultant
 * @param contactEmail the contact email of the auto repair consultant
 * @param ruc the RUC number of the auto repair consultant
 * @param locationInformation the location information of the auto repair consultant
 * @param membershipDescription the membership description for the auto repair consultant
 * @param started the start date of the membership
 * @param over the end date of the membership
 * @param cardNumber the credit/debit card number for payment
 * @param cardType the type of the card
 * @param month the expiration month of the card
 * @param year the expiration year of the card
 * @param cvv the CVV code of the card
 */
public record AutoRepairSignUpCommand(String name,
                                      String lastName,
                                      String dni,
                                      String phoneNumber,
                                      String username,
                                      String email,
                                      String password,
                                      String contactEmail,
                                      String ruc,
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
