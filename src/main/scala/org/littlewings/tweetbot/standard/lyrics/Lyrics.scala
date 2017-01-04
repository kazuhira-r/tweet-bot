package org.littlewings.tweetbot.standard.lyrics

import org.littlewings.tweetbot.tweet.TweetSource

object LyricsFactory {
  def buildLyrics(artistAlias: String, tweetLyricsFormatter: LyricsFormatter = new StandardLyricsFormatter): Seq[Lyrics] = {
    val artist = ArtistFactory.create(artistAlias)
    val media =
      artist
        .media
        .map(albumNameAlias => MediaFactory.create(artist, albumNameAlias))

    media
      .flatMap { m =>
        m.trackLyrics.flatMap { case (trackName, lyrics) =>
          lyrics.map(l => Lyrics(artist.name, m.name, m.numbering, m.mediaType, trackName, l, tweetLyricsFormatter))
        }
      }
  }
}

case class Lyrics(artist: String,
                  album: String,
                  numbering: String,
                  mediaType: String,
                  trackName: String,
                  lyrics: String,
                  lyricsFormatter: LyricsFormatter) extends TweetSource {
  override def format: String = lyricsFormatter.format(this)
}
