package org.fostash.atomic.mysql;


import org.fostash.atomic.dsl.IFrom;
import org.fostash.atomic.dsl.ISql;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Table oracle implementation.
 */
public final class Table implements IFrom.ITable {

    private int type;
    /** name field. */
    private String name;
    /** alias field. */
    private String alias;

    public IFrom.IJoin joinWith;

    /**
     * @param tName table name
     * @param tAlias table alias
     */
    private Table(final String tName, final String tAlias, final int type) {
        this.name = tName;
        this.alias = tAlias;
        this.type = type;
    }

    /**
     * @param name table name
     * @return ITable interface
     */
    public static IFrom.ITable table(final String name) {
        return table(name, null, 0);
    }

    /**
     * @param name table name
     * @param alias table alias
     * @return ITable interface
     */
    public static IFrom.ITable table(final String name, final String alias) {
        return new Table(name, alias, 0);
    }

    /**
     * @param name table name
     * @param alias table alias
     * @return ITable interface
     */
    public static IFrom.ITable table(final String name, final String alias, final int type) {
        return new Table(name, alias, type);
    }

    /**
     * @param select select from table
     * @param alias select from table alias
     * @return ITable interface
     */
    public static IFrom.ITable table(final ISql select, final String alias) {
        return table(select.toString(), alias, 2);
    }

    private static IFrom.ITable table(final IFrom.ITable table) {
        return table(table.toString());
    }

    /**
     * @param name table name
     * @param alias table alias
     * @return IJoinTable interface
     */
    @Override
    public IFrom.IJoin innerJoin(final String name, final String alias) {
        type = 1;
        joinWith = new Join(JoinType.INNER, table(name, alias), this);
        return joinWith;
    }

    /**
     * @param name table name
     * @param alias table alias
     * @return IJoinTable interface
     */
    @Override
    public IFrom.IJoin leftJoin(final String name, final String alias) {
        type = 1;
        joinWith = new Join(JoinType.LEFT, table(name, alias), this);
        return joinWith;
    }

    /**
     * @param name table name
     * @param alias table alias
     * @return IJoinTable interface
     */
    @Override
    public IFrom.IJoin rightJoin(final String name, final String alias) {
        type = 1;
        joinWith = new Join(JoinType.RIGHT, table(name, alias), this);
        return joinWith;
    }

    private Supplier<String> compose = () -> {
        final String s;
        switch (type) {
            case 0:
            case 1:
                s = name + " " + Optional.ofNullable(alias).orElse("") + Optional.ofNullable(joinWith).map(Object::toString).orElse("");
                break;
            case 2:
                s = "( " + name + ") ";
                break;
            default:
                s = null;
        }
        return s;
    };

    @Override
    public String toString() {
        return compose.get();
    }



    /**
     * IJoinTable implementation.
     */
    public static final class Join implements IFrom.IJoin {

        /** */
        private IFrom.ITable parent;
        /** joinType field. */
        private final JoinType joinType;
        /** rightT field. */
        private final IFrom.ITable rightT;
        /** condition field. */
        private IFrom.IJoinCondition condition;

        /**
         * @param aJoinType joinType
         * @param aRightT rightT
         */
        private Join(final JoinType aJoinType,
                     final IFrom.ITable aRightT,
                     final IFrom.ITable parent) {
            this.parent = parent;
            this.joinType = aJoinType;
            this.rightT = aRightT;
        }

        @Override
        public String toString() {
            return " " + joinType.value()
                    + " "
                    + rightT
                    + " ON "
                    + condition;
        }

        @Override
        public IFrom.ITable on(final String left, final String right) {
            condition = new JoinCondition(left, right);
            return table(parent);
        }
    }

    /**
     * JoinType enum.
     */
    private enum JoinType {
        /** inner join type. */
        INNER {
            @Override
            public String value() {
                return "INNER JOIN";
            }
        },
        /** left outer join type. */
        LEFT {
            @Override
            public String value() {
                return "LEFT OUTER JOIN";
            }
        },
        /** right outer join type. */
        RIGHT {
            @Override
            public String value() {
                return "RIGHT OUTER JOIN";
            }
        };

        /**
         * @return join type value.
         */
        public abstract String value();
    }

    public static class JoinCondition implements IFrom.IJoinCondition {

        private final String left;
        private final String right;

        public JoinCondition(String left, String right) {
            this.left = left;
            this.right = right;
        }

        public static final JoinCondition condition(final String left, final String right) {
            return new JoinCondition(left, right);
        }

        @Override
        public String toString() {
            return left + " = " + right;
        }
    }
}
