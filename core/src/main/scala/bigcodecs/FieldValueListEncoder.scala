package bigcodecs

import com.google.cloud.bigquery.FieldValueList

trait FieldValueListEncoder[A] { self =>

  def encode(value: A): FieldValueList

  final def contramap[B](f: B => A): FieldValueListEncoder[B] =
    new FieldValueListEncoder[B] {
      override def encode(value: B): FieldValueList =
        self.encode(f(value))
    }

}

object FieldValueListEncoder {

  def apply[A](implicit ev: FieldValueListEncoder[A]): FieldValueListEncoder[A] =
    ev

  def instance[A](f: A => FieldValueList): FieldValueListEncoder[A] =
    new FieldValueListEncoder[A] {
      override def encode(value: A): FieldValueList =
        f(value)
    }

}
