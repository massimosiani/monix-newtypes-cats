/*
 * Copyright 2022 Massimo Siani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
