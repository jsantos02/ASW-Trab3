package web.s4v.shared;
import java.io.Serializable;


/**
 * Information on a volunteer shared between client and server including a private key for authentication.
 * An instance of this class is returned by Volunteer.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @extends VolunteerInfo
 * @implements Serializable
 */
public class AuthenticatedVolunteerInfo extends VolunteerInfo implements Serializable {

    private String privateKey;

    /**
     * An empty volunteer info with password
     */
    public AuthenticatedVolunteerInfo() {
        super();
    }

    /**
     * A named volunteer info with password. Other properties must be set using setters.
     * @param info on volunteer
     * @param privateKey for this volunteer
     */
    public AuthenticatedVolunteerInfo(VolunteerInfo info, String privateKey ) {
        super(info.getName());
        this.privateKey = privateKey;
    }

    /**
     * Private key
     * @return privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }












}