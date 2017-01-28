package org.fostash.atomic.mysql;

import org.fostash.atomic.dsl.*;
import org.fostash.atomic.dsl.exception.SqlBuilderException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by Fausto on 09/03/16.
 */
public class Where implements IWhere {

    /** from field. */
    private IFrom from;
    /** update field. */
    private IUpdate update;
    /** delete field. */
    private IDelete delete;
    /** hasWhere field. */
    private boolean hasWhere = false;

    /** order field. */
    private List<ISelect.IColumn> order = new ArrayList<>();

    /**
     * @param aFrom aFrom
     */
    public Where(final IFrom aFrom) {
        this.from = aFrom;
    }

    /**
     * @param anUpdate anUpdate
     */
    public Where(final IUpdate anUpdate) {
        this.update = anUpdate;
    }

    /**
     * @param aDelete aDelete
     */
    public Where(final IDelete aDelete) {
        this.delete = aDelete;
    }

    @Override
    public final ICondition where() {
        setHasWhere(true);
        return Factory.condition(this);
    }

    private void setHasWhere(boolean hasWhere) {
        this.hasWhere = hasWhere;
    }

    @Override
    public final IOrder orderBy(final ISelect.IColumn... c) {
        if (c.length > 0) {
            order.addAll(Arrays.asList(c));
        }
        return this;
    }

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder(build());
        if (hasWhere) {
            builder.append(" WHERE ");
        } else if (order.size() > 0) {
            builder.append(" ORDER BY");
            builder.append(
                    order
                            .stream()
                            .map(ISelect.IColumn::getName)
                            .collect(Collectors.joining(", "))
            );
        }
        return builder.toString();
    }

    /**
     * @return where condition string
     */
    private String build() {
        if (from != null) {
            return from.toString();
        } else if (update != null) {
            return update.toString();
        } else if (delete != null) {
            return delete.toString();
        } else {
            throw new SqlBuilderException(
                    "no operation defined before where condition");
        }
    }
}
