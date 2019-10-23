package bigcodecs

import com.google.cloud.bigquery.FieldValue

trait FieldValueEncoder[A] { self =>

  def encode(value: A): FieldValue

  final def contramap[B](f: B => A): FieldValueEncoder[B] =
    new FieldValueEncoder[B] {
      override def encode(value: B): FieldValue =
        self.encode(f(value))
    }

}

object FieldValueEncoder {

  def apply[A](implicit ev: FieldValueEncoder[A]): FieldValueEncoder[A] =
    ev

  def instance[A](f: A => FieldValue): FieldValueEncoder[A] =
    new FieldValueEncoder[A] {
      override def encode(value: A): FieldValue =
        f(value)
    }

}
