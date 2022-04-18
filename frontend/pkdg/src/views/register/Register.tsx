import { Field, Form, Formik, FormikHelpers } from "formik";
import { useEffect, useState } from "react";
import { connect } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../../redux/actions/auth";
import { RootState } from "../../redux/reducers";

interface RegisterProps {
	isLoggedIn: boolean;
	dispatch: any;
}

interface Values {
	username: string;
	password: string;
	email: string;
}

const Register = ({ isLoggedIn, dispatch }: RegisterProps) => {
	const [successMessage, setSuccessMessage] = useState<string | null>(null);
	const [errorMessage, setErrorMessage] = useState<string | null>(null);

	const navigate = useNavigate();

	const onRegister = (values: Values, { setSubmitting, resetForm }: FormikHelpers<Values>) => {
		dispatch(register(values.username, values.email, values.password))
			.then(() => {
				setSuccessMessage("Rejestracja udana. Możesz się już zalogować.");
				setErrorMessage(null);
				resetForm();
			})
			.catch((errorMessage: string) => {
				setSubmitting(false);
				setErrorMessage(errorMessage);
				setSuccessMessage(null);
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
						password: "",
						email: ""
					}}
					onSubmit={onRegister}
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

							{successMessage && (
								<div
									className="p-4 mb-4 text-sm text-green-700 bg-green-100 rounded-lg dark:bg-green-200 dark:text-green-800"
									role="alert"
								>
									{successMessage}
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

							<div>
								<label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">
									Email
								</label>
								<Field
									id="email"
									name="email"
									type="email"
									placeholder="Email"
									className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
								/>
							</div>

							<button
								type="submit"
								disabled={isSubmitting}
								className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
							>
								Zarejestruj się
							</button>
							<div className="text-sm font-medium text-gray-500 dark:text-gray-300">
								<span>Masz już konto? </span>
								<Link to="/login" className="text-blue-700 hover:underline dark:text-blue-500">
									Zaloguj się
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

export default connect(mapStateToProps)(Register);
