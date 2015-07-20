package scalaxb.servlet

import java.net.{URI, URISyntaxException}
import java.io.{File}
import scala.collection.mutable
import scalaxb.servlet.model.{Driver, ScalaFile, SchemaFile}
import scalaxb.compiler.Config
import scalaxb.compiler.ConfigEntry._
import unfiltered.request._
import unfiltered.response._
import unfiltered.request.{Path => UFPath}
import unfiltered.filter.request._

object CompilerFilter {
  lazy val filter = unfiltered.filter.Planify ({
    case POST(UFPath(Seg("compile" :: what :: Nil)) & MultiPart(req)) =>
      val (params, multipart) = MultiPartParams.Memory(req) match {
        case MultipartData(params, files) =>
          (params, files("argf") filter {_.size > 0})
      }
      def nonempty(s: String) = params(s) filter { "" != }
      def opt(s: String) = nonempty(s).headOption
      try {
        lazy val files = (nonempty("arg") map { x => SchemaFile.fromURI(new URI(x)) }) ++
          (multipart map { x => SchemaFile.fromBytes(x.name, x.bytes) })

        lazy val config0 = Config.default.
          update(PackageNames(Map[Option[String], Option[String]](
            (None -> opt("defaultPackageName")) ::
            (params("namespaceURI") zip params("packageName")).toList.map { x =>
              Some(x._1) -> Some(x._2) }:_*)))
        lazy val config1 = opt("classPrefix") match {
          case Some(x) => config0.update(ClassPrefix(x))
          case _       => config0
        }
        lazy val config2 = opt("paramPrefix") match {
          case Some(x) => config1.update(ParamPrefix(x))
          case _       => config1
        }
        lazy val config = config2.update(WrappedComplexTypes(nonempty("wrapContents").toList))
        lazy val scalaFiles = Driver.process(files, config)

        if (files.isEmpty) BadRequest ~> ResponseString("missing arg.")
        else ContentType("application/octet-stream") ~> 
          ResponseHeader("Content-disposition",
            Seq("attachment; filename=\"" + what + "\"")) ~>
          ("""([.]\w+)$""".r.findFirstIn(what) match {
            case Some(".scala") => ResponseString(scalaFiles.head.content)
            case Some(".zip")   => ResponseBytes(ScalaFile.zip(scalaFiles))
            case _ => BadRequest ~> ResponseString("unsupported file extension.")
          })
      }
      catch {
        case e: URISyntaxException => BadRequest ~> ResponseString(e.getMessage)
        case e: Exception => InternalServerError ~>
          ResponseString(e.getMessage + "\n\n" +
            e.getStackTrace.toArray.mkString("\n"))
      }
  })
}
