import React, { useEffect } from "react";
import { useNavigate } from "react-router";
import User, { UserRole } from "../../model/User";

interface ProtectedRouteProps {
	user: User | null;
	children: React.ReactElement;
}

const ProtectedRoute = ({ user, children }: ProtectedRouteProps) => {
	const navigate = useNavigate();
	
	useEffect(() => {
		if (!user) {
			navigate("/login");
		} else if (user.role === UserRole.ADMIN) {
			navigate("/admin");
		}
	}, [user]);

	return children;
};

export default ProtectedRoute;
