import React, { useEffect } from 'react';
import { useNavigate } from 'react-router';
import User from '../model/User';

interface ProtectedRouteProps {
  user: User | null;
  children: React.ReactElement;
}

const ProtectedRoute = ({ user, children }: ProtectedRouteProps) => {

  const navigate = useNavigate();

  useEffect(() => {
    if (!user) {
      navigate("/login");
    }
  }, [user])

  return children;
};

export default ProtectedRoute;
