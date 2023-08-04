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

describe('FeedBackFromEmployee e2e test', () => {
  const feedBackFromEmployeePageUrl = '/feed-back-from-employee';
  const feedBackFromEmployeePageUrlPattern = new RegExp('/feed-back-from-employee(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const feedBackFromEmployeeSample = {};

  let feedBackFromEmployee;
  // let vendor;
  // let product;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/vendors',
      body: {"vendorNameEnglish":"Trial Bicycle","vendorNameArabic":"invoice","vendorId":"Ball","vendorAccountNumber":"Intranet Trans Integrated","vendorCRNumber":"primary Regional man","vendorVATNumber":"Mouse","vendorType":"FOREIGN","vendorCategory":"SUPPLIER","vendorEstablishmentDate":"quae","vendorLogo":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","vendorLogoContentType":"unknown","height":6237,"width":16980,"taken":"2023-08-03T13:24:22.165Z","uploaded":"2023-08-03T16:18:11.229Z","contactFullName":"Iridium Technician","contactEmailAddress":"Jskj@u:GJo'.p<C","contactPrimaryPhoneNo":"hertz","contactPrimaryFaxNo":"Account male vastly","contactSecondaryPhoneNo":"Herminio Rap Clothing","contactSecondaryFaxNo":"Pants kilogram","officeLocation":"streamline","officeFunctionality":"SHOWROOM","websiteURL":"product forenenst","buildingNo":"Avon","vendorStreetName":"Designer","zipCode":"33501-5735","districtName":"East","additionalNo":"Factors brightly communities","cityName":"Kansas","unitNo":"withdrawal Cambridgeshire","country":"INDIA","googleMap":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=","combinedAddress":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=","cRCertificateUpload":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","cRCertificateUploadContentType":"unknown","vATCertificateUpload":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","vATCertificateUploadContentType":"unknown","nationalAddressUpload":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","nationalAddressUploadContentType":"unknown","companyProfileUpload":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","companyProfileUploadContentType":"unknown","otherUpload":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","otherUploadContentType":"unknown","cash":"South Technetium","credit":"down green","letterOfCredit":"ecstatic","others":"East"},
    }).then(({ body }) => {
      vendor = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/products',
      body: {"productName":"Music furthermore male","productRemark":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=","productOrigin":"INDIA","productStockStatus":"PARTIALLY_IN_STOCK","productAvgDeliveryTime":"WEEKLY","productManufacturer":"Slovakia indigo mint","productImage":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","productImageContentType":"unknown","height":2933,"width":1777,"taken":"2023-08-03T06:33:43.767Z","uploaded":"2023-08-03T02:48:33.667Z","productAttachments":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","productAttachmentsContentType":"unknown"},
    }).then(({ body }) => {
      product = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/feed-back-from-employees+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/feed-back-from-employees').as('postEntityRequest');
    cy.intercept('DELETE', '/api/feed-back-from-employees/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/vendors', {
      statusCode: 200,
      body: [vendor],
    });

    cy.intercept('GET', '/api/products', {
      statusCode: 200,
      body: [product],
    });

  });
   */

  afterEach(() => {
    if (feedBackFromEmployee) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/feed-back-from-employees/${feedBackFromEmployee.id}`,
      }).then(() => {
        feedBackFromEmployee = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (vendor) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/vendors/${vendor.id}`,
      }).then(() => {
        vendor = undefined;
      });
    }
    if (product) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/products/${product.id}`,
      }).then(() => {
        product = undefined;
      });
    }
  });
   */

  it('FeedBackFromEmployees menu should load FeedBackFromEmployees page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('feed-back-from-employee');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FeedBackFromEmployee').should('exist');
    cy.url().should('match', feedBackFromEmployeePageUrlPattern);
  });

  describe('FeedBackFromEmployee page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(feedBackFromEmployeePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FeedBackFromEmployee page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/feed-back-from-employee/new$'));
        cy.getEntityCreateUpdateHeading('FeedBackFromEmployee');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', feedBackFromEmployeePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/feed-back-from-employees',
          body: {
            ...feedBackFromEmployeeSample,
            vendorsName: vendor,
            productName: product,
          },
        }).then(({ body }) => {
          feedBackFromEmployee = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/feed-back-from-employees+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/feed-back-from-employees?page=0&size=20>; rel="last",<http://localhost/api/feed-back-from-employees?page=0&size=20>; rel="first"',
              },
              body: [feedBackFromEmployee],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(feedBackFromEmployeePageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(feedBackFromEmployeePageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details FeedBackFromEmployee page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('feedBackFromEmployee');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', feedBackFromEmployeePageUrlPattern);
      });

      it('edit button click should load edit FeedBackFromEmployee page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FeedBackFromEmployee');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', feedBackFromEmployeePageUrlPattern);
      });

      it('edit button click should load edit FeedBackFromEmployee page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FeedBackFromEmployee');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', feedBackFromEmployeePageUrlPattern);
      });

      it.skip('last delete button click should delete instance of FeedBackFromEmployee', () => {
        cy.intercept('GET', '/api/feed-back-from-employees/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('feedBackFromEmployee').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', feedBackFromEmployeePageUrlPattern);

        feedBackFromEmployee = undefined;
      });
    });
  });

  describe('new FeedBackFromEmployee page', () => {
    beforeEach(() => {
      cy.visit(`${feedBackFromEmployeePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FeedBackFromEmployee');
    });

    it.skip('should create an instance of FeedBackFromEmployee', () => {
      cy.get(`[data-cy="refContractPONumber"]`).type('blue');
      cy.get(`[data-cy="refContractPONumber"]`).should('have.value', 'blue');

      cy.get(`[data-cy="feedBackCategory"]`).select('ADVICE');

      cy.get(`[data-cy="comment"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="comment"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="vendorsName"]`).select(1);
      cy.get(`[data-cy="productName"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        feedBackFromEmployee = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', feedBackFromEmployeePageUrlPattern);
    });
  });
});
