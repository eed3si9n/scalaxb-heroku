package scalaxb.servlet.model

import java.net.{URI}
import java.io.{File, Reader}

case class SchemaFile(uri: URI, reader: Reader)

object SchemaFile {
  import java.net.URL
  
  def fromURI(uri: URI): SchemaFile = {
    import dispatch._, Defaults._
    val s = Http(url(uri.toString) OK as.String)
    val stream = new java.io.ByteArrayInputStream(s().getBytes)
    val reader = new java.io.InputStreamReader(stream)
    SchemaFile(uri, reader)
  }
  
  def fromBytes(name: String, content: Array[Byte]) = {
    val stream = new java.io.ByteArrayInputStream(content)
    val reader = new java.io.InputStreamReader(stream)
    SchemaFile(new File(name).toURI, reader)    
  }
}
