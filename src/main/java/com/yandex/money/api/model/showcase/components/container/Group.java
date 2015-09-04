package com.yandex.money.api.model.showcase.components.container;

import com.yandex.money.api.model.showcase.components.Component;
import com.yandex.money.api.model.showcase.components.uicontrol.ParameterControl;
import com.yandex.money.api.utils.ToStringBuilder;

/**
 * A {@link Group} is implementation of a {@link Component} that can contain only {@link Component}
 * instances.
 *
 * @author Aleksandr Ershov (asershov@yamoney.com)
 */
public final class Group extends Container<Component> {

    /**
     * {@link Layout}.
     */
    public final Layout layout;

    private Group(Builder builder) {
        super(builder);
        layout = builder.layout;
    }

    /**
     * Validates contained components across constraints.
     * <p/>
     * TODO: refactor inheritance.
     *
     * @return {@code true} if group is valid and {@code false} otherwise.
     */
    public boolean isValid() {
        for (Component component : items) {
            boolean valid = true;
            if (component instanceof ParameterControl) {
                valid = ((ParameterControl) component).isValid();
            } else if (component instanceof Group) {
                valid = ((Group) component).isValid();
            }
            if (!valid) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Group group = (Group) o;

        return layout == group.layout;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (layout != null ? layout.hashCode() : 0);
        return result;
    }

    @Override
    protected ToStringBuilder getToStringBuilder() {
        return super.getToStringBuilder()
                .setName("Group")
                .append("layout", layout);
    }

    /**
     * Possible options that specifies arrangement of contained {@link Component}s.
     */
    public enum Layout {

        /**
         * Vertical arrangement.
         */
        VERTICAL("VBox"),

        /**
         * Horizontal arrangement.
         */
        HORIZONTAL("HBox");

        public final String code;

        Layout(String code) {
            this.code = code;
        }

        public static Layout parse(String code) {
            for (Layout type : values()) {
                if (type.code.equals(code)) {
                    return type;
                }
            }
            return VERTICAL;
        }
    }

    /**
     * {@link Group} builder.
     */
    public static final class Builder extends Container.Builder<Component> {

        private Layout layout = Layout.VERTICAL;

        @Override
        public Group create() {
            return new Group(this);
        }

        public Builder setLayout(Layout layout) {
            if (layout == null) {
                throw new NullPointerException("layout is null");
            }
            this.layout = layout;
            return this;
        }
    }
}