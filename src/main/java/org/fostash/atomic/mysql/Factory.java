package org.fostash.atomic.mysql;

import org.fostash.atomic.dsl.ICondition;
import org.fostash.atomic.dsl.IDelete;
import org.fostash.atomic.dsl.IFrom;
import org.fostash.atomic.dsl.IInsert;
import org.fostash.atomic.dsl.IJoint;
import org.fostash.atomic.dsl.ISelect;
import org.fostash.atomic.dsl.IUpdate;
import org.fostash.atomic.dsl.IWhere;

/**
 *
 * Created by Fausto on 10/03/16.
 */
public final class Factory {

    /**
     * private constructor.
     */
    private Factory() {
    }

    /**
     * utility method for select from column.
     * @param c array of columns
     * @return IFrom interface
     */
    public static IFrom select(final ISelect.IColumn...c) {
        return new Select().select(c);
    }

    /**
     * utility method for from.
     * @param select ISelect interface
     * @return From instance
     */
    public static IFrom from(final ISelect select) {
        return new From(select);
    }

    /**
     * utility method for where structure.
     * @param from IFrom interface
     * @return Where instance
     */
    public static IWhere where(final IFrom from) {
        return new Where(from);
    }

    /**
     * utility method for where structure.
     * @param from IFromJoin interface
     * @return Where instance
     */
    /*public static IWhere where(final IFrom.IJoin from) {
        return new Where(from);
    }*/

    /**
     * utility method for where structure.
     * @param update IUpdate interface
     * @return Where instance
     */
    public static IWhere where(final IUpdate update) {
        return new Where(update);
    }

    /**
     * utility method for where structure.
     * @param delete IDelete interface
     * @return Where instance
     */
    public static IWhere where(final IDelete delete) {
        return new Where(delete);
    }

    /**
     * utility method for conditions.
     * @param where IWhere interface
     * @return Condition instance
     */
    public static ICondition condition(final IWhere where) {
        return new Condition(where);
    }

    /**
     * utility method for conditions.
     * @param joint IJoint interface
     * @return Condition instance
     */
    public static ICondition condition(final IJoint joint) {
        return new Condition(joint);
    }

    /**
     * utility method for joint operator.
     * @param condition ICondition interface
     * @return Joint instance
     */
    public static IJoint joint(final ICondition condition) {
        return new Joint(condition);
    }

    /**
     * utility method for joint operator.
     * @param joint IJoint interface
     * @return Joint instance
     */
    public static IJoint joint(final IJoint joint) {
        return new Joint(joint);
    }

    /**
     * utility method for update.
     * @param table IFrom.ITable interface
     * @return Update instance
     */
    public static IUpdate update(final IFrom.ITable table) {
        return new Update(table);
    }

    /**
     * utility method for insert.
     * @return Insert instance
     */
    public static IInsert insert() {
        return new Insert();
    }

    /**
     * utility method for delete.
     * @param table IFrom.ITable interface
     * @return Where instance
     */
    public static IDelete delete(final IFrom.ITable table) {
        return new Delete(table);
    }
}
