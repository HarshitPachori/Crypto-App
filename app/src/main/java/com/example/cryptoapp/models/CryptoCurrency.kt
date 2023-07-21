package com.example.cryptoapp.models

import java.io.Serializable

data class CryptoCurrency(
    val auditInfoList: List<AuditInfo>,
    val circulatingSupply: Double,
    val cmcRank: Double,
    val dateAdded: String,
    val id: Int,
    val isActive: Double,
    val isAudited: Boolean,
    val lastUpdated: String,
    val marketPairCount: Double,
    val maxSupply: Double,
    val name: String,
    val platform: Platform,
    val quotes: List<Quote>,
    val selfReportedCirculatingSupply: Double,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val totalSupply: Double
):Serializable{
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}


// yahan pe serializable add krna hai tabhi use kar paenge after that go to nav and select details class and from design add data with custom serializable and nullable type @null to get data in details we used safe ars package
