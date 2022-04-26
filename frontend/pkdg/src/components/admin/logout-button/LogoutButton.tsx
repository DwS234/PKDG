import { forwardRef } from "react";
import { connect } from "react-redux";
import { useNavigate } from "react-router";

import MenuItem from "@mui/material/MenuItem";
import ExitIcon from "@mui/icons-material/PowerSettingsNew";

import { logout } from "../../../redux/actions/auth";

interface LogoutButtonProps {
  dispatch: any;
}

const LogoutButton = forwardRef(({ dispatch }: LogoutButtonProps, ref: any) => {
  const navigate = useNavigate();

  const onLogoutHandler = () => {
    dispatch(logout());
    navigate("/");
  };

  return (
    <MenuItem onClick={onLogoutHandler} ref={ref}>
      <ExitIcon />
      Logout
    </MenuItem>
  );
});

export default connect()(LogoutButton);
