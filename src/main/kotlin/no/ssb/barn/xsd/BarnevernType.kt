package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import java.io.Serializable
import java.time.LocalDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlRootElement(name = "Barnevern")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BarnevernType",
    propOrder = ["datoUttrekk", "fagsystem", "avgiver", "sak"]
)
data class BarnevernType(
    @field:XmlAttribute(name = "DatoUttrekk", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var datoUttrekk: LocalDateTime = LocalDateTime.now(),

    @field:XmlElement(name = "Fagsystem", required = true)
    var fagsystem: FagsystemType = FagsystemType(),

    @field:XmlElement(name = "Avgiver", required = true)
    var avgiver: AvgiverType = AvgiverType(),

    @field:XmlElement(name = "Sak", type = SakType::class, required = true)
    var sak: SakType = SakType()
) : Serializable