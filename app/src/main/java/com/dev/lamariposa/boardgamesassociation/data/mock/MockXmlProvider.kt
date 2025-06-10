package com.dev.lamariposa.boardgamesassociation.data.mock

/**
 * Provides mock XML responses for testing and development
 */
object MockXmlProvider {
    
    /**
     * Returns a mock XML response for the search endpoint
     */
    fun getMockSearchResponse(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <items total="311" termsofuse="https://boardgamegeek.com/xmlapi/termsofuse">
                <item type="boardgame" id="134277">
                    <name type="alternate" value="The 7 Wonders of Catan (fan expansion for Catan)"/>
                    <yearpublished value="2012" />
                </item>
                <item type="boardgame" id="110308">
                    <name type="primary" value="7 Wonders: Catan"/>
                    <yearpublished value="2011" />
                </item>
                <item type="boardgame" id="123386">
                    <name type="primary" value="Baden-Württemberg Catan"/>
                    <yearpublished value="2012" />
                </item>
                <item type="videogame" id="84431">
                    <name type="primary" value="Catan: The First Island"/>
                </item>
                <item type="videogame" id="80050">
                    <name type="primary" value="Catan: The Seafarers"/>
                </item>
                <item type="videogame" id="251673">
                    <name type="primary" value="catAnod"/>
                </item>
                <item type="videogame" id="144338">
                    <name type="primary" value="The Rivals for Catan"/>
                </item>
                <item type="videogame" id="103047">
                    <name type="primary" value="Die Sternenfahrer von Catan"/>
                </item>
                <item type="videogame" id="340972">
                    <name type="primary" value="Sternenschiff Catan"/>
                </item>
            </items>
        """.trimIndent()
    }
}
