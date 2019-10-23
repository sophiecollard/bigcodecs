package bigcodecs

import bigcodecs.error.DecodingFailure
import cats.syntax.either._
import com.google.cloud.bigquery.FieldValueList

trait FieldValueListDecoder[A] { self =>

  def decode(fieldValueList: FieldValueList): Either[DecodingFailure, A]

  final def map[B](f: A => B): FieldValueListDecoder[B] =
    new FieldValueListDecoder[B] {
      override def decode(fieldValueList: FieldValueList): Either[DecodingFailure, B] =
        self.decode(fieldValueList).map(f)
    }

}

object FieldValueListDecoder {

  def apply[A](implicit ev: FieldValueListDecoder[A]): FieldValueListDecoder[A] =
    ev

  def instance[A](f: FieldValueList => Either[DecodingFailure, A]): FieldValueListDecoder[A] =
    new FieldValueListDecoder[A] {
      override def decode(fieldValueList: FieldValueList): Either[DecodingFailure, A] =
        f(fieldValueList)
    }

}
