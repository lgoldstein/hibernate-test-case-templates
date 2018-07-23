package org.hibernate.bugs.hhh12641;

import java.util.Date;

public interface CreationDateTracker {
    /**
     * Name of logical JPA column mapping to be used. <B>Caveat emptor:</B> if
     * you change the name of the <I>get/set</I>ter(s) must also change this value as well.
     */
    String PROP_CREATED_DATE = "createdDate";

    /**
     * Matching mapped physical column in database. <B>Caveat emptor:</B> if
     * you change the name of the <I>get/set</I>ter(s) must also change this
     * value. The same applies if the JPA mapping strategy of logical to
     * physical names is changed
     */
    String DB_FIELD_CREATED_DATE = "created_date";

    /**
     * @return The timestamp value at which the tracked entity was created
     */
    Date getCreatedDate();
}
