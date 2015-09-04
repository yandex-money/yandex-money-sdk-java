package com.yandex.money.api.typeadapters.showcase.uicontrol;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.yandex.money.api.model.showcase.components.uicontrol.Checkbox;

import java.lang.reflect.Type;

/**
 * Type adapter for {@link Checkbox} component.
 *
 * @author Anton Ermak (ermak@yamoney.ru)
 */
public final class CheckboxTypeAdapter extends ParameterControlTypeAdapter<Checkbox, Checkbox
        .Builder> {

    public static final CheckboxTypeAdapter INSTANCE = new CheckboxTypeAdapter();

    private static final String MEMBER_CHECKED = "checked";

    private CheckboxTypeAdapter() {
    }

    @Override
    protected void serialize(Checkbox src, JsonObject to, JsonSerializationContext context) {
        to.addProperty(MEMBER_CHECKED, src.checked);
        super.serialize(src, to, context);
    }

    @Override
    protected void deserialize(JsonObject src, Checkbox.Builder builder,
                               JsonDeserializationContext context) {
        builder.setChecked(src.get(MEMBER_CHECKED).getAsBoolean());
        super.deserialize(src, builder, context);
    }

    @Override
    protected Checkbox.Builder createBuilderInstance() {
        return new Checkbox.Builder();
    }

    @Override
    protected Checkbox createInstance(Checkbox.Builder builder) {
        return builder.create();
    }

    @Override
    protected Type getType() {
        return Checkbox.class;
    }
}