package com.randomblock1.fetchsampleapp

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

val HTTP_CLIENT = OkHttpClient()
const val DATA_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

data class Item(
    val id: Int, val listId: Int, val name: String
)

// Can throw exception, but handled in MainActivity
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
        val name = item.getString("name") // coerces null to string "null"
        if (!name.isEmpty() && name != "null") {
            filteredItems.add(Item(id, listId, name))
        }
    }

    return sortItems(filteredItems)
}

// Optimizes item sorting by pre-calculating extracted numbers
fun sortItems(items: List<Item>): List<Item> {
    val regex = Regex("""\w*\s+(\d+)$""")

    // Use a HashMap to store extracted numbers to avoid redundant regex operations
    val itemNumbers = HashMap<String, Int>()
    items.forEach { item ->
        val match = regex.find(item.name)
        if (match != null) {
            itemNumbers[item.name] = match.groupValues[1].toInt()
        }
    }

    val sortedItems = items.sortedWith { a, b ->
        when {
            itemNumbers.containsKey(a.name) && itemNumbers.containsKey(b.name) -> {
                val numA = itemNumbers[a.name]!!
                val numB = itemNumbers[b.name]!!
                numA.compareTo(numB)
            }
            // If one isn't in the usual format, sort normally
            else -> a.name.compareTo(b.name)
        }
    }

    return sortedItems
}