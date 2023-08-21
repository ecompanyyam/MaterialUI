import React, { useEffect } from 'react';
import Drawer from '@mui/material/Drawer';

import { useTheme } from '@mui/material/styles';
import { useMediaQuery } from '@mui/material';
import { Nav } from 'reactstrap';
import { Brand, BrandIcon, Dashboard, Home } from 'app/shared/layout/header/header-components';
import { AccountMenu, AdminMenu, EntitiesAddMenu, EntitiesMenu, LocaleMenu, UploadMenu } from 'app/shared/layout/menus';
import { Storage, Translate } from 'react-jhipster';
import { setLocale } from 'app/shared/reducers/locale';
import { isRTL } from 'app/config/translation';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import CloseIcon from '@mui/icons-material/Close';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import EntitiesMenuItems from 'app/entities/menu';
interface SidebarProps {
  open: boolean;
  onClose: () => void;
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
  currentLocale: string;
}

const Sidebar: React.FC<SidebarProps> = props => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  useEffect(() => {
    document.querySelector('html').setAttribute('dir', isRTL(Storage.session.get('locale')) ? 'rtl' : 'ltr');
  }, []);

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  const dispatch = useAppDispatch();

  const handleLocaleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    dispatch(setLocale(langKey));
    document.querySelector('html').setAttribute('dir', isRTL(langKey) ? 'rtl' : 'ltr');
  };
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Drawer anchor="left" open={props.open} onClose={props.onClose}>
      <Nav className={`sidebar ${props.open ? 'open' : ''}`}>
        <div className="close-button" onClick={props.onClose}>
          <CloseIcon />
        </div>
        <Brand />
        <div className="sidebar-content">
          <div className="menu-container">
            {props.isAuthenticated && (
              <List>
                <Dashboard />
                <EntitiesMenu />
              </List>
            )}
            <div className="user-bg">
              <FontAwesomeIcon icon="user" />
              <Translate contentKey="global.menu.message" interpolate={{ username: account.login }}>
                Hi {account.login}.
              </Translate>
            </div>
          </div>
        </div>
      </Nav>
    </Drawer>
  );
};

export default Sidebar;
