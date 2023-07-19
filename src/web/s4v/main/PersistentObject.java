package web.s4v.main;

import web.s4v.shared.*;

import java.io.*;

/**
 * Static methods to back up and restore objects form serializable classes, typically singletons.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 */


public class PersistentObject {
    public PersistentObject() {
        // empty constructor
    }

    /**
     * Save given an instance of a serializable class in given file.
     * @param object to save.
     * @param backupFile  where object is saved
     * @param <S> type of object to save, must be serializable
     * @throws SpotsForVolunteeringException if backup file is not writable or I/O error occurs during writing, or if the object's class is not serializable.
     */

    static <S extends Serializable> void backup(S object, File backupFile) throws SpotsForVolunteeringException {
        try {
            FileOutputStream fileOut = new FileOutputStream(backupFile);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(object);
            objOut.close();
            fileOut.close();
        } catch (IOException err) {
            throw new SpotsForVolunteeringException("Failed to backup object: " + err.getMessage());
        }
    }

    /**
     * Restore backup file and return saved object.
     * @param backupFile file where backup is saved
     * @return activity pool or null if not available
     * @throws SpotsForVolunteeringException if backup file is not readable or I/O error occurs during reading
     */

    static Object restore(File backupFile) throws SpotsForVolunteeringException {
        try {
            FileInputStream fileIn = new FileInputStream(backupFile);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            Object object = objIn.readObject();
            objIn.close();
            fileIn.close();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            throw new SpotsForVolunteeringException("Failed to restore object: " + e.getMessage());
        }
    }
}

