package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.LovhjemmelType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber
import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class LegalBasisAgeAboveEighteenNoMeasureSpec extends Specification {

    @Subject
    LegalBasisAgeAboveEighteenNoMeasure sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new LegalBasisAgeAboveEighteenNoMeasure()
        context = getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def sak = context.rootObject.sak
        and:
        sak.fodselsnummer = getMockSocialSecurityNumber(age)
        and:
        sak.virksomhet[0].tiltak = List.of(sak.virksomhet[0].tiltak[0])
        and:
        sak.virksomhet[0].tiltak[0].lovhjemmel = null
        and:
        if (createLovhjemmel) {
            def leddList = ledd != null
                    ? List.of(ledd)
                    : List.of()

            sak.virksomhet[0].tiltak[0].lovhjemmel = new LovhjemmelType(
                    "BVL",
                    kapittel,
                    paragraf,
                    leddList as List<String>,
                    List.of())
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("år og skal dermed ikke ha omsorgstiltak")
        }

        where:
        age | createLovhjemmel | kapittel | paragraf | ledd || errorExpected
        17  | false            | ""       | ""       | null || false
        18  | false            | ""       | ""       | null || false
        18  | true             | ""       | ""       | null || false
        18  | true             | "4"      | "12"     | null || true
        18  | true             | "4"      | "8"      | null || false
        18  | true             | "4"      | "8"      | "2"  || true
        18  | true             | "4"      | "8"      | "3"  || true
        18  | true             | "4"      | "8"      | "4"  || false
    }
}
