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

describe('Product e2e test', () => {
  const productPageUrl = '/product';
  const productPageUrlPattern = new RegExp('/product(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const productSample = {};

  let product;
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
        vendorNameEnglish: 'Expanded quos Sedan',
        vendorNameArabic: 'paradigms coulomb oof',
        vendorId: 'Cliff Ball',
        vendorAccountNumber: 'trader Falls Bacon',
        vendorCRNumber: 'consequently',
        vendorVATNumber: 'diligently readily primary',
        vendorType: 'LOCAL',
        vendorCategory: 'DISTRIBUTOR',
        vendorEstablishmentDate: 'red',
        vendorLogo: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        vendorLogoContentType: 'unknown',
        height: 32279,
        width: 14209,
        taken: '2023-08-03T06:51:17.832Z',
        uploaded: '2023-08-03T13:14:32.710Z',
        contactFullName: 'invoice',
        contactEmailAddress: 'bER@i}G.&1MY',
        contactPrimaryPhoneNo: 'East Avon Delaware',
        contactPrimaryFaxNo: 'North blue',
        contactSecondaryPhoneNo: 'pronounce angelic',
        contactSecondaryFaxNo: 'surge',
        officeLocation: 'Hop',
        officeFunctionality: 'MAIN',
        websiteURL: 'cyan Salad',
        buildingNo: 'Configurable override',
        vendorStreetName: 'Diesel',
        zipCode: '43799-9188',
        districtName: 'Argon',
        additionalNo: 'Bugatti definition Southwest',
        cityName: 'Usability copy users',
        unitNo: 'Rupiah Sports',
        country: 'SA',
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
        cash: 'whoever so system',
        credit: 'Fiat',
        letterOfCredit: 'complexity questionably engage',
        others: 'Interactions',
      },
    }).then(({ body }) => {
      vendor = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/products+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/products').as('postEntityRequest');
    cy.intercept('DELETE', '/api/products/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/vendors', {
      statusCode: 200,
      body: [vendor],
    });
  });

  afterEach(() => {
    if (product) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/products/${product.id}`,
      }).then(() => {
        product = undefined;
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

  it('Products menu should load Products page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('product');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Product').should('exist');
    cy.url().should('match', productPageUrlPattern);
  });

  describe('Product page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(productPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Product page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/product/new$'));
        cy.getEntityCreateUpdateHeading('Product');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/products',
          body: {
            ...productSample,
            vendorsName: vendor,
          },
        }).then(({ body }) => {
          product = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/products+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/products?page=0&size=20>; rel="last",<http://localhost/api/products?page=0&size=20>; rel="first"',
              },
              body: [product],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(productPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Product page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('product');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productPageUrlPattern);
      });

      it('edit button click should load edit Product page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Product');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productPageUrlPattern);
      });

      it('edit button click should load edit Product page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Product');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productPageUrlPattern);
      });

      it('last delete button click should delete instance of Product', () => {
        cy.intercept('GET', '/api/products/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('product').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productPageUrlPattern);

        product = undefined;
      });
    });
  });

  describe('new Product page', () => {
    beforeEach(() => {
      cy.visit(`${productPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Product');
    });

    it('should create an instance of Product', () => {
      cy.get(`[data-cy="productName"]`).type('Madagascar Tin');
      cy.get(`[data-cy="productName"]`).should('have.value', 'Madagascar Tin');

      cy.get(`[data-cy="productRemark"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="productRemark"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="productOrigin"]`).select('INDIA');

      cy.get(`[data-cy="productStockStatus"]`).select('PARTIALLY_IN_STOCK');

      cy.get(`[data-cy="productAvgDeliveryTime"]`).select('QUARTERLY');

      cy.get(`[data-cy="productManufacturer"]`).type('Corporate');
      cy.get(`[data-cy="productManufacturer"]`).should('have.value', 'Corporate');

      cy.setFieldImageAsBytesOfEntity('productImage', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="height"]`).type('9257');
      cy.get(`[data-cy="height"]`).should('have.value', '9257');

      cy.get(`[data-cy="width"]`).type('24988');
      cy.get(`[data-cy="width"]`).should('have.value', '24988');

      cy.get(`[data-cy="taken"]`).type('2023-08-03T15:21');
      cy.get(`[data-cy="taken"]`).blur();
      cy.get(`[data-cy="taken"]`).should('have.value', '2023-08-03T15:21');

      cy.get(`[data-cy="uploaded"]`).type('2023-08-03T10:07');
      cy.get(`[data-cy="uploaded"]`).blur();
      cy.get(`[data-cy="uploaded"]`).should('have.value', '2023-08-03T10:07');

      cy.setFieldImageAsBytesOfEntity('productAttachments', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="vendorsName"]`).select(1);

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        product = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', productPageUrlPattern);
    });
  });
});
