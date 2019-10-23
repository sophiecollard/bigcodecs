package bigcodecs

import bigcodecs.error.DecodingFailure
import cats.syntax.either._
import com.google.cloud.bigquery.FieldValue

trait FieldValueDecoder[A] { self =>

  def decode(fieldValue: FieldValue): Either[DecodingFailure, A]

  final def map[B](f: A => B): FieldValueDecoder[B] =
    new FieldValueDecoder[B] {
      override def decode(fieldValue: FieldValue): Either[DecodingFailure, B] =
        self.decode(fieldValue).map(f)
    }

}

object FieldValueDecoder {

  def apply[A](implicit ev: FieldValueDecoder[A]): FieldValueDecoder[A] =
    ev

  def instance[A](f: FieldValue => Either[DecodingFailure, A]): FieldValueDecoder[A] =
    new FieldValueDecoder[A] {
      override def decode(fieldValue: FieldValue): Either[DecodingFailure, A] =
        f(fieldValue)
    }

}
