package eu.europa.ec.grow.espd.xml.request.exclusion

import eu.europa.ec.grow.espd.criteria.enums.ExclusionCriterion
import eu.europa.ec.grow.espd.domain.LawCriterion
import eu.europa.ec.grow.espd.domain.EspdDocument
import eu.europa.ec.grow.espd.xml.base.AbstractExclusionCriteriaFixture
/**
 * Created by ratoico on 12/9/15 at 1:15 PM.
 */
class BreachingObligationsEnvironmentalRequestTest extends AbstractExclusionCriteriaFixture {


    def "09. should contain the 'Breaching of obligations in the fields of environmental law' criterion"() {
        given:
        def espd = new EspdDocument(breachingObligationsEnvironmental: new LawCriterion(exists: true))

        when:
        def request = parseRequestXml(espd)
        def idx = getRequestCriterionIndex(ExclusionCriterion.BREACHING_OF_OBLIGATIONS_ENVIRONMENTAL)

        then: "CriterionID element"
        request.Criterion.size() == getRequestNumberOfCriteria()
        checkCriterionId(request, idx, "a80ddb62-d25b-4e4e-ae22-3968460dc0a9")

        then: "CriterionTypeCode element"
        checkCriterionTypeCode(request, idx, "EXCLUSION.ENVIRONMENTAL_LAW")

        then: "CriterionName element"
        request.Criterion[idx].Name.text() == "Breaching of obligations in the fields of environmental law"

        then: "CriterionDescription element"
        request.Criterion[idx].Description.text() == "Has the economic operator, to its knowledge, breached its obligations in the fields of environmental law? As referred to for the purposes of this procurement in national law, in the relevant notice or the procurement documents or in Article 18(2) of Directive 2004/18/EU."

        then: "CriterionLegislationReference element"
        checkLegislationReference(request, idx, "57(4)")

        then: "check all the sub groups"
        request.Criterion[idx].RequirementGroup.size() == 1

        then: "main sub group"
        request.Criterion[idx].RequirementGroup[0].ID.text() == "976b5acb-c00f-46ca-8f83-5ce6abfdfe43"
        request.Criterion[idx].RequirementGroup[0].RequirementGroup.size() == 1
        request.Criterion[idx].RequirementGroup[0].Requirement.size() == 2

        then: "main sub group requirements"
        def r1_0 = request.Criterion[idx].RequirementGroup[0].Requirement[0]
        checkRequirement(r1_0, "974c8196-9d1c-419c-9ca9-45bb9f5fd59a", "Your answer?", "INDICATOR")

        def r1_1 = request.Criterion[idx].RequirementGroup[0].Requirement[1]
        checkRequirement(r1_1, "e098da8e-4717-4500-965f-f882d5b4e1ad", "Please describe them", "DESCRIPTION")

        then: "check first sub group"
        def sub1_1 = request.Criterion[idx].RequirementGroup[0].RequirementGroup[0]
        checkSelfCleaningRequirementGroup(sub1_1)
    }

}