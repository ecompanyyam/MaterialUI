import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import VendorAssessment from './vendor-assessment';
import VendorAssessmentDetail from './vendor-assessment-detail';
import VendorAssessmentUpdate from './vendor-assessment-update';
import VendorAssessmentDeleteDialog from './vendor-assessment-delete-dialog';

const VendorAssessmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<VendorAssessment />} />
    <Route path="new" element={<VendorAssessmentUpdate />} />
    <Route path=":id">
      <Route index element={<VendorAssessmentDetail />} />
      <Route path="edit" element={<VendorAssessmentUpdate />} />
      <Route path="delete" element={<VendorAssessmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VendorAssessmentRoutes;
