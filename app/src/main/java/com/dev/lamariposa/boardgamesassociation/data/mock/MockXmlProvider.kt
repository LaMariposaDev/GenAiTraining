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
                <item type="boardgameaccessory" id="173657">
                    <name type="primary" value="Catan: Explorers &amp; Pirates – Wooden Viking Pieces"/>
                </item>
                <item type="boardgameaccessory" id="399098">
                    <name type="primary" value="CATAN: Family Promo Card"/>
                    <yearpublished value="2023" />
                </item>
                <item type="boardgameaccessory" id="365835">
                    <name type="primary" value="Catan: Hexadocks Base Set"/>
                    <yearpublished value="2020" />
                </item>
                <item type="boardgameaccessory" id="365837">
                    <name type="primary" value="Catan: Hexadocks Extension Set"/>
                    <yearpublished value="2021" />
                </item>
                <item type="boardgameaccessory" id="365755">
                    <name type="primary" value="Catan: Hexatower"/>
                    <yearpublished value="2020" />
                </item>
                <item type="boardgameaccessory" id="434492">
                    <name type="primary" value="Catan: Inclusion Dice Set"/>
                    <yearpublished value="2024" />
                </item>
                <item type="boardgameaccessory" id="346990">
                    <name type="primary" value="Catan: Laserox Organizer"/>
                    <yearpublished value="2021" />
                </item>
                <item type="boardgameaccessory" id="346991">
                    <name type="primary" value="Catan: Laserox Traders Organizer"/>
                    <yearpublished value="2021" />
                </item>
                <item type="boardgameaccessory" id="346992">
                    <name type="primary" value="Catan: Lasrerox Conquerors Organizer"/>
                    <yearpublished value="2021" />
                </item>
                <item type="boardgameaccessory" id="238496">
                    <name type="primary" value="Catan: Legend of the Sea Robbers – Wooden Viking Pieces"/>
                    <yearpublished value="2017" />
                </item>
                <item type="boardgameaccessory" id="439447">
                    <name type="primary" value="CATAN: Masterpiece Series"/>
                    <yearpublished value="2025" />
                </item>
                <item type="boardgameaccessory" id="434493">
                    <name type="primary" value="Catan: Opallite Robber + Metal Dice"/>
                </item>
                <item type="boardgameaccessory" id="243110">
                    <name type="primary" value="Catan: Pewter Player Pieces"/>
                    <yearpublished value="2011" />
                </item>
                <item type="boardgameaccessory" id="273551">
                    <name type="primary" value="Catan: Resource Token Set"/>
                </item>
                <item type="boardgameaccessory" id="273549">
                    <name type="primary" value="Catan: Robber Token"/>
                </item>
                <item type="boardgameaccessory" id="182441">
                    <name type="primary" value="Catan: Seafarers – Galley and Fort Figurines"/>
                    <yearpublished value="2015" />
                </item>
                <item type="boardgameaccessory" id="140409">
                    <name type="primary" value="Catan: Seafarers – Wooden Viking Pieces"/>
                </item>
                <item type="boardgameaccessory" id="271278">
                    <name type="primary" value="Catan: Settlers Organizer"/>
                    <yearpublished value="2018" />
                </item>
                <item type="boardgameaccessory" id="431245">
                    <name type="primary" value="Catan: Spring Catan Hexes"/>
                    <yearpublished value="2024" />
                </item>
                <item type="boardgameaccessory" id="331315">
                    <name type="primary" value="Catan: Summer Catan Hexes"/>
                    <yearpublished value="2019" />
                </item>
                <item type="boardgameaccessory" id="139876">
                    <name type="primary" value="Catan: Table of Catan"/>
                </item>
                <item type="boardgameaccessory" id="273592">
                    <name type="primary" value="Catan: Tower Rex Card Holder"/>
                </item>
                <item type="boardgameaccessory" id="182431">
                    <name type="primary" value="Catan: Traders &amp; Barbarians – Barbarian Meeples"/>
                    <yearpublished value="2015" />
                </item>
                <item type="boardgameaccessory" id="140215">
                    <name type="primary" value="Catan: Traders &amp; Barbarians – Game Cards"/>
                    <yearpublished value="2007" />
                </item>
                <item type="boardgameaccessory" id="182439">
                    <name type="primary" value="Catan: Traders &amp; Barbarians – Knight Meeples"/>
                    <yearpublished value="2015" />
                </item>
                <item type="boardgameaccessory" id="238495">
                    <name type="primary" value="Catan: Traders &amp; Barbarians – Wooden Viking Pieces"/>
                    <yearpublished value="2007" />
                </item>
                <item type="boardgameaccessory" id="365838">
                    <name type="primary" value="Catan: Trading Post Convertible Card Tray"/>
                    <yearpublished value="2020" />
                </item>
                <item type="boardgameaccessory" id="273557">
                    <name type="primary" value="Catan: Trading Ship Figurines"/>
                </item>
                <item type="boardgameaccessory" id="360479">
                    <name type="primary" value="Catan: Treasures, Dragons &amp; Adventurers – Wooden Pieces"/>
                    <yearpublished value="2021" />
                </item>
                <item type="boardgameaccessory" id="182444">
                    <name type="primary" value="Catan: Wooden Pieces Basic Set (4-Players)"/>
                    <yearpublished value="2015" />
                </item>
                <item type="boardgameaccessory" id="182448">
                    <name type="primary" value="Catan: Wooden Pieces for 5-6 Players"/>
                    <yearpublished value="2015" />
                </item>
                <item type="boardgameaccessory" id="140406">
                    <name type="primary" value="Catan: Wooden Viking Pieces (Basic and Expansion)"/>
                </item>
                <item type="boardgameaccessory" id="140407">
                    <name type="primary" value="Catan: Wooden Viking Pieces (Basic Only)"/>
                </item>
                <item type="boardgameaccessory" id="141863">
                    <name type="primary" value="Catan: Yucatan Adventure Board"/>
                    <yearpublished value="2008" />
                </item>
                <item type="boardgameaccessory" id="140260">
                    <name type="primary" value="Catan: Yucatan Wooden Token Set (125 Tokens)"/>
                </item>
                <item type="boardgameaccessory" id="140259">
                    <name type="primary" value="Catan: Yucatan Wooden Token Set (95 Pack)"/>
                    <yearpublished value="2009" />
                </item>
                <item type="boardgameaccessory" id="393266">
                    <name type="primary" value="Catan: ZV3DCreations Insert"/>
                    <yearpublished value="2022" />
                </item>
                <item type="boardgameaccessory" id="156679">
                    <name type="primary" value="Die Fürsten von Catan: 6 Kartenhalter"/>
                    <yearpublished value="2010" />
                </item>
                <item type="boardgameaccessory" id="20890">
                    <name type="primary" value="Im Zeichen des Sechsecks: Klaus Teuber &amp; Die Siedler von Catan"/>
                    <yearpublished value="2005" />
                </item>
                <item type="boardgameaccessory" id="192180">
                    <name type="primary" value="De Kolonisten Van Catan: Dobbelbeker"/>
                    <yearpublished value="2010" />
                </item>
                <item type="boardgameaccessory" id="139613">
                    <name type="primary" value="The Settlers of Catan: Building Costs Coasters"/>
                </item>
                <item type="boardgameaccessory" id="140404">
                    <name type="primary" value="The Settlers of Catan: Game Cards"/>
                    <yearpublished value="2007" />
                </item>
                <item type="boardgameaccessory" id="138407">
                    <name type="primary" value="Settlers of Catan: Pre-2007 Adapter Kit"/>
                    <yearpublished value="2008" />
                </item>
                <item type="boardgameaccessory" id="139612">
                    <name type="primary" value="The Settlers of Catan: Robber Trio"/>
                </item>
                <item type="boardgameaccessory" id="425760">
                    <name type="primary" value="Settlers of Catan: Three Sea Studios Magnetic Hex Tiles"/>
                    <yearpublished value="2024" />
                </item>
                <item type="boardgameaccessory" id="193277">
                    <name type="primary" value="Die Siedler von Catan: Club-Paket"/>
                </item>
                <item type="boardgameaccessory" id="151252">
                    <name type="primary" value="Die Siedler von Catan: Der Catanische Rat"/>
                    <yearpublished value="2013" />
                </item>
                <item type="boardgameaccessory" id="182434">
                    <name type="primary" value="Die Siedler von Catan: Die große Karawane – Camel Meeples"/>
                    <yearpublished value="2015" />
                </item>
                <item type="boardgameaccessory" id="369439">
                    <name type="primary" value="Die Siedler von Catan: Kompatibilitäts-Kit"/>
                    <yearpublished value="2014" />
                </item>
                <item type="boardgameaccessory" id="149168">
                    <name type="primary" value="Star Trek: Catan – Promotional Dice"/>
                    <yearpublished value="2013" />
                </item>
                <item type="boardgameaccessory" id="181663">
                    <name type="primary" value="Star Trek: Catan – Victory Point Displays"/>
                </item>
                <item type="boardgameaccessory" id="255247">
                    <name type="primary" value="Starfarers of Catan: Rancho 3D Organizer"/>
                    <yearpublished value="2018" />
                </item>
                <item type="boardgameaccessory" id="158741">
                    <name type="primary" value="Starfarers of Catan: Replacement Rings"/>
                </item>
                <item type="boardgameaccessory" id="138429">
                    <name type="primary" value="Die Sternenfahrer von Catan: Die Fürsten der Völker"/>
                    <yearpublished value="2000" />
                </item>
                <item type="boardgameexpansion" id="134277">
                    <name type="alternate" value="The 7 Wonders of Catan (fan expansion for Catan)"/>
                    <yearpublished value="2012" />
                </item>
                <item type="boardgameexpansion" id="110308">
                    <name type="primary" value="7 Wonders: Catan"/>
                    <yearpublished value="2011" />
                </item>
                <item type="boardgameexpansion" id="26352">
                    <name type="primary" value="Catan Austria / Wien meets Catan"/>
                    <yearpublished value="2004" />
                </item>
                <item type="boardgameexpansion" id="21817">
                    <name type="primary" value="Catan Card Game: Artisans &amp; Benefactors"/>
                    <yearpublished value="2006" />
                </item>
                <item type="boardgameexpansion" id="12543">
                    <name type="primary" value="Catan Card Game: Barbarians &amp; Traders Upgrade Kit"/>
                    <yearpublished value="2003" />
                </item>
                <item type="boardgameexpansion" id="2915">
                    <name type="primary" value="Catan Card Game: Expansion Set"/>
                    <yearpublished value="2002" />
                </item>
                <item type="boardgameexpansion" id="432107">
                    <name type="primary" value="Catan Cenários: Portugal"/>
                    <yearpublished value="2020" />
                </item>
            </items>
        """.trimIndent()
    }
}
