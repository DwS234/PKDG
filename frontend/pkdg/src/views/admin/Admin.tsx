import AdminLayout from "../../components/admin-layout/AdminLayout";
import User from "../../model/User";

interface AdminProps {
	user: User | null;
}

const Admin = ({ user }: AdminProps) => {
	return (
		<>
			<AdminLayout user={user} />
		</>
	);
};

export default Admin;
