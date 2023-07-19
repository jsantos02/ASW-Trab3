package web.s4v.main;

import web.s4v.shared.SpotsForVolunteeringException;
import web.s4v.shared.VolunteerInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing an Activity.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @extends PersistentObject
 * @implements Serializable
 */

public class VolunteerPool extends PersistentObject implements Serializable {
    // private static File backupFile;
    private static VolunteerPool volunteerPool;
    private Set<Volunteer> volunteers = new HashSet<>();
  //  private static PersistentObject persistentObject;


    public VolunteerPool() {
        // empty constructor
    }


    /**
     * Get the current file used for backup. It is an absolute pathname
     * @return backup file.
     */

    /* public static File getBackupFile() {
        return backupFile;
    } */

    /**
     * Change the file used for backups. This file is persisted as an absolute pathname, even if given as a relative pathname
     * @param backupFile to save the serialization.
     */
    /* public static void setBackupFile(File backupFile) {
        if (!backupFile.isAbsolute()) {
            backupFile = backupFile.getAbsoluteFile();
        }
        VolunteerPool.backupFile = backupFile;
    }

    /**
     * Convenience method to set the backup file as a string
     * @param name of backup file.
     */
/*
    public static void setBackupFile(String name) {
        backupFile = new File(name);
    }

    /**
     * Backup volunteer pool instance to the file
     * @throws SpotsForVolunteeringException raised by PersistentObject.backup(Serializable, File)
     */
/*
    static void backup() throws SpotsForVolunteeringException {
        PersistentObject.backup(volunteerPool, backupFile);
    }
*/
    /**
     * Restore a volunteer pool instance from the backup file
     * @return VolunteerPool
     * @throws SpotsForVolunteeringException raised by PersistentObject.restore(File)
     */
/*
    static VolunteerPool restore() throws SpotsForVolunteeringException {
        return (VolunteerPool) PersistentObject.restore(backupFile);
    }

    /**
     * Singleton
     * @return instance
     */
    public static VolunteerPool getInstance() {
        if(volunteerPool == null)
            return volunteerPool = new VolunteerPool();
        else
            return volunteerPool;
    }

    /**
     * Reset pool to its initial state. Use it only for unit testing
     */
/*
    void reset() {
        volunteerPool = null;
        volunteers.clear();
        try (FileWriter writer = new FileWriter(backupFile)) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("File reset error");
        }
    }

    /**
     * Searches a volunteer
     * @param volunteerId id of the volunteer
     * @return a volunteer
     */
    Volunteer getVolunteer(String volunteerId) {
        for(Volunteer volunteer : volunteers) {
            if(volunteer.getId().equals(volunteerId))
                return volunteer;
        }
        return null;
    }

    /**
     * Register a volunteer with given information. This volunteer is indexed to speedup retrieval.
     * @param volunteerInfo to initialize volunteer
     * @return id of volunteer
     * @throws SpotsForVolunteeringException if info id null of if ID is has already set.
     */
    Volunteer registerVolunteer(VolunteerInfo volunteerInfo) throws SpotsForVolunteeringException {
        if(volunteerInfo==null) throw new SpotsForVolunteeringException("VolunteerInfo null");
        for(Volunteer search : volunteers) {
            if(search.getId().equals(volunteerInfo.getId())) throw new SpotsForVolunteeringException("already registered");
        }
        Volunteer volunteer = new Volunteer(volunteerInfo);
        volunteers.add(volunteer);
        return volunteer;
    }

    /**
     * removes a volunteer
     * @param volunteerId id of the volunteer to be removed.
     */

    void removeVolunteer(String volunteerId) {
        for(Volunteer volunteer : volunteers) {
            if(volunteer.getId().equals(volunteerId))
                volunteers.remove(volunteer);
        }
    }
}
