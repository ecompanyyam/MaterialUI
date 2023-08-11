import React from 'react';
import { Route } from 'react-router-dom';
import Dataset1 from './Dataset1';
import Dataset2 from './Dataset2';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoutes>
      {/* prettier-ignore */}
      <Route path="/upload/dataset1" element={<Dataset1 />} />
      <Route path="/upload/dataset2" element={<Dataset2 />} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </ErrorBoundaryRoutes>
  </div>
);
export default Routes;
