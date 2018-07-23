package org.hibernate.bugs.hhh12641;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * A JPA entity that takes care of {@link MutableCreationDate} and/or {@link MutableModifiedDate} entities.
 *
 * </br></br>
 * <A HREF="http://download.oracle.com/otn-pub/jcp/persistence-2_1-fr-eval-spec/JavaPersistence.pdf">
 * JPA 2.1 spec, section 3.5:</A>
 * <blockquote>A single class must not have more than one lifecycle callback method for the same lifecycle
 * event. The same method may be used for multiple callback events</blockquote>
 */
public class EntityTimestampsTrackingListener {
    public EntityTimestampsTrackingListener() {
        super();
    }

    @PrePersist
    public void touchForCreate(Object target) {
        if (target instanceof MutableCreationDate) {
            MutableCreationDate tracker = (MutableCreationDate) target;
            if (tracker.getCreatedDate() == null) {
                tracker.setCreatedDate(new Date());
            }
        }
        // workaround for invoking @PreUpdate when @prePersist is called. javadoc comment above
        touchForUpdate(target);
    }

    @PreUpdate
    public void touchForUpdate(Object target) {
        if (target instanceof MutableModifiedDate) {
            MutableModifiedDate tracker = (MutableModifiedDate) target;
            tracker.setLastModified(new Date());
        }
    }
}
