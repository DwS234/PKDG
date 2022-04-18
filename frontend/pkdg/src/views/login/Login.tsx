import { Field, Form, Formik, FormikHelpers } from "formik";
import { useEffect, useState } from "react";
import { connect } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../../redux/actions/auth";
import { RootState } from "../../redux/reducers";

interface LoginProps {
	isLoggedIn: boolean;
	dispatch: any;
}

interface Values {
	username: string;
	password: string;
}

const Login = ({ isLoggedIn, dispatch }: LoginProps) => {
	const [errorMessage, setErrorMessage] = useState<string | null>(null);

	const navigate = useNavigate();

	const onLogin = (values: Values, { setSubmitting }: FormikHelpers<Values>) => {
		dispatch(login(values.username, values.password))
			.then(() => {
				navigate("/");
			})
			.catch((errorMessage: string) => {
				setSubmitting(false);
				setErrorMessage(errorMessage);
			});
	};

	useEffect(() => {
		if (isLoggedIn) {
			navigate("/");
		}
	}, [isLoggedIn]);

	return (
		<div className="flex justify-center mt-20">
			<div className="p-4 max-w-sm bg-white rounded-lg border border-gray-200 shadow-md sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700">
				<Formik
					initialValues={{
						username: "",
						password: ""
					}}
					onSubmit={onLogin}
				>
					{({ isSubmitting }) => (
						<Form className="space-y-6">
							<h5 className="text-xl font-medium text-gray-900 dark:text-white">PKDG</h5>

							{errorMessage && (
								<div
									className="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg dark:bg-red-200 dark:text-red-800"
									role="alert"
								>
									{errorMessage}
								</div>
							)}

							<div>
								<label htmlFor="username" className="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">
									Nazwa użytkownika
								</label>
								<Field
									id="username"
									name="username"
									placeholder="Nazwa użytkownika"
									className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
								/>
							</div>

							<div>
								<label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">
									Hasło
								</label>
								<Field
									id="password"
									name="password"
									type="password"
									placeholder="Hasło"
									className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
								/>
							</div>

							<button
								type="submit"
								disabled={isSubmitting}
								className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
							>
								Zaloguj się
							</button>
							<div className="text-sm font-medium text-gray-500 dark:text-gray-300">
								<span>Nie masz konta? </span>
								<Link to="/register" className="text-blue-700 hover:underline dark:text-blue-500">
									Załóż konto
								</Link>
							</div>
						</Form>
					)}
				</Formik>
			</div>
		</div>
	);
};

const mapStateToProps = (state: RootState) => {
	const { isLoggedIn } = state.auth;
	return {
		isLoggedIn
	};
};

export default connect(mapStateToProps)(Login);
