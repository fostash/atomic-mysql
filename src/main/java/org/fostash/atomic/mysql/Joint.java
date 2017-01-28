package org.fostash.atomic.mysql;

import org.fostash.atomic.dsl.ICondition;
import org.fostash.atomic.dsl.IJoint;
import org.fostash.atomic.dsl.ISelect;
import org.fostash.atomic.dsl.IOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by Fausto on 08/03/16.
 */
public class Joint implements IJoint, IOrder {

    /** joiner field. */
    private String joiner;
    /** condition field. */
    private ICondition condition;
    /** joint field. */
    private IJoint joint;

    /**
     * @param aCondition condition
     */
    public Joint(final ICondition aCondition) {
        this.condition = aCondition;
    }

    /**
     * @param aJoint aJoint
     */
    public Joint(final IJoint aJoint) {
        this.joint = aJoint;
    }

    @Override
    public final ICondition and() {
        joiner = " AND ";
        return Factory.condition(this);
    }

    @Override
    public final ICondition openOr() {
        joiner = " AND ( ";
        return Factory.condition(this);
    }

    @Override
    public final ICondition or() {
        joiner = " OR ";
        return Factory.condition(this);
    }

    @Override
    public final IJoint endOr() {
        joiner = " ) ";
        return Factory.joint(this);
    }

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        if (condition != null) {
            builder.append(condition);
        } else if (joint != null) {
            builder.append(joint);
        }
        if (joiner != null) {
            builder.append(joiner);
        }
        if (order.size() > 0) {
            builder.append(" ORDER BY ");
            builder.append(
                    order
                    .stream()
                    .map(ISelect.IColumn::getName)
                    .collect(Collectors.joining(", "))
            );
        }
        return builder.toString();
    }

    /** order field. */
    private List<ISelect.IColumn> order = new ArrayList<>();

    @Override
    public final IOrder orderBy(final ISelect.IColumn... c) {
        if (c.length > 0) {
            order.addAll(Arrays.asList(c));
        }
        return this;
    }
}
