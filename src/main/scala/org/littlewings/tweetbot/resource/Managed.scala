package org.littlewings.tweetbot.resource

import resource.{ManagedResource, Resource}

object Managed {
  def apply[A: Resource : OptManifest](opener: => A): ManagedResource[A] = resource.managed(opener)
}
