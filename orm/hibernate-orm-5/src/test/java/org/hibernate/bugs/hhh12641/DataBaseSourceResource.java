package org.hibernate.bugs.hhh12641;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = DataBaseSourceResource.TABLE_NAME)
@DiscriminatorValue("DATA_BASE")
@PrimaryKeyJoinColumn(name = "id")
@Access(AccessType.PROPERTY)
public class DataBaseSourceResource extends DataSourceResource {
    private static final long serialVersionUID = 2003685890186487451L;

    public static final String TABLE_NAME = "DATA_BASE_SETTINGS";

    private String dbName;
    private String dbType;
    private String maintenanceDb;
    private String query;
    private String tableName;

    public DataBaseSourceResource() {
        super();
    }

    @Column(nullable = false)
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Column
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Column(nullable = false, length = 50)
    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Column
    public String getMaintenanceDb() {
        return maintenanceDb;
    }

    public void setMaintenanceDb(String maintenanceDb) {
        this.maintenanceDb = maintenanceDb;
    }

    @Column
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return super.toString()
            + "[dbName=" + getDbName()
            + ", tableName=" + getTableName()
            + ", query=" + getQuery()
            + "]";
    }
}
