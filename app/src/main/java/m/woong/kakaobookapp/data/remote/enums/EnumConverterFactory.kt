package m.woong.kakaobookapp.data.remote.enums

import com.google.gson.annotations.SerializedName
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class EnumConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<Enum<*>, String>? =
        if (type is Class<*> && type.isEnum) {
            Converter { enum ->
                try {
                    enum.javaClass.getField(enum.name)
                        .getAnnotation(SerializedName::class.java)?.value
                } catch (e: Exception) {
                    null
                } ?: enum.toString()
            }
        } else {
            null
        }
}