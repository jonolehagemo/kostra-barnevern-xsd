package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.converter.UuidAdapter
import java.time.LocalDateTime
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OversendelsePrivatKravType", propOrder = ["id", "startDato", "konklusjon"])
data class OversendelsePrivatKravType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var startDato: LocalDateTime = LocalDateTime.now(),

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: OversendelsePrivatKravKonklusjonType? = null
)
