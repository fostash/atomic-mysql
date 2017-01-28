package org.fostash.atomic.mysql;

import org.fostash.atomic.dsl.*;

import static org.fostash.atomic.mysql.SQLUtils.*;

/**
 *
 * Created by Fausto on 09/03/16.
 */
public class Condition implements ICondition {

    /** joint field. */
    private IJoint joint;
    /** where field. */
    private IWhere where;
    /** condition field. */
    private String condition;

    /**
     * constructor for IWhere interface.
     * @param aWhere IWhere interface
     */
    public Condition(final IWhere aWhere) {
        this.where = aWhere;
    }

    /**
     * constructor for IJoint interface.
     * @param aJoint IJoint interface
     */
    public Condition(final IJoint aJoint) {
        this.joint = aJoint;
    }

    @Override
    public final IJoint eq(final String f, final  Object v) {
        condition = Operation.EQ.build(f, v);
        return Factory.joint(this);
    }

    @Override
    public final IJoint notEq(final String f, final Object v) {
        condition = Operation.NOT_EQ.build(f, v);
        return null;
    }

    @Override
    public final IJoint gt(final String f, final Object v) {
        condition = Operation.GT.build(f, v);
        return Factory.joint(this);
    }

    @Override
    public final IJoint lt(final String f, final Object v) {
        condition = Operation.LT.build(f, v);
        return Factory.joint(this);
    }

    @Override
    public final IJoint ge(final String f, final Object v) {
        condition = Operation.GE.build(f, v);
        return Factory.joint(this);
    }

    @Override
    public final IJoint le(final String f, final Object v) {
        condition = Operation.LE.build(f, v);
        return Factory.joint(this);
    }

    @Override
    public final IJoint in(final String f, final Object...v) {
        condition = Operation.IN.build(f, v);
        return Factory.joint(this);
    }

    @Override
    public final IJoint notIn(final String f, final Object[] v) {
        condition = Operation.NOT_IN.build(f, v);
        return Factory.joint(this);
    }

    @Override
    public final IJoint exists(final ISelect select) {
        condition = Operation.EXISTS.build(null, select);
        return Factory.joint(this);
    }

    @Override
    public final IJoint notExists(final ISelect select) {
        condition = Operation.NOT_EXISTS.build(null, select);
        return Factory.joint(this);
    }

    @Override
    public final IJoint isNull(final String f) {
        condition = Operation.IS_NULL.build(f);
        return Factory.joint(this);
    }

    @Override
    public final IJoint isNotNull(final String f) {
        condition = Operation.IS_NOT_NULL.build(f);
        return Factory.joint(this);
    }

    @Override
    public ISql limit(int i) {
        // TODO implementation
        return null;
    }

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        if (where != null) {
            builder.append(where);
        } else if (joint != null) {
            builder.append(joint);
        }
        if (condition != null) {
            builder.append(condition);
        }
        return builder.toString();
    }

    /**
     * Operation enum.
     */
    private enum Operation {

        /** eq template. */
        EQ("{{f}} = {{v}}"),
        /** not eq template. */
        NOT_EQ("{{f}} != {{v}}"),
        /** gt template. */
        GT("{{f}} > {{v}}"),
        /** lt template. */
        LT("{{f}} < {{v}}"),
        /** ge template. */
        GE("{{f}} >= {{v}}"),
        /** le template. */
        LE("{{f}} <= {{v}}"),
        /** in template. */
        IN("{{f}} in ({{v}})"),
        /** not in template. */
        NOT_IN("{{f}} not in ({{v}})"),
        /** exists template. */
        EXISTS("exists ({{v}})"),
        /** not exists template. */
        NOT_EXISTS("not exists ({{v}})"),
        /** is null template. */
        IS_NULL("{{f}} is null"),
        /** is not null template. */
        IS_NOT_NULL("{{f}} is not null");

        /** */
        private String operation;

        /**
         * @param anOperation operation
         */
        Operation(final String anOperation) {
            this.operation = anOperation;
        }

        /**
         * build template with condition data.
         *
         * @param f column name
         * @param v value
         * @return template converted with correct formatted value
         */
        private String build(final String f, final Object v) {
            return operation.replace("{{f}}", f).replace("{{v}}",
                    SQLUtils.SQLValue.toSQL(v));
        }

        /**
         * build template for column.
         *
         * @param f column name
         * @return template converted with correct formatted value
         */
        private String build(final String f) {
            return operation.replace("{{f}}", f);
        }
    }
}
