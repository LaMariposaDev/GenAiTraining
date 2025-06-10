package com.dev.lamariposa.boardgamesassociation.data.api.model

import org.simpleframework.xml.Attribute
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
    @field:Attribute(name = "id", required = false)
    var id: String? = null,

    @field:Attribute(name = "type", required = false)
    var type: String? = null,

    @field:Element(name = "name", required = false)
    var name: BoardGameName? = null,

    @field:Element(name = "yearpublished", required = false)
    var yearPublished: BoardGameYearPublished? = null
)

@Root(name = "name", strict = false)
data class BoardGameName(
    @field:Attribute(name = "value", required = false)
    var value: String? = null
)

@Root(name = "yearpublished", strict = false)
data class BoardGameYearPublished(
    @field:Attribute(name = "value", required = false)
    var value: String? = null
)
