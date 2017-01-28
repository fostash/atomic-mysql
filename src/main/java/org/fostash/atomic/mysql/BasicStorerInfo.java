package org.fostash.atomic.mysql;

import org.fostash.atomic.dsl.ISelect;

import java.util.Arrays;
import java.util.List;

import static org.fostash.atomic.mysql.Select.Column.column;

/**
 * Basic store info utility class.
 * Created by Fausto on 15/05/16.
 */
public final class BasicStorerInfo {

    /** static field for created_by base column information. */
    private static final String CREATED_BY = "created_by";
    /** static field for created_on base column information. */
    private static final String CREATED_ON = "created_by";
    /** static field for updated_by base column information. */
    private static final String UPDATED_BY = "updated_by";
    /** static field for updated_on base column information. */
    private static final String UPDATED_ON = "updated_by";
    /** static field for deleted_by base column information. */
    private static final String DELETED_BY = "deleted_by";
    /** static field for deleted_on base column information. */
    private static final String DELETED_ON = "deleted_by";

    /**
     * @return base info for object creation
     */
    public static List<ISelect.IColumn> getCreateBaseInfo() {
        return Arrays.asList(column(BasicStorerInfo.CREATED_BY), column(BasicStorerInfo.CREATED_ON));
    }

    /**
     * @return base info for object update
     */
    public static List<ISelect.IColumn> getUpdateBaseInfo() {
        return Arrays.asList(column(BasicStorerInfo.UPDATED_BY), column(BasicStorerInfo.UPDATED_ON));
    }

    /**
     * @return base info for object logic deletion
     */
    public static List<ISelect.IColumn> getDeleteBaseInfo() {
        return Arrays.asList(column(BasicStorerInfo.DELETED_BY), column(BasicStorerInfo.DELETED_ON));
    }
}
