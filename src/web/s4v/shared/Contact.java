package web.s4v.shared;

import java.io.Serializable;

/**
 * Class representing the contact of a volunteer, such as phone or an email.
 * Composed only by the class constructor.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @implements Serializable
 */

public class Contact implements Serializable {
    private String address;
    private ContactType type;

    /**
     * Constructor to create a contact
     * @param address of the contact
     * @param type of contact
     */
    public Contact(String address, ContactType type) {
        this.address = address;
        this.type = type;
    }

    public Contact() {
    }


    /**
     * Get contact type
     * @return type
     */
    public ContactType getType() {
        return type;
    }

    /**
     * Get the contact address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

