import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BankDetail from './bank-detail';
import FeedBackFromEmployee from './feed-back-from-employee';
import Product from './product';
import SalesRepresentative from './sales-representative';
import Vendor from './vendor';
import VendorAssessment from './vendor-assessment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="bank-detail/*" element={<BankDetail />} />
        <Route path="feed-back-from-employee/*" element={<FeedBackFromEmployee />} />
        <Route path="product/*" element={<Product />} />
        <Route path="sales-representative/*" element={<SalesRepresentative />} />
        <Route path="vendor/*" element={<Vendor />} />
        <Route path="vendor-assessment/*" element={<VendorAssessment />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
