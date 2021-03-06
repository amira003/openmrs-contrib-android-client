package org.openmrs.client.databases.tables;

import org.openmrs.client.databases.DBOpenHelper;
import org.openmrs.client.databases.OpenMRSDBOpenHelper;
import org.openmrs.client.models.Encounter;

public class EncounterTable extends Table<Encounter> {
    public static final String TABLE_NAME = "encounters";

    /**
     * Number of columns without ID column
     * use as a param to
     *
     * @see org.openmrs.client.databases.tables.Table#values(int)
     */
    private static final int INSERT_COLUMNS_COUNT = 5;

    @Override
    public String crateTableDefinition() {
        return CREATE_TABLE + TABLE_NAME + "("
                + Column.ID + PRIMARY_KEY
                + Column.VISIT_KEY_ID + Column.Type.INT_TYPE_NOT_NULL
                + Column.UUID + Column.Type.TEXT_TYPE_NOT_NULL
                + Column.DISPLAY + Column.Type.TEXT_TYPE_WITH_COMMA
                + Column.ENCOUNTER_DATETIME + Column.Type.DATE_TYPE_NOT_NULL
                + Column.ENCOUNTER_TYPE + Column.Type.DATE_TYPE
                + ");";
    }

    @Override
    public String insertIntoTableDefinition() {
        return INSERT_INTO + TABLE_NAME + "("
                + Column.VISIT_KEY_ID + Column.COMMA
                + Column.UUID + Column.COMMA
                + Column.DISPLAY + Column.COMMA
                + Column.ENCOUNTER_DATETIME + Column.COMMA
                + Column.ENCOUNTER_TYPE + ")"
                + values(INSERT_COLUMNS_COUNT);
    }

    @Override
    public String dropTableDefinition() {
        return DROP_TABLE_IF_EXISTS + TABLE_NAME;
    }

    @Override
    public Long insert(Encounter tableObject) {
        DBOpenHelper helper = OpenMRSDBOpenHelper.getInstance().getDBOpenHelper();
        return helper.insertEncounter(helper.getWritableDatabase(), tableObject);
    }

    @Override
    public int update(long tableObjectID, Encounter tableObject) {
        return 0;
    }

    @Override
    public void delete(long tableObjectID) {
        DBOpenHelper openHelper = OpenMRSDBOpenHelper.getInstance().getDBOpenHelper();
        openHelper.getWritableDatabase().delete(TABLE_NAME, MasterColumn.ID + MasterColumn.EQUALS + tableObjectID, null);
    }

    public class Column extends MasterColumn {
        public static final String VISIT_KEY_ID = "visit_id";
        public static final String ENCOUNTER_DATETIME = "encounterDatetime";
        public static final String ENCOUNTER_TYPE = "type";
    }

    @Override
    public String toString() {
        return TABLE_NAME + crateTableDefinition();
    }
}
