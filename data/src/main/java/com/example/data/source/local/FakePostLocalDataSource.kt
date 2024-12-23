package com.example.data.source.local

import com.example.data.model.ApiResponse
import com.example.data.model.PostItemDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class FakePostLocalDataSource @Inject constructor() {

    suspend fun getPostDetails(): Response<PostItemDto> {
        val jsonData = "{\n"+
                "    \"status\": true,\n"+
                "    \"message\": \"Done\",\n"+
                "    \"data\": {\n"+
                "        \"uuid\": \"2f5b6c6b-6ebb-4fc6-808e-ab6685f74fe1\",\n"+
                "        \"user_uuid\": \"2f8c6af0-ea49-489d-9ff9-7fe7a398d343\",\n"+
                "        \"user_name\": \"mohamed\",\n"+
                "        \"user_image\": \"http://127.0.0.1:8000/storage/upload/user/personal//kX0lwDAfe56gwxk2OYpUysk2IuR2S0Wa9DhNwZFH.jpg\",\n"+
                "        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/y6Ud7Pi0dXRRDgR2DrdkSsn7EJjK50N44GjCOLed.png\",\n"+
                "        \"post_name\": \"Hyatt Oliver1\",\n"+
                "        \"post_details\": \"Quia dolore Nam dign1\",\n"+
                "        \"num_offers\": 8,\n"+
                "        \"status\": \"1\",\n"+
                "        \"favorite_categories\": [\n"+
                "            {\n"+
                "                \"uuid\": \"2c100fc8-784c-4e09-b6f9-8b7501650216\",\n"+
                "                \"category_uuid\": \"063dfea7-806f-47f5-a501-a7653545413d\",\n"+
                "                \"category_name\": \"food\"\n"+
                "            }\n"+
                "        ],\n"+
                "        \"offers\": [\n"+
                "            {\n"+
                "                \"uuid\": \"11db4caf-86ad-473f-a97b-503911a280af\",\n"+
                "                \"user_name\": \"mohamed2\",\n"+
                "                \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n"+
                "                \"image\": \"http://127.0.0.1:8000/storage/upload/offer/YI4Fmo65imzMiKlEkhKw0uU5LzqCCvebe2x7f3Fz.jpg\",\n"+
                "                \"title\": \"test test test test\",\n"+
                "                \"details\": \"test test test test test test test test test test test test test test test test\"\n"+
                "            },\n"+
                "            {\n"+
                "                \"uuid\": \"1debb622-2c0a-4310-a1ae-196672acf240\",\n"+
                "                \"user_name\": \"ahmed\",\n"+
                "                \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n"+
                "                \"image\": \"http://127.0.0.1:8000/storage/upload/offer/8QEDPqtLXtNgHbLUSLZRWM1rTIvvX4wZIz8g3s4c.jpg\",\n"+
                "                \"title\": \"test test test\",\n"+
                "                \"details\": \"test test testtest test testtest test testtest test testtest test test\"\n"+
                "            },\n"+
                "            {\n"+
                "                \"uuid\": \"2aaae2d6-b6de-4936-8554-f140b79f6e77\",\n"+
                "                \"user_name\": \"ahmed\",\n"+
                "                \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n"+
                "                \"image\": \"http://127.0.0.1:8000/storage/upload/offer/Um01IwOgTmbM83cZcZ4KvL44PuwCc7zSqEHjM9yx.jpg\",\n"+
                "                \"title\": \"test test test\",\n"+
                "                \"details\": \"test test testtest test testtest test testtest test testtest test test\"\n"+
                "            },\n"+
                "            {\n"+
                "                \"uuid\": \"5b23eb84-8ed6-49bd-b5e1-ba4fd9ab5e4e\",\n"+
                "                \"user_name\": \"ahmed\",\n"+
                "                \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n"+
                "                \"image\": \"http://127.0.0.1:8000/storage/upload/offer/SiwI34E5Miv4ggmUPswgNfmfvDcvZru1vGjh1EjR.jpg\",\n"+
                "                \"title\": \"test test test\",\n"+
                "                \"details\": \"test test testtest test testtest test testtest test testtest test test\"\n"+
                "            },\n"+
                "            {\n"+
                "                \"uuid\": \"8bc624f2-a9cf-48df-a6b1-bfd95d1bd96c\",\n"+
                "                \"user_name\": \"ahmed\",\n"+
                "                \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n"+
                "                \"image\": \"http://127.0.0.1:8000/storage/upload/offer/J0oSiGiCpPuv6OOIaolXRNmc11F7nNbrJsaFyqcX.jpg\",\n"+
                "                \"title\": \"test test test\",\n"+
                "                \"details\": \"test test testtest test testtest test testtest test testtest test test\"\n"+
                "            },\n"+
                "            {\n"+
                "                \"uuid\": \"a23d919c-1eb8-43e8-8483-9232c778271a\",\n"+
                "                \"user_name\": \"ahmed\",\n"+
                "                \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n"+
                "                \"image\": \"http://127.0.0.1:8000/storage/upload/offer/0XLhkOdR0O7ZIQLzan5XHbYEnia0Tcj2wNwDfKkA.jpg\",\n"+
                "                \"title\": \"test test test\",\n"+
                "                \"details\": \"test test testtest test testtest test testtest test testtest test test\"\n"+
                "            },\n"+
                "            {\n"+
                "                \"uuid\": \"d25a8467-77a7-4dcb-9706-b7cd1a96e3d8\",\n"+
                "                \"user_name\": \"ahmed\",\n"+
                "                \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n"+
                "                \"image\": \"http://127.0.0.1:8000/storage/upload/offer/kY0ao8hQXZ4SAizSZjhl7c2QRskuNSV8fn0adaqD.jpg\",\n"+
                "                \"title\": \"test test test\",\n"+
                "                \"details\": \"test test testtest test testtest test testtest test testtest test test\"\n"+
                "            },\n"+
                "            {\n"+
                "                \"uuid\": \"dfd2c2e4-bb02-4300-8654-d7b62af4c4f3\",\n"+
                "                \"user_name\": \"mohamed2\",\n"+
                "                \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n"+
                "                \"image\": \"http://127.0.0.1:8000/storage/upload/offer/4wRAnqpDOhW450EHPYbE9J0lzUh6UM6W3LJWoPjb.jpg\",\n"+
                "                \"title\": \"test test test test\",\n"+
                "                \"details\": \"test test test test test test test test test test test test test test test test\"\n"+
                "            }\n"+
                "        ]\n"+
                "    },\n"+
                "    \"pages\": {\n"+
                "        \"current_page\": 0,\n"+
                "        \"first_page_url\": \"\",\n"+
                "        \"from\": 0,\n"+
                "        \"last_page\": 0,\n"+
                "        \"last_page_url\": \"\",\n"+
                "        \"next_page_url\": null,\n"+
                "        \"path\": \"\",\n"+
                "        \"per_page\": 0,\n"+
                "        \"prev_page_url\": null,\n"+
                "        \"to\": 0,\n"+
                "        \"total\": 0\n"+
                "    },\n"+
                "    \"errors\": []\n"+
                "}".trimMargin()

        val type = object : TypeToken<ApiResponse<PostItemDto>>() {}.type
        val apiResponse: ApiResponse<PostItemDto> = Gson().fromJson(jsonData, type)

        delay(1000)
        return Response.success(apiResponse.data)
    }
}