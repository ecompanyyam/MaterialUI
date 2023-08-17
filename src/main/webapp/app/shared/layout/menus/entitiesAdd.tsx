import React from 'react';
import { translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import EntitiesAddMenuItems from 'app/entities/addmenu';

export const EntitiesAddMenu = () => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entitiesAdd.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <EntitiesAddMenuItems />
  </NavDropdown>
);
