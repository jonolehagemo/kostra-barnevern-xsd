package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.VirksomhetType
import java.util.*

class BusinessStartDateBeforeCaseStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Virksomhet Kontroll 2e: StartDato er før sakens StartDato",
    VirksomhetType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak
        val caseStartDate = sak.startDato

        return sak.virksomhet.asSequence()
            .filter { virksomhet ->
                virksomhet.startDato.isBefore(caseStartDate)
            }
            .map {
                createReportEntry(
                    "Virksomhetens sluttdato (${it.startDato}) "
                            + "er før sakens sakens sluttdato ($caseStartDate)",
                    UUID.randomUUID() // Faking id because Virksomhet/Business does not have an id based on UUID
                )
            }
            .toList()
            .ifEmpty { null }
    }
}