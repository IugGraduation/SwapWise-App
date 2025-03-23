import com.example.data.model.response.ApiResponseDto
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class CustomConverterFactory(private val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type is ParameterizedType && type.rawType == ApiResponseDto::class.java) {
            val dataType = type.actualTypeArguments[0]
            return StatusAwareResponseBodyConverter(gson, dataType)
        }
        return null
    }

    private class StatusAwareResponseBodyConverter(
        private val gson: Gson,
        private val dataType: Type
    ) : Converter<ResponseBody, ApiResponseDto<*>> {
        override fun convert(value: ResponseBody): ApiResponseDto<*> {
            val json = value.string()
            val jsonObject = gson.fromJson(json, com.google.gson.JsonObject::class.java)

            val status = jsonObject.get("status").asBoolean
            val message = jsonObject.get("message").asString

            return if (status) {
                val dataJson = jsonObject.get("data")
                val data = gson.fromJson<Any>(dataJson, dataType)
                ApiResponseDto(true, message, data)
            } else {
                ApiResponseDto(false, message, null)
            }
        }
    }
}


