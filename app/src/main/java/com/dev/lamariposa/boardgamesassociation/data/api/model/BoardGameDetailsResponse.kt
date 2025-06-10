package com.dev.lamariposa.boardgamesassociation.data.api.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "items", strict = false)
data class BoardGameDetailsResponse(
    @field:ElementList(entry = "item", inline = true, required = false)
    var items: List<BoardGameDetailsItem>? = null
)

@Root(name = "item", strict = false)
data class BoardGameDetailsItem(
    @field:Attribute(name = "id", required = false)
    var id: String? = null,

    @field:Element(name = "name", required = false)
    var name: BoardGameName? = null,

    @field:Element(name = "yearpublished", required = false)
    var yearPublished: BoardGameYearPublished? = null,

    @field:Element(name = "description", required = false)
    var description: String? = null,

    @field:Element(name = "image", required = false)
    var image: String? = null
)
