import { Admin as ReactAdmin, Resource } from "react-admin";
import { connect } from "react-redux";
import { RootState } from "../../redux/reducers";

import WordList from "../../components/admin/word-list/WordList";
import User from "../../model/User";
import WithAuthDataProvider from "../../components/admin/with-auth-data-provider/WithAuthDataProvider";

interface AdminProps {
	user: User | null;
}

const Admin = ({ user }: AdminProps) => {
	return (
		<>
			<ReactAdmin basename="/admin" dataProvider={WithAuthDataProvider}>
				<Resource name="words" list={WordList} />
			</ReactAdmin>
		</>
	);
};

const mapStateToProps = (state: RootState) => {
	return {
		user: state.auth.user
	};
};

export default connect(mapStateToProps)(Admin);
