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

describe('Client e2e test', () => {
  const clientPageUrl = '/client';
  const clientPageUrlPattern = new RegExp('/client(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const clientSample = {};

  let client;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/clients+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/clients').as('postEntityRequest');
    cy.intercept('DELETE', '/api/clients/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (client) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/clients/${client.id}`,
      }).then(() => {
        client = undefined;
      });
    }
  });

  it('Clients menu should load Clients page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('client');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Client').should('exist');
    cy.url().should('match', clientPageUrlPattern);
  });

  describe('Client page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(clientPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Client page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/client/new$'));
        cy.getEntityCreateUpdateHeading('Client');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/clients',
          body: clientSample,
        }).then(({ body }) => {
          client = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/clients+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/clients?page=0&size=20>; rel="last",<http://localhost/api/clients?page=0&size=20>; rel="first"',
              },
              body: [client],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(clientPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Client page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('client');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientPageUrlPattern);
      });

      it('edit button click should load edit Client page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Client');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientPageUrlPattern);
      });

      it('edit button click should load edit Client page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Client');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientPageUrlPattern);
      });

      it('last delete button click should delete instance of Client', () => {
        cy.intercept('GET', '/api/clients/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('client').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientPageUrlPattern);

        client = undefined;
      });
    });
  });

  describe('new Client page', () => {
    beforeEach(() => {
      cy.visit(`${clientPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Client');
    });

    it('should create an instance of Client', () => {
      cy.get(`[data-cy="clientName"]`).type('aliquid Loan Southeast');
      cy.get(`[data-cy="clientName"]`).should('have.value', 'aliquid Loan Southeast');

      cy.setFieldImageAsBytesOfEntity('logo', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="height"]`).type('28321');
      cy.get(`[data-cy="height"]`).should('have.value', '28321');

      cy.get(`[data-cy="width"]`).type('18457');
      cy.get(`[data-cy="width"]`).should('have.value', '18457');

      cy.get(`[data-cy="taken"]`).type('2023-08-11T18:11');
      cy.get(`[data-cy="taken"]`).blur();
      cy.get(`[data-cy="taken"]`).should('have.value', '2023-08-11T18:11');

      cy.get(`[data-cy="uploaded"]`).type('2023-08-11T21:21');
      cy.get(`[data-cy="uploaded"]`).blur();
      cy.get(`[data-cy="uploaded"]`).should('have.value', '2023-08-11T21:21');

      cy.get(`[data-cy="dateOfSubmittal"]`).type('2023-08-11T10:26');
      cy.get(`[data-cy="dateOfSubmittal"]`).blur();
      cy.get(`[data-cy="dateOfSubmittal"]`).should('have.value', '2023-08-11T10:26');

      cy.get(`[data-cy="approvalStatus"]`).select('FINAL_APPROVAL_STAGE');

      cy.get(`[data-cy="registrationNumber"]`).type('Fresh payment');
      cy.get(`[data-cy="registrationNumber"]`).should('have.value', 'Fresh payment');

      cy.get(`[data-cy="dateOfRegistration"]`).type('2023-08-11T04:14');
      cy.get(`[data-cy="dateOfRegistration"]`).blur();
      cy.get(`[data-cy="dateOfRegistration"]`).should('have.value', '2023-08-11T04:14');

      cy.get(`[data-cy="dateOfExpiry"]`).type('2023-08-12T02:38');
      cy.get(`[data-cy="dateOfExpiry"]`).blur();
      cy.get(`[data-cy="dateOfExpiry"]`).should('have.value', '2023-08-12T02:38');

      cy.setFieldImageAsBytesOfEntity('approvalDocument', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        client = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', clientPageUrlPattern);
    });
  });
});
