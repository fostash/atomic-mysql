package org.fostash.atomic.mysql;

import org.fostash.atomic.dsl.ICondition;
import org.fostash.atomic.dsl.IDelete;
import org.fostash.atomic.dsl.IFrom;

/**
 *
 * Created by Fausto on 25/04/16.
 */
public class Delete implements IDelete {

    /** table field. */
    private final IFrom.ITable table;

    /**
     * @param aTable table
     */
    public Delete(final IFrom.ITable aTable) {
        this.table = aTable;
    }

    @Override
    public final ICondition where() {
        return Factory.where(this).where();
    }

    @Override
    public final String toString() {
        return "DELETE FROM " + table;
    }
}
