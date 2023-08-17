import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesAddMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/vendor/new">
        <Translate contentKey="global.menu.entitiesAdd.vendor" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product/new">
        <Translate contentKey="global.menu.entitiesAdd.product" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bank-detail/new">
        <Translate contentKey="global.menu.entitiesAdd.bankDetail" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/sales-representative/new">
        <Translate contentKey="global.menu.entitiesAdd.salesRepresentative" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/feed-back-from-employee/new">
        <Translate contentKey="global.menu.entitiesAdd.feedBackFromEmployee" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/vendor-assessment/new">
        <Translate contentKey="global.menu.entitiesAdd.vendorAssessment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client/new">
        <Translate contentKey="global.menu.entitiesAdd.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/document/new">
        <Translate contentKey="global.menu.entitiesAdd.document" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesAddMenu;
