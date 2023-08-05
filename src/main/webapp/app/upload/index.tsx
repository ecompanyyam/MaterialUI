import React from 'react';
import { Route } from 'react-router-dom';
import PrivateRoute from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import Loadable from 'react-loadable';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

const loading = <div>loading ...</div>;
const Dataset1 = Loadable({
  loader: () => import(/* webpackChunkName: "Dataset1" */ './Dataset1'),
  loading: () => loading,
});
const Dataset2 = Loadable({
  loader: () => import(/* webpackChunkName: "Dataset1" */ './Dataset2'),
  loading: () => loading,
});

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoutes>
      <Route>
        {/* eslint-disable-next-line react/no-children-prop */}
        <PrivateRoute path="/upload/dataset1" children={Dataset1} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
        {/* eslint-disable-next-line react/no-children-prop */}
        <PrivateRoute path="/upload/dataset2" children={Dataset2} hasAnyAuthorities={[AUTHORITIES.USER]} />
      </Route>
    </ErrorBoundaryRoutes>
  </div>
);
const routes = Routes;
const routes1 = routes;
export default routes1;
