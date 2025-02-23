package com.randomblock1.fetchsampleapp

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

val HTTP_CLIENT = OkHttpClient()
const val DATA_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

data class Item(
    val id: Int, val listId: Int, val name: String?
)

fun getData(): String {
    val request = Request.Builder().url(DATA_URL).build()
    val response = HTTP_CLIENT.newCall(request).execute()
    return response.body.string()
}

fun parseData(data: JSONArray): List<Item> {
    val filteredItems = mutableListOf<Item>()
    for (i in 0 until data.length()) {
        val item = data.getJSONObject(i)
        val id = item.getInt("id")
        val listId = item.getInt("listId")
        val name = item.getString("name") // coerces nulls to string "null"
        if (!name.isEmpty() && name != "null") filteredItems.add(Item(id, listId, name))
    }
    filteredItems.sortBy { it.name }

    return filteredItems
}