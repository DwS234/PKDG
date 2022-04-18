import { connect } from "react-redux";
import { Link, Outlet, useNavigate } from "react-router-dom";
import User from "../../model/User";
import { logout } from "../../redux/actions/auth";

interface AdminLayoutProps {
	user: User | null;
	dispatch: any;
}

const AdminLayout = ({ user, dispatch }: AdminLayoutProps) => {
	const navigate = useNavigate();

	const onLogout = () => {
		dispatch(logout());
		navigate("/");
	};

	return (
		<div>
			<nav className="bg-white border-gray-200 px-2 sm:px-4 py-2.5 rounded dark:bg-gray-800">
				<div className="container flex flex-wrap justify-between items-center mx-auto">
					<a href="#" className="flex items-center">
						<span className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">PKDG</span>
					</a>
					<div className="hidden justify-between items-center w-full md:flex md:w-auto md:order-1" id="mobile-menu-3">
						<ul className="flex flex-col mt-4 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium">
							<li>
								<a
									href="#"
									className="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
									onClick={onLogout}
								>
									Wyloguj się, {user?.username}
								</a>
							</li>
						</ul>
					</div>
				</div>
			</nav>

			<Outlet />
		</div>
	);
};

export default connect()(AdminLayout);
