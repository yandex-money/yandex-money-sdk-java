package com.yandex.money.api.typeadapters.showcase.uicontrol;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.yandex.money.api.methods.JsonUtils;
import com.yandex.money.api.model.showcase.components.uicontrol.TextArea;

import java.lang.reflect.Type;

/**
 * Base type adapter for subclasses of {@link TextArea} component.
 *
 * @author Anton Ermak (ermak@yamoney.ru)
 */
public abstract class TextAreaTypeAdapter<T extends TextArea, U extends TextArea.Builder>
        extends ParameterControlTypeAdapter<T, U> {

    /**
     * Type adapter for {@link TextArea} component.
     */
    public static final TextAreaTypeAdapter<TextArea, TextArea.Builder> INSTANCE =
            new TextAreaTypeAdapter<TextArea, TextArea.Builder>() {

                @Override
                protected TextArea.Builder createBuilderInstance() {
                    return new TextArea.Builder();
                }

                @Override
                protected Type getType() {
                    return TextArea.class;
                }

                @Override
                protected TextArea createInstance(TextArea.Builder builder) {
                    return builder.create();
                }
            };

    private static final String MEMBER_MINLENGTH = "minlength";
    private static final String MEMBER_MAXLENGTH = "maxlength";

    @Override
    protected void deserialize(JsonObject src, U builder,
                               JsonDeserializationContext context) {
        builder.setMinLength(JsonUtils.getInt(src, MEMBER_MINLENGTH));
        builder.setMaxLength(JsonUtils.getInt(src, MEMBER_MAXLENGTH));
        super.deserialize(src, builder, context);
    }

    @Override
    protected void serialize(T src, JsonObject to, JsonSerializationContext context) {
        to.addProperty(MEMBER_MINLENGTH, src.minLength);
        to.addProperty(MEMBER_MAXLENGTH, src.maxLength);
        super.serialize(src, to, context);
    }
}