package monix.newtypes.integrations

import cats.syntax.all.*
import cats.{Eq, Hash, Show}
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
    implicitly[Hash[SomeNewtype]]
  }
  property("the Hash instances produce the same result") {
    forAll { (x: Internal) =>
      x.hash === SomeNewtype(x).hash
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
}

object DerivedCatsInstancesSuite {
  final type Internal = String

  final type SomeNewtype = SomeNewtype.Type
  object SomeNewtype extends NewtypeWrapped[Internal] with DerivedCatsInstances
}
