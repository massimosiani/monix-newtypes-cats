package monix.newtypes.integrations

import cats.{Eq, Hash, Show}
import monix.newtypes.HasExtractor

trait DerivedCatsInstances extends DerivedCatsEq with DerivedCatsHash with DerivedCatsShow

trait DerivedCatsEq {
  implicit def catsEq[T, S](implicit extractor: HasExtractor.Aux[T, S], eqs: Eq[S]): Eq[T] = new Eq[T] {
    override def eqv(x: T, y: T): Boolean = eqs.eqv(extractor.extract(x), extractor.extract(y))
  }
}

trait DerivedCatsHash { self: DerivedCatsEq =>
  implicit def catsHash[T, S](implicit extractor: HasExtractor.Aux[T, S], hashS: Hash[S]): Hash[T] = new Hash[T] {
    override def eqv(x: T, y: T): Boolean = self.catsEq.eqv(x, y)
    override def hash(x: T): Int          = hashS.hash(extractor.extract(x))
  }
}

trait DerivedCatsShow {
  implicit def catsShow[T, S](implicit extractor: HasExtractor.Aux[T, S], showS: Show[S]): Show[T] = new Show[T] {
    override def show(t: T): String = showS.show(extractor.extract(t))
  }
}
