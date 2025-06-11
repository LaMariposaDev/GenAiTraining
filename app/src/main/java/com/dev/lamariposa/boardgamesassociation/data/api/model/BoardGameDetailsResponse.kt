package com.dev.lamariposa.boardgamesassociation.data.api.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "items", strict = false)
data class BoardGameDetailsResponse(
    @field:ElementList(entry = "item", inline = true, required = false)
    var items: List<BoardGameDetailsItem>? = null,

    @field:Attribute(name = "termsofuse", required = false)
    var termsOfUse: String? = null
)

@Root(name = "item", strict = false)
data class BoardGameDetailsItem(
    @field:Attribute(name = "id", required = false)
    var id: String? = null,

    @field:Attribute(name = "type", required = false)
    var type: String? = null,

    @field:ElementList(entry = "name", inline = false, required = false)
    var names: List<BoardGameDetailsName>? = null,

    @field:Element(name = "yearpublished", required = false)
    var yearPublished: BoardGameYearPublishedDetails? = null,

    @field:Element(name = "description", required = false)
    var description: String? = null,

    @field:Element(name = "image", required = false)
    var image: String? = null,
    
    @field:Element(name = "thumbnail", required = false)
    var thumbnail: String? = null,
    
    @field:Element(name = "minplayers", required = false)
    var minPlayers: BoardGameValue? = null,
    
    @field:Element(name = "maxplayers", required = false)
    var maxPlayers: BoardGameValue? = null,
    
    @field:Element(name = "playingtime", required = false)
    var playingTime: BoardGameValue? = null,

    @field:Element(name = "minplaytime", required = false)
    var minPlayTime: BoardGameValue? = null,

    @field:Element(name = "maxplaytime", required = false)
    var maxPlayTime: BoardGameValue? = null,

    @field:Element(name = "minage", required = false)
    var minAge: BoardGameValue? = null,

    @field:ElementList(entry = "poll", inline = false, required = false)
    var polls: List<BoardGamePoll>? = null,

    @field:ElementList(entry = "link", inline = false, required = false)
    var links: List<BoardGameLink>? = null
) {
    // Właściwość do łatwego dostępu do głównej nazwy gry
    val primaryName: String?
        get() = names?.firstOrNull { it.type == "primary" }?.value
}

@Root(name = "name", strict = false)
data class BoardGameDetailsName(
    @field:Attribute(name = "type", required = false)
    var type: String? = null,
    
    @field:Attribute(name = "value", required = false)
    var value: String? = null,

    @field:Attribute(name = "sortindex", required = false)
    var sortIndex: String? = null
)

@Root(name = "poll", strict = false)
data class BoardGamePoll(
    @field:Attribute(name = "name", required = false)
    var name: String? = null,

    @field:Attribute(name = "title", required = false)
    var title: String? = null,

    @field:Attribute(name = "totalvotes", required = false)
    var totalVotes: String? = null,

    @field:ElementList(entry = "results", inline = false, required = false)
    var results: List<BoardGamePollResults>? = null
)

@Root(name = "results", strict = false)
data class BoardGamePollResults(
    @field:Attribute(name = "numplayers", required = false)
    var numPlayers: String? = null,

    @field:ElementList(entry = "result", inline = false, required = false)
    var results: List<BoardGamePollResult>? = null
)

@Root(name = "result", strict = false)
data class BoardGamePollResult(
    @field:Attribute(name = "value", required = false)
    var value: String? = null,

    @field:Attribute(name = "numvotes", required = false)
    var numVotes: String? = null,

    @field:Attribute(name = "level", required = false)
    var level: String? = null
)

@Root(name = "poll-summary", strict = false)
data class BoardGamePollSummary(
    @field:Attribute(name = "name", required = false)
    var name: String? = null,

    @field:Attribute(name = "title", required = false)
    var title: String? = null,

    @field:ElementList(entry = "result", inline = false, required = false)
    var results: List<BoardGamePollSummaryResult>? = null
)

@Root(name = "result", strict = false)
data class BoardGamePollSummaryResult(
    @field:Attribute(name = "name", required = false)
    var name: String? = null,

    @field:Attribute(name = "value", required = false)
    var value: String? = null
)

@Root(name = "link", strict = false)
data class BoardGameLink(
    @field:Attribute(name = "type", required = false)
    var type: String? = null,

    @field:Attribute(name = "id", required = false)
    var id: String? = null,

    @field:Attribute(name = "value", required = false)
    var value: String? = null
)

@Root(name = "yearpublished", strict = false)
data class BoardGameYearPublishedDetails(
    @field:Attribute(name = "value", required = false)
    var value: String? = null
)

@Root(strict = false)
data class BoardGameValue(
    @field:Attribute(name = "value", required = false)
    var value: String? = null
)
