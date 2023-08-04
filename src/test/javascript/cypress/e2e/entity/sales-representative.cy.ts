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

describe('SalesRepresentative e2e test', () => {
  const salesRepresentativePageUrl = '/sales-representative';
  const salesRepresentativePageUrlPattern = new RegExp('/sales-representative(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const salesRepresentativeSample = {};

  let salesRepresentative;
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
        vendorNameEnglish: 'maroon extend male',
        vendorNameArabic: 'er redundant Oxygen',
        vendorId: 'orange Cleora',
        vendorAccountNumber: 'Electric Drives',
        vendorCRNumber: 'Mount definition Transmasculine',
        vendorVATNumber: 'cum Electronic',
        vendorType: 'LOCAL',
        vendorCategory: 'SUBCONTRACTOR',
        vendorEstablishmentDate: 'Tactics',
        vendorLogo: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        vendorLogoContentType: 'unknown',
        height: 21024,
        width: 4352,
        taken: '2023-08-03T13:11:25.123Z',
        uploaded: '2023-08-03T18:59:03.026Z',
        contactFullName: 'Berkshire Folding',
        contactEmailAddress: ';f5@GmO5.g:*V',
        contactPrimaryPhoneNo: 'teal',
        contactPrimaryFaxNo: 'hybrid Dynamic Bigender',
        contactSecondaryPhoneNo: 'system',
        contactSecondaryFaxNo: 'interestingly Optimized tremble',
        officeLocation: 'payment',
        officeFunctionality: 'SHOWROOM',
        websiteURL: 'Bicycle quas Interactions',
        buildingNo: 'charbroil Folk',
        vendorStreetName: 'Bicycle Missouri',
        zipCode: '58978',
        districtName: 'Polygender',
        additionalNo: 'Electric',
        cityName: 'Blues Vermont',
        unitNo: 'withdrawal Honda Maarten',
        country: 'INDIA',
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
        cash: 'salmon',
        credit: 'solution Electronic quantifying',
        letterOfCredit: 'male',
        others: 'payment',
      },
    }).then(({ body }) => {
      vendor = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sales-representatives+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sales-representatives').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sales-representatives/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/vendors', {
      statusCode: 200,
      body: [vendor],
    });
  });

  afterEach(() => {
    if (salesRepresentative) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sales-representatives/${salesRepresentative.id}`,
      }).then(() => {
        salesRepresentative = undefined;
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

  it('SalesRepresentatives menu should load SalesRepresentatives page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('sales-representative');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SalesRepresentative').should('exist');
    cy.url().should('match', salesRepresentativePageUrlPattern);
  });

  describe('SalesRepresentative page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(salesRepresentativePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SalesRepresentative page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/sales-representative/new$'));
        cy.getEntityCreateUpdateHeading('SalesRepresentative');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', salesRepresentativePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/sales-representatives',
          body: {
            ...salesRepresentativeSample,
            vendorsName: vendor,
          },
        }).then(({ body }) => {
          salesRepresentative = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sales-representatives+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/sales-representatives?page=0&size=20>; rel="last",<http://localhost/api/sales-representatives?page=0&size=20>; rel="first"',
              },
              body: [salesRepresentative],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(salesRepresentativePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SalesRepresentative page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('salesRepresentative');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', salesRepresentativePageUrlPattern);
      });

      it('edit button click should load edit SalesRepresentative page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SalesRepresentative');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', salesRepresentativePageUrlPattern);
      });

      it('edit button click should load edit SalesRepresentative page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SalesRepresentative');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', salesRepresentativePageUrlPattern);
      });

      it('last delete button click should delete instance of SalesRepresentative', () => {
        cy.intercept('GET', '/api/sales-representatives/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('salesRepresentative').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', salesRepresentativePageUrlPattern);

        salesRepresentative = undefined;
      });
    });
  });

  describe('new SalesRepresentative page', () => {
    beforeEach(() => {
      cy.visit(`${salesRepresentativePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SalesRepresentative');
    });

    it('should create an instance of SalesRepresentative', () => {
      cy.get(`[data-cy="fullName"]`).type('Distributed incentivize harm');
      cy.get(`[data-cy="fullName"]`).should('have.value', 'Distributed incentivize harm');

      cy.get(`[data-cy="jobTitle"]`).type('Global Program Technician');
      cy.get(`[data-cy="jobTitle"]`).should('have.value', 'Global Program Technician');

      cy.get(`[data-cy="email"]`).type('m@sqx.w&lt;1lb~');
      cy.get(`[data-cy="email"]`).should('have.value', 'm@sqx.w&lt;1lb~');

      cy.get(`[data-cy="phone"]`).type('905.645.3975 x8738');
      cy.get(`[data-cy="phone"]`).should('have.value', '905.645.3975 x8738');

      cy.get(`[data-cy="officeLocation"]`).type('white Facilitator software');
      cy.get(`[data-cy="officeLocation"]`).should('have.value', 'white Facilitator software');

      cy.get(`[data-cy="addressLine1"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="addressLine1"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="otherDetails"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="otherDetails"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="vendorsName"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        salesRepresentative = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', salesRepresentativePageUrlPattern);
    });
  });
});
