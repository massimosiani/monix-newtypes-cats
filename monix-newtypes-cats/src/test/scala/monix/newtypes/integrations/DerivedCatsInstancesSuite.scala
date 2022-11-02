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

import cats.syntax.all.*
import cats.{Eq, Hash, Order, Show}
import monix.newtypes.NewtypeWrapped
import monix.newtypes.integrations.DerivedCatsInstancesSuite.*
import org.scalacheck.Prop.*

class DerivedCatsInstancesSuite extends munit.ScalaCheckSuite {

  test("an instance of Eq exists") {
    implicitly[Eq[SomeNewtype]]
  }
  property("the Eq instances produce the same result") {
    forAll { (x: Internal, y: Internal) =>
      x.eqv(y) === SomeNewtype(x).eqv(SomeNewtype(y))
    }
  }

  test("an instance of Hash exists") {
    assert(SomeNewtype.catsHash[SomeNewtype, Internal].isInstanceOf[Hash[_]])
  }
  property("the Hash instances produce the same result") {
    forAll { (x: Internal) =>
      x.hash === SomeNewtype.catsHash[SomeNewtype, Internal].hash(SomeNewtype(x))
    }
  }

  test("an instance of Show exists") {
    implicitly[Show[SomeNewtype]]
  }
  property("the Show instances produce the same result") {
    forAll { (x: Internal) =>
      x.show === SomeNewtype(x).show
    }
  }

  test("an instance of Order exists") {
    implicitly[Order[SomeNewtype]]
  }
  property("the Order instances produce the same result") {
    forAll { (x: Internal, y: Internal) =>
      x.compare(y) === SomeNewtype(x).compare(SomeNewtype(y))
    }
  }
}

object DerivedCatsInstancesSuite {
  final type Internal = String

  final type SomeNewtype = SomeNewtype.Type
  object SomeNewtype extends NewtypeWrapped[Internal] with DerivedCatsInstances
}
