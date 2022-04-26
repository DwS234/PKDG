import { Admin as ReactAdmin, Resource } from "react-admin";

import WordList from "../../components/admin/word-list/WordList";
import WithAuthDataProvider from "../../components/admin/with-auth-data-provider/WithAuthDataProvider";
import CustomLayout from "../../components/admin/custom-layout/CustomLayout";

const Admin = () => {
  return (
    <ReactAdmin basename="/admin" dataProvider={WithAuthDataProvider} layout={CustomLayout}>
      <Resource name="words" list={WordList} />
    </ReactAdmin>
  );
};

export default Admin;
