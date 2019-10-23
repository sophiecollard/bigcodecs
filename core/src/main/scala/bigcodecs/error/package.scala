package bigcodecs

package object error {

  sealed trait Error {
    def message: String
  }

  final case class DecodingFailure(message: String) extends Error

}
