import { useEffect } from "react";
import { connect } from "react-redux";
import { useLocation } from "react-router";
import { logout } from "../../redux/actions/auth";
import AuthService from "../../services/auth/AuthService";
import { parseJwt } from "../../utils/JwtHandler";

interface AuthVerifyProps {
	dispatch: any;
}

const AuthVerify = ({ dispatch }: AuthVerifyProps) => {
	let location = useLocation();

	useEffect(() => {
		const user = AuthService.getLocalUser();
		if (user) {
			const decodedJwt = parseJwt(user.token);
			if (decodedJwt.exp * 1000 < Date.now()) {
				dispatch(logout());
			}
		}
	}, [location]);

	return <></>;
};

export default connect()(AuthVerify);
