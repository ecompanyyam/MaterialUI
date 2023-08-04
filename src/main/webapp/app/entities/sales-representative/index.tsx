import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SalesRepresentative from './sales-representative';
import SalesRepresentativeDetail from './sales-representative-detail';
import SalesRepresentativeUpdate from './sales-representative-update';
import SalesRepresentativeDeleteDialog from './sales-representative-delete-dialog';

const SalesRepresentativeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SalesRepresentative />} />
    <Route path="new" element={<SalesRepresentativeUpdate />} />
    <Route path=":id">
      <Route index element={<SalesRepresentativeDetail />} />
      <Route path="edit" element={<SalesRepresentativeUpdate />} />
      <Route path="delete" element={<SalesRepresentativeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SalesRepresentativeRoutes;
