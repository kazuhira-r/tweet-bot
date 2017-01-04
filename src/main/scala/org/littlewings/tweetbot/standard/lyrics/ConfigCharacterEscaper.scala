package org.littlewings.tweetbot.standard.lyrics

trait ConfigCharacterEscaper {
  private[lyrics] val escapeTargetChars: Set[Char] = Set(':', ',', '&', '.', '!', '#')

  def escape(target: String): String =
    target
      .flatMap { c =>
        if (escapeTargetChars.contains(c))
          "\"" + c + "\""
        else
          c.toString
      }
}
