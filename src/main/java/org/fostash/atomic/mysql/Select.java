package org.fostash.atomic.mysql;

import org.fostash.atomic.dsl.IFrom;
import org.fostash.atomic.dsl.ISelect;
import org.fostash.atomic.dsl.exception.SqlBuilderException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by Fausto on 09/03/16.
 */
public class Select implements ISelect {

    /** columns field. */
    private final List<IColumn> columns = new ArrayList<>();

    @Override
    public final IFrom select(final IColumn... c) {
        if (c.length > 0) {
            columns.addAll(Arrays.asList(c));
        } else {
            columns.add(Column.column("*"));
        }
        return Factory.from(this);
    }

    @Override
    public final String toString() {
        return "SELECT "
                + columns.stream()
                .map(IColumn::build)
                .collect(Collectors.joining(", "));
    }

    /**
     * IColumn implementation.
     */
    public static final class Column implements IColumn {

        /** name field. */
        public final String name;
        /** alias field. */
        public final String alias;
        /** function field. */
        public final String function;

        /**
         * @param aName column name
         * @param anAlias column alias
         * @param aFunction function applied to column
         */
        private Column(final String aName, final String anAlias,
                       final String aFunction) {
            this.name = aName;
            this.alias = anAlias;
            this.function = aFunction;
        }

        /**
         * method for only column name.
         * @param name column name
         * @return IColumn interface
         */
        public static IColumn column(final String name) {
            return column(name, null);
        }

        /**
         * method for column name and alias.
         * @param name column name
         * @param alias column alias
         * @return IColumn interface
         */
        public static IColumn column(final String name, final String alias) {
            return column(name, alias, null);
        }

        /**
         * method for column name, alias and function.
         * @param name column name
         * @param alias column alias
         * @param function column function
         * @return IColumn interface
         */
        public static IColumn column(final String name, final String alias,
                                     final String function) {
            return new Column(name, alias, function);
        }

        /**
         * @return true if alias has defined false otherwise
         */
        public boolean hasAlias() {
            return alias != null;
        }

        /**
         * @return true if function has defined false otherwise
         */
        public boolean hasFunction() {
            return function != null;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getAlias() {
            return this.alias;
        }

        /**
         * @return function applied to column
         * @throws SqlBuilderException SqlBuilderException
         */
        public String getFunction() throws SqlBuilderException {
            if (hasFunction()) {
                return function + "(" + name + ")";
            } else {
                throw new SqlBuilderException("SqlBuilderException - no function defined for column " + name);
            }
        }

        /**
         * @return sql string build
         * @throws SqlBuilderException
         */
        public String build() throws SqlBuilderException {
            final StringBuilder builder = new StringBuilder();
            if (hasFunction()) {
                builder.append(getFunction());
            } else {
                builder.append(name);
            }
            if (hasAlias()) {
                builder.append(alias);
            }
            return builder.toString();
        }

        @Override
        public String toString() {
            return build();
        }
    }
}
