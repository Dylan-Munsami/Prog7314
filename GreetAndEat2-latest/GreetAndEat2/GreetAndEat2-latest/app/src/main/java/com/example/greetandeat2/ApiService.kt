package com.example.greetandeat2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path



interface ApiService {
    @POST("orders")
    suspend fun placeOrder(@Body order: Order): Response<Order>

    @GET("orders/{userId}")
    suspend fun getOrders(@Path("userId") userId: String): Response<List<Order>>

    @GET("order/{orderId}")
    suspend fun getOrder(@Path("orderId") orderId: String): Response<Order>

    // ✅ Make sure this endpoint exists
    @POST("orders/{orderId}/next")
    suspend fun progressOrder(@Path("orderId") orderId: String): Response<Order>
    @POST("register-token")
    suspend fun registerToken(@Body body: Map<String, String>): Response<Unit>

}