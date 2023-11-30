package com.example.exercise4

object Constants {
    private val championsList = mutableListOf<ChampionModel>(
        ChampionModel("Ashe", "Iceborn warmother of the Avarosan tribe, Ashe commands the most populous horde in the north. Stoic, intelligent, and idealistic, yet uncomfortable with her role as leader, she taps into the ancestral magics of her lineage to wield a bow of True Ice", Lane.BOTTOM, 3f),
        ChampionModel("Aatrox", "Once honored defenders of Shurima against the Void, Aatrox and his brethren would eventually become an even greater threat to Runeterra, and were defeated only by cunning mortal sorcery. But after centuries of imprisonment, Aatrox was the first to find", Lane.TOP, 5f),
        ChampionModel("Ahri", "Innately connected to the magic of the spirit realm, Ahri is a fox-like vastaya who can manipulate her prey's emotions and consume their essence—receiving flashes of their memory and insight from each soul she consumes. Once a powerful yet wayward", Lane.MID, 1f),
        ChampionModel("Akali", "Abandoning the Kinkou Order and her title of the Fist of Shadow, Akali now strikes alone, ready to be the deadly weapon her people need. Though she holds onto all she learned from her master Shen, she has pledged to defend Ionia from its enemies, one", Lane.MID, 5f),
        ChampionModel("Alistar", "Always a mighty warrior with a fearsome reputation, Alistar seeks revenge for the death of his clan at the hands of the Noxian empire. Though he was enslaved and forced into the life of a gladiator, his unbreakable will was what kept him from truly", Lane.SUPPORT, 3.5f),
        ChampionModel("Cho'Gath", "From the moment Cho'Gath first emerged into the harsh light of Runeterra's sun, the beast was driven by the most pure and insatiable hunger. A perfect expression of the Void's desire to consume all life, Cho'Gath's complex biology quickly converts", Lane.TOP, 2f),
        ChampionModel("Corki", "The yordle pilot Corki loves two things above all others: flying, and his glamorous mustache... though not necessarily in that order. After leaving Bandle City, he settled in Piltover and fell in love with the wondrous machines he found there.", Lane.MID, 4f),
        ChampionModel("Garen", "A proud and noble warrior, Garen fights as one of the Dauntless Vanguard. He is popular among his fellows, and respected well enough by his enemies—not least as a scion of the prestigious Crownguard family, entrusted with defending Demacia and its", Lane.TOP, 2f),
        ChampionModel("Heimerdinger", "A brilliant yet eccentric yordle scientist, Professor Cecil B. Heimerdinger is one of the most innovative and esteemed inventors Piltover has ever known. Relentless in his work to the point of neurotic obsession, he thrives on answering the universe's", Lane.MID, 1f),
        ChampionModel("Lee Sin", "A master of Ionia's ancient martial arts, Lee Sin is a principled fighter who channels the essence of the dragon spirit to face any challenge. Though he lost his sight many years ago, the warrior-monk has devoted his life to protecting his homeland", Lane.JUNGLE, 5f),
        ChampionModel("Rammus", "Idolized by many, dismissed by some, mystifying to all, the curious being Rammus is an enigma. Protected by a spiked shell, he inspires increasingly disparate theories on his origin wherever he goes—from demigod, to sacred oracle, to a mere beast", Lane.JUNGLE, 5f),
        ChampionModel("Sion", "A war hero from a bygone era, Sion was revered in Noxus for choking the life out of a Demacian king with his bare hands—but, denied oblivion, he was resurrected to serve his empire even in death. His indiscriminate slaughter claimed all who stood in his", Lane.TOP, 5f),

    )
    fun getChampionsList(): MutableList<ChampionModel> {
        val list = ArrayList<ChampionModel>()
        for (i in championsList.indices) {
            list.add(championsList[i])
        }
        return list
    }

    fun addChampion(champion: ChampionModel) {
        championsList.add(champion)
    }
}