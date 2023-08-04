import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BankDetail from './bank-detail';
import BankDetailDetail from './bank-detail-detail';
import BankDetailUpdate from './bank-detail-update';
import BankDetailDeleteDialog from './bank-detail-delete-dialog';

const BankDetailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BankDetail />} />
    <Route path="new" element={<BankDetailUpdate />} />
    <Route path=":id">
      <Route index element={<BankDetailDetail />} />
      <Route path="edit" element={<BankDetailUpdate />} />
      <Route path="delete" element={<BankDetailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankDetailRoutes;
