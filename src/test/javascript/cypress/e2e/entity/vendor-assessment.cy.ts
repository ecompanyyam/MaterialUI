import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('VendorAssessment e2e test', () => {
  const vendorAssessmentPageUrl = '/vendor-assessment';
  const vendorAssessmentPageUrlPattern = new RegExp('/vendor-assessment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const vendorAssessmentSample = {};

  let vendorAssessment;
  let vendor;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/vendors',
      body: {
        vendorNameEnglish: 'Cheese Sausages',
        vendorNameArabic: 'International Northeast',
        vendorId: 'Functionality',
        vendorAccountNumber: 'Dakota',
        vendorCRNumber: 'Handmade',
        vendorVATNumber: 'Southwest Agender',
        vendorType: 'LOCAL',
        vendorCategory: 'MANUFACTURER',
        vendorEstablishmentDate: 'revivify Steel',
        vendorLogo: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        vendorLogoContentType: 'unknown',
        height: 19634,
        width: 23243,
        taken: '2023-08-03T10:18:24.261Z',
        uploaded: '2023-08-03T17:30:42.538Z',
        contactFullName: 'shop Computer',
        contactEmailAddress: 'vO*z]@j7cj[.#',
        contactPrimaryPhoneNo: 'eek',
        contactPrimaryFaxNo: 'esse',
        contactSecondaryPhoneNo: 'Gender Savings',
        contactSecondaryFaxNo: 'Toys Cambridgeshire Tuvalu',
        officeLocation: 'Home Music',
        officeFunctionality: 'MAIN',
        websiteURL: 'Thallium Southwest',
        buildingNo: 'Connecticut',
        vendorStreetName: 'wearily',
        zipCode: '35751-0244',
        districtName: 'Massachusetts International sievert',
        additionalNo: 'Concrete Diesel',
        cityName: 'reboot',
        unitNo: 'continually Garden Underpass',
        country: 'USA',
        googleMap: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        combinedAddress: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        cRCertificateUpload: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        cRCertificateUploadContentType: 'unknown',
        vATCertificateUpload: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        vATCertificateUploadContentType: 'unknown',
        nationalAddressUpload: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        nationalAddressUploadContentType: 'unknown',
        companyProfileUpload: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        companyProfileUploadContentType: 'unknown',
        otherUpload: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        otherUploadContentType: 'unknown',
        cash: 'that card adipisci',
        credit: 'Southwest Trans MTF',
        letterOfCredit: 'provided badly',
        others: 'Coordinator against Sedan',
      },
    }).then(({ body }) => {
      vendor = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/vendor-assessments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/vendor-assessments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/vendor-assessments/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/vendors', {
      statusCode: 200,
      body: [vendor],
    });
  });

  afterEach(() => {
    if (vendorAssessment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/vendor-assessments/${vendorAssessment.id}`,
      }).then(() => {
        vendorAssessment = undefined;
      });
    }
  });

  afterEach(() => {
    if (vendor) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/vendors/${vendor.id}`,
      }).then(() => {
        vendor = undefined;
      });
    }
  });

  it('VendorAssessments menu should load VendorAssessments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('vendor-assessment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('VendorAssessment').should('exist');
    cy.url().should('match', vendorAssessmentPageUrlPattern);
  });

  describe('VendorAssessment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(vendorAssessmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create VendorAssessment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/vendor-assessment/new$'));
        cy.getEntityCreateUpdateHeading('VendorAssessment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorAssessmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/vendor-assessments',
          body: {
            ...vendorAssessmentSample,
            vendorsName: vendor,
          },
        }).then(({ body }) => {
          vendorAssessment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/vendor-assessments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/vendor-assessments?page=0&size=20>; rel="last",<http://localhost/api/vendor-assessments?page=0&size=20>; rel="first"',
              },
              body: [vendorAssessment],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(vendorAssessmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details VendorAssessment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('vendorAssessment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorAssessmentPageUrlPattern);
      });

      it('edit button click should load edit VendorAssessment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('VendorAssessment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorAssessmentPageUrlPattern);
      });

      it('edit button click should load edit VendorAssessment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('VendorAssessment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorAssessmentPageUrlPattern);
      });

      it('last delete button click should delete instance of VendorAssessment', () => {
        cy.intercept('GET', '/api/vendor-assessments/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('vendorAssessment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorAssessmentPageUrlPattern);

        vendorAssessment = undefined;
      });
    });
  });

  describe('new VendorAssessment page', () => {
    beforeEach(() => {
      cy.visit(`${vendorAssessmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('VendorAssessment');
    });

    it('should create an instance of VendorAssessment', () => {
      cy.get(`[data-cy="assessmentDate"]`).type('2023-08-04T00:19');
      cy.get(`[data-cy="assessmentDate"]`).blur();
      cy.get(`[data-cy="assessmentDate"]`).should('have.value', '2023-08-04T00:19');

      cy.get(`[data-cy="assessedBY"]`).type('saepe');
      cy.get(`[data-cy="assessedBY"]`).should('have.value', 'saepe');

      cy.get(`[data-cy="jobKnowledge"]`).select('POOR');

      cy.get(`[data-cy="jobKnowledgeComment"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="jobKnowledgeComment"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="workQuality"]`).select('EXCELLENT');

      cy.get(`[data-cy="workQualityComment"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="workQualityComment"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="attendancePunctuality"]`).select('SATISFACTORY');

      cy.get(`[data-cy="attendancePunctualityComment"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="attendancePunctualityComment"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="initiative"]`).select('EXCELLENT');

      cy.get(`[data-cy="initiativeComment"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="initiativeComment"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="communicationListeningSkills"]`).select('EXCELLENT');

      cy.get(`[data-cy="communicationListeningSkillsComment"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="communicationListeningSkillsComment"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="dependability"]`).select('SATISFACTORY');

      cy.get(`[data-cy="dependabilityComment"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="dependabilityComment"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="vendorsName"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        vendorAssessment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', vendorAssessmentPageUrlPattern);
    });
  });
});
