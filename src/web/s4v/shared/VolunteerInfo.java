package web.s4v.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static web.s4v.shared.IDGenerator.generateID;

/**
 * Information on a volunteer shared between client and server.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @implements Serializable
 */

public class VolunteerInfo implements Serializable {
    private String id;
    private String name;
    private List<Contact> contacts;

    /**
     * An empty volunteer info
     */

    public VolunteerInfo() {
        contacts = new ArrayList<Contact>();
        id = generateID();
    }

    /**
     * A named volunteer info. Other properties must be set using setters.
     * @param name of volunteer
     */
    public VolunteerInfo(String name) {
        contacts = new ArrayList<Contact>();
        this.name = name;
        id = generateID();
    }

    /**
     * Add a contact to this volunteer.
     * @param contact to add
     */

    public void addContact(Contact contact) {
        if(contacts == null) {
            contacts = new ArrayList<>();
        }
        contacts.add(contact);
    }

    /**
     * Convenience method to add a contact to this volunteer by providing address and type.
     * @param address of volunteer
     * @param type of address
     */

    public void addContact(String address,ContactType type) {
        Contact contact = new Contact(address,type);
        addContact(contact);
    }

    /**
     * List of contacts of this volunteer
     * @return list of contacts
     */

    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * Get first contact of given type, if it exists
     * @param type of contact
     * @return contact or null
     */

    public String getContact(ContactType type) {
        for (Contact contact : this.contacts) {
            if (contact.getType() == type) {
                return contact.getAddress();
            }
        }
        return null;
    }

    /**
     * Remove the first occurrence of address in contacts
     * @param address to remove
     */

    public void removeContactWithAddress(String address) {
        if(contacts == null) {
            return;
        }

        for(Contact contact: this.contacts) {
            if(contact.getAddress().equals(address)) {
                this.contacts.remove(contact);
                return;
            }
        }

        return;
    }

    /**
     * The id of this volunteer. When created the id is null and is only set when a Volunteer is created.
     * @return id
     */

    public String getId() {
        return id;
    }
    
    public String getId(String name){
        if (this.name.equals(name))
            return id;
        else return null;
    }

    /**
     * Sets an id for this volunteer if none was set yet.
     * Ids cannot be changed after assigned.
     * This method is called only by Volunteer.
     * @param id to set
     */
    
    public void setId(String id) {
        if(this.id == null) this.id = id;
    }

    /**
     * Name of volunteer
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * Set or change the name of the volunteer
     * @param name to set
     */

    public void setName(String name) {
        this.name = name;
    }
}
