import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';
export const UploadMenu = props => (
  <NavDropdown icon="th-list" name="Upload" id="upload-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/upload/dataset1">
      Dataset1
    </MenuItem>
    <MenuItem icon="asterisk" to="/upload/dataset2">
      Dataset2
    </MenuItem>
  </NavDropdown>
);
