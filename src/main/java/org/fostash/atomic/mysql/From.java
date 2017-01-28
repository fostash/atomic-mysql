package org.fostash.atomic.mysql;

import org.fostash.atomic.dsl.IFrom;
import org.fostash.atomic.dsl.ISelect;
import org.fostash.atomic.dsl.IWhere;

/**
 *
 * Created by Fausto on 09/03/16.
 */
public class From implements IFrom {

    /** select field. */
    private ISelect select;
    /** sigle table field */
    private ITable table;

    /**
     * @param aSelect ISelect interface
     */
    public From(final ISelect aSelect) {
        this.select = aSelect;
    }

    /**
     * @param t table list
     * @return IWhere interface
     */
    @Override
    public final IWhere from(final ITable t) {
        table = t;
        return Factory.where(this);
    }

    @Override
    public IWhere where() {
        return Factory.where(this);
    }

    @Override
    public final String toString() {
        return select
                + " FROM " + table;
    }
}
