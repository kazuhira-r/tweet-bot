package org.littlewings.tweetbot.standard.lyrics

import org.junit.Test
import org.littlewings.tweetbot.test.ScalaTestJUnitTestSupport

class LyricsTest extends ScalaTestJUnitTestSupport {
  @Test
  def load(): Unit = {
    val lyrics = LyricsFactory.buildLyrics("singer")

    lyrics should have size 8

    val l1 = lyrics(0)
    l1.artist should be("Singer")
    l1.album should be("1st Album")
    l1.numbering should be("1st Full Album")
    l1.trackName should be("1-track-one")
    l1.lyrics should be("1-abcde")

    val l2 = lyrics(1)
    l2.artist should be("Singer")
    l2.album should be("1st Album")
    l2.numbering should be("1st Full Album")
    l2.trackName should be("1-track-one")
    l2.lyrics should be("1-efghi")

    val l3 = lyrics(2)
    l3.artist should be("Singer")
    l3.album should be("1st Album")
    l3.numbering should be("1st Full Album")
    l3.trackName should be("1-track-two")
    l3.lyrics should be("1-jklmn")

    val l4 = lyrics(3)
    l4.artist should be("Singer")
    l4.album should be("1st Album")
    l4.numbering should be("1st Full Album")
    l4.trackName should be("1-track-two")
    l4.lyrics should be("1-opqrs")

    val l5 = lyrics(4)
    l5.artist should be("Singer")
    l5.album should be("2nd Album")
    l5.numbering should be("2nd Full Album")
    l5.trackName should be("2-track-one")
    l5.lyrics should be("2-abcde")

    val l6 = lyrics(5)
    l6.artist should be("Singer")
    l6.album should be("2nd Album")
    l6.numbering should be("2nd Full Album")
    l6.trackName should be("2-track-one")
    l6.lyrics should be("2-efghi")

    val l7 = lyrics(6)
    l7.artist should be("Singer")
    l7.album should be("2nd Album")
    l7.numbering should be("2nd Full Album")
    l7.trackName should be("2-track-two")
    l7.lyrics should be("2-jklmn")

    val l8 = lyrics(7)
    l8.artist should be("Singer")
    l8.album should be("2nd Album")
    l8.numbering should be("2nd Full Album")
    l8.trackName should be("2-track-two")
    l8.lyrics should be("2-opqrs")
  }
}
