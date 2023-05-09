/*
 *
 * Copyright 2016 EUROPEAN COMMISSION
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/community/eupl/og_page/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 *
 */

package eu.europa.ec.grow.espd.domain;

import eu.europa.ec.grow.espd.domain.enums.criteria.ExclusionCriterion;
import eu.europa.ec.grow.espd.domain.enums.criteria.SelectionCriterion;
import eu.europa.ec.grow.espd.domain.ubl.CcvCriterion;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.CriterionType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Data
@Slf4j
public class EspdDocument {

    private Boolean extendCe;

    private String html;

    private PartyImpl authority;
    private EconomicOperatorImpl economicOperator;

    private String ojsNumber;
    private String ngojNumber;
    private String procedureType;
    private String testTextField;
    private String procedureTitle;
    private String procedureShortDesc;
    private String lotConcerned;
    private String fileRefByCA;
    private String tedUrl; // aka ojsUrl
    private String consortiumName;

    private EspdRequestMetadata requestMetadata;

    private String tedReceptionId;

    private Date documentDate;
    private String location;

    // Award criteria
    private OtherCriterion procurementReserved;
    private OtherCriterion eoRegistered;
    private OtherCriterion eoParticipatingProcurementProcedure;
    private OtherCriterion eoReliesCapacities;
    private OtherCriterion meetsObjective;
    private OtherCriterion subcontractingThirdParties;

    // Exclusion criteria

    private CriminalConvictionsCriterion criminalConvictions;
    private CriminalConvictionsCriterion corruption;
    private CriminalConvictionsCriterion fraud;
    private CriminalConvictionsCriterion terroristOffences;
    private CriminalConvictionsCriterion moneyLaundering;
    private CriminalConvictionsCriterion childLabour;

    private TaxesCriterion paymentTaxes;
    private TaxesCriterion paymentSocialSecurity;

    private LawCriterion breachingObligationsEnvironmental;
    private LawCriterion breachingObligationsSocial;
    private LawCriterion breachingObligationsLabour;

    private BankruptcyCriterion bankruptcy;
    private BankruptcyCriterion insolvency;
    private BankruptcyCriterion arrangementWithCreditors;
    private BankruptcyCriterion analogousSituation;
    private BankruptcyCriterion assetsAdministeredByLiquidator;
    private BankruptcyCriterion businessActivitiesSuspended;

    private MisconductDistortionCriterion guiltyGrave;
    private MisconductDistortionCriterion agreementsWithOtherEO;

    private ConflictInterestCriterion conflictInterest;
    private ConflictInterestCriterion involvementPreparationProcurement;
    private ConflictInterestCriterion earlyTermination;
    private ConflictInterestCriterion guiltyMisinterpretation;

    private PurelyNationalGrounds purelyNationalGrounds;

    // Selection criteria

    private SatisfiesAllCriterion selectionSatisfiesAll;

    private SuitabilityCriterion enrolmentProfessionalRegister;
    private SuitabilityCriterion enrolmentTradeRegister;
    private SuitabilityCriterion serviceContractsAuthorisation;
    private SuitabilityCriterion serviceContractsMembership;

    private EconomicFinancialStandingCriterion generalYearlyTurnover;
    private EconomicFinancialStandingCriterion averageYearlyTurnover;
    private EconomicFinancialStandingCriterion specificYearlyTurnover;
    private EconomicFinancialStandingCriterion specificAverageTurnover;
    private EconomicFinancialStandingCriterion setupEconomicOperator;
    private EconomicFinancialStandingCriterion financialRatio;
    private EconomicFinancialStandingCriterion professionalRiskInsurance;
    private EconomicFinancialStandingCriterion otherEconomicFinancialRequirements;

    private TechnicalProfessionalCriterion workContractsPerformanceOfWorks;
    private TechnicalProfessionalCriterion supplyContractsPerformanceDeliveries;
    private TechnicalProfessionalCriterion serviceContractsPerformanceServices;
    private TechnicalProfessionalCriterion techniciansTechnicalBodies;
    private TechnicalProfessionalCriterion workContractsTechnicians;
    private TechnicalProfessionalCriterion technicalFacilitiesMeasures;
    private TechnicalProfessionalCriterion studyResearchFacilities;
    private TechnicalProfessionalCriterion supplyChainManagement;
    private TechnicalProfessionalCriterion allowanceOfChecks;
    private TechnicalProfessionalCriterion educationalProfessionalQualifications;
    private TechnicalProfessionalCriterion environmentalManagementFeatures;
    private TechnicalProfessionalCriterion numberManagerialStaff;
    private TechnicalProfessionalCriterion averageAnnualManpower;
    private TechnicalProfessionalCriterion toolsPlantTechnicalEquipment;
    private TechnicalProfessionalCriterion subcontractingProportion;
    private TechnicalProfessionalCriterion supplyContractsSamplesDescriptionsWithoutCa;
    private TechnicalProfessionalCriterion supplyContractsSamplesDescriptionsWithCa;
    private TechnicalProfessionalCriterion supplyContractsCertificatesQc;

    private QualityAssuranceCriterion certificateIndependentBodiesAboutQa;
    private QualityAssuranceCriterion certificateIndependentBodiesAboutEnvironmental;

    private List<CriterionType> ublCriteria;

    public final boolean getAtLeastOneSelectionCriterionWasSelected() {
        for (SelectionCriterion ccvCriterion : SelectionCriterion.ALL_VALUES) {
            EspdCriterion espdCriterion = readCriterionFromEspd(ccvCriterion);
            if (espdCriterion != null && espdCriterion.getExists()) {
                return true;
            }
        }
        return false;
    }

    public final boolean getAllSelectionCriterionWasSelectedExceptAlpha() {
        for (SelectionCriterion ccvCriterion : SelectionCriterion.ALL_VALUES) {
            if (!SelectionCriterion.ALL_SELECTION_CRITERIA_SATISFIED.getUuid().equals(ccvCriterion.getUuid())) {
                EspdCriterion espdCriterion = readCriterionFromEspd(ccvCriterion);
                if (espdCriterion == null || !espdCriterion.getExists()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Read the value associated with a {@link CcvCriterion} stored on this domain object instance.
     *
     * @param ccvCriterion The UBL criterion for which we want to retrieve the information
     *
     * @return The value stored on the ESPD domain object for the given criterion
     */
    public final EspdCriterion readCriterionFromEspd(CcvCriterion ccvCriterion) {
        try {
            return (EspdCriterion) PropertyUtils.getSimpleProperty(this, ccvCriterion.getEspdDocumentField());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // it should never happen, the tests should cover the improper management of these values
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Turn the 'exists' flag of all exclusion criteria fields to 'true, basically instilling life into all of them.
     */
    public final void giveLifeToAllExclusionCriteria() {
        for (eu.europa.ec.grow.espd.domain.enums.criteria.ExclusionCriterion crit : ExclusionCriterion.ALL_VALUES) {
            instantiateEspdCriterion(crit.getEspdDocumentField(), true);
        }
    }

    /**
     * Turn the 'exists' flag of all selection criteria fields to 'true, basically instilling life into all of them.
     */
    public final void giveLifeToAllSelectionCriteria() {
        for (SelectionCriterion crit : SelectionCriterion.ALL_VALUES) {
            instantiateEspdCriterion(crit.getEspdDocumentField(), true);
        }
    }

    public final void selectCAExclusionCriteriaEU() {
        for (eu.europa.ec.grow.espd.domain.enums.criteria.ExclusionCriterion crit : ExclusionCriterion.ALL_VALUES) {
            instantiateEspdCriterion(crit.getEspdDocumentField(),
                    !ExclusionCriterion.NATIONAL_EXCLUSION_GROUNDS.equals(crit));
        }
    }

    private void instantiateEspdCriterion(String fieldName, Boolean value) {
        try {
            Class<?> clazz = PropertyUtils.getPropertyType(this, fieldName);
            EspdCriterion espdCriterion = (EspdCriterion) clazz.newInstance();
            espdCriterion.setExists(value);
            PropertyUtils.setSimpleProperty(this, fieldName, espdCriterion);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            // this is covered by tests, it should never happen so we don't care
            log.error(e.getMessage(), e);
        }
    }

    public final boolean hasProcurementInformation() {
        return hasPublicationInformation() || hasProcurementProcedureInformation() || hasProcurerIdentity();
    }

    private boolean hasPublicationInformation() {
        return isNotBlank(ojsNumber) || isNotBlank(tedUrl);
    }

    private boolean hasProcurementProcedureInformation() {
        return isNotBlank(procedureTitle) || isNotBlank(procedureShortDesc) || isNotBlank(fileRefByCA);
    }

    private boolean hasProcurerIdentity() {
        return authority != null && isNotBlank(authority.getName());
    }
}
