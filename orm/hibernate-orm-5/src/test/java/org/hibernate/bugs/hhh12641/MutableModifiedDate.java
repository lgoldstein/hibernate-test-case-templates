package org.hibernate.bugs.hhh12641;

import java.util.Date;

public interface MutableModifiedDate extends LastModifiedDateTracker {
    void setLastModified(Date lastModifiedDate);
}
