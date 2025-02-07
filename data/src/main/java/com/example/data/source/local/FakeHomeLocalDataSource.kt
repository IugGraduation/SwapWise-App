package com.example.data.source.local

import com.example.data.model.ApiResponse
import com.example.data.model.HomeDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class FakeHomeLocalDataSource @Inject constructor() {

    suspend fun getHomeData(): Response<HomeDto> {
        val jsonData = "{\n" +
                "    \"status\": true,\n" +
                "    \"message\": \"Done\",\n" +
                "    \"data\": {\n" +
                "        \"user\": {\n" +
                "            \"name\": \"mohamed\",\n" +
                "            \"image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n" +
                "            \"welcome\": \"مساء الخير\"\n" +
                "        },\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "                \"title\": \"Browse categories\",\n" +
                "                \"url\": \"category\",\n" +
                "                \"data_type\": \"Categories\",\n" +
                "                \"type\": \"array\",\n" +
                "                \"data\": [\n" +
                "                    {\n" +
                "                        \"uuid\": \"063dfea7-806f-47f5-a501-a7653545413d\",\n" +
                "                        \"name\": \"food\",\n" +
                "                        \"image\": \"http://127.0.0.1:8000/storage/upload/category/images//TDrlBTdilKNDybjc7FF5NlE8XlFGmW65hEzurEhD.jpg\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"title\": \"Popular products for rent\",\n" +
                "                \"url\": \"top_interactive\",\n" +
                "                \"data_type\": \"Top Interactive\",\n" +
                "                \"type\": \"array\",\n" +
                "                \"data\": [\n" +
                "                    {\n" +
                "                        \"uuid\": \"11eb4a38-d06a-461e-9435-07df97cd050c\",\n" +
                "                        \"user_uuid\": \"a8325d1e-5ce1-4cc0-af9e-c5de0bf45f05\",\n" +
                "                        \"user_name\": \"mohamed2\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/xqDZCKAwkfpT6BD9ZfbH4sgaJxGoSm4vdotkavO8.jpg\",\n" +
                "                        \"post_name\": \"test test test\",\n" +
                "                        \"status\": \"Close\",\n" +
                "                        \"post_details\": \"edittest edittest test testtest test testtest test testtest test test\",\n" +
                "                        \"num_offers\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uuid\": \"2f5b6c6b-6ebb-4fc6-808e-ab6685f74fe1\",\n" +
                "                        \"user_uuid\": \"2f8c6af0-ea49-489d-9ff9-7fe7a398d343\",\n" +
                "                        \"user_name\": \"mohamed\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/storage/upload/user/personal//kX0lwDAfe56gwxk2OYpUysk2IuR2S0Wa9DhNwZFH.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/y6Ud7Pi0dXRRDgR2DrdkSsn7EJjK50N44GjCOLed.png\",\n" +
                "                        \"post_name\": \"Hyatt Oliver1\",\n" +
                "                        \"status\": \"Active\",\n" +
                "                        \"post_details\": \"Quia dolore Nam dign1\",\n" +
                "                        \"num_offers\": 8\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uuid\": \"6a5edec2-6df1-499a-97b1-3ec99e057da2\",\n" +
                "                        \"user_uuid\": \"17f91149-7d7b-4ea2-985e-db92ca5b7b82\",\n" +
                "                        \"user_name\": \"mohamed\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/SLpa7XHRya8uOtVVTiACxcSldMoJyiJh00eyXlHg.jpg\",\n" +
                "                        \"post_name\": \"test test test\",\n" +
                "                        \"status\": \"Rejected\",\n" +
                "                        \"post_details\": \"test test testtest test testtest test testtest test testtest test test\",\n" +
                "                        \"num_offers\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uuid\": \"77b83735-ace3-46b5-b726-7f9b0b43b290\",\n" +
                "                        \"user_uuid\": \"17f91149-7d7b-4ea2-985e-db92ca5b7b82\",\n" +
                "                        \"user_name\": \"mohamed\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/dEO7KQbhO2Qd4wSqOPVbBWMRogNXsHlTES54aF1m.jpg\",\n" +
                "                        \"post_name\": \"test test test\",\n" +
                "                        \"status\": \"Pending\",\n" +
                "                        \"post_details\": \"test test testtest test testtest test testtest test testtest test test\",\n" +
                "                        \"num_offers\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uuid\": \"7a574a48-7471-44b7-a3bb-d1dadb471d6e\",\n" +
                "                        \"user_uuid\": \"17f91149-7d7b-4ea2-985e-db92ca5b7b82\",\n" +
                "                        \"user_name\": \"mohamed\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/7nyu0KSIA3zG739FESaj9rKp8uqQHR1Rl1lZUcAB.jpg\",\n" +
                "                        \"post_name\": \"mohamed\",\n" +
                "                        \"status\": \"Pending\",\n" +
                "                        \"post_details\": \"ززززززززززززززززززززززززز\",\n" +
                "                        \"num_offers\": 0\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"title\": \"Popular products for rent\",\n" +
                "                \"url\": \"recent_posts\",\n" +
                "                \"data_type\": \"Recent Posts\",\n" +
                "                \"type\": \"array\",\n" +
                "                \"data\": [\n" +
                "                    {\n" +
                "                        \"uuid\": \"7a574a48-7471-44b7-a3bb-d1dadb471d6e\",\n" +
                "                        \"user_uuid\": \"17f91149-7d7b-4ea2-985e-db92ca5b7b82\",\n" +
                "                        \"user_name\": \"mohamed\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/7nyu0KSIA3zG739FESaj9rKp8uqQHR1Rl1lZUcAB.jpg\",\n" +
                "                        \"post_name\": \"mohamed\",\n" +
                "                        \"status\": \"Pending\",\n" +
                "                        \"post_details\": \"ززززززززززززززززززززززززز\",\n" +
                "                        \"num_offers\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uuid\": \"77b83735-ace3-46b5-b726-7f9b0b43b290\",\n" +
                "                        \"user_uuid\": \"17f91149-7d7b-4ea2-985e-db92ca5b7b82\",\n" +
                "                        \"user_name\": \"mohamed\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/dEO7KQbhO2Qd4wSqOPVbBWMRogNXsHlTES54aF1m.jpg\",\n" +
                "                        \"post_name\": \"test test test\",\n" +
                "                        \"status\": \"Pending\",\n" +
                "                        \"post_details\": \"test test testtest test testtest test testtest test testtest test test\",\n" +
                "                        \"num_offers\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uuid\": \"6a5edec2-6df1-499a-97b1-3ec99e057da2\",\n" +
                "                        \"user_uuid\": \"17f91149-7d7b-4ea2-985e-db92ca5b7b82\",\n" +
                "                        \"user_name\": \"mohamed\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/SLpa7XHRya8uOtVVTiACxcSldMoJyiJh00eyXlHg.jpg\",\n" +
                "                        \"post_name\": \"test test test\",\n" +
                "                        \"status\": \"Rejected\",\n" +
                "                        \"post_details\": \"test test testtest test testtest test testtest test testtest test test\",\n" +
                "                        \"num_offers\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uuid\": \"11eb4a38-d06a-461e-9435-07df97cd050c\",\n" +
                "                        \"user_uuid\": \"a8325d1e-5ce1-4cc0-af9e-c5de0bf45f05\",\n" +
                "                        \"user_name\": \"mohamed2\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/dashboard/app-assets/images/4367.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/xqDZCKAwkfpT6BD9ZfbH4sgaJxGoSm4vdotkavO8.jpg\",\n" +
                "                        \"post_name\": \"test test test\",\n" +
                "                        \"status\": \"Close\",\n" +
                "                        \"post_details\": \"edittest edittest test testtest test testtest test testtest test test\",\n" +
                "                        \"num_offers\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uuid\": \"2f5b6c6b-6ebb-4fc6-808e-ab6685f74fe1\",\n" +
                "                        \"user_uuid\": \"2f8c6af0-ea49-489d-9ff9-7fe7a398d343\",\n" +
                "                        \"user_name\": \"mohamed\",\n" +
                "                        \"user_image\": \"http://127.0.0.1:8000/storage/upload/user/personal//kX0lwDAfe56gwxk2OYpUysk2IuR2S0Wa9DhNwZFH.jpg\",\n" +
                "                        \"post_image\": \"http://127.0.0.1:8000/storage/upload/post/y6Ud7Pi0dXRRDgR2DrdkSsn7EJjK50N44GjCOLed.png\",\n" +
                "                        \"post_name\": \"Hyatt Oliver1\",\n" +
                "                        \"status\": \"Active\",\n" +
                "                        \"post_details\": \"Quia dolore Nam dign1\",\n" +
                "                        \"num_offers\": 8\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"pages\": {\n" +
                "        \"current_page\": 0,\n" +
                "        \"first_page_url\": \"\",\n" +
                "        \"from\": 0,\n" +
                "        \"last_page\": 0,\n" +
                "        \"last_page_url\": \"\",\n" +
                "        \"next_page_url\": null,\n" +
                "        \"path\": \"\",\n" +
                "        \"per_page\": 0,\n" +
                "        \"prev_page_url\": null,\n" +
                "        \"to\": 0,\n" +
                "        \"total\": 0\n" +
                "    },\n" +
                "    \"errors\": []\n" +
                "}".trimMargin()

        val type = object : TypeToken<ApiResponse<HomeDto>>() {}.type
        val apiResponse: ApiResponse<HomeDto> = Gson().fromJson(jsonData, type)

        delay(500)
        return Response.success(apiResponse.data)
    }
}