import React, { useEffect } from 'react';
import Drawer from '@mui/material/Drawer';

import { useTheme } from '@mui/material/styles';
import { useMediaQuery } from '@mui/material';
import { Nav } from 'reactstrap';
import { Dashboard, Home } from 'app/shared/layout/header/header-components';
import { AccountMenu, AdminMenu, EntitiesAddMenu, EntitiesMenu, LocaleMenu, UploadMenu } from 'app/shared/layout/menus';
import { Storage, Translate } from 'react-jhipster';
import { setLocale } from 'app/shared/reducers/locale';
import { isRTL } from 'app/config/translation';
import { useAppDispatch } from 'app/config/store';

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

  return (
    <Drawer anchor="left" open={props.open} onClose={props.onClose}>
      <Nav id="header-tabs" className="ms-auto" navbar>
        <Dashboard />
        {props.isAuthenticated && <EntitiesAddMenu />}
        {props.isAuthenticated && <EntitiesMenu />}
        {props.isAuthenticated && <UploadMenu />}
      </Nav>
    </Drawer>
  );
};

export default Sidebar;
