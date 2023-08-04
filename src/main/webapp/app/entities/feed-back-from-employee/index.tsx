import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FeedBackFromEmployee from './feed-back-from-employee';
import FeedBackFromEmployeeDetail from './feed-back-from-employee-detail';
import FeedBackFromEmployeeUpdate from './feed-back-from-employee-update';
import FeedBackFromEmployeeDeleteDialog from './feed-back-from-employee-delete-dialog';

const FeedBackFromEmployeeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FeedBackFromEmployee />} />
    <Route path="new" element={<FeedBackFromEmployeeUpdate />} />
    <Route path=":id">
      <Route index element={<FeedBackFromEmployeeDetail />} />
      <Route path="edit" element={<FeedBackFromEmployeeUpdate />} />
      <Route path="delete" element={<FeedBackFromEmployeeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FeedBackFromEmployeeRoutes;
