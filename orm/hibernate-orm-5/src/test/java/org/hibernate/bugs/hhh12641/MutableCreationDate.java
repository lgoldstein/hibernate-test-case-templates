package org.hibernate.bugs.hhh12641;

import java.util.Date;

public interface MutableCreationDate extends CreationDateTracker {
    void setCreatedDate(Date createdDate);
}
