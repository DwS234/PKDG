import User, { UserRole } from "../../model/User";

interface RequireRoleProps {
	user: User | null;
	role: UserRole;
	children: React.ReactElement;
}

const RequireRole = ({ user, role, children }: RequireRoleProps) => {
	return user?.role === role ? children : null;
};

export default RequireRole;
