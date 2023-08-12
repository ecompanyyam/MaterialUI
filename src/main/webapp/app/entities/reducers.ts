import bankDetail from 'app/entities/bank-detail/bank-detail.reducer';
import feedBackFromEmployee from 'app/entities/feed-back-from-employee/feed-back-from-employee.reducer';
import product from 'app/entities/product/product.reducer';
import salesRepresentative from 'app/entities/sales-representative/sales-representative.reducer';
import vendor from 'app/entities/vendor/vendor.reducer';
import vendorAssessment from 'app/entities/vendor-assessment/vendor-assessment.reducer';
import client from 'app/entities/client/client.reducer';
import document from 'app/entities/document/document.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  bankDetail,
  feedBackFromEmployee,
  product,
  salesRepresentative,
  vendor,
  vendorAssessment,
  client,
  document,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
