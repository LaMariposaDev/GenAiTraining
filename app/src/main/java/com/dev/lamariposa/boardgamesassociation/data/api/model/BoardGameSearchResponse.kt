package com.dev.lamariposa.boardgamesassociation.data.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "items", strict = false)
data class BoardGameSearchResponse(
    @field:ElementList(entry = "item", inline = true, required = false)
    var items: List<BoardGameSearchItem>? = null
)

@Root(name = "item", strict = false)
data class BoardGameSearchItem(
    @field:Element(name = "id", required = false)
    var id: String? = null,

    @field:Element(name = "name", required = false)
    var name: BoardGameName? = null,

    @field:Element(name = "yearpublished", required = false)
    var yearPublished: BoardGameYearPublished? = null
)

@Root(name = "name", strict = false)
data class BoardGameName(
    @field:Element(name = "value", required = false)
    var value: String? = null
)

@Root(name = "yearpublished", strict = false)
data class BoardGameYearPublished(
    @field:Element(name = "value", required = false)
    var value: String? = null
)
