import { connect } from "react-redux";
import { Route, Routes } from "react-router";
import AuthVerify from "./components/auth-verify/AuthVerify";
import ProtectedRoute from "./components/protected-route/ProtectedRoute";
import Layout from "./components/layout/Layout";
import User, { UserRole } from "./model/User";
import { RootState } from "./redux/reducers";
import Login from "./views/login/Login";
import MyWords from "./views/my-words/MyWords";
import Register from "./views/register/Register";
import AvailableWords from "./components/available-words/AvailableWords";
import DueRepetitions from "./views/due-repetitions/DueRepetitions";
import Admin from "./views/admin/Admin";
import RequireRole from "./components/require-role/RequireRole";

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
              <RequireRole user={user} role={UserRole.USER}>
                <Layout user={user} />
              </RequireRole>
            </ProtectedRoute>
          }
        >
          <Route index element={<AvailableWords user={user} />} />
          <Route path="my-words" element={<MyWords user={user} />} />
          <Route path="due-repetitions" element={<DueRepetitions />} />
        </Route>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/admin/*"
          element={
            <RequireRole user={user} role={UserRole.ADMIN}>
              <Admin />
            </RequireRole>
          }
        />
      </Routes>
      <AuthVerify />
    </>
  );
}

const mapStateToProps = (state: RootState) => {
  return {
    user: state.auth.user,
  };
};

export default connect(mapStateToProps)(App);
