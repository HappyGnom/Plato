package by.happygnom.data.util

import androidx.sqlite.db.SimpleSQLiteQuery

class SimpleQueryBuilder constructor(
    private var sql: String = ""
) {
    private val args = ArrayList<Any?>()
    private var hasFilter = false

    fun isArgsNotEmpty() = args.isNotEmpty()

    fun addRaw(sqlPartRaw: String, vararg args: Any?) {
        sql += " $sqlPartRaw "
        this.args.addAll(args)
    }

    fun addFilter(sqlPartRaw: String, vararg args: Any?) {
        if (hasFilter) {
            sql += " AND $sqlPartRaw "
        } else {
            sql += " WHERE $sqlPartRaw"
            hasFilter = true
        }

        this.args.addAll(args)
    }

    fun build(): SimpleSQLiteQuery {
        sql += ";"
        return SimpleSQLiteQuery(sql, args.toTypedArray())
    }

    override fun toString(): String {
        return "sql = '$sql',\nargs = ${args.joinToString { "`$it`" }}"
    }
}
