package bigcodecs.instances

import bigcodecs.FieldValueDecoder
import bigcodecs.error.DecodingFailure

import scala.util.{Failure, Success, Try}

trait FieldValueDecoderInstances {

  implicit val stringFieldValueDecoder: FieldValueDecoder[String] =
    FieldValueDecoder.instance { fieldValue =>
      Try(fieldValue.getStringValue) match {
        case Success(string) => Right(string)
        case Failure(_)      => Left(DecodingFailure(s"Failed to decode String from [$fieldValue]"))
      }
    }

  implicit val booleanFieldValueDecoder: FieldValueDecoder[Boolean] =
    FieldValueDecoder.instance { fieldValue =>
      Try(fieldValue.getBooleanValue) match {
        case Success(boolean) => Right(boolean)
        case Failure(_)       => Left(DecodingFailure(s"Failed to decode Boolean from [$fieldValue]"))
      }
    }

  implicit val intFieldValueDecoder: FieldValueDecoder[Int] =
    FieldValueDecoder.instance { fieldValue =>
      Try(fieldValue.getLongValue) match {
        case Success(long) if long <= Int.MaxValue =>
          Right(long.toInt)
        case _                                     =>
          Left(DecodingFailure(s"Failed to decode Int from [$fieldValue]"))
      }
    }

  implicit val longFieldValueDecoder: FieldValueDecoder[Long] =
    FieldValueDecoder.instance { fieldValue =>
      Try(fieldValue.getLongValue) match {
        case Success(long) => Right(long)
        case Failure(_)    => Left(DecodingFailure(s"Failed to decode Long from [$fieldValue]"))
      }
    }

  implicit val doubleFieldValueDecoder: FieldValueDecoder[Double] =
    FieldValueDecoder.instance { fieldValue =>
      Try(fieldValue.getDoubleValue) match {
        case Success(double) => Right(double)
        case Failure(_)      => Left(DecodingFailure(s"Failed to decode Double from [$fieldValue]"))
      }
    }

  implicit val bigDecimalFieldValueDecoder: FieldValueDecoder[BigDecimal] =
    FieldValueDecoder.instance { fieldValue =>
      Try(fieldValue.getNumericValue) match {
        case Success(bigDecimal) => Right(bigDecimal)
        case Failure(_)          => Left(DecodingFailure(s"Failed to decode BigDecimal from [$fieldValue]"))
      }
    }

}
