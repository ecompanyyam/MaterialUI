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

describe('Vendor e2e test', () => {
  const vendorPageUrl = '/vendor';
  const vendorPageUrlPattern = new RegExp('/vendor(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const vendorSample = { vendorNameEnglish: 'interactive', vendorId: 'male', contactEmailAddress: '6}n@:QIs.?' };

  let vendor;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/vendors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/vendors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/vendors/*').as('deleteEntityRequest');
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

  it('Vendors menu should load Vendors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('vendor');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Vendor').should('exist');
    cy.url().should('match', vendorPageUrlPattern);
  });

  describe('Vendor page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(vendorPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Vendor page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/vendor/new$'));
        cy.getEntityCreateUpdateHeading('Vendor');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/vendors',
          body: vendorSample,
        }).then(({ body }) => {
          vendor = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/vendors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/vendors?page=0&size=20>; rel="last",<http://localhost/api/vendors?page=0&size=20>; rel="first"',
              },
              body: [vendor],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(vendorPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Vendor page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('vendor');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorPageUrlPattern);
      });

      it('edit button click should load edit Vendor page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Vendor');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorPageUrlPattern);
      });

      it('edit button click should load edit Vendor page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Vendor');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorPageUrlPattern);
      });

      it('last delete button click should delete instance of Vendor', () => {
        cy.intercept('GET', '/api/vendors/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('vendor').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', vendorPageUrlPattern);

        vendor = undefined;
      });
    });
  });

  describe('new Vendor page', () => {
    beforeEach(() => {
      cy.visit(`${vendorPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Vendor');
    });

    it('should create an instance of Vendor', () => {
      cy.get(`[data-cy="vendorNameEnglish"]`).type('hack Liaison');
      cy.get(`[data-cy="vendorNameEnglish"]`).should('have.value', 'hack Liaison');

      cy.get(`[data-cy="vendorNameArabic"]`).type('Bicycle Dynamic');
      cy.get(`[data-cy="vendorNameArabic"]`).should('have.value', 'Bicycle Dynamic');

      cy.get(`[data-cy="vendorId"]`).type('Hybrid');
      cy.get(`[data-cy="vendorId"]`).should('have.value', 'Hybrid');

      cy.get(`[data-cy="vendorAccountNumber"]`).type('Southeast Chips');
      cy.get(`[data-cy="vendorAccountNumber"]`).should('have.value', 'Southeast Chips');

      cy.get(`[data-cy="vendorCRNumber"]`).type('repurpose Diesel');
      cy.get(`[data-cy="vendorCRNumber"]`).should('have.value', 'repurpose Diesel');

      cy.get(`[data-cy="vendorVATNumber"]`).type('Credit');
      cy.get(`[data-cy="vendorVATNumber"]`).should('have.value', 'Credit');

      cy.get(`[data-cy="vendorType"]`).select('LOCAL');

      cy.get(`[data-cy="vendorCategory"]`).select('MANUFACTURER');

      cy.get(`[data-cy="vendorEstablishmentDate"]`).type('female Bugatti');
      cy.get(`[data-cy="vendorEstablishmentDate"]`).should('have.value', 'female Bugatti');

      cy.setFieldImageAsBytesOfEntity('vendorLogo', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="height"]`).type('30681');
      cy.get(`[data-cy="height"]`).should('have.value', '30681');

      cy.get(`[data-cy="width"]`).type('23602');
      cy.get(`[data-cy="width"]`).should('have.value', '23602');

      cy.get(`[data-cy="taken"]`).type('2023-08-03T01:22');
      cy.get(`[data-cy="taken"]`).blur();
      cy.get(`[data-cy="taken"]`).should('have.value', '2023-08-03T01:22');

      cy.get(`[data-cy="uploaded"]`).type('2023-08-04T00:31');
      cy.get(`[data-cy="uploaded"]`).blur();
      cy.get(`[data-cy="uploaded"]`).should('have.value', '2023-08-04T00:31');

      cy.get(`[data-cy="contactFullName"]`).type('vainly');
      cy.get(`[data-cy="contactFullName"]`).should('have.value', 'vainly');

      cy.get(`[data-cy="contactEmailAddress"]`).type('9o@(M|.fIVz');
      cy.get(`[data-cy="contactEmailAddress"]`).should('have.value', '9o@(M|.fIVz');

      cy.get(`[data-cy="contactPrimaryPhoneNo"]`).type('Strategist');
      cy.get(`[data-cy="contactPrimaryPhoneNo"]`).should('have.value', 'Strategist');

      cy.get(`[data-cy="contactPrimaryFaxNo"]`).type('Minnesota Tasty');
      cy.get(`[data-cy="contactPrimaryFaxNo"]`).should('have.value', 'Minnesota Tasty');

      cy.get(`[data-cy="contactSecondaryPhoneNo"]`).type('Cambridgeshire brand');
      cy.get(`[data-cy="contactSecondaryPhoneNo"]`).should('have.value', 'Cambridgeshire brand');

      cy.get(`[data-cy="contactSecondaryFaxNo"]`).type('Product abaft');
      cy.get(`[data-cy="contactSecondaryFaxNo"]`).should('have.value', 'Product abaft');

      cy.get(`[data-cy="officeLocation"]`).type('Southeast');
      cy.get(`[data-cy="officeLocation"]`).should('have.value', 'Southeast');

      cy.get(`[data-cy="officeFunctionality"]`).select('SHOWROOM');

      cy.get(`[data-cy="websiteURL"]`).type('online');
      cy.get(`[data-cy="websiteURL"]`).should('have.value', 'online');

      cy.get(`[data-cy="buildingNo"]`).type('Southwest');
      cy.get(`[data-cy="buildingNo"]`).should('have.value', 'Southwest');

      cy.get(`[data-cy="vendorStreetName"]`).type('trans');
      cy.get(`[data-cy="vendorStreetName"]`).should('have.value', 'trans');

      cy.get(`[data-cy="zipCode"]`).type('17205');
      cy.get(`[data-cy="zipCode"]`).should('have.value', '17205');

      cy.get(`[data-cy="districtName"]`).type('Hybrid');
      cy.get(`[data-cy="districtName"]`).should('have.value', 'Hybrid');

      cy.get(`[data-cy="additionalNo"]`).type('female');
      cy.get(`[data-cy="additionalNo"]`).should('have.value', 'female');

      cy.get(`[data-cy="cityName"]`).type('Granite harness neural');
      cy.get(`[data-cy="cityName"]`).should('have.value', 'Granite harness neural');

      cy.get(`[data-cy="unitNo"]`).type('easily extensible male');
      cy.get(`[data-cy="unitNo"]`).should('have.value', 'easily extensible male');

      cy.get(`[data-cy="country"]`).select('USA');

      cy.get(`[data-cy="googleMap"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="googleMap"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="combinedAddress"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="combinedAddress"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.setFieldImageAsBytesOfEntity('cRCertificateUpload', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('vATCertificateUpload', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('nationalAddressUpload', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('companyProfileUpload', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('otherUpload', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="cash"]`).type('sky');
      cy.get(`[data-cy="cash"]`).should('have.value', 'sky');

      cy.get(`[data-cy="credit"]`).type('Gasoline mole');
      cy.get(`[data-cy="credit"]`).should('have.value', 'Gasoline mole');

      cy.get(`[data-cy="letterOfCredit"]`).type('apropos male Soft');
      cy.get(`[data-cy="letterOfCredit"]`).should('have.value', 'apropos male Soft');

      cy.get(`[data-cy="others"]`).type('Metrics payment');
      cy.get(`[data-cy="others"]`).should('have.value', 'Metrics payment');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        vendor = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', vendorPageUrlPattern);
    });
  });
});
