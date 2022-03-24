import { connect } from "react-redux";
import { Route, Routes } from "react-router";
import AuthVerify from "./components/AuthVerify";
import ProtectedRoute from "./components/ProtectedRoute";
import Layout from "./Layout";
import User from "./model/User";
import { RootState } from "./redux/reducers";
import Login from "./views/Login";
import MyWords from "./views/MyWords";
import Register from "./views/Register";
import AvailableWordsToRepeat from "./components/AvailableWordsToRepeat";

interface AppProps {
	user: User | null;
}

function App({ user }: AppProps) {
	return (
		<>
			<Routes>
				<Route
					path="/"
					element={
						<ProtectedRoute user={user}>
							<Layout user={user} />
						</ProtectedRoute>
					}
				>
					<Route index element={<AvailableWordsToRepeat user={user} />} />
					<Route path="my-words" element={<MyWords user={user} />} />
				</Route>
				<Route path="/login" element={<Login />} />
				<Route path="/register" element={<Register />} />
			</Routes>
			<AuthVerify />
		</>
	);
}

const mapStateToProps = (state: RootState) => {
	return {
		user: state.auth.user
	};
};

export default connect(mapStateToProps)(App);
