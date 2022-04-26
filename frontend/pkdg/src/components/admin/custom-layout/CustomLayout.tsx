import { AppBar, Layout, UserMenu } from "react-admin";

import LogoutButton from "../logout-button/LogoutButton";

const CustomUserMenu = () => {
  return (
    <UserMenu>
      <LogoutButton />
    </UserMenu>
  );
};

const CustomAppBar = () => {
  return <AppBar userMenu={<CustomUserMenu />} />;
};

const CustomLayout = (props: any) => {
  return <Layout {...props} appBar={CustomAppBar} />;
};

export default CustomLayout;
