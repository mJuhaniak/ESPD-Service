package eu.europa.ec.grow.espd.xml.request.selection

import eu.europa.ec.grow.espd.criteria.enums.SelectionCriterion
import eu.europa.ec.grow.espd.domain.*
import eu.europa.ec.grow.espd.xml.base.AbstractSelectionCriteriaFixture

/**
 *  Created by vigi on 11/19/15:3:32 PM.
 */
class EspdRequestSelectionCriteriaMarshallingTest extends AbstractSelectionCriteriaFixture {

    def "should contain all selection Criterion elements even if the economic operator claims that it satisfies all the criteria"() {
        given:
        def espd = new EspdDocument(selectionSatisfiesAll: new SatisfiesAllCriterion(exists: true),
                enrolmentProfessionalRegister: new SuitabilityCriterion(exists: true),
                enrolmentTradeRegister: new SuitabilityCriterion(exists: true),
                serviceContractsAuthorisation: new SuitabilityCriterion(exists: true),
                serviceContractsMembership: new SuitabilityCriterion(exists: true),)
        def idx = 0

        when:
        def request = parseRequestXml(espd)

        then: "one and only one selection criteria"
        request.Criterion.size() == 5

        then: "check the CriterionID"
        checkCriterionId(request, idx, "7e7db838-eeac-46d9-ab39-42927486f22d")

        then: "check the CriterionTypeCode"
        request.Criterion[idx].TypeCode.text() == "SELECTION.ALL_CRITERIA_SATISFIED"
        request.Criterion[idx].TypeCode.@listAgencyID.text() == "EU-COM-GROW"
        request.Criterion[idx].TypeCode.@listID.text() == "CriteriaTypeCode"
        request.Criterion[idx].TypeCode.@listVersionID.text() == "1.0"

        then: "check name and description"
        request.Criterion[idx].Name.text() == "All selection criteria will be satisfied"
        request.Criterion[idx].Description.text() == "It satisfies all the required selection criteria indicated in the relevant notice or in the procurement documents referred to in the notice."

        then: "main subgroup"
        request.Criterion[idx].RequirementGroup.size() == 1
        request.Criterion[idx].RequirementGroup[0].ID.text() == "f3a6836d-2de2-4cd1-81ca-fb06178d05c5"

        checkRequirement(request.Criterion[idx].RequirementGroup[0].Requirement[0], "15335c12-ad77-4728-b5ad-3c06a60d65a4", "Your answer?", "INDICATOR")
    }

    def "all selection criteria should be in the correct order"() {
        given:
        def espd = new EspdDocument(
                selectionSatisfiesAll: new SatisfiesAllCriterion(exists: true),
                enrolmentProfessionalRegister: new SuitabilityCriterion(exists: true),
                enrolmentTradeRegister: new SuitabilityCriterion(exists: true),
                serviceContractsAuthorisation: new SuitabilityCriterion(exists: true),
                serviceContractsMembership: new SuitabilityCriterion(exists: true),
                generalYearlyTurnover: new EconomicFinancialStandingCriterion(exists: true),
                averageYearlyTurnover: new EconomicFinancialStandingCriterion(exists: true),
                specificYearlyTurnover: new EconomicFinancialStandingCriterion(exists: true),
                specificAverageTurnover: new EconomicFinancialStandingCriterion(exists: true),
                setupEconomicOperator: new EconomicFinancialStandingCriterion(exists: true),
                financialRatio: new EconomicFinancialStandingCriterion(exists: true),
                professionalRiskInsurance: new EconomicFinancialStandingCriterion(exists: true),
                otherEconomicFinancialRequirements: new EconomicFinancialStandingCriterion(exists: true),
                workContractsPerformanceOfWorks: new TechnicalProfessionalCriterion(exists: true),
                supplyContractsPerformanceDeliveries: new TechnicalProfessionalCriterion(exists: true),
                serviceContractsPerformanceServices: new TechnicalProfessionalCriterion(exists: true),
                techniciansTechnicalBodies: new TechnicalProfessionalCriterion(exists: true),
                workContractsTechnicians: new TechnicalProfessionalCriterion(exists: true),
                technicalFacilitiesMeasures: new TechnicalProfessionalCriterion(exists: true),
                studyResearchFacilities: new TechnicalProfessionalCriterion(exists: true),
                supplyChainManagement: new TechnicalProfessionalCriterion(exists: true),
                allowanceOfChecks: new TechnicalProfessionalCriterion(exists: true),
                educationalProfessionalQualifications: new TechnicalProfessionalCriterion(exists: true),
                environmentalManagementFeatures: new TechnicalProfessionalCriterion(exists: true),
                numberManagerialStaff: new TechnicalProfessionalCriterion(exists: true),
                averageAnnualManpower: new TechnicalProfessionalCriterion(exists: true),
                toolsPlantTechnicalEquipment: new TechnicalProfessionalCriterion(exists: true),
                subcontractingProportion: new TechnicalProfessionalCriterion(exists: true),
                supplyContractsSamplesDescriptionsWithoutCa: new TechnicalProfessionalCriterion(exists: true),
                supplyContractsSamplesDescriptionsWithCa: new TechnicalProfessionalCriterion(exists: true),
                supplyContractsCertificatesQc: new TechnicalProfessionalCriterion(exists: true),
                certificateIndependentBodiesAboutQa: new TechnicalProfessionalCriterion(exists: true),
                certificateIndependentBodiesAboutEnvironmental: new TechnicalProfessionalCriterion(exists: true))

        when:
        def request = parseRequestXml(espd)

        then:
        request.Criterion.size() == SelectionCriterion.values().size()
        int idx = 0;
        for (SelectionCriterion criterion : SelectionCriterion.values()) {
            checkCriterionId(request, idx++, criterion.getUuid())
        }
    }

}