import React from "react";
import { connect } from "react-redux";
import { Route, Routes } from "react-router";
import AuthVerify from "./components/AuthVerify";
import ProtectedRoute from "./components/ProtectedRoute";
import Layout from "./Layout";
import User from "./model/User";
import { RootState } from "./redux/reducers";
import Login from "./views/Login";
import Register from "./views/Register";

interface AppProps {
  user: User | null;
}


function App({ user }: AppProps) {
  return (
    <>
      <Routes>
        <Route path="/" element={
          <ProtectedRoute user={user}>
            <Layout user={user} />
          </ProtectedRoute>
        }>
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
  }
}

export default connect(mapStateToProps)(App);
