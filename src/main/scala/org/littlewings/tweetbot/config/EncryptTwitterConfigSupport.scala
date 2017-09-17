package org.littlewings.tweetbot.config

import org.jasypt.util.text.{BasicTextEncryptor, TextEncryptor}

object EncryptTwitterConfigSupport {
  val DisabledEncrypt: Boolean =
    java.lang.Boolean.parseBoolean(System.getenv("TWEET_BOT_DISABLE_ENCRYPT_TWITTER_CONFIG"))

  val EncryptPassword: String = System.getenv("TWEET_BOT_TWITTER_ENTRYPT_PASSWORD")
}

trait EncryptTwitterConfigSupport extends TwitterConfigSupport {
  protected val textEncryptor: Option[TextEncryptor] = {
    if (EncryptTwitterConfigSupport.DisabledEncrypt) {
      Option.empty
    } else {
      val entryptor = new BasicTextEncryptor
      entryptor.setPassword(EncryptTwitterConfigSupport.EncryptPassword)
      Option(entryptor)
    }
  }

  protected def decryptIfNeeded(fun: => String): String =
    textEncryptor.map(t => t.decrypt(fun)).getOrElse(fun)

  abstract override def twitterOAuthConsumerKey: String =
    decryptIfNeeded(super.twitterOAuthConsumerKey)

  abstract override def twitterOAuthConsumerSecret: String =
    decryptIfNeeded(super.twitterOAuthConsumerSecret)

  abstract override def twitterOAuthAccessToken: String =
    decryptIfNeeded(super.twitterOAuthAccessToken)

  abstract override def twitterOAuthAccessTokenSecret: String =
    decryptIfNeeded(super.twitterOAuthAccessTokenSecret)
}
