package dronePackage;

public interface Iterator {

    boolean hasNext();
    ManagedObject next();
    void reset();
    /**
     * @param a_Id Unique identifier for the seeked object. Returns null if not found on the interation
     * 
     */
    ManagedObject getElement(String a_Id);
    /**
     * 
     * @param a_Object
     * @return 1 means the element was added successfully, 0 when the id already existed.
     */
    int addElement(ManagedObject a_Object);
    boolean removeElement(String a_Identifier);
    boolean IdExists(String a_Identifier);
    boolean moveTo(String a_itemId, Iterator a_iterator);
    int length();

}