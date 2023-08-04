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

describe('BankDetail e2e test', () => {
  const bankDetailPageUrl = '/bank-detail';
  const bankDetailPageUrlPattern = new RegExp('/bank-detail(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bankDetailSample = {};

  let bankDetail;
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
        vendorNameEnglish: 'Estate',
        vendorNameArabic: 'Southwest labour',
        vendorId: '24/7 Plastic',
        vendorAccountNumber: 'porro diphthongize',
        vendorCRNumber: 'blue Centralized',
        vendorVATNumber: 'drive reluctantly degree',
        vendorType: 'LOCAL',
        vendorCategory: 'DISTRIBUTOR',
        vendorEstablishmentDate: 'Mouse calculate',
        vendorLogo: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        vendorLogoContentType: 'unknown',
        height: 7413,
        width: 16130,
        taken: '2023-08-04T00:15:18.790Z',
        uploaded: '2023-08-03T00:57:29.836Z',
        contactFullName: 'forenenst reconfigure',
        contactEmailAddress: '7eaO1o@_P.n<K!',
        contactPrimaryPhoneNo: 'Music Dynamic',
        contactPrimaryFaxNo: 'male',
        contactSecondaryPhoneNo: 'West who',
        contactSecondaryFaxNo: 'Auto teal parse',
        officeLocation: 'klutzy',
        officeFunctionality: 'BRANCH',
        websiteURL: 'East Tenge',
        buildingNo: 'services',
        vendorStreetName: 'Books',
        zipCode: '82254',
        districtName: 'Hryvnia attempt programming',
        additionalNo: 'compress Lamborghini',
        cityName: 'Buckinghamshire Erbium',
        unitNo: 'District Cruiser Diesel',
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
        cash: 'distributed Producer intermediate',
        credit: 'Mouse',
        letterOfCredit: 'Buckinghamshire Computers',
        others: 'Van engineer favor',
      },
    }).then(({ body }) => {
      vendor = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/bank-details+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/bank-details').as('postEntityRequest');
    cy.intercept('DELETE', '/api/bank-details/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/vendors', {
      statusCode: 200,
      body: [vendor],
    });
  });

  afterEach(() => {
    if (bankDetail) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/bank-details/${bankDetail.id}`,
      }).then(() => {
        bankDetail = undefined;
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

  it('BankDetails menu should load BankDetails page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('bank-detail');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BankDetail').should('exist');
    cy.url().should('match', bankDetailPageUrlPattern);
  });

  describe('BankDetail page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bankDetailPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BankDetail page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/bank-detail/new$'));
        cy.getEntityCreateUpdateHeading('BankDetail');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bankDetailPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/bank-details',
          body: {
            ...bankDetailSample,
            vendorsName: vendor,
          },
        }).then(({ body }) => {
          bankDetail = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/bank-details+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/bank-details?page=0&size=20>; rel="last",<http://localhost/api/bank-details?page=0&size=20>; rel="first"',
              },
              body: [bankDetail],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bankDetailPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BankDetail page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bankDetail');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bankDetailPageUrlPattern);
      });

      it('edit button click should load edit BankDetail page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BankDetail');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bankDetailPageUrlPattern);
      });

      it('edit button click should load edit BankDetail page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BankDetail');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bankDetailPageUrlPattern);
      });

      it('last delete button click should delete instance of BankDetail', () => {
        cy.intercept('GET', '/api/bank-details/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('bankDetail').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bankDetailPageUrlPattern);

        bankDetail = undefined;
      });
    });
  });

  describe('new BankDetail page', () => {
    beforeEach(() => {
      cy.visit(`${bankDetailPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BankDetail');
    });

    it('should create an instance of BankDetail', () => {
      cy.get(`[data-cy="bankAccount"]`).should('not.be.checked');
      cy.get(`[data-cy="bankAccount"]`).click();
      cy.get(`[data-cy="bankAccount"]`).should('be.checked');

      cy.get(`[data-cy="bankName"]`).type('Factors');
      cy.get(`[data-cy="bankName"]`).should('have.value', 'Factors');

      cy.get(`[data-cy="branchSwiftCode"]`).type('fervently Intranet');
      cy.get(`[data-cy="branchSwiftCode"]`).should('have.value', 'fervently Intranet');

      cy.get(`[data-cy="ibanNo"]`).type('modular orange');
      cy.get(`[data-cy="ibanNo"]`).should('have.value', 'modular orange');

      cy.get(`[data-cy="accountNumber"]`).type('Practical Marketing');
      cy.get(`[data-cy="accountNumber"]`).should('have.value', 'Practical Marketing');

      cy.get(`[data-cy="vendorsName"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        bankDetail = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', bankDetailPageUrlPattern);
    });
  });
});
