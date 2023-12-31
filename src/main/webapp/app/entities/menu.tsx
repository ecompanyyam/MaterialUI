import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/vendor">
        <Translate contentKey="global.menu.entities.vendor" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        <Translate contentKey="global.menu.entities.product" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bank-detail">
        <Translate contentKey="global.menu.entities.bankDetail" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/sales-representative">
        <Translate contentKey="global.menu.entities.salesRepresentative" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/feed-back-from-employee">
        <Translate contentKey="global.menu.entities.feedBackFromEmployee" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/vendor-assessment">
        <Translate contentKey="global.menu.entities.vendorAssessment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client">
        <Translate contentKey="global.menu.entities.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/document">
        <Translate contentKey="global.menu.entities.document" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
