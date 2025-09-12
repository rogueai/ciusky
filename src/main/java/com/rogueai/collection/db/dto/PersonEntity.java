package com.rogueai.collection.db.dto;

public class PersonEntity {

    public long id;

    public String name;

    public String contactNumber;

    public String email;

    public boolean supervisor;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("PersonDto [id=%s, name=%s, contactNumber=%s, supervisor=%s]", id, name, contactNumber,
                supervisor);
    }

}
