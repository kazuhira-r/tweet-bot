package org.littlewings.tweetbot.standard.lyrics

trait LyricsFormatter extends Serializable {
  def format(tweetLyrics: Lyrics): String
}

@SerialVersionUID(1L)
class StandardLyricsFormatter extends LyricsFormatter {
  override def format(tweetLyrics: Lyrics): String =
    if (tweetLyrics.mediaType == "digital-single" || tweetLyrics.mediaType == "self-produced-single")
      s"""|${tweetLyrics.lyrics.substring(1)}
          |
          |[${tweetLyrics.trackName}]""".stripMargin
    else
      s"""|${tweetLyrics.lyrics.substring(1)}
          |
          |[${tweetLyrics.trackName} / ${tweetLyrics.album}]""".stripMargin
}
